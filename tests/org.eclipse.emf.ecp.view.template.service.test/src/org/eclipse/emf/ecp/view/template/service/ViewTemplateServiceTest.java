package org.eclipse.emf.ecp.view.template.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.isNull;
import static org.mockito.Matchers.notNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.Set;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emf.ecp.view.template.model.VTStyle;
import org.eclipse.emf.ecp.view.template.model.VTStyleProperty;
import org.eclipse.emf.ecp.view.template.model.VTStyleSelector;
import org.eclipse.emf.ecp.view.template.model.VTViewTemplate;
import org.junit.Before;
import org.junit.Test;

public class ViewTemplateServiceTest {

	private ViewTemplateProviderImpl templateProvider;
	private VTStyle mockedStyle;

	@Before
	public void setup(){
		templateProvider = new ViewTemplateProviderImpl();
	}
	
	@Test
	public void testNoTemplateModel(){
		Set<VTStyleProperty> styleProperties = templateProvider.getStyleProperties(mock(VElement.class), mock(ViewModelContext.class));
		assertTrue(styleProperties.isEmpty());
	}
	
	@Test
	public void testVElementIsNull(){
		mockTemplate(1d);
		Set<VTStyleProperty> styleProperties = templateProvider.getStyleProperties(null, mock(ViewModelContext.class));
		assertTrue(styleProperties.isEmpty());
	}
	@Test
	public void testViewModelContextIsNull(){
		mockTemplate(1d);
		Set<VTStyleProperty> styleProperties = templateProvider.getStyleProperties(mock(VElement.class),null);
		assertTrue(styleProperties.isEmpty());
	}
	
	@Test
	public void testIsApplicable() {
		mockTemplate(1d);
		
		VControl vElement=mock(VControl.class);
		VDomainModelReference dmr=mock(VDomainModelReference.class);
		when(vElement.getDomainModelReference()).thenReturn(dmr);
		Setting setting=mock(Setting.class);
		when(dmr.getIterator()).thenReturn(Collections.singleton(setting).iterator());
		ViewModelContext viewModelContext=mock(ViewModelContext.class); 
		Set<VTStyleProperty> styleProperties = templateProvider.getStyleProperties(vElement, viewModelContext);
		
		assertEquals(1, styleProperties.size());
		assertEquals(mockedStyle.getProperties().get(0), styleProperties.iterator().next());
		
	}
	
	@Test
	public void testMultipleStyles() {
		mockTemplate(1d);
		VTStyle mockedStyle2=mockStyle(1d);
		templateProvider.getViewTemplate().getStyles().add(mockedStyle2);
		
		VControl vElement=mock(VControl.class);
		VDomainModelReference dmr=mock(VDomainModelReference.class);
		when(vElement.getDomainModelReference()).thenReturn(dmr);
		Setting setting=mock(Setting.class);
		when(dmr.getIterator()).thenReturn(Collections.singleton(setting).iterator());
		ViewModelContext viewModelContext=mock(ViewModelContext.class); 
		Set<VTStyleProperty> styleProperties = templateProvider.getStyleProperties(vElement, viewModelContext);
		
		assertEquals(2, styleProperties.size());
		assertTrue( styleProperties.contains(mockedStyle2.getProperties().get(0)));
		assertTrue( styleProperties.contains(templateProvider.getViewTemplate().getStyles().get(0).getProperties().get(0)));
		
	}
	
	@Test
	public void testOnlyOneStyleProperty() {
		mockTemplate(1d);
		VTStyle mockedStyle2=mockStyle(10);
		templateProvider.getViewTemplate().getStyles().add(mockedStyle2);
		when(templateProvider.getViewTemplate().getStyles().get(0).getProperties().get(0).equalStyles(mockedStyle2.getProperties().get(0))).thenReturn(true);
		VControl vElement=mock(VControl.class);
		VDomainModelReference dmr=mock(VDomainModelReference.class);
		when(vElement.getDomainModelReference()).thenReturn(dmr);
		Setting setting=mock(Setting.class);
		when(dmr.getIterator()).thenReturn(Collections.singleton(setting).iterator());
		ViewModelContext viewModelContext=mock(ViewModelContext.class); 
		Set<VTStyleProperty> styleProperties = templateProvider.getStyleProperties(vElement, viewModelContext);
		
		assertEquals(1, styleProperties.size());
		assertEquals(mockedStyle2.getProperties().get(0), styleProperties.iterator().next());
		
	}

	private void mockTemplate(double specificity) {
		mockedStyle = mockStyle(specificity);
		EList<VTStyle> styles=new BasicEList<VTStyle>();
		styles.add(mockedStyle);
		VTViewTemplate viewTemplate=mock(VTViewTemplate.class);
		templateProvider.setViewTemplate(viewTemplate);
		when(viewTemplate.getStyles()).thenReturn(styles);
	}

	private VTStyle mockStyle(double specificity) {
		VTStyle style=mock(VTStyle.class);
		VTStyleSelector styleSelector=mock(VTStyleSelector.class);
		when(style.getSelector()).thenReturn(styleSelector);
		
		when(styleSelector.isApplicable(isNull(VElement.class), isNull(ViewModelContext.class))).thenReturn(VTStyleSelector.NOT_APPLICABLE);
		when(styleSelector.isApplicable(isNull(VElement.class), notNull(ViewModelContext.class))).thenReturn(VTStyleSelector.NOT_APPLICABLE);
		when(styleSelector.isApplicable(notNull(VElement.class), isNull(ViewModelContext.class))).thenReturn(VTStyleSelector.NOT_APPLICABLE);
		when(styleSelector.isApplicable(notNull(VElement.class), notNull(ViewModelContext.class))).thenReturn(specificity);
		
		VTStyleProperty styleProperty = mock(VTStyleProperty.class);
		EList<VTStyleProperty> properties=new BasicEList<VTStyleProperty>();
		properties.add(styleProperty);
		
		when(style.getProperties()).thenReturn(properties);
		
		
		return style;
	}

	@Test
	public void testIsNotApplicable() {
		mockTemplate(VTStyleSelector.NOT_APPLICABLE);
		
		VControl vElement=mock(VControl.class);
		VDomainModelReference dmr=mock(VDomainModelReference.class);
		when(vElement.getDomainModelReference()).thenReturn(dmr);
		Setting setting=mock(Setting.class);
		when(dmr.getIterator()).thenReturn(Collections.singleton(setting).iterator());
		ViewModelContext viewModelContext=mock(ViewModelContext.class); 
		Set<VTStyleProperty> styleProperties = templateProvider.getStyleProperties(vElement, viewModelContext);
		
		assertEquals(0, styleProperties.size());
		
		
	}
}
