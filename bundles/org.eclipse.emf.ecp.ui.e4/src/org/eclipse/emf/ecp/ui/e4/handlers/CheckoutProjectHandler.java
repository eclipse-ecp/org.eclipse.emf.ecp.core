package org.eclipse.emf.ecp.ui.e4.handlers;

import java.util.Collections;

import javax.inject.Named;

import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.emf.ecp.core.util.ECPCheckoutSource;
import org.eclipse.emf.ecp.internal.ui.util.ECPHandlerHelper;
import org.eclipse.swt.widgets.Shell;

public class CheckoutProjectHandler {

	@Execute
	public void execute(Shell shell,@Named(IServiceConstants.ACTIVE_SELECTION) @Optional ECPCheckoutSource checkoutSource){
		ECPHandlerHelper.checkout(Collections.singletonList(checkoutSource), shell);
	}
	@CanExecute
	public boolean canExecute(){
		return true;
	}
}
