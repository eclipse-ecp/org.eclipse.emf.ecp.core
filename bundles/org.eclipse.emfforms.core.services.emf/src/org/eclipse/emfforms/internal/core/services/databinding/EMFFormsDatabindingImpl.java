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
 * Eugen Neufeld - changed interface to EMFFormsDatabindingEMF
 * Lucas Koehler - Added support for DMR Segments
 ******************************************************************************/
package org.eclipse.emfforms.internal.core.services.databinding;

import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;

import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.databinding.property.list.IListProperty;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.databinding.IEMFListProperty;
import org.eclipse.emf.databinding.IEMFObservable;
import org.eclipse.emf.databinding.IEMFValueProperty;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecp.common.spi.asserts.Assert;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReferenceSegment;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emfforms.common.RankingHelper;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedException;
import org.eclipse.emfforms.spi.core.services.databinding.DomainModelReferenceConverter;
import org.eclipse.emfforms.spi.core.services.databinding.EMFFormsDatabinding;
import org.eclipse.emfforms.spi.core.services.databinding.emf.DomainModelReferenceConverterEMF;
import org.eclipse.emfforms.spi.core.services.databinding.emf.DomainModelReferenceSegmentConverterEMF;
import org.eclipse.emfforms.spi.core.services.databinding.emf.EMFFormsDatabindingEMF;
import org.eclipse.emfforms.spi.core.services.databinding.emf.EMFFormsSegmentResolver;
import org.eclipse.emfforms.spi.core.services.databinding.emf.SegmentConverterListResultEMF;
import org.eclipse.emfforms.spi.core.services.databinding.emf.SegmentConverterValueResultEMF;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;

/**
 * EMF implementation of {@link EMFFormsDatabindingEMF}.
 *
 * @author Lucas Koehler
 *
 */
@Component(name = "databindingService", service = { EMFFormsDatabinding.class, EMFFormsDatabindingEMF.class,
	EMFFormsSegmentResolver.class })
public class EMFFormsDatabindingImpl implements EMFFormsDatabindingEMF, EMFFormsSegmentResolver {

	private static final RankingHelper<DomainModelReferenceConverterEMF> DMR_RANKING_HELPER = //
		new RankingHelper<DomainModelReferenceConverterEMF>(
			DomainModelReferenceConverter.class,
			DomainModelReferenceConverter.NOT_APPLICABLE,
			DomainModelReferenceConverter.NOT_APPLICABLE);

	private static final RankingHelper<DomainModelReferenceSegmentConverterEMF> SEGMENTS_RANKING_HELPER = //
		new RankingHelper<>(
			DomainModelReferenceSegmentConverterEMF.class,
			DomainModelReferenceSegmentConverterEMF.NOT_APPLICABLE,
			DomainModelReferenceSegmentConverterEMF.NOT_APPLICABLE);

	private final Set<DomainModelReferenceConverterEMF> referenceConverters = new LinkedHashSet<>();
	private final Set<DomainModelReferenceSegmentConverterEMF> segmentConverters = new LinkedHashSet<>();

	/**
	 * Adds the given {@link DomainModelReferenceSegmentConverterEMF} to the Set of segment converters.
	 *
	 * @param converter The {@link DomainModelReferenceSegmentConverterEMF} to add
	 */
	@Reference(cardinality = ReferenceCardinality.MULTIPLE, policy = ReferencePolicy.DYNAMIC)
	protected void addDomainModelReferenceSegmentConverter(DomainModelReferenceSegmentConverterEMF converter) {
		segmentConverters.add(converter);
	}

	/**
	 * Removes the given {@link DomainModelReferenceSegmentConverterEMF} from the Set of segment converters.
	 *
	 * @param converter The {@link DomainModelReferenceSegmentConverterEMF} to remove
	 */
	protected void removeDomainModelReferenceSegmentConverter(DomainModelReferenceSegmentConverterEMF converter) {
		segmentConverters.remove(converter);
	}

