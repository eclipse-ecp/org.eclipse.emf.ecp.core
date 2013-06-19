package org.eclipse.emf.ecp.ui.view.swt;

import org.eclipse.emf.ecp.edit.internal.swt.util.SWTControl;
import org.eclipse.emf.ecp.internal.ui.view.renderer.ControlRenderer;
import org.eclipse.emf.ecp.view.model.Composite;
import org.eclipse.swt.widgets.Control;


/**
 * Common interface for all SWT renderers.
 *
 * @param T
 * 			the type of the SWT control 
 */
public interface SWTRenderer<T extends Composite> 
	extends ControlRenderer<Control, T> {
		
	/**
	 * Variant constant for indicating RAP controls.
	 */
	static final String CUSTOM_VARIANT = "org.eclipse.rap.rwt.customVariant";

}
