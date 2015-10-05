package org.eclipse.emf.ecp.util.fx;

import java.net.URL;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.CommandActionDelegate;
import org.eclipse.emf.edit.command.CommandParameter;
import org.eclipse.emf.edit.command.CreateChildCommand;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.util.Callback;

public class EMFUtil {

	public static MenuItem getCreateChildrenMenu(final EObject parenEObject) {
		return getCreateChildrenMenu(parenEObject, null);
	}

	public static MenuItem getCreateChildrenMenu(final EObject parenEObject,
		final Callback<Void, Void> callback) {
		final Menu result = new Menu("Possible Containments");

		final EditingDomain domain = AdapterFactoryEditingDomain
			.getEditingDomainFor(parenEObject);
		final Collection<?> descriptors = domain.getNewChildDescriptors(
			parenEObject, null);
		for (final Object descriptor : descriptors) {
			if (!CommandParameter.class.isInstance(descriptor)) {
				continue;
			}
			final CommandParameter cp = (CommandParameter) descriptor;
			if (cp.getEReference() == null) {
				continue;
			}
			if (!cp.getEReference().isMany()
				&& parenEObject.eIsSet(cp.getEStructuralFeature())) {
				continue;
			} else if (cp.getEReference().isMany()
				&& cp.getEReference().getUpperBound() != -1
				&& cp.getEReference().getUpperBound() <= ((List<?>) parenEObject
					.eGet(cp.getEReference())).size()) {
				continue;
			}

			final MenuItem item = new MenuItem();
			final Command command = (Command) CreateChildCommand.create(domain,
				parenEObject, descriptor,
				Collections.singletonList(parenEObject));
			if (CommandActionDelegate.class.isInstance(command)) {
				final CommandActionDelegate cad = (CommandActionDelegate) command;
				final Object image = cad.getImage();
				ImageView imageView = null;
				if (URL.class.isInstance(image)) {
					imageView = new ImageView(((URL) image).toExternalForm());
				}
				final HBox hbox = new HBox();
				if (imageView != null) {
					hbox.getChildren().add(imageView);
				}
				final Label label = new Label(cad.getText());
				hbox.getChildren().add(label);
				item.setGraphic(hbox);
			} else {
				final Label label = new Label(command.getLabel());
				item.setGraphic(label);

			}
			item.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					domain.getCommandStack().execute(command);
					if (callback != null) {
						callback.call(null);
					}
				}
			});

			result.getItems().add(item);
		}
		if (result.getItems().size() == 0) {
			return null;
		} else if (result.getItems().size() == 1) {
			return result.getItems().get(0);
		}
		return result;
	}

}
