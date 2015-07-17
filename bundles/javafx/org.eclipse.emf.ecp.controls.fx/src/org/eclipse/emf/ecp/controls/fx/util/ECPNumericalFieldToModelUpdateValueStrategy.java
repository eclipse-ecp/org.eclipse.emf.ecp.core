package org.eclipse.emf.ecp.controls.fx.util;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Locale;

import org.eclipse.core.databinding.conversion.IConverter;
import org.eclipse.core.databinding.conversion.StringToNumberConverter;
import org.eclipse.emf.ecp.controls.internal.fx.Activator;

import com.ibm.icu.text.NumberFormat;

public class ECPNumericalFieldToModelUpdateValueStrategy extends
	ECPTextFieldToModelUpdateValueStrategy {

	public ECPNumericalFieldToModelUpdateValueStrategy(Class<?> clazz) {
		final IConverter converter = getConverter(clazz);
		setConverter(converter);
	}

	private IConverter getConverter(Class<?> clazz) {
		final Locale locale = Locale.getDefault();
		final NumberFormat numberInstance = NumberFormat.getNumberInstance(locale);
		numberInstance.setParseIntegerOnly(NumericalHelper.isInteger(clazz));
		numberInstance.setGroupingUsed(false);
		numberInstance.setMaximumFractionDigits(100);

		if (clazz.isPrimitive()) {
			try {
				if (Double.class.getField("TYPE").get(null).equals(clazz)) { //$NON-NLS-1$
					return StringToNumberConverter.toDouble(numberInstance,
						true);
				} else if (Float.class.getField("TYPE").get(null).equals(clazz)) { //$NON-NLS-1$
					return StringToNumberConverter
						.toFloat(numberInstance, true);
				} else if (Integer.class
					.getField("TYPE").get(null).equals(clazz)) { //$NON-NLS-1$
					return StringToNumberConverter.toInteger(numberInstance,
						true);
				} else if (Long.class.getField("TYPE").get(null).equals(clazz)) { //$NON-NLS-1$
					return StringToNumberConverter.toLong(numberInstance, true);
				}
			} catch (final IllegalArgumentException ex) {
				Activator.logException(ex);
			} catch (final SecurityException ex) {
				Activator.logException(ex);
			} catch (final IllegalAccessException ex) {
				Activator.logException(ex);
			} catch (final NoSuchFieldException ex) {
				Activator.logException(ex);
			}
		} else if (BigDecimal.class.isAssignableFrom(clazz)) {
			return StringToNumberConverter.toBigDecimal(numberInstance);
		} else if (BigInteger.class.isAssignableFrom(clazz)) {
			return StringToNumberConverter.toBigInteger(numberInstance);
		} else if (Double.class.isAssignableFrom(clazz)) {
			return StringToNumberConverter.toDouble(numberInstance, false);
		} else if (Float.class.isAssignableFrom(clazz)) {
			return StringToNumberConverter.toFloat(numberInstance, false);
		} else if (Integer.class.isAssignableFrom(clazz)) {
			return StringToNumberConverter.toInteger(numberInstance, false);
		} else if (Long.class.isAssignableFrom(clazz)) {
			return StringToNumberConverter.toLong(numberInstance, false);
		}
		return null;
	}

}
