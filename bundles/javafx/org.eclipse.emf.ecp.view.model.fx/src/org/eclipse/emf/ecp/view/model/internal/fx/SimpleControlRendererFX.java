package org.eclipse.emf.ecp.view.model.internal.fx;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;

import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.ecp.view.spi.model.LabelAlignment;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.spi.model.VDiagnostic;
import org.eclipse.emf.ecp.view.spi.renderer.NoPropertyDescriptorFoundExeption;
import org.eclipse.emf.ecp.view.spi.renderer.NoRendererFoundException;

public abstract class SimpleControlRendererFX extends AbstractControlRendererFX {

	private GridDescriptionFX rendererGridDescription;
	private Label validationIcon;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final GridDescriptionFX getGridDescription() {
		if (rendererGridDescription == null) {
			rendererGridDescription = GridDescriptionFXFactory.INSTANCE.createSimpleGrid(1,
				getVElement().getLabelAlignment() == LabelAlignment.NONE ? 2 : 3, this);
		}
		return rendererGridDescription;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected final Node renderNode(GridCellFX gridCell)
		throws NoRendererFoundException, NoPropertyDescriptorFoundExeption {
		int controlIndex = gridCell.getColumn();
		if (getVElement().getLabelAlignment() == LabelAlignment.NONE) {
			controlIndex++;
		}
		switch (controlIndex) {
		case 0:
			return createLabel();
		case 1:
			validationIcon = (Label) createValidationIcon();
			return validationIcon;
		case 2:
			return createControl();
		default:
			throw new IllegalArgumentException(
				String
					.format(
						"The provided GridCellFX (%1$s) cannot be used by this (%2$s) renderer.", gridCell.toString(), toString())); //$NON-NLS-1$
		}
	}

	@Override
	protected void applyValidation(VControl control, Node node) {
		super.applyValidation(control, node);
		if (control.getDiagnostic() == null) {
			validationIcon.setGraphic(null);
			return;
		}
		final VDiagnostic diagnostic = control.getDiagnostic();
		switch (diagnostic.getHighestSeverity()) {
		case Diagnostic.ERROR:
			validationIcon.setGraphic(new ImageView(Activator.getImage("icons/validation_error.png"))); //$NON-NLS-1$
			validationIcon.setTooltip(new Tooltip(diagnostic.getMessage()));
			break;
		case Diagnostic.OK:
			validationIcon.setGraphic(null);
			break;
		}
	}

	/**
	 * Creates the control itself (e.g. a checkbox for a boolean control)
	 *
	 * @return the control itself
	 */
	protected abstract Node createControl();
}