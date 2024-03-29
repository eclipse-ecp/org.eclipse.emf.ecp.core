/**
 * Copyright (c) 2002-2010 IBM Corporation and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * IBM - Initial API and implementation
 */
// REUSED CLASS
package org.eclipse.emf.edit.ui.provider;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.common.CommonPlugin;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.edit.EMFEditPlugin;
import org.eclipse.emf.edit.provider.ComposedImage;
import org.eclipse.jface.resource.CompositeImageDescriptor;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Display;

/**
 *
 */
public class ExtendedImageRegistry {
	public static final ExtendedImageRegistry INSTANCE = new ExtendedImageRegistry() {
		@Override
		public Image getImage(Object object) {
			return getInstance().getImage(object);
		}

		@Override
		public ImageDescriptor getImageDescriptor(Object object) {
			return getInstance().getImageDescriptor(object);
		}
	};

	private static final Method INSTANCE_METHOD;

	static {
		Method instanceMethod = null;
		try {
			final Class<?> singletonUtilityClass = CommonPlugin.loadClass("org.eclipse.rap.rwt",
				"org.eclipse.rap.rwt.SingletonUtil");
			instanceMethod = singletonUtilityClass.getMethod("getSessionInstance", Class.class);
		} catch (final Exception exception) {
			try {
				final Class<?> singletonUtilityClass = CommonPlugin.loadClass("org.eclipse.rap.rwt",
					"org.eclipse.rap.rwt.SessionSingletonBase");
				instanceMethod = singletonUtilityClass.getMethod("getInstance", Class.class);
			} catch (final Exception exception2) {
				Activator.log(exception2);
			}
		}
		INSTANCE_METHOD = instanceMethod;
	}

	@SuppressWarnings("unchecked")
	static <T> T getInstance(Class<T> _class) {
		try {
			return (T) INSTANCE_METHOD.invoke(null, _class);
		} catch (final Exception exception) {
			throw new RuntimeException(exception);
		}
	}

	public static ExtendedImageRegistry getInstance() {
		return getInstance(ExtendedImageRegistry.class);
	}

	protected HashMap<Object, Image> table = new HashMap<Object, Image>(10);

	public ExtendedImageRegistry() {
		final Display display = Display.getCurrent();
		hookDisplayDispose(display);
	}

	public ExtendedImageRegistry(Display display) {
		hookDisplayDispose(display);
	}

	protected static Object resourceURL = EMFEditPlugin.INSTANCE.getImage("full/obj16/Resource");

	protected static String resourceURLPrefix = resourceURL.toString() + "#";

	protected static String itemURLPrefix = EMFEditPlugin.INSTANCE.getImage("full/obj16/Item").toString() + "#";

	protected static String createChildURLPrefix = EMFEditPlugin.INSTANCE.getImage("full/ctool16/CreateChild")
		.toString() + "#";

	public Image getImage(Object object) {
		if (object instanceof Image) {
			return (Image) object;
		} else {
			Image result = table.get(object);
			if (result == null) {
				if (object instanceof ImageDescriptor) {
					final ImageDescriptor imageDescriptor = (ImageDescriptor) object;
					result = imageDescriptor.createImage();
				} else if (object instanceof URL || object instanceof URI) {
					final String urlString = object.toString();
					ImageDescriptor imageDescriptor = null;
					if (urlString.startsWith(resourceURLPrefix)) {
						result = getImage(resourceURL);
					} else if (urlString.startsWith(itemURLPrefix)) {
						try {
							final URL url = new URL(urlString.substring(0, itemURLPrefix.length()));
							final String key1 = urlString.substring(itemURLPrefix.length());
							imageDescriptor = new URLImageDescriptor(url, key1, null);
						} catch (final IOException exception) {
							// Ignore
						}
					} else if (urlString.startsWith(createChildURLPrefix)) {
						try {
							final URL url = new URL(urlString.substring(0, createChildURLPrefix.length()));
							String key1 = urlString.substring(createChildURLPrefix.length() + 1);
							String key2 = null;
							final int index = key1.indexOf("/");
							if (index != -1) {
								key2 = key1.substring(index + 1);
								key1 = key1.substring(0, index);
							}
							imageDescriptor = new URLImageDescriptor(url, key1, key2);
						} catch (final IOException exception) {
							// Ignore
						}
					} else {
						try {
							imageDescriptor = ImageDescriptor.createFromURL(new URL(urlString));
						} catch (final IOException exception) {
							// Ignore
						}
					}
					if (imageDescriptor != null) {
						result = imageDescriptor.createImage();
					}
				} else if (object instanceof ComposedImage) {
					final ImageDescriptor composedImageDescriptor = new ComposedImageDescriptor((ComposedImage) object);
					result = composedImageDescriptor.createImage();
				}

				if (result != null) {
					table.put(object, result);
				}

			}
			return result;
		}
	}

	public ImageDescriptor getImageDescriptor(Object object) {
		if (object instanceof ImageDescriptor) {
			return (ImageDescriptor) object;
		} else {
			final Image image = getImage(object);
			if (image != null) {
				return new ImageWrapperImageDescriptor(image);
			} else {
				return null;
			}
		}
	}

