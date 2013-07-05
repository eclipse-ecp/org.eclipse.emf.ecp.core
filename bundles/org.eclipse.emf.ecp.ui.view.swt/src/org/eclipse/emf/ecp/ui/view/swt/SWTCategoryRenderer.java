package org.eclipse.emf.ecp.ui.view.swt;

import org.eclipse.emf.ecp.edit.ECPControlContext;
import org.eclipse.emf.ecp.internal.ui.view.renderer.NoPropertyDescriptorFoundExeption;
import org.eclipse.emf.ecp.internal.ui.view.renderer.NoRendererFoundException;
import org.eclipse.emf.ecp.internal.ui.view.renderer.Node;
import org.eclipse.emf.ecp.internal.ui.view.renderer.WithRenderedObject;
import org.eclipse.emf.ecp.view.model.Category;
import org.eclipse.emf.ecp.view.model.Column;
import org.eclipse.emf.ecp.view.model.ColumnComposite;
import org.eclipse.emf.edit.provider.AdapterFactoryItemDelegator;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;

public class SWTCategoryRenderer extends AbstractSWTRenderer<Category> {
	
	private static final Object CONTROL_COLUMN = "org_eclipse_emf_ecp_ui_category";

	@Override
	public Control render(Node<Category> node,
			ECPControlContext controlContext, AdapterFactoryItemDelegator adapterFactoryItemDelegator) throws NoRendererFoundException, NoPropertyDescriptorFoundExeption {
//		return SWTRenderers.INSTANCE.render(getParent(), category.getComposite(), controlContext, adapterFactoryItemDelegator);
		
		Category category = node.getRenderable();
		
		Composite categoryComposite = new Composite(getParent(), SWT.NONE);
		categoryComposite.setBackground(getParent().getBackground());
		// TODO: custom variant
//		columnComposite.setData(CUSTOM_VARIANT, CONTROL_COLUMN_COMPOSITE);
		
		GridLayoutFactory.fillDefaults()
			.numColumns(1)
			.applyTo(categoryComposite);
		GridDataFactory.fillDefaults()
			.align(SWT.FILL, SWT.FILL)
			.grab(true, true)
			.applyTo(categoryComposite);
		
		
		node.lift(withSWT(categoryComposite));
//		
//		Composite composite = new Composite(categoryComposite, SWT.NONE);
//		composite.setBackground(getParent().getBackground());
//			
//		GridDataFactory.fillDefaults()
//			.align(SWT.FILL, SWT.FILL)
//			.grab(true, true)
//			.applyTo(composite);
//			
//		GridLayoutFactory.fillDefaults()
//			.applyTo(composite);
			
		Node childNode = node.getChildren().get(0);
		
		Control control = SWTRenderers.INSTANCE.render(categoryComposite, childNode, controlContext, adapterFactoryItemDelegator);
			
		if (!childNode.isLeaf()) {
			GridDataFactory.fillDefaults()
			.align(SWT.FILL, SWT.FILL)
			.grab(true, true)
			.span(2, 1)
			.applyTo(control);
		} 
		
		return categoryComposite;
	}
}
