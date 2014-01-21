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

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecp.diffmerge.spi.context.DiffMergeModelContext;
import org.eclipse.emf.ecp.edit.internal.swt.util.SWTControl;
import org.eclipse.emf.ecp.edit.spi.ECPAbstractControl;
import org.eclipse.emf.ecp.edit.spi.ECPControlFactory;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;

/**
 * Class creating a diff Dialog.
 * 
 * @author Eugen Neufeld
 * 
 */
// TODO API
@SuppressWarnings("restriction")
public class DiffDialog {

	/**
	 * Variant constant for indicating RAP controls.
	 */
	private static final String CUSTOM_VARIANT = "org.eclipse.rap.rwt.customVariant"; //$NON-NLS-1$

	private final VDomainModelReference left;
	private final VDomainModelReference right;
	private final VDomainModelReference main;
	private final String diffAttribute;
	private final DiffMergeModelContext viewModelContext;
	private final Set<ECPAbstractControl> toDispose = new LinkedHashSet<ECPAbstractControl>();

	/**
	 * Constructor for the diff dialog.
	 * 
	 * @param viewModelContext the {@link ViewModelContext}
	 * @param diffAttribute the display name of the attribute
	 * @param left the left {@link VDomainModelReference}
	 * @param right the right {@link VDomainModelReference}
	 * @param main the main {@link VDomainModelReference}
	 */
	public DiffDialog(DiffMergeModelContext viewModelContext, String diffAttribute, VDomainModelReference left,
		VDomainModelReference right, VDomainModelReference main) {
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
		final BundleContext bundleContext = FrameworkUtil.getBundle(getClass()).getBundleContext();
		parent.addDisposeListener(new DisposeListener() {

			private static final long serialVersionUID = 1L;

			public void widgetDisposed(DisposeEvent event) {
				for (final ECPAbstractControl control : toDispose) {
					control.dispose();
				}
			}
		});
		final ServiceReference<ECPControlFactory> serviceReference = bundleContext
			.getServiceReference(ECPControlFactory.class);
		final ECPControlFactory controlFactory = bundleContext.getService(serviceReference);

		final Composite composite = new Composite(parent, SWT.NONE);
		GridLayoutFactory.fillDefaults().numColumns(1).equalWidth(false).applyTo(composite);

		final Control title = createTitleLabel(composite);
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.CENTER).grab(true, false).applyTo(title);

