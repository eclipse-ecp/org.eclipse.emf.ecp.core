/*******************************************************************************
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.diffmerge.swt;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.command.BasicCommandStack;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecp.diffmerge.spi.context.DefaultMergeUtil;
import org.eclipse.emf.ecp.diffmerge.spi.context.DiffMergeModelContext;
import org.eclipse.emf.ecp.ui.view.ECPRendererException;
import org.eclipse.emf.ecp.ui.view.swt.ECPSWTViewRenderer;
import org.eclipse.emf.ecp.view.spi.model.LabelAlignment;
import org.eclipse.emf.ecp.view.spi.model.VContainedElement;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emf.ecp.view.spi.model.VView;
import org.eclipse.emf.ecp.view.spi.model.VViewFactory;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.ReflectiveItemProviderAdapterFactory;
import org.eclipse.emf.emfforms.spi.localization.LocalizationServiceHelper;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

/**
 * Class creating a diff Dialog.
 *
 * @author Eugen Neufeld
 *
 */
public class DiffDialog {

	/**
	 * Variant constant for indicating RAP controls.
	 */
	private static final String CUSTOM_VARIANT = "org.eclipse.rap.rwt.customVariant"; //$NON-NLS-1$

	private final VControl left;
	private final VControl right;
	private final VControl main;
	private final String diffAttribute;
	private final DiffMergeModelContext viewModelContext;

	private VControl mergeControl;
	private boolean diffConfirmed = true;

	/**
	 * Constructor for the diff dialog.
	 *
	 * @param viewModelContext the {@link org.eclipse.emf.ecp.view.spi.context.ViewModelContext ViewModelContext}
	 * @param diffAttribute the display name of the attribute
	 * @param left the left {@link VControl}
	 * @param right the right {@link VControl}
	 * @param main the main {@link VControl}
	 */
	public DiffDialog(DiffMergeModelContext viewModelContext, String diffAttribute, VControl left,
		VControl right, VControl main) {
		this.viewModelContext = viewModelContext;
		this.diffAttribute = diffAttribute;
		this.left = left;
		this.right = right;
		this.main = main;
	}

