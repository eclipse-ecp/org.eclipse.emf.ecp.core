/*******************************************************************************
 * Copyright (c) 2011-2018 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Lucas Koehler - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.internal.core.services.datatemplate;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecp.spi.common.ui.CompositeFactory;
import org.eclipse.emf.ecp.spi.common.ui.composites.SelectionComposite;
import org.eclipse.emfforms.common.Optional;
import org.eclipse.emfforms.datatemplate.Template;
import org.eclipse.emfforms.spi.localization.EMFFormsLocalizationService;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.widgets.Composite;

/**
 * Wizard that allows to select an {@link EClass} on the first page and a model element on the second page. Thereby, the
 * second page only shows model elements that are an instance of the selected EClass.
 * <p>
 * If there is only one possible EClass, the class selection page is skipped and the template selection is shown
 * directly.
 * <br />
 * If there is only one template left after selecting the EClass, the template selection page is skipped and the
 * remaining {@link Template} counts as selected.
 *
 * @author Lucas Koehler
 *
 */
public class SelectSubclassAndTemplateWizard extends Wizard {

	private static final String TEMPLATE_PAGE_NAME = "templateSelectionPage"; //$NON-NLS-1$
	private static final String CLASS_PAGE_NAME = "subClassSelectionPage"; //$NON-NLS-1$

	private SubClassSelectionPage subClassSelectionPage;
	private TemplateSelectionPage modelElementPage;
	private final Set<EClass> subClasses;
	private final Set<Template> allTemplates;
	private boolean showTemplateSelectionPage = true;
	private final EMFFormsLocalizationService localizationService;

	private EClass selectedSubClass;
	private boolean finished;

