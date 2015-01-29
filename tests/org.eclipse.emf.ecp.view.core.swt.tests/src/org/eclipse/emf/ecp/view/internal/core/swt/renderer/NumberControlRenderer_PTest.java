package org.eclipse.emf.ecp.view.internal.core.swt.renderer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecp.view.spi.model.LabelAlignment;
import org.eclipse.emf.ecp.view.spi.renderer.NoPropertyDescriptorFoundExeption;
import org.eclipse.emf.ecp.view.spi.renderer.NoRendererFoundException;
import org.eclipse.emf.ecp.view.spi.swt.SWTRendererFactory;
import org.eclipse.emf.ecp.view.spi.swt.layout.SWTGridCell;
import org.eclipse.emf.ecp.view.test.common.swt.spi.DatabindingClassRunner;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Text;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(DatabindingClassRunner.class)
public class NumberControlRenderer_PTest extends AbstractControl_PTest {

	@Before
	public void before() {
		final SWTRendererFactory factory = mock(SWTRendererFactory.class);
		setup();
		new NumberControlSWTRenderer(vControl, context, factory);
	}

	@After
	public void testTearDown() {
		dispose();
	}

	@Test
	public void renderControlLabelAlignmentNone()
		throws NoRendererFoundException, NoPropertyDescriptorFoundExeption {
		setMockLabelAlignment(LabelAlignment.NONE);
		final Control render = renderControl(new SWTGridCell(0, 1, renderer));
		assertControl(render);
	}

	@Test
	public void renderControlLabelAlignmentLeft()
		throws NoRendererFoundException, NoPropertyDescriptorFoundExeption {
		setMockLabelAlignment(LabelAlignment.LEFT);
		final Control render = renderControl(new SWTGridCell(0, 2, renderer));

		assertControl(render);
	}

	@Test
	public void renderLabel() throws NoRendererFoundException, NoPropertyDescriptorFoundExeption {
		renderLabel("Lower Bound");
	}

	private void assertControl(Control render) {
		assertTrue(Text.class.isInstance(render));
		assertEquals(SWT.RIGHT, Text.class.cast(render).getStyle()
			& SWT.RIGHT);
		assertEquals(SWT.RIGHT, Text.class.cast(render).getStyle()
			& SWT.RIGHT);

		assertEquals("org_eclipse_emf_ecp_control_numerical", Text.class.cast(render).getData(CUSTOM_VARIANT));
	}

	@Override
	protected void mockControl() {
		final EStructuralFeature eObject = EcoreFactory.eINSTANCE.createEAttribute();
		final EStructuralFeature eStructuralFeature = EcorePackage.eINSTANCE.getETypedElement_LowerBound();
		super.mockControl(eObject, eStructuralFeature);
	}

}
