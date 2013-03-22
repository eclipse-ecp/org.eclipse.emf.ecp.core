package org.eclipse.emf.ecp.emfstore.internal.ui.property;

import org.eclipse.emf.ecp.core.ECPProviderRegistry;
import org.eclipse.emf.ecp.core.ECPRepository;
import org.eclipse.emf.ecp.core.util.ECPUtil;
import org.eclipse.emf.ecp.emfstore.core.internal.EMFStoreProvider;
import org.eclipse.emf.ecp.spi.core.InternalRepository;
import org.eclipse.emf.emfstore.client.ESServer;
import org.eclipse.emf.emfstore.internal.server.EMFStoreController;

import org.eclipse.core.expressions.PropertyTester;

import java.util.HashSet;
import java.util.Set;

public class EmfStoreLocalServerAndNotRunningTester extends PropertyTester {

	private static final Set<String> allowedLocalUris = new HashSet<String>();
	static {
		allowedLocalUris.add("localhost");
		allowedLocalUris.add("127.0.0.1");
	}

	public boolean test(Object receiver, String property, Object[] args, Object expectedValue) {
		if (receiver instanceof ECPRepository && expectedValue instanceof Boolean) {
			final ECPRepository ecpRepository = (ECPRepository) receiver;
			EMFStoreProvider emfStoreProvider = (EMFStoreProvider) ECPUtil
				.getResolvedElement(ECPProviderRegistry.INSTANCE.getProvider(EMFStoreProvider.NAME));
			final ESServer serverInfo = emfStoreProvider.getServerInfo((InternalRepository) ecpRepository);
			if (allowedLocalUris.contains(serverInfo.getURL())) {
				if (EMFStoreController.getInstance() == null) {
					return Boolean.TRUE.equals(expectedValue);
				}
			}
		}
		return Boolean.FALSE.equals(expectedValue);
	}
}
