package org.eclipse.emfforms.spi.editor.helpers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.eclipse.emf.common.command.BasicCommandStack;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.plugin.EcorePlugin;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.junit.Before;
import org.junit.Test;

public class ResourceSetHelpers_Test {

	@Before
	public void setUp() throws Exception {
	}

	public static <T> Predicate<T> not(Predicate<T> t) {
		return t.negate();
	}

	@Test
	public void testCreateResourceSet() {
		ComposedAdapterFactory adapterFactory = new ComposedAdapterFactory(
			ComposedAdapterFactory.Descriptor.Registry.INSTANCE);
		AdapterFactoryEditingDomain editingDomain = new AdapterFactoryEditingDomain(adapterFactory,
			new BasicCommandStack());
		ResourceSet rs = ResourceSetHelpers.createResourceSet(editingDomain);

		Map<URI, URI> uriMap = rs.getURIConverter().getURIMap();
		Map<URI, URI> platformURIMap = EcorePlugin.computePlatformURIMap(true);
		List<URI> listNotFoundKeys = platformURIMap.keySet().stream().filter(not(uriMap::containsKey))
			.collect(Collectors.toList());
		assertTrue(listNotFoundKeys.isEmpty());

		List<URI> listNotFoundValues = platformURIMap.keySet().stream()
			.filter(u -> uriMap.get(u) != platformURIMap.get(u)).collect(Collectors.toList());
		assertTrue(listNotFoundValues.isEmpty());

		assertTrue(rs.getLoadOptions().containsKey(XMLResource.OPTION_DEFER_IDREF_RESOLUTION));
		assertEquals(rs.getLoadOptions().get(XMLResource.OPTION_DEFER_IDREF_RESOLUTION), Boolean.TRUE);

		adapterFactory.dispose();
	}

}
