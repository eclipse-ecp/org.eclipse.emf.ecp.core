/*******************************************************************************
 * Copyright (c) 2019 Christian W. Damus and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Christian W. Damus - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.spi.swt.masterdetail;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecp.ui.view.ECPRendererException;
import org.eclipse.emf.ecp.ui.view.swt.ECPSWTView;
import org.eclipse.emf.ecp.ui.view.swt.ECPSWTViewRenderer;
import org.eclipse.emf.ecp.ui.view.swt.RenderFailureView;
import org.eclipse.emf.ecp.view.internal.swt.Activator;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContextDisposeListener;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContextFactory;
import org.eclipse.emf.ecp.view.spi.label.model.VLabel;
import org.eclipse.emf.ecp.view.spi.label.model.VLabelFactory;
import org.eclipse.emf.ecp.view.spi.label.model.VLabelPackage;
import org.eclipse.emf.ecp.view.spi.model.LocalizationAdapter;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emf.ecp.view.spi.model.VView;
import org.eclipse.emf.ecp.view.spi.model.VViewFactory;
import org.eclipse.emf.ecp.view.spi.model.VViewModelLoadingProperties;
import org.eclipse.emf.ecp.view.spi.model.VViewModelProperties;
import org.eclipse.emf.ecp.view.spi.model.util.ViewModelPropertiesHelper;
import org.eclipse.emf.ecp.view.spi.provider.ViewProviderHelper;
import org.eclipse.emf.ecp.view.spi.swt.layout.PageLayout;
import org.eclipse.emf.ecp.view.spi.swt.reporting.RenderingFailedReport;
import org.eclipse.emf.emfforms.spi.view.annotation.model.VAnnotation;
import org.eclipse.emf.emfforms.spi.view.annotation.model.VAnnotationFactory;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.localization.LocalizationServiceHelper;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Widget;

/**
 * A manager for the SWT renderings of detail views, with caching.
 *
 * @since 1.22
 */
public class DetailViewManager implements DetailViewCache {

	/**
	 * Name of a boolean-valued {@linkplain VViewModelProperties view model property}
	 * indicating that the view model is being rendered as a detail in a master-detail
	 * presentation. It is useful to distinguish this case in enablement/visibility
	 * rules, templates, etc. from the general case of a view rendering.
	 */
	public static final String DETAIL_PROPERTY = "detail"; //$NON-NLS-1$

	private DetailViewCache cache = DetailViewCache.EMPTY;

	private final Set<ECPSWTView> views = new HashSet<>();
	private final Composite detailStack;
	private final PageLayout detailLayout;

	/** The currently presented detail view. */
	private ECPSWTView currentDetailView;

	/** Whether the currently presented detail view is intrinsically read-only. */
	private boolean currentDetailViewReadOnly;

	/** View-model properties for the detail view. */
	private final VViewModelLoadingProperties detailProperties = VViewFactory.eINSTANCE
		.createViewModelLoadingProperties();

	private final Function<? super EObject, ? extends VView> detailViewFunction;

	/** View to present when there is no detail view provided by the framework. */
	private VView noDetails;
	private ECPSWTView renderedNoDetails;

	private String noDetailMessage;

	private boolean disposed;

	/**
	 * Initializes me.
	 *
	 * @param parent the composite in which to create the detail container
	 */
	public DetailViewManager(Composite parent) {
		this(parent, null);
	}

	/**
	 * Initializes me with explicit detail views. When the detail function can provide a
	 * detail view, then the framework's pluggable view providers are not consulted for it.
	 *
	 * @param parent the composite in which to create the detail container
	 * @param detailView the detail-view provider function
	 */
	public DetailViewManager(Composite parent, Function<? super EObject, ? extends VView> detailView) {
		super();

		detailStack = new Composite(parent, SWT.NONE);
		detailLayout = new PageLayout(detailStack);

		detailProperties.addNonInheritableProperty(DETAIL_PROPERTY, true);
		detailViewFunction = detailView != null ? detailView : __ -> null;

		detailStack.addDisposeListener(__ -> dispose());
	}

