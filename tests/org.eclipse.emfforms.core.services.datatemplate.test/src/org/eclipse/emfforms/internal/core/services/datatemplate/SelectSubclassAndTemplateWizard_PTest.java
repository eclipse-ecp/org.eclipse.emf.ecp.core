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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecp.view.test.common.swt.spi.SWTTestUtil;
import org.eclipse.emfforms.common.Optional;
import org.eclipse.emfforms.core.services.datatemplate.test.model.audit.AuditPackage;
import org.eclipse.emfforms.datatemplate.DataTemplateFactory;
import org.eclipse.emfforms.datatemplate.Template;
import org.eclipse.emfforms.spi.localization.EMFFormsLocalizationService;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTException;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.osgi.framework.Bundle;

/**
 * Plugin tests for the {@link SelectSubclassAndTemplateWizard}.
 * <p>
 * <strong>Note:</strong> The wizard itself would be unit testable but the used selection composites use the
 * LocalizationServiceHelper which only works in a Plugin execution context.
 *
 * @author Lucas Koehler
 *
 */
public class SelectSubclassAndTemplateWizard_PTest {

	private EMFFormsLocalizationService localizationService;
	private Shell shell;

	/**
	 * @throws SWTException if the Shell cannot be instantiated
	 */
	@Before
	public void setUp() throws SWTException {
		localizationService = mock(EMFFormsLocalizationService.class);
		when(localizationService.getString(any(Class.class), any(String.class))).thenReturn(""); //$NON-NLS-1$
		when(localizationService.getString(any(Bundle.class), any(String.class))).thenAnswer(
			new Answer<String>() {
				@SuppressWarnings("nls")
				@Override
				public String answer(InvocationOnMock invocation) throws Throwable {
					final String key = (String) invocation.getArguments()[1];
					if ("_UI_AbstractSubUser_type".equals(key)) {
						return "Abstract Sub User";
					}
					if ("_UI_AdminUser_type".equals(key)) {
						return "Admin User";
					}
					if ("_UI_GuestUser_type".equals(key)) {
						return "Guest User";
					}
					if ("_UI_PrivilegedUser_type".equals(key)) {
						return "Privileged User";
					}
					if ("_UI_RegisteredUser_type".equals(key)) {
						return "Registered User";
					}
					if ("_UI_UserGroup_type".equals(key)) {
						return "User Group";
					}
					if ("_UI_User_type".equals(key)) {
						return "User";
					}
					return "";
				}
			});
		shell = new Shell();
	}

	@After
	public void tearDown() {
		shell.dispose();
	}

