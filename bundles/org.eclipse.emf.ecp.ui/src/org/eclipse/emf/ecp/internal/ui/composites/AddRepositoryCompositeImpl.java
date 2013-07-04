/*******************************************************************************
 * Copyright (c) 2011-2012 EclipseSource Muenchen GmbH and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 * 
 *******************************************************************************/

package org.eclipse.emf.ecp.internal.ui.composites;

import org.eclipse.emf.ecp.core.ECPProvider;
import org.eclipse.emf.ecp.core.util.ECPProperties;
import org.eclipse.emf.ecp.core.util.ECPUtil;
import org.eclipse.emf.ecp.internal.ui.messages.Messages;
import org.eclipse.emf.ecp.internal.ui.model.ProvidersContentProvider;
import org.eclipse.emf.ecp.internal.ui.model.ProvidersLabelProvider;
import org.eclipse.emf.ecp.spi.ui.UIProvider;
import org.eclipse.emf.ecp.spi.ui.UIProviderRegistry;
import org.eclipse.emf.ecp.ui.common.AddRepositoryComposite;

import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import java.util.HashMap;
import java.util.Map;

/**
 * This {@link AddRepositoryComposite} provides a Composite which allows to create a new Repository.
 * This class alsa defines a Listener for name, label description and provider changes.
 * 
 * @author Eugen Neufeld
 */
public class AddRepositoryCompositeImpl implements AddRepositoryComposite {

	/**
	 * Constructor to use if a specific provider should be used.
	 * 
	 * @param provider the {@link ECPProvider} to use for this repository
	 */
	public AddRepositoryCompositeImpl(ECPProvider provider) {
		this.provider = provider;
	}

	private Composite providerStack;

	private StackLayout providerStackLayout;

	private Text repositoryDescriptionText;

	private Text repositoryNameText;

	private AddRepositoryChangeListener listener;

	private ECPProvider provider;

	private String repositoryName;

	private String repositoryDescription;

	/** {@inheritDoc} **/
	public Composite createUI(Composite parent) {
		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayoutData(new GridData(GridData.FILL_BOTH));
		composite.setLayout(new GridLayout(2, false));

		if (provider == null) {
			createProviderSelector(composite);
		}

		GridData gdProviderStack = new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1);
		providerStackLayout = new StackLayout();
		providerStack = new Composite(composite, SWT.NONE);
		providerStack.setLayout(providerStackLayout);
		providerStack.setLayoutData(gdProviderStack);
		{
			Label repositoryNameLabel = new Label(composite, SWT.NONE);
			repositoryNameLabel.setLayoutData(new GridData(SWT.FILL, SWT.TOP, false, false, 1, 1));
			repositoryNameLabel.setText(Messages.AddRepositoryComposite_RepositoryName);

			repositoryNameText = new Text(composite, SWT.BORDER);
			// repositoryNameText.setText(repositoryName);
			repositoryNameText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
			repositoryNameText.addModifyListener(new ModifyListener() {
				public void modifyText(ModifyEvent e) {
					repositoryName = repositoryNameText.getText();
					if (listener != null) {
						listener.repositoryNameChanged(repositoryName);
					}
				}
			});
		}
		{
			Label repositoryDescriptionLabel = new Label(composite, SWT.NONE);
			repositoryDescriptionLabel.setLayoutData(new GridData(SWT.FILL, SWT.TOP, false, false, 1, 1));
			repositoryDescriptionLabel.setText(Messages.AddRepositoryComposite_RepositoryDescription);

			repositoryDescriptionText = new Text(composite, SWT.BORDER);
			// repositoryDescriptionText.setText(repositoryDescription);
			GridData gdRepositoryDescriptionText = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
			gdRepositoryDescriptionText.heightHint = 36;
			repositoryDescriptionText.setLayoutData(gdRepositoryDescriptionText);
			repositoryDescriptionText.addModifyListener(new ModifyListener() {
				public void modifyText(ModifyEvent e) {
					repositoryDescription = repositoryDescriptionText.getText();
					if (listener != null) {
						listener.repositoryDescriptionChanged(repositoryDescription);
					}
				}
			});
		}

		if (provider != null) {
			showProviderUI(provider);
		}

		return composite;
	}

	/**
	 * @param composite
	 */
	private void createProviderSelector(Composite composite) {
		Label label = new Label(composite, SWT.NONE);
		label.setLayoutData(new GridData(SWT.FILL, SWT.TOP, false, false, 1, 1));
		label.setText(Messages.AddRepositoryComposite_RepositoryProvider);

		ComboViewer providersViewer = new ComboViewer(composite, SWT.NONE);
		Combo combo = providersViewer.getCombo();
		GridData gdCombo = new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1);
		gdCombo.widthHint = 150;
		combo.setLayoutData(gdCombo);
		providersViewer.setContentProvider(new ProvidersContentProvider(true));
		providersViewer.setLabelProvider(new ProvidersLabelProvider());
		providersViewer.setSorter(new ViewerSorter());
		providersViewer.setInput(ECPUtil.getECPProviderRegistry());
		providersViewer.addSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(SelectionChangedEvent event) {
				IStructuredSelection selection = (IStructuredSelection) event.getSelection();
				provider = (ECPProvider) selection.getFirstElement();
				if (provider != null) {
					showProviderUI(provider);
					if (listener != null) {
						listener.repositoryProviderChanged(provider);
					}
				}
			}
		});

		for (ECPProvider provider : ECPUtil.getECPProviderRegistry().getProviders()) {
			if (provider.hasCreateRepositorySupport()) {
				providersViewer.setSelection(new StructuredSelection(provider));
				break;
			}
		}
	}

	private Map<String, Control> providerControls = new HashMap<String, Control>();

	private Map<String, ECPProperties> providerProperties = new HashMap<String, ECPProperties>();

	private void showProviderUI(ECPProvider provider) {
		if (providerStack == null) {
			return;
		}
		String name = provider.getName();
		Control control = providerControls.get(name);
		if (control == null) {
			ECPProperties properties = ECPUtil.createProperties();

			UIProvider uiProvider = UIProviderRegistry.INSTANCE.getUIProvider(name);
			// since we don't have a dedicated Label-Text but use the Name-Text, we use repositoryNameText twice
			control = uiProvider.createAddRepositoryUI(providerStack, properties, repositoryNameText,
				repositoryNameText, repositoryDescriptionText);
			providerControls.put(name, control);
			providerProperties.put(name, properties);
		}

		providerStackLayout.topControl = control;
		providerStack.layout();
	}

	/** {@inheritDoc} **/
	public ECPProvider getProvider() {
		return provider;
	}

	/** {@inheritDoc} **/
	public String getRepositoryName() {
		return repositoryName;
	}

	/** {@inheritDoc} **/
	public String getRepositoryDescription() {
		return repositoryDescription;
	}

	/** {@inheritDoc} **/
	public ECPProperties getProperties() {
		if (provider == null) {
			return null;
		}

		return providerProperties.get(provider.getName());
	}

	/** {@inheritDoc} **/
	public String getRepositoryLabel() {
		// since we don't have a dedicated label-textfield we use the repository name
		return repositoryName;
	}

	/** {@inheritDoc} **/
	public void setListener(AddRepositoryChangeListener listener) {
		this.listener = listener;
	}

	/** {@inheritDoc} **/
	public void dispose() {

	}
}
