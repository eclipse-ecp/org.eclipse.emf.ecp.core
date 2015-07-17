package org.eclipse.emf.ecp.view.model.internal.fx;

import java.util.LinkedHashSet;
import java.util.Set;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.InvalidRegistryObjectException;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.ecp.view.model.common.ECPRendererTester;
import org.eclipse.emf.ecp.view.model.common.ECPStaticRendererTester;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.osgi.framework.Bundle;

public final class RendererFactoryImpl implements RendererFactory {

	private static final String RENDER_EXTENSION = "org.eclipse.emf.ecp.view.model.fx.renderer";
	private static final String TEST_DYNAMIC = "dynamicTest";
	private static final String TEST_STATIC = "staticTest";
	private static final String TESTER_PRIORITY = "priority";
	private static final String TESTER_VELEMENT = "element";
	private static final String RENDERER_TESTER = "testClass";

	private final Set<ECPRendererDescription> rendererDescriptors = new LinkedHashSet<ECPRendererDescription>();

	private RendererFactoryImpl() {
		readRenderer();
	}

	private void readRenderer() {
		final IExtensionPoint extensionPoint = Platform.getExtensionRegistry()
			.getExtensionPoint(RENDER_EXTENSION);
		for (final IExtension extension : extensionPoint.getExtensions()) {

			for (final IConfigurationElement configurationElement : extension
				.getConfigurationElements()) {
				try {
					final Class<RendererFX<VElement>> rendererClass = loadClass(configurationElement
						.getContributor().getName(), configurationElement
						.getAttribute("renderer"));

					// Get tester(s) for the current renderer.
					final Set<ECPRendererTester> tester = new LinkedHashSet<ECPRendererTester>();
					for (final IConfigurationElement testerExtension : configurationElement.getChildren()) {
						if (TEST_DYNAMIC.equals(testerExtension.getName())) {
							tester.add((ECPRendererTester) testerExtension.createExecutableExtension(RENDERER_TESTER));
						}
						else if (TEST_STATIC.equals(testerExtension.getName())) {

							final int priority = Integer.parseInt(testerExtension.getAttribute(TESTER_PRIORITY));

							final String vElement = testerExtension.getAttribute(TESTER_VELEMENT);
							final Class<? extends VElement> supportedEObject = loadClass(testerExtension
								.getContributor()
								.getName(), vElement);

							tester.add(new ECPStaticRendererTester(priority,
								supportedEObject));
						}
					}

					rendererDescriptors.add(new ECPRendererDescription(rendererClass, tester));
				} catch (final CoreException ex) {
					ex.printStackTrace();
				} catch (final ClassNotFoundException e) {
					e.printStackTrace();
				} catch (final InvalidRegistryObjectException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	private static <T> Class<T> loadClass(String bundleName, String clazz)
		throws ClassNotFoundException {
		final Bundle bundle = Platform.getBundle(bundleName);
		if (bundle == null) {
			throw new ClassNotFoundException(clazz + bundleName);
		}
		return (Class<T>) bundle.loadClass(clazz);

	}

	private static RendererFactoryImpl INSTANCE;

	public static RendererFactory getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new RendererFactoryImpl();
		}
		return INSTANCE;
	}

	// TODO delete
	// @Override
	// public <T extends VElement> Set<RenderingResultRow<Node>> render(
	// T renderable, final ViewModelContext viewContext) {
	// RendererFX<VElement> renderer = getRenderer(renderable, viewContext);
	// try {
	// return renderer.render(renderable, viewContext);
	// } catch(NullPointerException ex) {
	// ex.printStackTrace();
	// return Collections.<RenderingResultRow<Node>>emptySet();
	// }
	//
	// }

	/**
	 * @param vElement the vElement that should be rendered
	 * @param viewContext the view model context
	 * @return the applicable renderer for the given VElement with the highest priority
	 */
	@Override
	public RendererFX<VElement> getRenderer(VElement vElement, ViewModelContext viewContext) {
		int highestPriority = -1;
		RendererFX<VElement> bestCandidate = null;
		for (final ECPRendererDescription description : rendererDescriptors) {

			int currentPriority = -1;

			for (final ECPRendererTester tester : description.getTester()) {
				final int testerPriority = tester.isApplicable(vElement, viewContext);
				if (testerPriority > currentPriority) {
					currentPriority = testerPriority;
				}

			}

			if (currentPriority > highestPriority) {
				highestPriority = currentPriority;
				try {
					bestCandidate = description.getRenderer().newInstance();
				} catch (final InstantiationException ex) {
					ex.printStackTrace();
					return null;
				} catch (final IllegalAccessException ex) {
					ex.printStackTrace();
					return null;
				}
			}
		}

		bestCandidate.init(vElement, viewContext);
		return bestCandidate;
	}
}