	/**
	 * Dispose of me and my UI resources.
	 */
	public void dispose() {
		disposed = true;

		disposeViews();

		if (renderedNoDetails != null) {
			renderedNoDetails.dispose();
		}

		if (currentDetailView != null) {
			currentDetailView.dispose();
			setCurrentDetail(null, false);
		}

		detailStack.dispose();
	}

	/**
	 * Queries whether I have been disposed.
	 *
	 * @return whether I am disposed
	 */
	public boolean isDisposed() {
		return disposed;
	}

	private void disposeViews() {
		// Copy 'views' set because disposal of the contexts will try to remove from it
		final List<ECPSWTView> toDispose = new ArrayList<>(views);
		views.clear();
		toDispose.forEach(ECPSWTView::dispose);
	}

	/**
	 * Obtain the control that contains the rendered details. This is useful, for example,
	 * to set layout parameters on it in the context of its parent composite.
	 *
	 * @return the container of the rendered details
	 */
	public Control getDetailContainer() {
		return detailStack;
	}

	/**
	 * Set the detail-view cache to use. If some other cache was being used, any rendered
	 * controls that it maintained {@linkplain Widget#dispose() will be disposed}.
	 *
	 * @param cache the {@link DetailViewCache} to use, or {@code null} to use no cache
	 */
	public void setCache(DetailViewCache cache) {
		if (cache != this.cache && this.cache != null) {
			disposeViews();
		}

		this.cache = cache != null ? cache : DetailViewCache.EMPTY;
	}

	/**
	 * Present the {@linkplain #isCached(EObject) previously cached} view for an object.
	 *
	 * @param eObject the object to present
	 *
	 * @return the re-rendered view
	 *
	 * @throws IllegalStateException if there is no view cached for the object
	 * @see #isCached(EObject)
	 */
	public ECPSWTView activate(EObject eObject) {
		cacheCurrentDetail(false);

		final ECPSWTView cached = cache.getCachedView(eObject);

		if (cached == null) {
			throw new IllegalStateException("not cached"); //$NON-NLS-1$
		}

		final ViewModelContext context = cached.getViewModelContext();
		if (context.getDomainModel() != eObject) {
			context.changeDomainModel(eObject);
		}

		// the "renderer" function in this case shouldn't be invoked
		final ECPSWTView result = render(context, (p, c) -> {
			throw new IllegalStateException("not cached"); //$NON-NLS-1$
		});

		return result;
	}

	/**
	 * Render the detail view for a given {@code context} in the specified {@code parent} composite.
	 * If a {@linkplain #isCached(EObject) cached rendering} is available for the domain model,
	 * then it is re-used and the {@code context} is {@linkplain ViewModelContext#dispose() disposed}.
	 * Otherwise, the supplied rendering function is used to render the detail view.
	 *
	 * @param context the context to present in the detail view
	 * @param renderer if needed to render a new detail view control
	 * @return the rendered view
	 */
	public ECPSWTView render(ViewModelContext context, DetailRenderingFunction renderer) {
		cacheCurrentDetail(false);

		final EObject domainModel = context.getDomainModel();
		ECPSWTView result = isCached(domainModel) ? getCachedView(domainModel) : null;

		if (result == null) {
			try {
				result = renderer.render(detailStack, context);
			} catch (final ECPRendererException e) {
				final ReportService reportService = context.getService(ReportService.class);
				reportService.report(new RenderingFailedReport(e));

				result = new RenderFailureView(detailStack, context, e);
			}

			// Track it for eventual disposal
			context.registerDisposeListener(new ContextDisposeListener(result));
		} else {
			if (result.getViewModelContext().getDomainModel() != domainModel) {
				result.getViewModelContext().changeDomainModel(domainModel);
			}

			if (context != result.getViewModelContext()) {
				// Don't need this context
				context.dispose();
			}
		}

		showDetail(result.getSWTControl());

		final VElement detailVView = result.getViewModelContext().getViewModel();
		setCurrentDetail(result,
			detailVView.isEffectivelyReadonly() || !detailVView.isEffectivelyEnabled());

		// It's currently showing, so don't track it for disposal on flushing the cache
		views.remove(result);

		return result;
	}

