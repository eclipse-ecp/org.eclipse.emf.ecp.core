package org.eclipse.emf.ecp.view.internal.core.swt.renderer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecp.view.spi.model.LabelAlignment;
import org.eclipse.emf.ecp.view.spi.renderer.NoPropertyDescriptorFoundExeption;
import org.eclipse.emf.ecp.view.spi.renderer.NoRendererFoundException;
import org.eclipse.emf.ecp.view.spi.swt.SWTRendererFactory;
import org.eclipse.emf.ecp.view.spi.swt.layout.GridCell;
import org.eclipse.emf.ecp.view.test.common.swt.DatabindingClassRunner;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Control;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(DatabindingClassRunner.class)
public class BooleanControlRendererTest extends AbstractControlTest {

	@Before
	public void before() {
		SWTRendererFactory factory = mock(SWTRendererFactory.class);
		setup(new BooleanControlSWTRenderer(factory));
	}

	@After
	public void testTearDown() {
		dispose();
	}

	@Test
	public void renderControlLabelAlignmentNone()
			throws NoRendererFoundException, NoPropertyDescriptorFoundExeption {
		setMockLabelAlignment(LabelAlignment.NONE);
		Control render = renderControl(new GridCell(0, 1,renderer));
		assertControl(render);
	}

	@Test
	public void renderControlLabelAlignmentLeft()
			throws NoRendererFoundException, NoPropertyDescriptorFoundExeption {
		setMockLabelAlignment(LabelAlignment.LEFT);
		Control render = renderControl(new GridCell(0, 2,renderer));

		assertControl(render);
	}
	@Test
	public void renderLabel() throws NoRendererFoundException, NoPropertyDescriptorFoundExeption{
		renderLabel("Interface");
	}

	private void assertControl(Control render) {
		assertTrue(Button.class.isInstance(render));
		assertEquals(SWT.CHECK, Button.class.cast(render).getStyle()
				& SWT.CHECK);
		assertEquals("org_eclipse_emf_ecp_control_boolean", Button.class.cast(render).getData(CUSTOM_VARIANT));
	}

	@Override
	protected void mockControl() {
		EClass eObject = EcoreFactory.eINSTANCE.createEClass();
		EStructuralFeature eStructuralFeature = EcorePackage.eINSTANCE
				.getEClass_Interface();
		super.mockControl(eObject, eStructuralFeature);
	}

}