	@Override
	public IObservableValue getObservableValue(VDomainModelReference domainModelReference, EObject object)
		throws DatabindingFailedException {
		Assert.create(domainModelReference).notNull();
		Assert.create(object).notNull();

		final IEMFValueProperty valueProperty = getValueProperty(domainModelReference, object);
		final Realm realm = Realm.getDefault();
		if (realm != null) {
			return valueProperty.observe(object);
		}
		final DefaultRealm dr = new DefaultRealm();
		final IObservableValue observableValue = valueProperty.observe(object);
		dr.dispose();
		return observableValue;
	}

	@Override
	public IEMFValueProperty getValueProperty(VDomainModelReference domainModelReference, EObject object)
		throws DatabindingFailedException {
		Assert.create(domainModelReference).notNull();

		final EList<VDomainModelReferenceSegment> segments = domainModelReference.getSegments();
		if (segments.isEmpty()) {
			// No segments => Fall back to legacy dmr resolving
			final DomainModelReferenceConverterEMF bestConverter = getBestDomainModelReferenceConverter(
				domainModelReference);
			return bestConverter.convertToValueProperty(domainModelReference, object);
		}

		Assert.create(object).notNull();
		final EditingDomain editingDomain = getEditingDomain(object);
		return internalGetValueProperty(domainModelReference, object.eClass(), editingDomain);
	}

	@Override
	public IEMFValueProperty getValueProperty(VDomainModelReference domainModelReference, EClass rootEClass)
		throws DatabindingFailedException {
		return getValueProperty(domainModelReference, rootEClass, null);
	}

	@Override
	public IEMFValueProperty getValueProperty(VDomainModelReference domainModelReference, EClass rootEClass,
		EditingDomain editingDomain) throws DatabindingFailedException {
		Assert.create(domainModelReference).notNull();

		final EList<VDomainModelReferenceSegment> segments = domainModelReference.getSegments();
		if (segments.isEmpty()) {
			// No segments => Fall back to legacy dmr resolving
			final DomainModelReferenceConverterEMF bestConverter = getBestDomainModelReferenceConverter(
				domainModelReference);
			return bestConverter.convertToValueProperty(domainModelReference, rootEClass, editingDomain);
		}

		Assert.create(rootEClass).notNull();
		return internalGetValueProperty(domainModelReference, rootEClass, editingDomain);
	}

	/**
	 * Actual calculation of a value property using {@link VDomainModelReferenceSegment segments}.
	 *
	 * @param domainModelReference The domain model reference pointing to the desired value
	 * @param rootEClass The root EClass of the rendered form
	 * @param editingDomain The {@link EditingDomain} of the resulting value property, may be null
	 * @return The resulting {@link IEMFValueProperty}
	 * @throws DatabindingFailedException
	 */
	private IEMFValueProperty internalGetValueProperty(VDomainModelReference domainModelReference, EClass rootEClass,
		EditingDomain editingDomain) throws DatabindingFailedException {

		final EList<VDomainModelReferenceSegment> segments = domainModelReference.getSegments();

		// Get value property for the (always present) first segment
		final DomainModelReferenceSegmentConverterEMF firstConverter = getBestDomainModelReferenceSegmentConverter(
			segments.get(0));
		SegmentConverterValueResultEMF converterResult = firstConverter.convertToValueProperty(segments.get(0),
			rootEClass, editingDomain);
		IEMFValueProperty resultProperty = converterResult.getValueProperty();

		// Iterate over all remaining segments and get the value properties for their corresponding EClasses.
		for (int i = 1; i < segments.size(); i++) {
			final EClass nextEClass = unpackNextEClass(converterResult.getNextEClass(), domainModelReference,
				segments.get(i));
			final VDomainModelReferenceSegment segment = segments.get(i);

			final DomainModelReferenceSegmentConverterEMF bestConverter = getBestDomainModelReferenceSegmentConverter(
				segment);
			converterResult = bestConverter.convertToValueProperty(segment,
				nextEClass, editingDomain);
			final IEMFValueProperty nextProperty = converterResult.getValueProperty();
			// Chain the properties together
			resultProperty = resultProperty.value(nextProperty);
		}

		return resultProperty;
	}

