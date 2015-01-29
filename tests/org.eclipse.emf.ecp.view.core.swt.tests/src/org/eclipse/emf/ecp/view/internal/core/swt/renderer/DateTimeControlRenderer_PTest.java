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
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Label;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(DatabindingClassRunner.class)
public class DateTimeControlRenderer_PTest extends AbstractControl_PTest {

	@Before
	public void before() {
		final SWTRendererFactory factory = mock(SWTRendererFactory.class);
		setup();
		renderer = new DateTimeControlSWTRenderer(vControl, context, factory);
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

	private void assertControl(Control render) {
		assertTrue(Composite.class.isInstance(render));
		final Composite top = Composite.class.cast(render);
		assertEquals(2, top.getChildren().length);
		assertTrue(Composite.class.isInstance(top.getChildren()[0]));
		assertTrue(Button.class.isInstance(top.getChildren()[1]));
		final Composite stack = Composite.class.cast(top.getChildren()[0]);
		assertTrue(StackLayout.class.isInstance(stack.getLayout()));
		assertEquals(2, stack.getChildren().length);
		assertTrue(Composite.class.isInstance(stack.getChildren()[0]));
		assertTrue(Label.class.isInstance(stack.getChildren()[1]));
		final Composite dateTime = Composite.class.cast(stack.getChildren()[0]);
		assertEquals(3, dateTime.getChildren().length);
		assertTrue(DateTime.class.isInstance(dateTime.getChildren()[0]));
		assertTrue(DateTime.class.isInstance(dateTime.getChildren()[1]));
		assertTrue(Button.class.isInstance(dateTime.getChildren()[2]));
	}

	@Override
	protected void mockControl() {
		final SimpleTestObject eObject = TestFactory.eINSTANCE.createSimpleTestObject();
		super.mockControl(eObject, TestPackage.eINSTANCE.getSimpleTestObject_Date());
	}

}
