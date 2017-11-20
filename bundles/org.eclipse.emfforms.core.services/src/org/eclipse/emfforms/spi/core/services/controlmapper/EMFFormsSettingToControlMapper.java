/*******************************************************************************
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Lucas Koehler - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.spi.core.services.controlmapper;

import java.util.Collection;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecp.common.spi.UniqueSetting;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.spi.model.VElement;

/**
 * A mapping between {@link UniqueSetting UniqueSettings} and Sets of {@link VControl VControls}.
 *
 * @author Lucas Koehler
 * @since 1.8
 *
 */
// TODO move to another bundle
public interface EMFFormsSettingToControlMapper {

	/**
	 * Returns all controls which are associated with the provided {@link Setting}. The {@link Setting} is converted to
	 * a {@link UniqueSetting}.
	 *
	 * @param setting the {@link Setting} to search controls for
	 * @return the Set of all controls associated with the provided setting or null if no controls can be found
	 */
	Set<VControl> getControlsFor(Setting setting);

	/**
	 * Returns all controls which are associated with the provided {@link UniqueSetting}.
	 *
	 * @param setting the {@link UniqueSetting} to search controls for
	 * @return the Set of all controls associated with the provided setting or null if no controls can be found
	 */
	Set<VElement> getControlsFor(UniqueSetting setting);

	/**
	 * Updates the setting to control mapping for the given {@link VControl}.
	 *
	 * @param vControl The {@link VControl}
	 */
	void updateControlMapping(VControl vControl);

	/**
	 * Removes a {@link VControl} from the setting to control mapping.
	 *
	 * @param vControl The {@link VControl} to remove
	 */
	void vControlRemoved(VControl vControl);

	/**
	 * Adds a {@link VControl} to the setting to control mapping.
	 *
	 * @param vControl The {@link VControl} to add
	 */
	void vControlAdded(VControl vControl);

	/**
	 * Checks and updates the mapping for the given {@link EObject}.
	 *
	 * @param eObject The {@link EObject}
	 */
	void checkAndUpdateSettingToControlMapping(EObject eObject);

	/**
	 * Checks whether any feature of this EObject has a registered control.
	 *
	 * @param eObject the EObject to check
	 * @return <code>true</code> if there is at least one control for any feature of the given EObject,
	 *         <code>false</code> otherwise
	 * @since 1.9
	 */
	boolean hasControlsFor(EObject eObject);

	/**
	 * Returns a collection of all EObjects which have a mapped setting.
	 *
	 * @return the EObjects
	 * @since 1.9
	 */
	Collection<EObject> getEObjectsWithSettings();

}
