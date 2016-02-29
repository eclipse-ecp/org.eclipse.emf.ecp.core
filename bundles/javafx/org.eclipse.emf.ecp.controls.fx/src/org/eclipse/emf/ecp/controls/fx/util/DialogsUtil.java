/*******************************************************************************
 * Copyright (c) 2011-2016 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Lucas Koehler - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.controls.fx.util;

import java.util.Collection;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.ReflectiveItemProviderAdapterFactory;
import org.eclipse.fx.emf.edit.ui.AdapterFactoryListCellFactory;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

/**
 * FIXME comment.
 *
 * @author Lucas Koehler
 *
 */
public final class DialogsUtil {

	/**
	 * DialogsUtil is a utility class which should not be instanced.
	 */
	private DialogsUtil() {
	}

	/**
	 * Opens a dialog to chose one of the given model elements.
	 *
	 * @param modelElements the model elements to chose from.
	 * @param <T> the type of the selected element.
	 * @return the chosen model element.
	 */
	@SuppressWarnings("unchecked")
	public static <T> T selectExistingModelElement(Collection<T> modelElements) {
		final Stage dialogStage = new Stage();
		dialogStage.initStyle(StageStyle.UTILITY);
		// dialogStage.setResizable(false);
		final GridPane gridPane = new GridPane();
		gridPane.getStyleClass().add("vertical"); //$NON-NLS-1$

		final ObservableList<T> listItems = FXCollections.observableArrayList(modelElements);
		final ListView<T> listView = new ListView<>(listItems);
		final ComposedAdapterFactory composedAdapterFactory = new ComposedAdapterFactory(
			new AdapterFactory[] {
				new ReflectiveItemProviderAdapterFactory(),
				new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE) });
		listView.setCellFactory(new AdapterFactoryListCellFactory<T>(composedAdapterFactory));
		listView.setMaxHeight(Double.MAX_VALUE);
		listView.setMaxWidth(Double.MAX_VALUE);

		final Button okButton = new Button("OK"); //$NON-NLS-1$
		okButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				dialogStage.close();
			}
		});

		final Button cancelButton = new Button("Cancel"); //$NON-NLS-1$
		cancelButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				listView.getSelectionModel().clearSelection();
				dialogStage.close();
			}
		});

		dialogStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent event) {
				listView.getSelectionModel().clearSelection();
				dialogStage.close();
			}
		});

		final HBox buttonBox = new HBox();
		buttonBox.setAlignment(Pos.CENTER_RIGHT);
		buttonBox.getChildren().addAll(okButton, cancelButton);
		gridPane.setVgap(5);
		gridPane.add(listView, 0, 0);
		gridPane.add(buttonBox, 0, 1);
		GridPane.setHgrow(listView, Priority.ALWAYS);
		GridPane.setVgrow(listView, Priority.ALWAYS);

		final Scene dialogScene = new Scene(gridPane);
		dialogStage.setScene(dialogScene);
		dialogStage.showAndWait();

		final Object selectedElement = listView.getSelectionModel().getSelectedItem();
		composedAdapterFactory.dispose();
		return (T) selectedElement;
	}
}
