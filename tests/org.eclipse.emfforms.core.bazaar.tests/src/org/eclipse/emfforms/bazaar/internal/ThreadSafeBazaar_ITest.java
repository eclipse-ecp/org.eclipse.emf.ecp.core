/*******************************************************************************
 * Copyright (c) 2018 Christian W. Damus and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Christian W. Damus - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.bazaar.internal;

import static java.lang.Integer.parseInt;
import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.Callable;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicInteger;

import org.eclipse.emfforms.bazaar.Bazaar;
import org.eclipse.emfforms.bazaar.BazaarContext;
import org.eclipse.emfforms.bazaar.BazaarContextFunction;
import org.eclipse.emfforms.bazaar.Bid;
import org.eclipse.emfforms.bazaar.Create;
import org.eclipse.emfforms.bazaar.Exchange;
import org.eclipse.emfforms.bazaar.Vendor;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Specific concurrency tests for the {@link ThreadSafeBazaar}. Other
 * tests are covered by the {@link Bazaar_ITest} suite.
 *
 * @author Christian W. Damus
 */
@SuppressWarnings("nls")
public class ThreadSafeBazaar_ITest {

	static final int NUM_THREADS = 3;

	private static AtomicInteger testCount = new AtomicInteger();
	private final Bazaar<String> bazaar = new ThreadSafeBazaar<String>();
	private volatile CyclicBarrier barrier = new CyclicBarrier(NUM_THREADS);
	private ExecutorService exec;

	/**
	 * Initializes e
	 */
	public ThreadSafeBazaar_ITest() {
		super();
	}

	@Test
	public void concurrentCreateProduct() {
		testTemplateConcurrentRead(auctionCallable(), setOf("A", "B", "C"));
	}

	<T> void testTemplateConcurrentRead(Callable<T> auction, Set<T> expected) {
		final Set<T> actual = asyncAuction(auction, null);
		assertThat(actual, is(expected));
	}

	@SuppressWarnings("unchecked")
	@Test
	public void concurrentCreateProducts() {
		testTemplateConcurrentRead(multiAuctionCallable(),
			setOf(asList("A", "B", "C"), asList("B", "A", "C"), asList("C", "A", "B")));
	}

	@SuppressWarnings("unchecked")
	@Test
	public void concurrentAddVendor() {
		// Run an auction on three threads, that will stop at the barrier while
		// bidding is in progress. At that point, add another vendor, then sync
		// again to let the auction threads complete
		final Callable<List<String>> auction = multiAuctionCallable();
		final Runnable addVendor = new Runnable() {
			@Override
			public void run() {
				// Synchronize with the auction threads
				barrierSync();

				// Add a vendor
				bazaar.addVendor(new StringVendor("D"));

				// And let everyone finish
				barrierSync();
			}
		};

		// The new vendor did not participate
		testTemplateConcurrentWrite(auction, addVendor,
			setOf(asList("A", "B", "C"), asList("B", "A", "C"), asList("C", "A", "B")));
	}

	<T> void testTemplateConcurrentWrite(Callable<T> auction, Runnable write, Set<T> expected) {
		// Make room for one more party in synchronizing the bidding
		barrier = new CyclicBarrier(NUM_THREADS + 1);

		final Set<T> products = asyncAuction(auction, write);

		// The auction completed as expected
		assertThat(products, is(expected));
	}

	@SuppressWarnings("unchecked")
	@Test
	public void concurrentAddContextFunction() {
		// Run an auction on three threads, that will stop at the barrier while
		// bidding is in progress. At that point, add a context function, then sync
		// again to let the auction threads complete
		final Callable<List<String>> auction = multiAuctionCallable();
		final Runnable addVendor = new Runnable() {
			@Override
			public void run() {
				// Synchronize with the auction threads
				barrierSync();

				final Thread wrongThread = Thread.currentThread();

				// Add a context function that would derail the auction
				bazaar.addContextFunction(Thread.class.getName(),
					new BazaarContextFunction() {
						@Exchange
						public Thread computeThread() {
							return wrongThread;
						}
					});

				// And let everyone finish
				barrierSync();
			}
		};

		testTemplateConcurrentWrite(auction, addVendor,
			setOf(asList("A", "B", "C"), asList("B", "A", "C"), asList("C", "A", "B")));
	}