	private void setCurrentDetail(ECPSWTView ecpView, boolean readOnly) {
		if (currentDetailView == ecpView) {
			return; // Nothing to change
		}

		currentDetailView = ecpView;
		currentDetailViewReadOnly = readOnly;
	}

	private void showDetail(Control control) {
		detailLayout.showPage(control);

		// Recompute scroll layout, if applicable
		for (Composite composite = detailStack; composite != null; composite = composite.getParent()) {
			if (composite.getParent() instanceof ScrolledComposite) {
				final ScrolledComposite scrollPane = (ScrolledComposite) composite.getParent();
				if (composite == scrollPane.getContent()) {
					scrollPane.setMinSize(composite.computeSize(SWT.DEFAULT, SWT.DEFAULT));
				}
				break;
			}
		}
	}

	/**
	 * Render the detail view for a given {@code object} in the specified master context.
	 *
	 * @param masterContext the master context in which the {@code object} is selected
	 * @param masterView the master view in which context to render the detail view
	 * @param object the selected object for which to present the detail
	 * @return the rendered view
	 */
	public ECPSWTView render(ViewModelContext masterContext, VElement masterView, EObject object) {
		cacheCurrentDetail(false);

		ECPSWTView result;
		final VView detail = getDetailView(object);

		// Set the detail view to read only if the master view is read only or disabled
		final boolean detailReadOnly = detail.isEffectivelyReadonly() || !detail.isEffectivelyEnabled();
		detail.setReadonly(detailReadOnly || !masterView.isEffectivelyEnabled() || masterView.isEffectivelyReadonly());

		if (isCached(object)) {
			result = activate(object);
			result.getViewModelContext().changeDomainModel(object);
		} else {
			final ViewModelContext childContext = masterContext.getChildContext(object, masterView, detail);
			result = render(childContext, ECPSWTViewRenderer.INSTANCE::render);
		}

		// The delegated render() call would have set this to the new read-only state
		currentDetailViewReadOnly = detailReadOnly;

		return result;
	}

	/**
	 * Obtain the currently presented detail view.
	 *
	 * @return the current rendered detail view, or {@code null} if none
	 */
	public final ECPSWTView getCurrentDetail() {
		return currentDetailView;
	}

	/**
	 * Cache the currently presented detail view, if any, and remove it from the UI.
	 */
	public void cacheCurrentDetail() {
		cacheCurrentDetail(true);
	}

	private void cacheCurrentDetail(boolean showEmpty) {
		if (currentDetailView != null) {
			final ECPSWTView ecpView = currentDetailView;
			setCurrentDetail(null, false);

			if (isDisposed()) {
				// Just dispose the detail, then, because we can't cache it
				ecpView.dispose();
				// but we need to show something ...
				showEmpty = true;
			} else if (!cacheView(ecpView)) {
				// The current detail view was disposed, so show this instead
				showEmpty = true;
			}

		}

		if (showEmpty && !isDisposed()) {
			showDetail(getNoDetailsControl().getSWTControl());
		}
	}

	/**
	 * Set focus to the currently presented detail view.
	 */
	public void setFocus() {
		if (currentDetailView != null && !currentDetailView.getSWTControl().isDisposed()) {
			currentDetailView.getSWTControl().setFocus();
		}
	}

	/**
	 * Obtain the view-model properties used for loading the detail view model.
	 * Clients may update these properties but must not change the {@link #DETAIL_PROPERTY detail property}.
	 *
	 * @return the view-model properties for the detail view
	 */
	public VViewModelProperties getDetailProperties() {
		return detailProperties;
	}

	/**
	 * Obtain the detail view in the context of the given master view-model context for a selected
	 * {@code object} in the master view.
	 *
	 * @param masterContext the master view-model context
	 * @param object the selection in the master view for which to get a detail view
	 * @return the detail view
	 */
	public VView getDetailView(ViewModelContext masterContext, EObject object) {
		return getDetailView(masterContext, object, null);
	}

