package org.eclipse.emf.ecp.controls.renderer.fx;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import org.eclipse.core.databinding.UpdateValueStrategy;
import org.eclipse.emf.databinding.EMFUpdateValueStrategy;

/**
 * A JavaFX renderer for {@link java.util.Date}.
 *
 * @author Lucas
 *
 */
public class DateRendererFX extends AbstractDateRendererFX {

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
