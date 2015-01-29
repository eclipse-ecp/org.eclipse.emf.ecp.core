package org.eclipse.emf.ecp.view.internal.core.swt.renderer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

import org.eclipse.emf.ecp.view.core.swt.test.model.SimpleTestObject;
import org.eclipse.emf.ecp.view.core.swt.test.model.TestFactory;
import org.eclipse.emf.ecp.view.core.swt.test.model.TestPackage;
import org.eclipse.emf.ecp.view.spi.model.LabelAlignment;
import org.eclipse.emf.ecp.view.spi.renderer.NoPropertyDescriptorFoundExeption;
import org.eclipse.emf.ecp.view.spi.renderer.NoRendererFoundException;
import org.eclipse.emf.ecp.view.spi.swt.SWTRendererFactory;
import org.eclipse.emf.ecp.view.spi.swt.layout.SWTGridCell;
import org.eclipse.emf.ecp.view.test.common.swt.spi.DatabindingClassRunner;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Text;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(DatabindingClassRunner.class)
public class XMLDateControlRenderer_PTest extends AbstractControl_PTest {

	@Before
	public void before() {
		final SWTRendererFactory factory = mock(SWTRendererFactory.class);
		setup();
		new XMLDateControlSWTRenderer(vControl, context, factory);
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
		renderLabel("Xml Date");
	}

	private void assertControl(Control render) {
		assertTrue(Composite.class.isInstance(render));
		final Composite top = Composite.class.cast(render);
		assertEquals(2, top.getChildren().length);
		assertTrue(Text.class.isInstance(top.getChildren()[0]));
		assertTrue(Button.class.isInstance(top.getChildren()[1]));
		final Text text = Text.class.cast(top.getChildren()[0]);
		assertEquals(SWT.LEFT, text.getStyle() & SWT.LEFT);
		assertEquals("org_eclipse_emf_ecp_control_xmldate", text.getData(CUSTOM_VARIANT));
	}

	@Override
	protected void mockControl() {
		final SimpleTestObject eObject = TestFactory.eINSTANCE.createSimpleTestObject();
		super.mockControl(eObject, TestPackage.eINSTANCE.getSimpleTestObject_XmlDate());
	}

}
