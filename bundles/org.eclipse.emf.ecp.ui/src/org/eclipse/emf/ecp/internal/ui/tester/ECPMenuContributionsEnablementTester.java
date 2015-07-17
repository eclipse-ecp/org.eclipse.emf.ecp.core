/*******************************************************************************
 * Copyright (c) 2008-2011 Chair for Applied Software Engineering,
 * Technische Universitaet Muenchen.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 ******************************************************************************/
package org.eclipse.emf.ecp.internal.ui.tester;

import org.eclipse.core.expressions.PropertyTester;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;

/**
 * Tests the enablement of ECP Menu contributions. The contributions are enabled by default and can be disabled by an
 * extension point.
 *
 * @author koegel
 * @since 1.3
 *
 */
public class ECPMenuContributionsEnablementTester extends PropertyTester {

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.core.expressions.IPropertyTester#test(java.lang.Object, java.lang.String, java.lang.Object[],
	 *      java.lang.Object)
	 */
	@Override
	public boolean test(Object receiver, String property, Object[] args, Object expectedValue) {

		if (expectedValue instanceof Boolean) {
			// default behavior, display menu contribution
			Boolean menuContributionEnabled = true;
			// fetch contribution configuration
			final IConfigurationElement[] elements = Platform.getExtensionRegistry().getConfigurationElementsFor(
				"org.eclipse.emf.ecp.ui.menuContributionEnablement");
			if (elements != null && elements.length > 0) {
				for (final IConfigurationElement configurationElement : elements) {

					// fetch configured default option. true = blacklist; false = whitelist
					menuContributionEnabled = Boolean.parseBoolean(configurationElement.getAttribute("enabled"));
					// iterate over configured exceptions
					for (final IConfigurationElement child : configurationElement.getChildren()) {
						final String commandID = child.getAttribute("commandID");
						// if blacklist & command on list -> block
						if (menuContributionEnabled && args[0].equals(commandID)) {
							return expectedValue.equals(false);
							// if whitelist & command on list -> don't block
						} else if (!menuContributionEnabled && args[0].equals(commandID)) {
							return expectedValue.equals(true);
						}
					}
				}
			}

			return expectedValue.equals(menuContributionEnabled);
		}
		return false;
	}

}