	/**
	 * Adds the given {@link DomainModelReferenceConverterEMF} to the Set of reference converters.
	 *
	 * @param converter The {@link DomainModelReferenceConverterEMF} to add
	 */
	@Reference(cardinality = ReferenceCardinality.MULTIPLE, policy = ReferencePolicy.DYNAMIC)
	protected void addDomainModelReferenceConverter(DomainModelReferenceConverterEMF converter) {
		referenceConverters.add(converter);
	}

	/**
	 * Removes the given {@link DomainModelReferenceConverterEMF} to the Set of reference converters.
	 *
	 * @param converter The {@link DomainModelReferenceConverterEMF} to remove
	 */
	protected void removeDomainModelReferenceConverter(DomainModelReferenceConverterEMF converter) {
		referenceConverters.remove(converter);
	}

	@Override
	public IObservableList getObservableList(VDomainModelReference domainModelReference, EObject object)
		throws DatabindingFailedException {
		Assert.create(domainModelReference).notNull();
		Assert.create(object).notNull();

		final IListProperty listProperty = getListProperty(domainModelReference, object);
		final Realm realm = Realm.getDefault();
		if (realm != null) {
			return listProperty.observe(object);
		}
		final DefaultRealm dr = new DefaultRealm();
		final IObservableList observableList = listProperty.observe(object);
		dr.dispose();
		return observableList;
	}

	@Override
	public IEMFListProperty getListProperty(VDomainModelReference domainModelReference, EObject object)
		throws DatabindingFailedException {
		Assert.create(domainModelReference).notNull();

		final EList<VDomainModelReferenceSegment> segments = domainModelReference.getSegments();
		if (segments.isEmpty()) {
			// No segments => Fall back to legacy dmr resolving
			final DomainModelReferenceConverterEMF bestConverter = getBestDomainModelReferenceConverter(
				domainModelReference);
			return bestConverter.convertToListProperty(domainModelReference, object);
		}

		Assert.create(object).notNull();
		final EditingDomain editingDomain = getEditingDomain(object);

		// If there is only one segment, get its list property. Otherwise, get its value property
		final DomainModelReferenceSegmentConverterEMF firstConverter = getBestDomainModelReferenceSegmentConverter(
			segments.get(0));
		if (segments.size() == 1) {
			// If there is only one segment, directly return its list property
			return firstConverter.convertToListProperty(segments.get(0), object.eClass(), editingDomain)
				.getListProperty();
		}

		final SegmentConverterValueResultEMF converterResult = firstConverter.convertToValueProperty(segments.get(0),
			object.eClass(), editingDomain);
		IEMFValueProperty valueProperty = converterResult.getValueProperty();

		/*
		 * Iterate over all "middle" segments and get the value properties for their corresponding EClasses.
		 * Get the EClass by getting the target EClass of the EReference from the value property of the previously
		 * resolved segment.
		 */
		EClass nextEClass = unpackNextEClass(converterResult.getNextEClass(), domainModelReference, segments.get(0));
		for (int i = 1; i < segments.size() - 1; i++) {
			final VDomainModelReferenceSegment segment = segments.get(i);

			final DomainModelReferenceSegmentConverterEMF bestConverter = getBestDomainModelReferenceSegmentConverter(
				segment);
			final SegmentConverterValueResultEMF nextConverterResult = bestConverter.convertToValueProperty(segment,
				nextEClass, editingDomain);
			final IEMFValueProperty nextProperty = nextConverterResult.getValueProperty();

			nextEClass = unpackNextEClass(nextConverterResult.getNextEClass(), domainModelReference, segment);
			// Chain the properties together
			valueProperty = valueProperty.value(nextProperty);
		}

		// Get the list property for the last segment
		final int lastIndex = segments.size() - 1;
		final DomainModelReferenceSegmentConverterEMF lastConverter = getBestDomainModelReferenceSegmentConverter(
			segments.get(lastIndex));
		final SegmentConverterListResultEMF converterListResult = lastConverter.convertToListProperty(
			segments.get(lastIndex), nextEClass,
			editingDomain);

		return valueProperty.list(converterListResult.getListProperty());
	}