	/**
	 * Tests the standard use case: first selecting an EClass and then a template on the next page.
	 */
	@Test
	public void testSubClassAndTemplateSelection() {
		// Add selectable EClasses
		final LinkedHashSet<EClass> subClasses = new LinkedHashSet<EClass>();
		subClasses.add(AuditPackage.eINSTANCE.getAdminUser());
		subClasses.add(AuditPackage.eINSTANCE.getRegisteredUser());

		// Configure templates
		final Template template1 = createTemplate("AdminTemplate1", AuditPackage.eINSTANCE.getAdminUser()); //$NON-NLS-1$
		final Template template2 = createTemplate("RegisteredUserTemplate1", //$NON-NLS-1$
			AuditPackage.eINSTANCE.getRegisteredUser());

		final LinkedHashSet<Template> templates = new LinkedHashSet<Template>();
		templates.add(template1);
		templates.add(template2);

		// Create and open wizard
		final SelectSubclassAndTemplateWizard wizard = new SelectSubclassAndTemplateWizard("Wizard", subClasses, //$NON-NLS-1$
			templates, localizationService);

		final WizardDialog wizardDialog = openWizardDialog(wizard);

		// Get the wizard dialog's shell from our parent shell
		final Shell wizardShell = shell.getShells()[0];
		final Composite dialogComposite = (Composite) Composite.class.cast(wizardShell.getChildren()[0])
			.getChildren()[0];

		// Get wizard buttons
		final Composite buttonComposite = getButtonComposite(wizardDialog);
		final Composite innerButtonComposite = (Composite) buttonComposite.getChildren()[0];
		final Button backButton = (Button) innerButtonComposite.getChildren()[0];
		final Button nextButton = (Button) innerButtonComposite.getChildren()[1];
		final Button cancelButton = (Button) buttonComposite.getChildren()[1];
		final Button finishButton = (Button) buttonComposite.getChildren()[2];

		// Get the composites containing the wizard pages
		final Composite pagesComposite = (Composite) Composite.class.cast(dialogComposite.getChildren()[0])
			.getChildren()[1];
		final Composite subClassPageComposite = (Composite) pagesComposite.getChildren()[0];
		final Composite templateSelectionPageComposite = (Composite) pagesComposite.getChildren()[1];

		final Tree subClassTable = (Tree) subClassPageComposite.getChildren()[2];
		assertEquals(2, subClassTable.getItemCount());

		// Select the registered user class
		final TreeItem item = selectTreeItem(subClassTable, 1);
		SWTTestUtil.waitForUIThread();
		assertEquals("RegisteredUser", item.getText()); //$NON-NLS-1$

		// verify button enablement
		assertFalse(backButton.isEnabled());
		assertTrue(nextButton.isEnabled());
		assertTrue(cancelButton.isEnabled());
		assertFalse(finishButton.isEnabled());

		// click next button and wait for next page
		SWTTestUtil.clickButton(nextButton);
		SWTTestUtil.waitForUIThread();

		final Table templateTable = (Table) templateSelectionPageComposite.getChildren()[2];

		// Next button must not be enabled on final page
		assertFalse(nextButton.isEnabled());

		assertEquals(2, templateTable.getItemCount());
		selectTableItem(templateTable, 0);
		SWTTestUtil.waitForUIThread();

		// Finish
		SWTTestUtil.clickButton(finishButton);
		SWTTestUtil.waitForUIThread();

		// Evaluate wizard result
		assertEquals(Window.OK, wizardDialog.getReturnCode());
		final Optional<Template> templateOptional = wizard.getSelectedTemplate();
		assertTrue(templateOptional.isPresent());
		assertEquals(template1, templateOptional.get());
	}

	/**
	 * Test that the sub class selection page is skipped if there is only one selectable {@link EClass}.
	 */
	@Test
	public void testTemplateSelectionOnly() {
		// Only add one sub class
		final LinkedHashSet<EClass> subClasses = new LinkedHashSet<EClass>();
		subClasses.add(AuditPackage.eINSTANCE.getRegisteredUser());

		// Configure templates
		final Template template1 = createTemplate("AdminTemplate1", AuditPackage.eINSTANCE.getAdminUser()); //$NON-NLS-1$
		final Template template2 = createTemplate("RegisteredUserTemplate1", //$NON-NLS-1$
			AuditPackage.eINSTANCE.getRegisteredUser());

		final LinkedHashSet<Template> templates = new LinkedHashSet<Template>();
		templates.add(template1);
		templates.add(template2);

		// Create and open wizard
		final SelectSubclassAndTemplateWizard wizard = new SelectSubclassAndTemplateWizard("Wizard", subClasses, //$NON-NLS-1$
			templates, localizationService);
		final WizardDialog wizardDialog = openWizardDialog(wizard);

		// Get the wizard dialog's shell from our parent shell
		final Shell wizardShell = shell.getShells()[0];
		final Composite dialogComposite = (Composite) Composite.class.cast(wizardShell.getChildren()[0])
			.getChildren()[0];

		// Get wizard buttons
		// final Composite buttonComposite = (Composite) Composite.class.cast(dialogComposite.getChildren()[1])
		// .getChildren()[0];
		final Composite buttonComposite = getButtonComposite(wizardDialog);
		final Button cancelButton = (Button) buttonComposite.getChildren()[0];
		final Button finishButton = (Button) buttonComposite.getChildren()[1];

		// Get the composites containing the template selection page
		final Composite pagesComposite = (Composite) Composite.class.cast(dialogComposite.getChildren()[0])
			.getChildren()[1];
		final Composite templateSelectionPageComposite = (Composite) pagesComposite.getChildren()[0];

		// Verify that the wizard starts at the template selection page
		assertTrue(wizardDialog.getCurrentPage() instanceof SelectSubclassAndTemplateWizard.TemplateSelectionPage);

		SWTTestUtil.waitForUIThread();

		assertTrue(cancelButton.isEnabled());
		assertFalse(finishButton.isEnabled());

		final Table templateTable = (Table) templateSelectionPageComposite.getChildren()[2];

		assertEquals(2, templateTable.getItemCount());
		selectTableItem(templateTable, 0);
		SWTTestUtil.waitForUIThread();

		// Finish
		assertTrue(finishButton.isEnabled());
		SWTTestUtil.clickButton(finishButton);
		SWTTestUtil.waitForUIThread();

		// Evaluate wizard result
		assertEquals(Window.OK, wizardDialog.getReturnCode());
		final Optional<Template> templateOptional = wizard.getSelectedTemplate();
		assertTrue(templateOptional.isPresent());
		assertEquals(template1, templateOptional.get());
	}

