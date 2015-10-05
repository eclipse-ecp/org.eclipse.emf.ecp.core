package org.eclipse.emf.ecp.controls.renderer.fx;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import org.eclipse.core.databinding.UpdateValueStrategy;
import org.eclipse.emf.databinding.EMFUpdateValueStrategy;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emfforms.spi.common.report.ReportService;

/**
 * A JavaFX renderer for {@link java.util.Date}.
 *
 * @author Lucas
 *
 */
public class DateRendererFX extends AbstractDateRendererFX {

	/**
	 * Default constructor.
	 *
	 * @param vElement the {@link VElement} to be rendered
	 * @param viewContext the {@link ViewModelContext} to use
	 * @param reportService The {@link ReportService} to use
	 */
	public DateRendererFX(VControl vElement, ViewModelContext viewContext, ReportService reportService) {
		super(vElement, viewContext, reportService);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.controls.renderer.fx.AbstractDateRendererFX#getModelToTargetStrategy()
	 */
	@Override
	protected UpdateValueStrategy getModelToTargetStrategy() {
		return new EMFUpdateValueStrategy() {
			@Override
			public Object convert(Object value) {
				if (value == null) {
					return null;
				}
				final Date date = (Date) value;
				final Instant ins = Instant.ofEpochMilli(date.getTime());
				final LocalDate result = LocalDateTime.ofInstant(ins, ZoneId.systemDefault()).toLocalDate();
				return result;
			}
		};
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.controls.renderer.fx.AbstractDateRendererFX#getTargetToModelStrategy()
	 */
	@Override
	protected UpdateValueStrategy getTargetToModelStrategy() {
		return new EMFUpdateValueStrategy() {
			@Override
			public Object convert(Object value) {
				if (value == null) {
					return null;
				}
				final LocalDate localDate = (LocalDate) value;
				final Instant ins = localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
				final Date result = Date.from(ins);
				return result;
			}
		};
	}
}