	/**
	 * Returns the most suitable {@link DomainModelReferenceConverter}, that is registered to this
	 * {@link EMFFormsDatabindingImpl}, for the given {@link VDomainModelReference}.
	 *
	 * @param domainModelReference The {@link VDomainModelReference} for which a {@link DomainModelReferenceConverter}
	 *            is needed
	 * @return The most suitable {@link DomainModelReferenceConverter}
	 * @throws DatabindingFailedException If no applicable DMR Converter was found
	 */
	private DomainModelReferenceConverterEMF getBestDomainModelReferenceConverter(
		final VDomainModelReference domainModelReference) throws DatabindingFailedException {
		if (domainModelReference == null) {
			throw new IllegalArgumentException("The given VDomainModelReference must not be null."); //$NON-NLS-1$
		}

		final DomainModelReferenceConverterEMF bestConverter = DMR_RANKING_HELPER.getHighestRankingElement(
			referenceConverters, converter -> converter.isApplicable(domainModelReference));
		if (bestConverter == null) {
			throw new DatabindingFailedException("No applicable DomainModelReferenceConverter could be found."); //$NON-NLS-1$
		}
		return bestConverter;
	}

	@Override
	public EStructuralFeature extractFeature(IObservableValue observableValue) throws DatabindingFailedException {
		if (IEMFObservable.class.isInstance(observableValue)) {
			return IEMFObservable.class.cast(observableValue).getStructuralFeature();
		}
		throw new DatabindingFailedException(
			String.format("The IObservableValue class %1$s is not supported!", observableValue.getClass().getName())); //$NON-NLS-1$
	}

	@Override
	public EStructuralFeature extractFeature(IObservableList observableList) throws DatabindingFailedException {
		if (IEMFObservable.class.isInstance(observableList)) {
			return IEMFObservable.class.cast(observableList).getStructuralFeature();
		}
		throw new DatabindingFailedException(
			String.format("The IObservableList class %1$s is not supported!", observableList.getClass().getName())); //$NON-NLS-1$
	}

	@Override
	public EObject extractObserved(IObservableValue observableValue) throws DatabindingFailedException {
		if (IEMFObservable.class.isInstance(observableValue)) {
			return (EObject) IEMFObservable.class.cast(observableValue).getObserved();
		}
		throw new DatabindingFailedException(
			String.format("The IObservableValue class %1$s is not supported!", observableValue.getClass().getName())); //$NON-NLS-1$
	}

	@Override
	public EObject extractObserved(IObservableList observableList) throws DatabindingFailedException {
		if (IEMFObservable.class.isInstance(observableList)) {
			return (EObject) IEMFObservable.class.cast(observableList).getObserved();
		}
		throw new DatabindingFailedException(
			String.format("The IObservableList class %1$s is not supported!", observableList.getClass().getName())); //$NON-NLS-1$
	}