	/**
	 * Tests that the template selection page is not shown if there is only one template for the selected EClass.
	 */
	@Test
	public void testSubClassSelectionOnly() {
		// Add selectable EClasses
		final LinkedHashSet<EClass> subClasses = new LinkedHashSet<EClass>();
		subClasses.add(AuditPackage.eINSTANCE.getAdminUser());
		subClasses.add(AuditPackage.eINSTANCE.getGuestUser());

		// Configure templates
		final Template template1 = createTemplate("AdminTemplate1", AuditPackage.eINSTANCE.getAdminUser()); //$NON-NLS-1$
		final Template template2 = createTemplate("GuestUserTemplate1", AuditPackage.eINSTANCE.getGuestUser()); //$NON-NLS-1$

		final LinkedHashSet<Template> templates = new LinkedHashSet<Template>();
		templates.add(template1);
		templates.add(template2);

		// Create and open wizard
		final SelectSubclassAndTemplateWizard wizard = new SelectSubclassAndTemplateWizard("Wizard", subClasses, //$NON-NLS-1$
			templates, localizationService);
		final WizardDialog wizardDialog = openWizardDialog(wizard);

		SWTTestUtil.waitForUIThread();

		// Get the wizard dialog's shell from our parent shell
		final Shell wizardShell = shell.getShells()[0];
		final Composite dialogComposite = (Composite) Composite.class.cast(wizardShell.getChildren()[0])
			.getChildren()[0];

		// Get wizard buttons
		final Composite buttonComposite = getButtonComposite(wizardDialog);
		final Composite innerButtonComposite = (Composite) buttonComposite.getChildren()[0];
		final Button backButton = (Button) innerButtonComposite.getChildren()[0];
		final Button nextButton = (Button) innerButtonComposite.getChildren()[1];
		final Button cancelButton = (Button) buttonComposite.getChildren()[1];
		final Button finishButton = (Button) buttonComposite.getChildren()[2];

		// Get the composites containing the wizard pages
		final Composite pagesComposite = (Composite) Composite.class.cast(dialogComposite.getChildren()[0])
			.getChildren()[1];
		final Composite subClassPageComposite = (Composite) pagesComposite.getChildren()[0];

		final Tree subClassTree = (Tree) subClassPageComposite.getChildren()[2];
		assertEquals(2, subClassTree.getItemCount());

		// Select the guest user class
		final TreeItem item = selectTreeItem(subClassTree, 1);
		SWTTestUtil.waitForUIThread();
		assertEquals("GuestUser", item.getText()); //$NON-NLS-1$

		// Because there is only one template for the guest user EClass the wizard should offer to finish directly but
		// not to go to the template selection page
		assertFalse(backButton.isEnabled());
		assertFalse(nextButton.isEnabled());
		assertTrue(cancelButton.isEnabled());
		assertTrue(finishButton.isEnabled());

		// Finish
		SWTTestUtil.clickButton(finishButton);
		SWTTestUtil.waitForUIThread();

		// Evaluate wizard result
		assertEquals(Window.OK, wizardDialog.getReturnCode());
		final Optional<Template> templateOptional = wizard.getSelectedTemplate();
		assertTrue(templateOptional.isPresent());
		assertEquals(template2, templateOptional.get());
	}

