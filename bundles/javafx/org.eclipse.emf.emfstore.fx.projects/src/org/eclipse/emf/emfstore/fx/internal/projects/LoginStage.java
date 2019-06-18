/*******************************************************************************
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Lucas Koehler - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.emfstore.fx.internal.projects;

import org.eclipse.emf.emfstore.client.ESUsersession;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.HBoxBuilder;
import javafx.scene.layout.VBoxBuilder;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class LoginStage extends Stage {

	private String name;
	private String password;
	private boolean savePassword;

	public String getName() {
		return name;
	}

	public String getPassword() {
		return password;
	}

	public boolean isSavePassword() {
		return savePassword;
	}

	public LoginStage(ESUsersession usersession) {
		initModality(Modality.WINDOW_MODAL);
		setTitle("Enter Login information");

		final TextField nameField = new TextField();
		final HBox nameBox = HBoxBuilder.create().children(new Label("Name"), nameField).build();
		final TextField pwField = new TextField();
		final HBox pwBox = HBoxBuilder.create().children(new Label("Password"), pwField).build();

		if (usersession != null) {
			nameField.setText(usersession.getUsername());
			pwField.setText(usersession.getPassword());
		}

		final CheckBox savePW = new CheckBox("Save Password");

		final Button buttonOK = new Button("OK");
		buttonOK.setMaxWidth(Double.MAX_VALUE);
		buttonOK.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				name = nameField.getText();
				password = pwField.getText();
				savePassword = savePW.isSelected();
				close();
			}
		});

		final Button buttonCancel = new Button("Cancel");
		buttonCancel.setMaxWidth(Double.MAX_VALUE);
		buttonCancel.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				close();
			}
		});
		final HBox buttonBox = HBoxBuilder.create().children(buttonOK, buttonCancel).build();
		setScene(new Scene(VBoxBuilder.create().children(nameBox, pwBox, savePW, buttonBox)
			.build()));
		sizeToScene();

	}
}