	@Override
	public Setting getSetting(VDomainModelReference domainModelReference, EObject object)
		throws DatabindingFailedException {
		Assert.create(domainModelReference).notNull();
		Assert.create(object).notNull();

		final EList<VDomainModelReferenceSegment> segments = domainModelReference.getSegments();
		if (segments.isEmpty()) {
			// No segments => Fall back to legacy dmr resolving
			final DomainModelReferenceConverterEMF bestConverter = getBestDomainModelReferenceConverter(
				domainModelReference);
			final Realm realm = Realm.getDefault();
			if (realm != null) {
				return bestConverter.getSetting(domainModelReference, object);
			}
			final DefaultRealm dr = new DefaultRealm();
			final Setting setting = bestConverter.getSetting(domainModelReference, object);
			dr.dispose();
			return setting;
		}

		Setting setting = resolveSegment(segments.get(0), object);

		/*
		 * If present, iterate over the remaining segments. For every iteration step, use the resolved EObject of the
		 * previously resolved Setting in order to resolve the next Setting.
		 */
		for (int i = 1; i < segments.size(); i++) {
			final VDomainModelReferenceSegment segment = segments.get(i);
			final Object nextObject = setting.get(true);

			if (!EObject.class.isInstance(nextObject)) {
				throw new DatabindingFailedException(
					String.format(
						"The Setting could not be fully resolved because an intermediate Object was no EObject or was null. " //$NON-NLS-1$
							+ "The DMR was %1$s. The last resolved segment was %2$s. The root EObject was %3$s.", //$NON-NLS-1$
						domainModelReference, segments.get(i - 1), object));
			}

			final EObject nextEObject = (EObject) nextObject;
			setting = resolveSegment(segment, nextEObject);
		}

		return setting;
	}

	/**
	 * Returns the most suitable {@link DomainModelReferenceSegmentConverterEMF}, that is registered to this
	 * {@link EMFFormsDatabindingImpl}, for the given {@link VDomainModelReferenceSegment}.
	 *
	 * @param segment The {@link VDomainModelReferenceSegment} for which a
	 *            {@link DomainModelReferenceSegmentConverterEMF}
	 *            is needed
	 * @return The most suitable {@link DomainModelReferenceSegmentConverterEMF}, does not return <code>null</code>
	 * @throws DatabindingFailedException if no suitable segment converter could be found
	 */
	private DomainModelReferenceSegmentConverterEMF getBestDomainModelReferenceSegmentConverter(
		final VDomainModelReferenceSegment segment) throws DatabindingFailedException {

		final DomainModelReferenceSegmentConverterEMF bestConverter = SEGMENTS_RANKING_HELPER.getHighestRankingElement(
			segmentConverters, converter -> converter.isApplicable(segment));

		if (bestConverter == null) {
			throw new DatabindingFailedException(String
				.format("No suitable DomainModelReferenceSegmentConverter could be found for segment %1$s", segment)); //$NON-NLS-1$
		}
		return bestConverter;

	}

	/**
	 * Unpacks the given {@link EClass} Optional and throws an exception if it is not present.
	 *
	 * @param nextEClass the {@link EClass} to check
	 * @param domainModelReference only needed for exception description
	 * @param segment only needed for exception description
	 * @return the unpacked {@link EClass}
	 * @throws DatabindingFailedException if the next EClass is <code>null</code>
	 */
	private EClass unpackNextEClass(final Optional<EClass> nextEClass, VDomainModelReference domainModelReference,
		final VDomainModelReferenceSegment segment) throws DatabindingFailedException {
		return nextEClass.orElseThrow(() -> new DatabindingFailedException(String.format(
			"The Segment [%1$s] could not be resolved because this segment's root EClass" //$NON-NLS-1$
				+ " could not be resolved from the preceding segment. The DMR is %2$s.", //$NON-NLS-1$
			segment, domainModelReference)));
	}

	private EditingDomain getEditingDomain(EObject object) throws DatabindingFailedException {
		return AdapterFactoryEditingDomain.getEditingDomainFor(object);
	}

	@Override
	public Setting resolveSegment(VDomainModelReferenceSegment segment, EObject domainObject)
		throws DatabindingFailedException {
		final DomainModelReferenceSegmentConverterEMF bestConverter = getBestDomainModelReferenceSegmentConverter(
			segment);
		return bestConverter.getSetting(segment, domainObject);
	}
}
