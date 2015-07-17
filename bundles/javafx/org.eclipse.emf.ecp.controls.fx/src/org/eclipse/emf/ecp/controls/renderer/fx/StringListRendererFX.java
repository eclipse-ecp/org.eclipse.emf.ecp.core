/**
 *
 */
package org.eclipse.emf.ecp.controls.renderer.fx;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecp.controls.internal.fx.Activator;
import org.eclipse.emf.ecp.controls.internal.fx.ManyAttributesObservableList;
import org.eclipse.emf.ecp.view.model.internal.fx.SimpleControlRendererFX;
import org.eclipse.emf.ecp.view.spi.model.VControl;

/**
 * @author Lucas
 *
 */
@SuppressWarnings("unchecked")
public class StringListRendererFX extends SimpleControlRendererFX {

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Node createControl() {

		final VControl control = getVElement();
		final Setting setting = control.getDomainModelReference().getIterator()
			.next();
		final VBox vBox = new VBox();

		final Button addBtn = new Button("Add");
		addBtn.setMaxWidth(Double.MAX_VALUE);
		addBtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				addItem(setting);
			}
		});
		vBox.getChildren().add(addBtn);

		final ListView<Object> listView = new ListView<>();

		VBox.setMargin(listView, new Insets(0));

		listView.setItems(new ManyAttributesObservableList<>(setting
			.getEObject(), setting.getEStructuralFeature()));
		listView.setEditable(true);

		listView.setCellFactory(new Callback<ListView<Object>, ListCell<Object>>() {
			@Override
			public ListCell<Object> call(ListView<Object> list) {
				return new ListCell<Object>() {

					@Override
					public void updateItem(Object item, boolean empty) {
						super.updateItem(item, empty);

						if (item != null) {
							final Label tf = new Label();
							tf.setText(item.toString());
							setGraphic(tf);
						}
					}

					@Override
					public void cancelEdit() {
						final Label tf = new Label();
						tf.setText(getListValue().toString());
						setGraphic(tf);
					}

					@Override
					public void commitEdit(Object value) {
						final List<Object> objects = (List<Object>) setting
							.get(false);
						objects.set(getIndex(), value);
						cancelEdit();
					}

					@Override
					public void startEdit() {
						final TextField textField = new TextField();
						textField.focusedProperty().addListener(
							new ChangeListener<Boolean>() {

								@Override
								public void changed(
									ObservableValue<? extends Boolean> observable,
									Boolean oldValue, Boolean newValue) {
									if (!newValue) {
										commitEdit(textField.getText());
									}
								}

							});

						textField.setText((String) getListValue());
						setGraphic(textField);
						setText(null);
						textField.selectPositionCaret(0);
					}

					private Object getListValue() {
						return ((List<Object>) setting.get(false))
							.get(getIndex());
					}

					@Override
					public void updateSelected(boolean selected) {
						super.updateSelected(selected);
					}

				};
			}
		});

		vBox.getChildren().add(listView);

		applyValidation(control, listView);

		return vBox;
	}

	protected void addItem(Setting setting) {
		Object defaultValue = setting.getEStructuralFeature().getEType()
			.getDefaultValue();
		if (defaultValue == null) {
			try {
				defaultValue = setting.getEStructuralFeature().getEType()
					.getInstanceClass().getConstructor().newInstance();
			} catch (final InstantiationException e) {
				Activator.logException(e);
			} catch (final IllegalAccessException e) {
				Activator.logException(e);
			} catch (final IllegalArgumentException e) {
				Activator.logException(e);
			} catch (final InvocationTargetException e) {
				Activator.logException(e);
			} catch (final NoSuchMethodException e) {
				Activator.logException(e);
			} catch (final SecurityException e) {
				Activator.logException(e);
			}
		}

		((List<Object>) setting.get(true)).add(defaultValue);
	}

}
