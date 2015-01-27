/*******************************************************************************
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.ui.editor.test;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecp.view.internal.table.swt.CellReadOnlyTesterHelper;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VFeaturePathDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VView;
import org.eclipse.emf.ecp.view.spi.model.VViewFactory;
import org.eclipse.emf.ecp.view.spi.table.model.VTableControl;
import org.eclipse.emf.ecp.view.spi.table.model.VTableFactory;
import org.eclipse.emf.ecp.view.spi.table.swt.ECPCellReadOnlyTester;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTable;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotText;

/**
 * @author Eugen
 *
 */
public class CellReadOnly_PTest extends ECPCommonSWTBotTest {

	private EClass superType1;
	private EClass superType2;

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.ui.editor.test.ECPCommonSWTBotTest#createDomainObject()
	 */
	@Override
	public EObject createDomainObject() {
		final EClass eClass = EcoreFactory.eINSTANCE.createEClass();
		superType1 = EcoreFactory.eINSTANCE.createEClass();
		superType2 = EcoreFactory.eINSTANCE.createEClass();
		eClass.getESuperTypes().add(superType1);
		eClass.getESuperTypes().add(superType2);

		superType1.setName("a");
		superType2.setName("b");
		return eClass;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.ui.editor.test.ECPCommonSWTBotTest#createView()
	 */
	@Override
	public VView createView() {

		final ECPCellReadOnlyTester tester = new ECPCellReadOnlyTester() {

			@Override
			public boolean isCellReadOnly(VTableControl vTableControl, Setting setting) {
				if (!EClass.class.isInstance(setting.getEObject())) {
					return false;
				}
				if (!EcorePackage.eINSTANCE.getENamedElement_Name().equals(setting.getEStructuralFeature())) {
					return false;
				}
				final EClass eClass = EClass.class.cast(setting.getEObject());
				if ("a".equals(eClass.getName())) {
					return true;
				}
				return false;
			}
		};
		CellReadOnlyTesterHelper.getInstance().registerCellReadOnlyTester(tester);

		final TableControlHandle tableControlHandle = createInitializedTableWithoutTableColumns();

		final VDomainModelReference tableColumn1 = createTableColumn(EcorePackage.eINSTANCE
			.getENamedElement_Name());
		tableControlHandle.addFirstTableColumn(tableColumn1);

		final VDomainModelReference tableColumn2 = createTableColumn(EcorePackage.eINSTANCE
			.getEClass_Interface());
		tableControlHandle.addSecondTableColumn(tableColumn2);

		final VView vview = VViewFactory.eINSTANCE.createView();

		vview.getChildren().add(tableControlHandle.getTableControl());
		vview.setRootEClass(EcorePackage.eINSTANCE.getEClass());

		return vview;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.ui.editor.test.ECPCommonSWTBotTest#logic()
	 */
	@Override
	public void logic() {
		final SWTBotTable table = bot.table();
		table.click(0, 1);
		bot.sleep(1000);
		final SWTBotText textCell01 = bot.text();
		Display.getDefault().syncExec(new Runnable() {

			@Override
			public void run() {
				assertFalse(textCell01.widget.getEditable());
			}
		});

		table.click(0, 2);
		Display.getDefault().syncExec(new Runnable() {

			@Override
			public void run() {
				assertTrue(superType1.isInterface());
			}
		});
		table.click(1, 1);
		bot.sleep(1000);
		final SWTBotText textCell11 = bot.text();
		Display.getDefault().syncExec(new Runnable() {

			@Override
			public void run() {
				assertTrue(textCell11.widget.getEditable());
			}
		});
		table.click(1, 2);
		Display.getDefault().syncExec(new Runnable() {

			@Override
			public void run() {
				assertTrue(superType2.isInterface());
			}
		});
	}

	public static VDomainModelReference createTableColumn(EStructuralFeature feature) {
		final VFeaturePathDomainModelReference reference = VViewFactory.eINSTANCE
			.createFeaturePathDomainModelReference();
		reference.setDomainModelEFeature(feature);
		return reference;
	}

	public static TableControlHandle createInitializedTableWithoutTableColumns() {
		final TableControlHandle tableControlHandle = createUninitializedTableWithoutColumns();
		final VFeaturePathDomainModelReference domainModelReference = VTableFactory.eINSTANCE
			.createTableDomainModelReference();
		domainModelReference.setDomainModelEFeature(EcorePackage.eINSTANCE.getEClass_ESuperTypes());
		tableControlHandle.getTableControl().setDomainModelReference(domainModelReference);

		return tableControlHandle;
	}

	public static TableControlHandle createUninitializedTableWithoutColumns() {
		final VTableControl tableControl = createTableControl();
		return new TableControlHandle(tableControl);
	}

	private static VTableControl createTableControl() {
		final VTableControl tc = VTableFactory.eINSTANCE.createTableControl();
		tc.setDomainModelReference(VTableFactory.eINSTANCE.createTableDomainModelReference());
		return tc;
	}
}
