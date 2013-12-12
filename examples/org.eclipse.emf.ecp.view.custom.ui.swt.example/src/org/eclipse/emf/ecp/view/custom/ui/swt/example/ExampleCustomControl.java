package org.eclipse.emf.ecp.view.custom.ui.swt.example;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecp.edit.internal.swt.util.SWTRenderingHelper;
import org.eclipse.emf.ecp.view.spi.custom.swt.ECPAbstractCustomControlSWT;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VFeaturePathDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VViewFactory;
import org.eclipse.emf.ecp.view.spi.renderer.RenderingResultRow;
import org.eclipse.emf.emfstore.bowling.BowlingPackage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;

public class ExampleCustomControl extends ECPAbstractCustomControlSWT {

	private static VFeaturePathDomainModelReference nameFeature;
	private static VFeaturePathDomainModelReference eMailFeature;

	public ExampleCustomControl() {
		super();
	}

	public Set<VDomainModelReference> getNeededDomainModelReferences() {
		Set<VDomainModelReference> result=new LinkedHashSet<VDomainModelReference>();
		nameFeature=VViewFactory.eINSTANCE.createFeaturePathDomainModelReference();
		nameFeature.setDomainModelEFeature(BowlingPackage.eINSTANCE.getPlayer_Name());
		result.add(nameFeature);
		
		eMailFeature=VViewFactory.eINSTANCE.createFeaturePathDomainModelReference();
		eMailFeature.setDomainModelEFeature(BowlingPackage.eINSTANCE.getPlayer_EMails());
		result.add(eMailFeature);
		
		return result;
	}

	@Override
	public List<RenderingResultRow<Control>> createControl(
			Composite composite) {
		final List<RenderingResultRow<Control>> result = new ArrayList<RenderingResultRow<Control>>();

		Label label = new Label(composite, SWT.NONE);
		label.setText("ExampleLabel");
		Composite nameComposite = createControl(nameFeature, composite);

		result.add(SWTRenderingHelper.INSTANCE.getResultRowFactory()
				.createRenderingResultRow(label, nameComposite));

		Composite emailComposite = createControl(eMailFeature, composite);

		result.add(SWTRenderingHelper.INSTANCE.getResultRowFactory()
				.createRenderingResultRow(emailComposite));

		return result;
	}

	@Override
	protected void handleContentValidation(int severity,
			EStructuralFeature feature) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void resetContentValidation() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void disposeCustomControl() {
		// TODO Auto-generated method stub

	}


	

}