	/**
	 * Creates a new instance.
	 *
	 * @param windowTitle The title of the wizard
	 * @param subClasses The EClasses to choose from
	 * @param templates The available templates for all given EClasses
	 * @param localizationService The {@link EMFFormsLocalizationService}
	 */
	public SelectSubclassAndTemplateWizard(String windowTitle, Set<EClass> subClasses, Set<Template> templates,
		EMFFormsLocalizationService localizationService) {
		setWindowTitle(windowTitle);
		this.subClasses = subClasses;
		this.localizationService = localizationService;
		allTemplates = templates;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.jface.wizard.Wizard#performFinish()
	 */
	@Override
	public boolean performFinish() {
		finished = true;
		return true;
	}

	@Override
	public boolean canFinish() {
		if (selectedSubClass != null && !showTemplateSelectionPage) {
			return true;
		}
		return super.canFinish();
	}

	/**
	 * Returns the template selected by the user. If there is only one valid template left after selecting a sub class,
	 * this template is selected automatically without showing the second wizard page.
	 *
	 * @return The selected {@link Template}, or the empty Optional if none was selected
	 */
	public Optional<Template> getSelectedTemplate() {
		if (!finished) {
			// Return an empty Optional in case the wizard was not finished successfully (e.g. by canceling it)
			return Optional.empty();
		}
		if (showTemplateSelectionPage) {
			final Object[] selection = modelElementPage.getSelectionComposite().getSelection();
			if (selection != null && selection.length > 0) {
				return Optional.of((Template) selection[0]);
			}
		} else {
			final Set<Template> templates = getAvailableTemplates(selectedSubClass);
			if (templates.iterator().hasNext()) {
				return Optional.of(templates.iterator().next());
			}
		}
		return Optional.empty();
	}

	@Override
	public void addPages() {
		modelElementPage = new TemplateSelectionPage(TEMPLATE_PAGE_NAME,
			localizationService.getString(SelectSubclassAndTemplateWizard.class,
				MessageKeys.SelectSubclassAndTemplateWizard_selectTemplateTitle),
			localizationService.getString(SelectSubclassAndTemplateWizard.class,
				MessageKeys.SelectSubclassAndTemplateWizard_selectTemplateDescription));

		if (subClasses.size() > 1) {
			subClassSelectionPage = new SubClassSelectionPage(CLASS_PAGE_NAME,
				localizationService.getString(SelectSubclassAndTemplateWizard.class,
					MessageKeys.SelectSubclassAndTemplateWizard_selectSubClassTitle),
				localizationService.getString(SelectSubclassAndTemplateWizard.class,
					MessageKeys.SelectSubclassAndTemplateWizard_selectSubClassDescription),
				subClasses);
			addPage(subClassSelectionPage);
		}

		addPage(modelElementPage);

		if (subClasses.size() == 1) {
			selectedSubClass = subClasses.iterator().next();
		}
		super.addPages();

	}

	@Override
	public IWizardPage getNextPage(IWizardPage page) {
		if (page == subClassSelectionPage && !showTemplateSelectionPage) {
			// Do not show the template selection page if there is no need to
			return null;
		}
		return super.getNextPage(page);
	}

	/**
	 * Update the selected sub class. This is typically called by the {@link SubClassSelectionPage}.
	 *
	 * @param subClass The selected sub class
	 */
	protected void setSubClass(EClass subClass) {
		// Necessary to update in case the user goes back in the wizard and selects a different sub class.
		final Set<Template> availableTemplates = getAvailableTemplates(subClass);
		showTemplateSelectionPage = availableTemplates.size() > 1;
		modelElementPage.updateSelectionComposite(availableTemplates);
		selectedSubClass = subClass;
	}

	/**
	 * Get all available templates for the given {@link EClass}.
	 *
	 * @param eClass The {@link EClass}
	 * @return The available templates
	 */
	protected Set<Template> getAvailableTemplates(EClass eClass) {
		final Set<Template> availableTemplates = new LinkedHashSet<Template>();
		if (eClass != null) {
			for (final Template template : allTemplates) {
				if (eClass.isSuperTypeOf(template.getInstance().eClass())) {
					availableTemplates.add(template);
				}
			}
		}

		return availableTemplates;
	}

	/**
	 * Abstract base class for the eclass and template selection pages.
	 *
	 * @author Lucas Koehler
	 */
	abstract class SelectionPage extends WizardPage {

		/**
		 * @param pageName The page's unique name
		 * @param pageTitle The title shown for this wizard page
		 * @param pageDescription The description shown on this wizard page
		 */
		protected SelectionPage(String pageName, String pageTitle, String pageDescription) {
			super(pageName);
			setTitle(pageTitle);
			setDescription(pageDescription);
		}

		@Override
		public void createControl(Composite parent) {
			final Composite composite = getSelectionComposite().createUI(parent);
			if (getSelectionComposite().getViewer() instanceof TreeViewer) {
				final TreeViewer tv = (TreeViewer) getSelectionComposite().getViewer();
				tv.expandToLevel(2);
			}
			getSelectionComposite().getViewer().addSelectionChangedListener(getSelectionChangedListener());
			setPageComplete(false);
			setControl(composite);

		}

		/**
		 * @return The selection listener that is called when the selection composite's selection changes
		 */
		protected abstract ISelectionChangedListener getSelectionChangedListener();

		/**
		 * @return The {@link SelectionComposite} that provides the selection viewer
		 */
		protected abstract SelectionComposite<? extends ColumnViewer> getSelectionComposite();
	}

	/**
	 * Page for selecting the sub {@link EClass}.
	 *
	 * @author Lucas Koehler
	 *
	 */
	class SubClassSelectionPage extends SelectionPage {
		private final Set<EClass> availableEClasses;
		private SelectionComposite<TableViewer> selectionComposite;

		/**
		 * Create a new sub class selection page that allows to select one of the available e classes.
		 *
		 * @param pageName The page's unique name
		 * @param pageTitle The title shown for this wizard page
		 * @param pageDescription The description shown for this wizard page
		 * @param availableEClasses The set of selectable e classes
		 */
		protected SubClassSelectionPage(String pageName, String pageTitle, String pageDescription,
			Set<EClass> availableEClasses) {
			super(pageName, pageTitle, pageDescription);
			setTitle(pageTitle);
			setDescription(pageDescription);
			this.availableEClasses = availableEClasses;
		}

		@Override
		protected SelectionComposite<? extends ColumnViewer> getSelectionComposite() {
			if (selectionComposite == null) {
				selectionComposite = CompositeFactory.getTableSelectionComposite(availableEClasses.toArray(), false);
			}
			return selectionComposite;
		}

		@Override
		public void createControl(Composite parent) {
			super.createControl(parent);
			getSelectionComposite().getViewer().setLabelProvider(new EClassLabelProvider(localizationService));
		}

		@Override
		protected ISelectionChangedListener getSelectionChangedListener() {
			return new ISelectionChangedListener() {

				@Override
				public void selectionChanged(SelectionChangedEvent event) {
					final IStructuredSelection sel = (IStructuredSelection) getSelectionComposite().getViewer()
						.getSelection();

					if (sel != null && !sel.isEmpty()) {
						setSubClass((EClass) sel.getFirstElement());
						setPageComplete(true);
					} else {
						setPageComplete(false);
					}
				}
			};
		}
	}

	/**
	 * Wizard page to select on template.
	 *
	 * @author Lucas Koehler
	 *
	 */
	class TemplateSelectionPage extends SelectionPage {
		private SelectionComposite<TableViewer> selectionComposite;

		/**
		 * @param pageName The page's unique name
		 * @param pageTitle The title shown for this wizard page
		 * @param pageDescription The description shown for this wizard page
		 */
		protected TemplateSelectionPage(String pageName, String pageTitle, String pageDescription) {
			super(pageName, pageTitle, pageDescription);
		}

		@Override
		protected SelectionComposite<? extends ColumnViewer> getSelectionComposite() {
			if (selectionComposite == null) {
				final Set<Template> templates = selectedSubClass != null ? getAvailableTemplates(selectedSubClass)
					: Collections.<Template> emptySet();
				selectionComposite = CompositeFactory.getTableSelectionComposite(templates, false);
			}
			return selectionComposite;
		}

		/**
		 * Update the selection composite with the given Set of templates.
		 *
		 * @param availableTemplates The Set of selectable templates
		 */
		public void updateSelectionComposite(Set<Template> availableTemplates) {
			getSelectionComposite().getViewer().setInput(availableTemplates);
			setPageComplete(false);
		}

		@Override
		protected ISelectionChangedListener getSelectionChangedListener() {
			return new ISelectionChangedListener() {

				@Override
				public void selectionChanged(SelectionChangedEvent event) {
					final IStructuredSelection sel = (IStructuredSelection) getSelectionComposite().getViewer()
						.getSelection();

					if (sel != null && !sel.isEmpty()) {
						setPageComplete(true);
					} else {
						setPageComplete(false);
					}
				}
			};
		}
	}
}