	/**
	 * Tests that the template selection table gets updated correctly after the back functionality of the wizard was
	 * used.
	 */
	@Test
	public void testBackButton() {
		// Add selectable EClasses
		final LinkedHashSet<EClass> subClasses = new LinkedHashSet<EClass>();
		subClasses.add(AuditPackage.eINSTANCE.getAdminUser());
		subClasses.add(AuditPackage.eINSTANCE.getGuestUser());

		// Configure 2 templates for each EClass
		final Template adminTemplate1 = createTemplate("AdminTemplate1", AuditPackage.eINSTANCE.getAdminUser()); //$NON-NLS-1$
		final Template adminTemplate2 = createTemplate("AdminTemplate2", AuditPackage.eINSTANCE.getAdminUser()); //$NON-NLS-1$
		final Template guestTemplate1 = createTemplate("GuestUserTemplate1", AuditPackage.eINSTANCE.getGuestUser()); //$NON-NLS-1$
		final Template guestTemplate2 = createTemplate("GuestUserTemplate2", AuditPackage.eINSTANCE.getGuestUser()); //$NON-NLS-1$

		final LinkedHashSet<Template> templates = new LinkedHashSet<Template>();
		templates.add(adminTemplate1);
		templates.add(adminTemplate2);
		templates.add(guestTemplate1);
		templates.add(guestTemplate2);

		// Create and open wizard
		final SelectSubclassAndTemplateWizard wizard = new SelectSubclassAndTemplateWizard("Wizard", subClasses, //$NON-NLS-1$
			templates, localizationService);
		final WizardDialog wizardDialog = openWizardDialog(wizard);

		// Get the wizard dialog's shell from our parent shell
		final Shell wizardShell = shell.getShells()[0];
		final Composite dialogComposite = (Composite) Composite.class.cast(wizardShell.getChildren()[0])
			.getChildren()[0];

		// Get wizard buttons
		final Composite buttonComposite = getButtonComposite(wizardDialog);
		final Composite innerButtonComposite = (Composite) buttonComposite.getChildren()[0];
		final Button backButton = (Button) innerButtonComposite.getChildren()[0];
		final Button nextButton = (Button) innerButtonComposite.getChildren()[1];
		// final Button cancelButton = (Button) buttonComposite.getChildren()[1];
		final Button finishButton = (Button) buttonComposite.getChildren()[2];

		// Get the composites containing the wizard pages
		final Composite pagesComposite = (Composite) Composite.class.cast(dialogComposite.getChildren()[0])
			.getChildren()[1];
		final Composite subClassPageComposite = (Composite) pagesComposite.getChildren()[0];
		final Composite templateSelectionPageComposite = (Composite) pagesComposite.getChildren()[1];

		final Tree subClassTable = (Tree) subClassPageComposite.getChildren()[2];
		assertEquals(2, subClassTable.getItemCount());

		// Select the admin user class
		selectTreeItem(subClassTable, 0);

		SWTTestUtil.waitForUIThread();

		// Next
		SWTTestUtil.clickButton(nextButton);
		SWTTestUtil.waitForUIThread();

		final Table templateTable = (Table) templateSelectionPageComposite.getChildren()[2];

		assertEquals(2, templateTable.getItemCount());
		assertTrue(templateTable.getItem(0).getText().contains("AdminTemplate1")); //$NON-NLS-1$
		assertTrue(templateTable.getItem(1).getText().contains("AdminTemplate2")); //$NON-NLS-1$

		// Go back
		SWTTestUtil.waitForUIThread();
		SWTTestUtil.clickButton(backButton);

		// Select guest user class and click next again
		selectTreeItem(subClassTable, 1);
		SWTTestUtil.waitForUIThread();
		assertTrue(nextButton.isEnabled());
		SWTTestUtil.clickButton(nextButton);
		SWTTestUtil.waitForUIThread();

		// Verify that the template selection table was updated correctly
		assertEquals(2, templateTable.getItemCount());
		assertTrue(templateTable.getItem(0).getText().contains("GuestUserTemplate1")); //$NON-NLS-1$
		assertTrue(templateTable.getItem(1).getText().contains("GuestUserTemplate2")); //$NON-NLS-1$

		selectTableItem(templateTable, 1);
		SWTTestUtil.clickButton(finishButton);
		SWTTestUtil.waitForUIThread();

		// Evaluate wizard result
		assertEquals(Window.OK, wizardDialog.getReturnCode());
		final Optional<Template> templateOptional = wizard.getSelectedTemplate();
		assertTrue(templateOptional.isPresent());
		assertEquals(guestTemplate2, templateOptional.get());
	}