	/**
	 * Obtain the detail view in the context of the given master view-model context for a selected
	 * {@code object} in the master view.
	 *
	 * @param masterContext the master view-model context
	 * @param object the selection in the master view for which to get a detail view
	 * @param propertiesManipulator an optional hook with which to inject properties to assist/filter the detail view
	 *            model resolution
	 *
	 * @return the detail view
	 */
	public VView getDetailView(ViewModelContext masterContext, EObject object,
		Consumer<? super VViewModelProperties> propertiesManipulator) {

		final VElement viewModel = masterContext.getViewModel();
		final VViewModelProperties properties = ViewModelPropertiesHelper
			.getInhertitedPropertiesOrEmpty(viewModel);

		// Inject client properties
		if (propertiesManipulator != null) {
			propertiesManipulator.accept(properties);
		}

		// Inject our properties
		detailProperties.getNonInheritableProperties().map().forEach(properties::addNonInheritableProperty);
		detailProperties.getInheritableProperties().map().forEach(properties::addInheritableProperty);

		return ViewProviderHelper.getView(object, properties);
	}

	/**
	 * Obtain the detail view for an {@code object} selected in the master view. This method
	 * is useful for master-detail presentations in which the master is not managed by a
	 * Form in a {@link ViewModelContext} but explicitly by some custom UI control.
	 *
	 * @param object the master view selection
	 * @return the detail view for the {@code object}
	 */
	public VView getDetailView(EObject object) {
		return getDetailView(object, getDetailProperties());
	}

	/**
	 * Set the current detail view read-only or not, unless it is intrinsically read-only,
	 * in which case it will remain so (this method will have no effect).
	 *
	 * @param readOnly {@code true} to set the detail view read-only;
	 *            {@code false} to let its intrinsic read-only state prevail
	 */
	public void setDetailReadOnly(boolean readOnly) {
		if (currentDetailView != null && !currentDetailView.getSWTControl().isDisposed()) {
			final VElement currentDetailVView = currentDetailView.getViewModelContext().getViewModel();
			currentDetailVView.setReadonly(readOnly || currentDetailViewReadOnly);
		}
	}

	/**
	 * Get the view provided by the framework for details of the given {@code object}.
	 *
	 * @param object the object for which to get the detail view
	 * @param properties the properties to use for loading the view
	 * @return the detail view (never {@code null}, even if the framework provides nothing)
	 */
	protected VView getDetailView(EObject object, VViewModelProperties properties) {
		VView result = detailViewFunction.apply(object);
		if (result != null) {
			// Have an explicit view
			result.setLoadingProperties(EcoreUtil.copy(properties));
		} else {
			// Go to the framework to get a provided view
			result = ViewProviderHelper.getView(object, properties);

			if (result == null) {
				// Nothing provided. Make that clear to the user
				result = getNoDetailView();
				result.setLoadingProperties(EcoreUtil.copy(properties));
			}
		}

		return result;
	}

	/**
	 * Obtain a view to use as placeholder when the framework provides no view for the
	 * detail of the selection or when there is no selection.
	 *
	 * @return the no-details placeholder view
	 */
	protected VView getNoDetailView() {
		if (noDetails == null) {
			noDetails = VViewFactory.eINSTANCE.createView();
			noDetails.setUuid(Integer.toHexString(noDetails.hashCode()));
			final VLabel label = VLabelFactory.eINSTANCE.createLabel();
			label.setUuid(Integer.toHexString(label.hashCode()));
			label.setName("%noDetails"); //$NON-NLS-1$

			// Annotate the label to let it match our styling template
			final VAnnotation annotation = VAnnotationFactory.eINSTANCE.createAnnotation();
			annotation.setKey("detail"); //$NON-NLS-1$
			annotation.setValue("noDetails"); //$NON-NLS-1$
			label.getAttachments().add(annotation);

			noDetails.getChildren().add(label);
		}

		final VView result = EcoreUtil.copy(noDetails);
		if (noDetailMessage != null) {
			// Replace the label name
			final VLabel label = (VLabel) EcoreUtil.getObjectByType(result.getChildren(),
				VLabelPackage.Literals.LABEL);
			label.setName(noDetailMessage);
		}

		result.eAdapters().add(new LocalizationAdapter() {

			@Override
			public String localize(String key) {
				return LocalizationServiceHelper.getString(Activator.getDefault().getBundle(),
					key);
			}
		});

		return result;
	}

