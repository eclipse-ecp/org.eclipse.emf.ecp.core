package org.eclipse.emf.ecp.rap;

import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;


/**
 * Configures the perspective layout. This class is contributed through the
 * plugin.xml.
 */
public class Perspective implements IPerspectiveFactory {

	public void createInitialLayout(IPageLayout layout) {
		String editorArea = layout.getEditorArea();
		layout.setEditorAreaVisible(true);
		layout.setFixed(true);

		layout.addView("org.eclipse.emf.ecp.ui.ModelExplorerView",  IPageLayout.LEFT, 0.3f,
			editorArea);
		layout.addView("org.eclipse.emf.ecp.ui.ModelRepositoriesView",  IPageLayout.BOTTOM, 0.7f,
			editorArea);
	}
}