		final Control diff = createDiff(composite, controlFactory);
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.BEGINNING).grab(true, false).applyTo(diff);

		final Control merge = createMerge(composite, controlFactory);
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.BEGINNING).grab(true, false).applyTo(merge);

		bundleContext.ungetService(serviceReference);

	}

	/**
	 * Creates the Merge content.
	 * 
	 * @param parent the {@link Composite}
	 * @param ecpControlFactory the {@link ECPControlFactory}
	 * @return the control showing the merge
	 */
	private Control createMerge(final Composite parent, final ECPControlFactory ecpControlFactory) {
		final Composite mainObjectComposite = new Composite(parent, SWT.NONE);
		mainObjectComposite.setData(CUSTOM_VARIANT, "org_eclipse_emf_ecp_compare_dialog_merge"); //$NON-NLS-1$
		GridLayoutFactory.fillDefaults().numColumns(2).equalWidth(false).applyTo(mainObjectComposite);

		final Label mainObject = new Label(mainObjectComposite, SWT.NONE);
		mainObject.setText(Messages.DiffDialog_mainObject);
		mainObject.setData(CUSTOM_VARIANT, "org_eclipse_emf_ecp_compare_dialog_merge_label"); //$NON-NLS-1$

		final SWTControl mainControl = ecpControlFactory.createControl(SWTControl.class, main);
		mainControl.init(viewModelContext, (VControl) main.eContainer());
		toDispose.add(mainControl);
		final Composite mainComposite = mainControl.createControl(mainObjectComposite);
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.BEGINNING).grab(true, false).applyTo(mainComposite);

		return mainObjectComposite;
	}

	/**
	 * Creates the Diff content.
	 * 
	 * @param parent the {@link Composite}
	 * @param ecpControlFactory the {@link ECPControlFactory}
	 * @return the control showing the diff
	 */
	private Control createDiff(final Composite parent, final ECPControlFactory ecpControlFactory) {
		final Group group = new Group(parent, SWT.NONE);
		group.setText(Messages.DiffDialog_DifferenceGroup);
		group.setData(CUSTOM_VARIANT, "org_eclipse_emf_ecp_compare_dialog_diff"); //$NON-NLS-1$
		GridLayoutFactory.fillDefaults().numColumns(3).equalWidth(false).applyTo(group);

		final Label leftObject = new Label(group, SWT.NONE);
		leftObject.setText(Messages.DiffDialog_leftObject);
		leftObject.setData(CUSTOM_VARIANT, "org_eclipse_emf_ecp_compare_dialog_diff_left"); //$NON-NLS-1$

		final SWTControl leftControl = ecpControlFactory.createControl(SWTControl.class, left);
		leftControl.init(viewModelContext, (VControl) left.eContainer());
		toDispose.add(leftControl);
		final Composite leftComposite = leftControl.createControl(group);
		leftControl.setEditable(false);
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.BEGINNING).grab(true, false).applyTo(leftComposite);

		final Button bReplaceWithLeft = new Button(group, SWT.PUSH);
		bReplaceWithLeft.setText(Messages.DiffDialog_replaceWithLeft);
		bReplaceWithLeft.setData(CUSTOM_VARIANT, "org_eclipse_emf_ecp_compare_dialog_diff_leftReplace"); //$NON-NLS-1$
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.BEGINNING).grab(false, false).applyTo(bReplaceWithLeft);
		bReplaceWithLeft.addSelectionListener(new SelectionAdapter() {

			private static final long serialVersionUID = 1L;

			/**
			 * {@inheritDoc}
			 * 
			 * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
			 */
			@Override
			public void widgetSelected(SelectionEvent e) {
				super.widgetSelected(e);
				replaceMainWith(left.getIterator());
			}

		});

		final Label rightObject = new Label(group, SWT.NONE);
		rightObject.setText(Messages.DiffDialog_rightObject);
		leftObject.setData(CUSTOM_VARIANT, "org_eclipse_emf_ecp_compare_dialog_diff_right"); //$NON-NLS-1$

		final SWTControl rightControl = ecpControlFactory.createControl(SWTControl.class, right);
		rightControl.init(viewModelContext, (VControl) right.eContainer());
		toDispose.add(rightControl);
		final Composite rightComposite = rightControl.createControl(group);
		rightControl.setEditable(false);
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.BEGINNING).grab(true, false).applyTo(rightComposite);

		final Button bReplaceWithRight = new Button(group, SWT.PUSH);
		bReplaceWithRight.setText(Messages.DiffDialog_replaceWithRight);
		bReplaceWithLeft.setData(CUSTOM_VARIANT, "org_eclipse_emf_ecp_compare_dialog_diff_rightReplace"); //$NON-NLS-1$
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.BEGINNING).grab(false, false).applyTo(bReplaceWithRight);
		bReplaceWithRight.addSelectionListener(new SelectionAdapter() {

			private static final long serialVersionUID = 1L;

			/**
			 * {@inheritDoc}
			 * 
			 * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
			 */
			@Override
			public void widgetSelected(SelectionEvent e) {
				super.widgetSelected(e);
				replaceMainWith(right.getIterator());
			}

		});

		return group;
	}

	private void replaceMainWith(Iterator<Setting> replaceValues) {
		final Iterator<Setting> mainValues = main.getIterator();
		while (replaceValues.hasNext()) {
			final Setting mainValue = mainValues.next();
			final Setting replaceValue = replaceValues.next();
			final EditingDomain editingDomain = AdapterFactoryEditingDomain.getEditingDomainFor(mainValue.getEObject());
			editingDomain.getCommandStack().execute(
				new SetCommand(editingDomain, mainValue.getEObject(), mainValue.getEStructuralFeature(), replaceValue
					.get(true)));
		}
	}

	/**
	 * Creates the title {@link Label}.
	 * 
	 * @param parent the {@link Composite}
	 * @return the control showing the title
	 */
	private Control createTitleLabel(final Composite parent) {
		final Label title = new Label(parent, SWT.NONE);
		title.setText(diffAttribute);
		title.setData(CUSTOM_VARIANT, "org_eclipse_emf_ecp_compare_dialog_title"); //$NON-NLS-1$
		return title;
	}
}