	/**
	 * Obtain the renderer control showing a hint that there is no selection or
	 * no details for the current selection.
	 *
	 * @return the no-details control
	 */
	protected ECPSWTView getNoDetailsControl() {
		if (renderedNoDetails == null) {
			final VView view = getNoDetailView();

			// The view is just a static label, so it doesn't matter that it renders itself
			final ViewModelContext context = ViewModelContextFactory.INSTANCE.createViewModelContext(view, view);

			try {
				renderedNoDetails = ECPSWTViewRenderer.INSTANCE.render(detailStack, context);
			} catch (final ECPRendererException e) {
				renderedNoDetails = new RenderFailureView(detailStack, context, e);
			}
		}

		return renderedNoDetails;
	}

	/**
	 * Apply a reasonable default layout for the parent {@code composite} of a detail container
	 * where the only thing contained in that parent is the detail container.
	 *
	 * @param composite the detail parent composite
	 * @return the {@code composite}, for convenience of call chaining
	 */
	public Composite layoutDetailParent(Composite composite) {
		final FillLayout layout = new FillLayout(SWT.VERTICAL);

		// Use the same margins as are defauls for the grid layout
		final GridLayout gridLayout = new GridLayout();
		layout.marginHeight = gridLayout.marginHeight;
		layout.marginWidth = gridLayout.marginWidth;

		composite.setLayout(layout);
		return composite;
	}

	/**
	 * Set a message to display to the user when there is no detail to show, either
	 * because there is no selection in the master or because it has no details.
	 *
	 * @param noDetailMessage the "no details" message to set, or {@code null}
	 *            for the default localized string
	 *            ("No selection or no details available for selection.")
	 */
	public void setNoDetailMessage(String noDetailMessage) {
		this.noDetailMessage = noDetailMessage;

		// Have we already renderered the "no details" view? If so, poke it
		if (renderedNoDetails != null) {
			// Rebuild it
			final boolean wasCurrent = detailLayout.getCurrentPage() == renderedNoDetails.getSWTControl();
			renderedNoDetails.dispose();
			renderedNoDetails = null;

			if (wasCurrent) {
				showDetail(getNoDetailsControl().getSWTControl());
			}
		}
	}

	//
	// DetailViewCache protocol
	//

	@Override
	public boolean isCached(EObject selection) {
		return cache.isCached(selection);
	}

	@Override
	public ECPSWTView getCachedView(EObject selection) {
		return cache.getCachedView(selection);
	}

	@Override
	public boolean cacheView(ECPSWTView ecpView) {
		final Control control = ecpView.getSWTControl();

		final boolean result = cache.cacheView(ecpView);

		// Track it if it was successfully cached
		if (result && !control.isDisposed()) {
			views.add(ecpView);
		}

		return result;
	}

	//
	// Nested types
	//

	/**
	 * Listener for disposal of the current view context.
	 */
	private final class ContextDisposeListener implements ViewModelContextDisposeListener {

		private final ECPSWTView ecpView;

		ContextDisposeListener(ECPSWTView ecpView) {
			super();

			this.ecpView = ecpView;
		}

		@Override
		public void contextDisposed(ViewModelContext viewModelContext) {
			ecpView.dispose();

			if (ecpView == currentDetailView) {
				// The current context is disposed, so this will result in
				// its rendering being disposed and the no-details control
				// being shown in its place
				cacheCurrentDetail();
			} else {
				views.remove(ecpView);
			}
		}

	}

}