	protected void handleDisplayDispose() {
		// TODO https://bugs.eclipse.org/bugs/show_bug.cgi?id=314239
		// for (Image image : table.values())
		// {
		// image.dispose();
		// }
		table = null;
	}

	protected void hookDisplayDispose(Display display) {
		display.disposeExec(new Runnable() {
			@Override
			public void run() {
				handleDisplayDispose();
			}
		});
	}
}

class ImageWrapperImageDescriptor extends ImageDescriptor {
	/**
	*
	*/
	private static final long serialVersionUID = 1L;
	protected Image image;

	public ImageWrapperImageDescriptor(Image image) {
		this.image = image;
	}

	@Override
	public boolean equals(Object that) {
		return that instanceof ImageWrapperImageDescriptor &&
			((ImageWrapperImageDescriptor) that).image.equals(image);
	}

	@Override
	public int hashCode() {
		return image.hashCode();
	}

	@Override
	public ImageData getImageData() {
		return image.getImageData();
	}
}

class ComposedImageDescriptor extends CompositeImageDescriptor {
	/**
	*
	*/
	private static final long serialVersionUID = 1L;
	protected ComposedImage composedImage;
	protected List<ImageData> imageDatas;

	public ComposedImageDescriptor(ComposedImage composedImage) {
		this.composedImage = composedImage;
	}

	@Override
	public void drawCompositeImage(int width, int height) {
		final ComposedImage.Size size = new ComposedImage.Size();
		size.width = width;
		size.height = height;
		final Iterator<ImageData> images = imageDatas.iterator();
		for (final Iterator<ComposedImage.Point> points = composedImage.getDrawPoints(size).iterator(); points
			.hasNext();) {
			final ComposedImage.Point point = points.next();
			drawImage(images.next(), point.x, point.y);
		}
	}

	@Override
	public Point getSize() {
		final List<Object> images = composedImage.getImages();
		imageDatas = new ArrayList<ImageData>(images.size());
		final List<ComposedImage.Size> sizes = new ArrayList<ComposedImage.Size>(images.size());
		for (final Object object : images) {
			final Image image = ExtendedImageRegistry.getInstance().getImage(object);
			final ImageData imageData = image.getImageData();
			imageDatas.add(imageData);

			final ComposedImage.Size size = new ComposedImage.Size();
			size.width = imageData.width;
			size.height = imageData.height;
			sizes.add(size);
		}

		final ComposedImage.Size result = composedImage.getSize(sizes);
		return new Point(result.width, result.height);
	}
}

class URLImageDescriptor extends ImageDescriptor {
	/**
	*
	*/
	private static final long serialVersionUID = 1L;
	protected URL url;
	protected String key1;
	protected String key2;

	URLImageDescriptor(URL url, String key1, String key2) {
		this.url = url;
		this.key1 = key1;
		this.key2 = key2;
	}

	@Override
	public ImageData getImageData() {
		InputStream in = null;
		try {
			if (key1 != null) {
				final ImageDataSynthesizer imageDataSynthesizer = new ImageDataSynthesizer(url);
				in = imageDataSynthesizer.generateGIF(key1, key2);
				return new ImageData(in);
			} else {
				in = url.openStream();
			}

			return new ImageData(in);
		} catch (final IOException e) {
			return null;
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (final IOException e) {
					return null;
				}
			}
		}
	}

	protected InputStream getStream() throws IOException {
		return url.openStream();
	}

	@Override
	public int hashCode() {
		return url.hashCode() | (key1 == null ? 0 : key1.hashCode()) | (key2 == null ? 0 : key2.hashCode());
	}

	@Override
	public boolean equals(Object that) {
		if (that instanceof URLImageDescriptor) {
			final URLImageDescriptor otherURLImageDescriptor = (URLImageDescriptor) that;
			return url.equals(otherURLImageDescriptor.url) &&
				(key1 == null ? otherURLImageDescriptor.key1 == null : key1.equals(otherURLImageDescriptor.key1)) &&
				(key2 == null ? otherURLImageDescriptor.key2 == null : key2.equals(otherURLImageDescriptor.key2));
		} else {
			return false;
		}
	}

	@Override
	public String toString() {
		return getClass().getName() + "(" + url + "#" + key1 + "/" + key2 + ")";
	}
}

class ImageDataSynthesizer {
	protected URL url;

	protected static final int tableOffset1 = 49;
	protected static final int tableOffset2 = 25;

	public ImageDataSynthesizer(URL url) {
		this.url = url;
	}

	protected int code(String code) {
		int result = 0;
		for (int i = 0; i < code.length(); ++i) {
			result += code.charAt(i) - 32;
		}
		return result;
	}