	/**
	 * If the wizard is cancelled it should return an empty Optional as a result even if a template was selected by the
	 * user.
	 */
	@Test
	public void testCancel() {
		// Only add one sub class
		final LinkedHashSet<EClass> subClasses = new LinkedHashSet<EClass>();
		subClasses.add(AuditPackage.eINSTANCE.getRegisteredUser());

		// Configure templates
		final Template template1 = createTemplate("AdminTemplate1", AuditPackage.eINSTANCE.getAdminUser()); //$NON-NLS-1$
		final Template template2 = createTemplate("RegisteredUserTemplate1", //$NON-NLS-1$
			AuditPackage.eINSTANCE.getRegisteredUser());

		final LinkedHashSet<Template> templates = new LinkedHashSet<Template>();
		templates.add(template1);
		templates.add(template2);

		// Create and open wizard
		final SelectSubclassAndTemplateWizard wizard = new SelectSubclassAndTemplateWizard("Wizard", subClasses, //$NON-NLS-1$
			templates, localizationService);
		final WizardDialog wizardDialog = openWizardDialog(wizard);

		// Get the wizard dialog's shell from our parent shell
		final Shell wizardShell = shell.getShells()[0];
		final Composite dialogComposite = (Composite) Composite.class.cast(wizardShell.getChildren()[0])
			.getChildren()[0];

		// Get wizard buttons
		final Composite buttonComposite = getButtonComposite(wizardDialog);
		final Button cancelButton = (Button) buttonComposite.getChildren()[0];
		final Button finishButton = (Button) buttonComposite.getChildren()[1];

		// Get the composites containing the template selection page
		final Composite pagesComposite = (Composite) Composite.class.cast(dialogComposite.getChildren()[0])
			.getChildren()[1];
		final Composite templateSelectionPageComposite = (Composite) pagesComposite.getChildren()[0];

		// Verify that the wizard starts at the template selection page
		assertTrue(wizardDialog.getCurrentPage() instanceof SelectSubclassAndTemplateWizard.TemplateSelectionPage);

		assertTrue(cancelButton.isEnabled());
		assertFalse(finishButton.isEnabled());

		final Table templateTable = (Table) templateSelectionPageComposite.getChildren()[2];

		// Select a template
		assertEquals(2, templateTable.getItemCount());
		selectTableItem(templateTable, 0);
		SWTTestUtil.waitForUIThread();

		// Cancel after a template was selected
		assertTrue(cancelButton.isEnabled());
		SWTTestUtil.clickButton(cancelButton);
		SWTTestUtil.waitForUIThread();

		// Evaluate wizard result: the result Optional should be empty
		assertEquals(Window.CANCEL, wizardDialog.getReturnCode());
		final Optional<Template> templateOptional = wizard.getSelectedTemplate();
		assertFalse(templateOptional.isPresent());
	}