	/**
	 * Creates the dialog content.
	 *
	 * @param parent the parent {@link Composite}
	 */
	public void create(Composite parent) {
		final Composite mainComposite = new Composite(parent, SWT.NONE);
		GridLayoutFactory.fillDefaults().numColumns(1).equalWidth(false).extendedMargins(10, 10, 10, 10)
			.applyTo(mainComposite);
		final ScrolledComposite scrolledComposite = new ScrolledComposite(mainComposite, SWT.H_SCROLL
			| SWT.V_SCROLL);
		{
			scrolledComposite.setExpandHorizontal(true);
			scrolledComposite.setExpandVertical(true);
			scrolledComposite.setShowFocusedControl(true);
			GridLayoutFactory.fillDefaults().numColumns(1).equalWidth(false).applyTo(scrolledComposite);
			GridDataFactory.fillDefaults().align(SWT.FILL, SWT.FILL).grab(true, true).applyTo(scrolledComposite);

			final Composite composite = new Composite(scrolledComposite, SWT.NONE);
			GridLayoutFactory.fillDefaults().numColumns(1).equalWidth(false)
				.applyTo(composite);
			GridDataFactory.fillDefaults().align(SWT.FILL, SWT.FILL).grab(true, true).applyTo(composite);

			final Control title = createTitleLabel(composite);
			GridDataFactory.fillDefaults().align(SWT.FILL, SWT.CENTER).grab(true, false).applyTo(title);

			final Control diff = createDiff(composite);
			GridDataFactory.fillDefaults().align(SWT.FILL, SWT.BEGINNING).grab(false, false).applyTo(diff);

			final Control merge = createTarget(composite);
			GridDataFactory.fillDefaults().align(SWT.FILL, SWT.BEGINNING).grab(false, false).applyTo(merge);

			scrolledComposite.setContent(composite);
			composite.layout();
			final Point point = composite.computeSize(SWT.DEFAULT, SWT.DEFAULT);
			scrolledComposite.setMinSize(point);
		}
		final Control nextPrevious = createButtonRow(mainComposite);
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.BEGINNING).grab(true, false).applyTo(nextPrevious);

	}

	private Control createButtonRow(Composite parent) {
		final Composite buttonRowComposite = new Composite(parent, SWT.NONE);
		buttonRowComposite.setData(CUSTOM_VARIANT, "org_eclipse_emf_ecp_compare_dialog_buttonRow"); //$NON-NLS-1$
		GridLayoutFactory.fillDefaults().numColumns(2).equalWidth(true).applyTo(buttonRowComposite);

		{
			final Composite diffConfirmedComposite = new Composite(buttonRowComposite, SWT.NONE);
			GridDataFactory.fillDefaults().align(SWT.FILL, SWT.BEGINNING).grab(true, false).span(2, 1)
				.applyTo(diffConfirmedComposite);
			diffConfirmedComposite.setLayout(new FillLayout(SWT.HORIZONTAL));
			final Button buttonDiffConfirmed = new Button(diffConfirmedComposite, SWT.CHECK);
			buttonDiffConfirmed.setText(LocalizationServiceHelper.getString(getClass(),
				MessageKeys.DiffDialog_DiffConfirmed));
			buttonDiffConfirmed.setSelection(diffConfirmed);
			buttonDiffConfirmed.addSelectionListener(new SelectionAdapter() {

				/**
				 * {@inheritDoc}
				 *
				 * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
				 */
				@Override
				public void widgetSelected(SelectionEvent e) {
					super.widgetSelected(e);
					diffConfirmed = buttonDiffConfirmed.getSelection();
				}

			});
		}

		{
			final int index = viewModelContext.getIndexOf(main);

			final Composite nextPreviousComposite = new Composite(buttonRowComposite, SWT.NONE);
			nextPreviousComposite.setData(CUSTOM_VARIANT, "org_eclipse_emf_ecp_compare_dialog_nextPrevious"); //$NON-NLS-1$
			GridLayoutFactory.fillDefaults().numColumns(2).equalWidth(true).applyTo(nextPreviousComposite);
			GridDataFactory.fillDefaults().align(SWT.BEGINNING, SWT.BEGINNING).grab(true, false)
				.applyTo(nextPreviousComposite);

			final Button previous = new Button(nextPreviousComposite, SWT.PUSH);
			previous.setText(LocalizationServiceHelper.getString(getClass(), MessageKeys.DiffDialog_Previous));
			previous.setImage(Activator.getImage("icons/arrow_left.png")); //$NON-NLS-1$
			GridDataFactory.fillDefaults().align(SWT.FILL, SWT.BEGINNING).grab(false, false).applyTo(previous);
			previous.addSelectionListener(new SelectionAdapter() {

				/**
				 * {@inheritDoc}
				 *
				 * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
				 */
				@Override
				public void widgetSelected(SelectionEvent e) {
					super.widgetSelected(e);
					saveAndCloseDialog(previous.getShell());
					DiffDialogHelper.showDialog(viewModelContext, index - 1);
				}

			});
			final Button next = new Button(nextPreviousComposite, SWT.PUSH);
			next.setText(LocalizationServiceHelper.getString(getClass(), MessageKeys.DiffDialog_Next));
			next.setImage(Activator.getImage("icons/arrow_right.png")); //$NON-NLS-1$
			GridDataFactory.fillDefaults().align(SWT.FILL, SWT.BEGINNING).grab(false, false).applyTo(next);
			next.addSelectionListener(new SelectionAdapter() {

				/**
				 * {@inheritDoc}
				 *
				 * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
				 */
				@Override
				public void widgetSelected(SelectionEvent e) {
					super.widgetSelected(e);
					saveAndCloseDialog(next.getShell());
					DiffDialogHelper.showDialog(viewModelContext, index + 1);
				}

			});

			if (index == 0) {
				previous.setEnabled(false);
			}
			if (index + 1 == viewModelContext.getTotalNumberOfDiffs()) {
				next.setEnabled(false);
			}
		}

		{
			final Button bConfirm = new Button(buttonRowComposite, SWT.PUSH);
			bConfirm.setText(LocalizationServiceHelper.getString(getClass(), MessageKeys.DiffDialog_Confirm));
			bConfirm.setData(CUSTOM_VARIANT, "org_eclipse_emf_ecp_compare_dialog_merge_confirm"); //$NON-NLS-1$
			GridDataFactory.fillDefaults().align(SWT.END, SWT.BEGINNING).grab(true, false).applyTo(bConfirm);
			bConfirm.addSelectionListener(new SelectionAdapter() {

				/**
				 * {@inheritDoc}
				 *
				 * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
				 */
				@Override
				public void widgetSelected(SelectionEvent e) {
					super.widgetSelected(e);
					saveAndCloseDialog(bConfirm.getShell());
				}

			});
		}

		return buttonRowComposite;
	}

	/**
	 * Creates the Merge content.
	 *
	 * @param parent the {@link Composite}
	 * @return the control showing the merge
	 */
	private Control createTarget(final Composite parent) {
		final Group targetGroup = new Group(parent, SWT.NONE);
		targetGroup.setData(CUSTOM_VARIANT, "org_eclipse_emf_ecp_compare_dialog_target"); //$NON-NLS-1$
		targetGroup.setText(LocalizationServiceHelper.getString(getClass(), MessageKeys.DiffDialog_targetObject));
		GridLayoutFactory.fillDefaults().numColumns(2).equalWidth(false).applyTo(targetGroup);

		// final Label mainObject = new Label(targetGroup, SWT.NONE);
		// mainObject.setText(Messages.DiffDialog_targetObject);
		//		mainObject.setData(CUSTOM_VARIANT, "org_eclipse_emf_ecp_compare_dialog_merge_label"); //$NON-NLS-1$
		// GridDataFactory.fillDefaults().align(SWT.BEGINNING, SWT.FILL).grab(false, false).applyTo(mainObject);

		mergeControl = EcoreUtil.copy(main);
		final ResourceSet resourceSet = new ResourceSetImpl();
		final AdapterFactoryEditingDomain domain = new AdapterFactoryEditingDomain(
			new ComposedAdapterFactory(new AdapterFactory[] {
				new ReflectiveItemProviderAdapterFactory(),
				new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE) }),
			new BasicCommandStack(), resourceSet);
		final EObject domainObject = EcoreUtil.copy(viewModelContext.getDomainModel());
		resourceSet.eAdapters().add(new AdapterFactoryEditingDomain.EditingDomainProvider(domain));
		final Resource resource = resourceSet.createResource(URI.createURI("VIRTUAL_URI_TEMP")); //$NON-NLS-1$
		resource.getContents().add(domainObject);
		createControl(targetGroup, mergeControl, domainObject, false);

		return targetGroup;
	}

	/**
	 * Creates the Diff content.
	 *
	 * @param parent the {@link Composite}
	 * @return the control showing the diff
	 */
	private Control createDiff(final Composite parent) {
		final Group group = new Group(parent, SWT.NONE);
		group.setText(LocalizationServiceHelper.getString(getClass(), MessageKeys.DiffDialog_DifferenceGroup));
		group.setData(CUSTOM_VARIANT, "org_eclipse_emf_ecp_compare_dialog_diff"); //$NON-NLS-1$
		GridLayoutFactory.fillDefaults().numColumns(2).equalWidth(false).applyTo(group);

		final Label leftObject = new Label(group, SWT.NONE);
		leftObject.setText(LocalizationServiceHelper.getString(getClass(), MessageKeys.DiffDialog_leftObject + ":")); //$NON-NLS-1$
		leftObject.setData(CUSTOM_VARIANT, "org_eclipse_emf_ecp_compare_dialog_diff_left"); //$NON-NLS-1$
		GridDataFactory.fillDefaults().align(SWT.BEGINNING, SWT.FILL).grab(false, false).span(2, 1).applyTo(leftObject);

		createControl(group, EcoreUtil.copy(left), EcoreUtil.copy(viewModelContext.getLeftModel()), true);

		final Button bReplaceWithLeft = new Button(group, SWT.PUSH);
		bReplaceWithLeft.setText(LocalizationServiceHelper
			.getString(getClass(), MessageKeys.DiffDialog_replaceWithLeft));
		bReplaceWithLeft.setImage(Activator.getImage("icons/arrow_down.png")); //$NON-NLS-1$
		bReplaceWithLeft.setData(CUSTOM_VARIANT, "org_eclipse_emf_ecp_compare_dialog_diff_leftReplace"); //$NON-NLS-1$
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.BEGINNING).grab(false, false).applyTo(bReplaceWithLeft);
		bReplaceWithLeft.addSelectionListener(new SelectionAdapter() {

			/**
			 * {@inheritDoc}
			 *
			 * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
			 */
			@Override
			public void widgetSelected(SelectionEvent e) {
				super.widgetSelected(e);
				replaceMainWith(left);
			}

		});

		final Label rightObject = new Label(group, SWT.NONE);
		rightObject.setText(LocalizationServiceHelper.getString(getClass(), MessageKeys.DiffDialog_rightObject + ":")); //$NON-NLS-1$
		rightObject.setData(CUSTOM_VARIANT, "org_eclipse_emf_ecp_compare_dialog_diff_right"); //$NON-NLS-1$
		GridDataFactory.fillDefaults().align(SWT.BEGINNING, SWT.FILL).grab(false, false).span(2, 1)
			.applyTo(rightObject);

		createControl(group, EcoreUtil.copy(right), EcoreUtil.copy(viewModelContext.getRightModel()), true);

		final Button bReplaceWithRight = new Button(group, SWT.PUSH);
		bReplaceWithRight.setText(LocalizationServiceHelper.getString(getClass(),
			MessageKeys.DiffDialog_replaceWithRight));
		bReplaceWithRight.setImage(Activator.getImage("icons/arrow_down.png")); //$NON-NLS-1$
		bReplaceWithLeft.setData(CUSTOM_VARIANT, "org_eclipse_emf_ecp_compare_dialog_diff_rightReplace"); //$NON-NLS-1$
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.BEGINNING).grab(false, false).applyTo(bReplaceWithRight);
		bReplaceWithRight.addSelectionListener(new SelectionAdapter() {

			/**
			 * {@inheritDoc}
			 *
			 * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
			 */
			@Override
			public void widgetSelected(SelectionEvent e) {
				super.widgetSelected(e);
				replaceMainWith(right);
			}

		});

		return group;
	}

	private void createControl(Composite parent, VControl control, EObject domainObject, boolean readonly) {
		final VView leftView = VViewFactory.eINSTANCE.createView();
		leftView.setRootEClass(domainObject.eClass());
		final VControl leftControl = control;
		leftControl.setReadonly(readonly);
		leftControl.setLabelAlignment(LabelAlignment.NONE);
		leftView.getChildren().add(leftControl);
		try {
			ECPSWTViewRenderer.INSTANCE.render(parent, domainObject, leftView);
		} catch (final ECPRendererException ex) {
			ex.printStackTrace();
		}
	}

	private void replaceMainWith(VControl replaceControl) {
		DefaultMergeUtil.copyValues(replaceControl, mergeControl);
	}

	/**
	 * Creates the title {@link Label}.
	 *
	 * @param parent the {@link Composite}
	 * @return the control showing the title
	 */
	private Control createTitleLabel(final Composite parent) {
		final Label title = new Label(parent, SWT.NONE);
		final List<String> breadCrumb = new ArrayList<String>();
		breadCrumb.add(diffAttribute);
		EObject parentEObject = main.eContainer();
		while (!VView.class.isInstance(parentEObject)) {
			final VElement vElement = (VElement) parentEObject;
			// FIXME hack -> will filter out groups
			if (!VContainedElement.class.isInstance(vElement)) {
				breadCrumb.add(vElement.getName());
			}
			parentEObject = parentEObject.eContainer();
		}

		Collections.reverse(breadCrumb);
		final StringBuilder sb = new StringBuilder();
		for (final String bc : breadCrumb) {
			if (sb.length() != 0)
			{
				sb.append(">"); //$NON-NLS-1$
			}
			sb.append(bc);
		}
		title.setText(sb.toString());
		title.setData(CUSTOM_VARIANT, "org_eclipse_emf_ecp_compare_dialog_title"); //$NON-NLS-1$
		return title;
	}

	private void saveAndCloseDialog(final Shell shell) {
		DefaultMergeUtil.copyValues(mergeControl, main);

		viewModelContext.markControl(main, diffConfirmed);

		shell.dispose();
	}
}
