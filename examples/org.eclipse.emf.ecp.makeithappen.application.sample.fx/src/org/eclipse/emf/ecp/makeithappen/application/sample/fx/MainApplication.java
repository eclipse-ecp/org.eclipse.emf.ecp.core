package org.eclipse.emf.ecp.makeithappen.application.sample.fx;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import org.eclipse.emf.ecp.makeithappen.model.task.TaskFactory;
import org.eclipse.emf.ecp.makeithappen.model.task.User;
import org.eclipse.emf.ecp.view.model.fx.ECPFXView;
import org.eclipse.emf.ecp.view.model.fx.ECPFXViewRenderer;
import org.eclipse.equinox.app.IApplicationContext;
import org.eclipse.fx.osgi.util.AbstractJFXApplication;

public class MainApplication extends AbstractJFXApplication {

	private void fillPane(BorderPane pane) {
		final User domainObject = TaskFactory.eINSTANCE.createUser();
		final ECPFXView ecpfxView = ECPFXViewRenderer.INSTANCE.render(domainObject);
		pane.setCenter(ecpfxView.getFXNode());
	}

	@Override
	protected void jfxStart(IApplicationContext applicationContext, Application jfxApplication, Stage primaryStage) {
		final BorderPane pane = new BorderPane();
		final Scene s = new Scene(pane);
		primaryStage.setScene(s);
		primaryStage.setWidth(800);
		primaryStage.setHeight(600);
		primaryStage.setTitle("Make it happen Demo"); //$NON-NLS-1$
		fillPane(pane);
		primaryStage.show();
	}
}