	@Test
	public void testGetAvailableTemplates() {
		final Set<EClass> subClasses = Collections.emptySet();
		final Set<Template> templates = new LinkedHashSet<Template>();
		// corupt eclass
		final Template templateInvalid = mock(Template.class);
		final EObject templateInstanceInvalid = mock(EObject.class);
		when(templateInvalid.getInstance()).thenReturn(templateInstanceInvalid);
		when(templateInstanceInvalid.eClass()).thenThrow(new IllegalArgumentException());
		templates.add(templateInvalid);
		// valid template
		final Template templateValid = mock(Template.class);
		final EObject templateInstanceValid = mock(EObject.class);
		when(templateValid.getInstance()).thenReturn(templateInstanceValid);
		when(templateInstanceValid.eClass()).thenReturn(EcorePackage.eINSTANCE.getEAttribute());
		templates.add(templateValid);

		final SelectSubclassAndTemplateWizard wizard = new SelectSubclassAndTemplateWizard("", subClasses, templates, //$NON-NLS-1$
			localizationService);
		final Set<Template> filteredTemplates = wizard.getAvailableTemplates(EcorePackage.eINSTANCE.getEAttribute());
		assertEquals(1, filteredTemplates.size());
		assertEquals(templateValid, filteredTemplates.iterator().next());
	}

	/**
	 * Gets the button composite containing the cancel, finish, and sometimes the back and next buttons.
	 *
	 * @param wizardDialog The {@link WizardDialog} to get the button composite from
	 * @return The {@link Composite} containing the wizard's buttons
	 */
	private Composite getButtonComposite(final WizardDialog wizardDialog) {
		final Composite buttonBar = (Composite) wizardDialog.buttonBar;
		// take the second as the first is a toolbar with help entries
		final Composite buttonComposite = (Composite) buttonBar.getChildren()[1];
		return buttonComposite;
	}

	/**
	 * Selects the index in the given table and notifies the table's listeners about the selection
	 *
	 * @param table
	 * @param index
	 * @return the selected table item
	 */
	private TreeItem selectTreeItem(Tree tree, int index) {
		tree.setSelection(tree.getItem(index));
		final Event event = new Event();
		event.type = SWT.Selection;
		event.widget = tree;
		final TreeItem result = tree.getItem(index);
		event.item = result;
		tree.notifyListeners(SWT.Selection, event);
		return result;
	}

	private TableItem selectTableItem(Table table, int index) {
		table.setSelection(index);
		final Event event = new Event();
		event.type = SWT.Selection;
		event.widget = table;
		final TableItem result = table.getItem(index);
		event.item = result;
		table.notifyListeners(SWT.Selection, event);
		return result;
	}

	/**
	 * Open the wizard in a wizard dialog and wait for the UI to finish opening it.
	 *
	 * @param wizard The wizard to open
	 * @return The opened {@link WizardDialog}
	 */
	private WizardDialog openWizardDialog(final Wizard wizard) {
		final WizardDialog wizardDialog = new WizardDialog(shell, wizard);
		wizardDialog.setBlockOnOpen(false);
		wizardDialog.open();
		SWTTestUtil.waitForUIThread();
		return wizardDialog;
	}

	/**
	 * Creates a new {@link Template} with the given name and an instance of the given {@link EClass}
	 *
	 * @param name The template's name
	 * @param instanceClass The instance's type
	 * @return The created {@link Template}
	 */
	private Template createTemplate(String name, EClass instanceClass) {
		final Template template = DataTemplateFactory.eINSTANCE.createTemplate();
		template.setName(name);
		final EObject instance = instanceClass.getEPackage().getEFactoryInstance().create(instanceClass);
		template.setInstance(instance);
		return template;
	}
}