	@Test
	public void concurrentSetOverlapCallback() {
		// This vendor ties with the best of the others
		bazaar.addVendor(new SimpleStringVendor("D", 100.0));

		// Run an auction on three threads, that will stop at the barrier while
		// bidding is in progress. At that point, set an overlap call-back, then sync
		// again to let the auction threads complete
		final Callable<String> auction = auctionCallable();
		final Runnable addVendor = new Runnable() {
			@Override
			public void run() {
				// Synchronize with the auction threads
				barrierSync();

				// Set an overlap call-back that would derail the auction
				bazaar.setPriorityOverlapCallBack(new Bazaar.PriorityOverlapCallBack<String>() {
					@Override
					public void priorityOverlap(Vendor<? extends String> winner, Vendor<? extends String> overlapping) {
						fail("Overlap call-back must not be called.");
					}
				});

				// And let everyone finish
				barrierSync();
			}
		};

		testTemplateConcurrentWrite(auction, addVendor, setOf("A", "B", "C"));
	}

	//
	// Test framework
	//

	@Before
	public void createFixture() {
		exec = createExecutorService(NUM_THREADS);

		bazaar.addVendor(new StringVendor("A"));
		bazaar.addVendor(new StringVendor("B"));
		bazaar.addVendor(new StringVendor("C"));
	}

	ExecutorService createExecutorService(int threads) {
		return Executors.newFixedThreadPool(threads, new ThreadFactory() {
			private final int generation = testCount.incrementAndGet();
			private final AtomicInteger ch = new AtomicInteger('A');

			@Override
			public Thread newThread(Runnable r) {
				return new Thread(r, String.format("Bazaar-%s-%s",
					generation,
					(char) ch.getAndIncrement()));
			}
		});
	}

	@After
	public void destroyFixture() {
		exec.shutdownNow();
		exec = null;

		barrier.reset();
	}

	class SimpleStringVendor implements Vendor<String> {
		private final String product;
		private final double bid;

		SimpleStringVendor(String product, double bid) {
			super();

			this.product = product;
			this.bid = bid;
		}

		@Bid
		public double bid() {
			return bid;
		}

		@Create
		public String create() {
			return product;
		}
	}

	class StringVendor implements Vendor<String> {
		private final String product;

		StringVendor(String product) {
			super();

			this.product = product;
		}

		@Bid
		public double bid(Thread thread) throws InterruptedException, BrokenBarrierException, TimeoutException {
			final String threadKey = lastChar(thread.getName());
			if (threadKey.equals(product)) {
				// Ensure that all threads are in the midst of the auction
				barrierSync();

				return 100.0;
			}

			return Math.abs(16 - parseInt(product, 16)); // Should be non-negative
		}

		private String lastChar(String s) {
			return s.substring(s.length() - 1);
		}

		@Create
		public String create() {
			return product;
		}
	}

	<T> Set<T> asyncAuction(Callable<T> auctionCallable, Runnable duringAuction) {
		final List<Future<T>> asyncProducts = new ArrayList<Future<T>>(NUM_THREADS);
		for (int i = 0; i < NUM_THREADS; i++) {
			asyncProducts.add(exec.submit(auctionCallable));
		}

		if (duringAuction != null) {
			duringAuction.run();
		}

		return getAll(asyncProducts);
	}

	void barrierSync() {
		try {
			barrier.await(1L, TimeUnit.SECONDS);
		} catch (final InterruptedException ex) {
			fail("Test interrupted while synchronizing at barrier.");
		} catch (final BrokenBarrierException ex) {
			fail("Broken barrier in test thread synchronization.");
		} catch (final TimeoutException ex) {
			fail("Timed out in synchronization at barrier.");
		}

	}

	<T> Set<T> getAll(Iterable<? extends Future<? extends T>> futures) {
		final Set<T> result = new HashSet<T>();

		for (final Future<? extends T> next : futures) {
			try {
				result.add(next.get(1L, TimeUnit.SECONDS));
			} catch (final InterruptedException ex) {
				fail("Test interrupted while waiting for async product.");
			} catch (final ExecutionException ex) {
				ex.getCause().printStackTrace();
				fail("Async auction failed with an exception");
			} catch (final TimeoutException ex) {
				fail("Test timed out waiting for async product.");
			}
		}

		return result;
	}

	static <T> Set<T> setOf(T... elements) {
		return new HashSet<T>(Arrays.asList(elements));
	}

	Callable<String> auctionCallable() {
		return new Callable<String>() {
			@Override
			public String call() throws Exception {
				final BazaarContext ctx = BazaarContext.Builder.empty()
					.put(Thread.class, Thread.currentThread()).build();
				final String result = bazaar.createProduct(ctx);

				// Synchronize on the way out of the auction
				barrierSync();

				return result;
			}
		};
	}

	Callable<List<String>> multiAuctionCallable() {
		return new Callable<List<String>>() {
			@Override
			public List<String> call() throws Exception {
				final BazaarContext ctx = BazaarContext.Builder.empty()
					.put(Thread.class, Thread.currentThread()).build();
				final List<String> result = bazaar.createProducts(ctx);

				// Synchronize on the way out of the auction
				barrierSync();

				return result;
			}
		};
	}
}