	public InputStream generateGIF(String key1, String key2) {
		final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		try {
			final byte[] content = new byte[5000];
			final int result = getContents(content, url);

			// generateColor();
			final ColorInformation info1 = ColorInformation.getColor(code(key1));
			final ColorInformation info2 = key2 == null ? null : ColorInformation.getColor(code(key2));

			for (int j = 0; j < result; ++j) {
				if (j == tableOffset1 || j == tableOffset1 + 3 || j == tableOffset1 + 6 || j == tableOffset1 + 9) {
					final int index = (j - tableOffset1) / 3;
					if (!info1.rainbow || info1.which == index - 1) {
						content[j] = info1.scale(info1.red, info1.factor[index]);
					}
				} else if (j == tableOffset1 + 1 || j == tableOffset1 + 4 || j == tableOffset1 + 7
					|| j == tableOffset1 + 10) {
					final int index = (j - tableOffset1 - 1) / 3;
					if (!info1.rainbow || info1.which == index - 1) {
						content[j] = info1.scale(info1.green, info1.factor[index]);
					}
				} else if (j == tableOffset1 + 2 || j == tableOffset1 + 5 || j == tableOffset1 + 8
					|| j == tableOffset1 + 11) {
					final int index = (j - tableOffset1 - 2) / 3;
					if (!info1.rainbow || info1.which == index - 1) {
						content[j] = info1.scale(info1.blue, info1.factor[index]);
					}
				}

				if (info2 != null) {
					if (j == tableOffset2 || j == tableOffset2 + 3 || j == tableOffset2 + 6 || j == tableOffset2 + 9) {
						final int index = (j - tableOffset2) / 3;
						if (!info2.rainbow || info2.which == index - 1) {
							content[j] = info2.scale(info2.red, info2.factor[index]);
						}
					} else if (j == tableOffset2 + 1 || j == tableOffset2 + 4 || j == tableOffset2 + 7
						|| j == tableOffset2 + 10) {
						final int index = (j - tableOffset2 - 1) / 3;
						if (!info2.rainbow || info2.which == index - 1) {
							content[j] = info2.scale(info2.green, info2.factor[index]);
						}
					} else if (j == tableOffset2 + 2 || j == tableOffset2 + 5 || j == tableOffset2 + 8
						|| j == tableOffset2 + 11) {
						final int index = (j - tableOffset2 - 2) / 3;
						if (!info2.rainbow || info2.which == index - 1) {
							content[j] = info2.scale(info2.blue, info2.factor[index]);
						}
					}
				}
			}

			final DataOutputStream writer = new DataOutputStream(outputStream);
			writer.write(content, 0, result);
			writer.close();
		} catch (final Exception exception) {
			exception.printStackTrace();
		}

		return new ByteArrayInputStream(outputStream.toByteArray());
	}

	protected int getContents(byte[] content, URL gifURL) throws IOException {
		final BufferedInputStream bufferedInputStream = new BufferedInputStream(gifURL.openStream());
		final DataInputStream reader = new DataInputStream(bufferedInputStream);
		final int result = reader.read(content, 0, content.length);
		reader.close();
		return result;
	}

	protected static class ColorInformation {
		public static ColorInformation getColor(int index) {
			index = Math.abs(index) % 61;
			while (entries.size() <= index) {
				instance.generateColor();

				final ColorInformation entry = new ColorInformation();
				entry.red = instance.red;
				entry.green = instance.green;
				entry.blue = instance.blue;
				entry.which = instance.which;
				entry.factor = new double[] { instance.factor[0], instance.factor[1], instance.factor[2],
					instance.factor[3] };
				entry.rainbow = instance.rainbow;
				entries.add(entry);
				instance.fixFactor();
			}
			return entries.get(index);
		}

		protected static ColorInformation instance = new ColorInformation();

		protected static List<ColorInformation> entries = new ArrayList<ColorInformation>(1000);

		public int red = 192;
		public int green = 64;
		public int blue = 64;

		public int which = 2;
		public int change = 64;

		public double[] factor = { 0.35, 0.1, -0.1, -0.3 };
		public boolean rainbow;

		public byte scale(int value, double factor) {
			if (factor > 0.0) {
				return (byte) (value + (255 - value) * factor);
			} else {
				return (byte) (value + value * factor);
			}
		}

		protected void generateColor() {
			switch (which) {
			case 0: {
				red += change;
				if (red <= 64) {
					which = 1;
					change = -change;
				} else if (red >= 192) {
					which = 1;
					change = -change;
				}
				break;
			}
			case 1: {
				green += change;
				if (green >= 192) {
					which = 2;
					change = -change;
				} else if (green <= 64) {
					which = 2;
					change = -change;
				}
				break;
			}
			case 2: {
				blue += change;
				if (blue >= 192) {
					which = 0;
					change = -change;
				} else if (blue <= 64) {
					which = 0;
					change = -change;
				}
				break;
			}
			}
		}

		protected void fixFactor() {
			if (red == 192 && green == 64 && blue == 64) {
				for (int j = 0; j < factor.length; ++j) {
					factor[j] += 0.3;
				}
				if (factor[0] >= 1.0) {
					rainbow = true;
					for (int j = 0; j < factor.length; ++j) {
						factor[j] -= 0.8;
					}
				}
			}
		}
	}
}
