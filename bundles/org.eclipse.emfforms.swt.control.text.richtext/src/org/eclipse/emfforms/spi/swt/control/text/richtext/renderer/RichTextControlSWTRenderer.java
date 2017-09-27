/*******************************************************************************
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Jonas Helming - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.spi.swt.control.text.richtext.renderer;

import javax.inject.Inject;

import org.eclipse.core.databinding.Binding;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.UpdateValueStrategy;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.core.swt.renderer.TextControlSWTRenderer;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.template.model.VTViewTemplateProvider;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.core.services.databinding.EMFFormsDatabinding;
import org.eclipse.emfforms.spi.core.services.editsupport.EMFFormsEditSupport;
import org.eclipse.emfforms.spi.core.services.label.EMFFormsLabelProvider;
import org.eclipse.emfforms.spi.swt.core.layout.EMFFormsSWTLayoutUtil;
import org.eclipse.emfforms.spi.swt.core.util.PopupWindow;
import org.eclipse.jface.databinding.swt.WidgetProperties;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.TraverseEvent;
import org.eclipse.swt.events.TraverseListener;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Text;

/**
 * The multi line text control renderer displays the text in a read only fashion. Once the user starts editing (pressing
 * any key except tab), a
 * popupcontrol is displayed allowing the user to edit the contents.
 *
 * @author Jonas Helming
 *
 */
public class RichTextControlSWTRenderer extends TextControlSWTRenderer {
	private GridData textGridData;
	private StyledText text;

	private static final String RETURN_PATTERN = "[\n]"; //$NON-NLS-1$
	private static final String CARRIAGE_PATTERN = "[\r]"; //$NON-NLS-1$
	private PopupWindow popupWindow;
	private Text innerText;
	private Binding binding;

	/**
	 * @author Jonas Helming
	 *
	 */
	private final class OpenPopupHandler implements MouseListener {
		private final StyledText text;

		private OpenPopupHandler(StyledText text) {
			this.text = text;
		}

		@Override
		public void mouseUp(MouseEvent e) {
			openPopUp(text, false);
		}

		@Override
		public void mouseDown(MouseEvent e) {

		}

		@Override
		public void mouseDoubleClick(MouseEvent e) {
		}
	}

	private void openPopUp(final StyledText text, boolean selectAll) {
		popupWindow = createPopupWindow();

		innerText = new Text(popupWindow.getContent(),
			SWT.MULTI | SWT.WRAP | SWT.V_SCROLL | SWT.H_SCROLL);
		final GridData layoutData = new GridData(SWT.FILL, SWT.FILL, true, true);
		layoutData.heightHint = getPreferrredPopupHeight();
		innerText.setLayoutData(layoutData);
		innerText.setSize(300, getPreferrredPopupHeight());
		popupWindow.getContent().pack();
		popupWindow.open();

		innerText.setFocus();
		innerText.setText(text.getText());

		if (selectAll) {
			innerText.selectAll();
		} else {
			innerText.setSelection(text.getSelectionRange().x, text.getSelectionRange().x + text.getSelectionCount());
		}

		innerText.addVerifyListener(new VerifyListener() {

			@Override
			public void verifyText(VerifyEvent e) {
				if (e.text != null) {
					e.text = e.text.replaceAll(CARRIAGE_PATTERN, "").replaceAll(RETURN_PATTERN, Text.DELIMITER); //$NON-NLS-1$
				}
			}
		});

		innerText.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.keyCode == 'a' && (isCtrlKeyPressed(e) || isCommandKeyPressed(e))) {
					innerText.selectAll();
				}
			}
		});

		innerText.addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent e) {
				storePopUpInOriginalText(text, innerText);
				popupWindow.close();
			}

			@Override
			public void focusGained(FocusEvent e) {
				// do nothing
			}
		});

		innerText.addDisposeListener(new DisposeListener() {

			@Override
			public void widgetDisposed(DisposeEvent e) {
				storePopUpInOriginalText(text, innerText);
				text.setCaretOffset(innerText.getCaretPosition());

			}

		});

	}

	private void storePopUpInOriginalText(final StyledText originalText, Text popUpText) {
		originalText.setText(popUpText.getText());
		originalText.setSelection(popUpText.getCaretPosition());
		binding.updateTargetToModel();
	}

	private static boolean isCtrlKeyPressed(KeyEvent e) {
		return (e.stateMask & SWT.MODIFIER_MASK) == SWT.CTRL;
	}

	private static boolean isCommandKeyPressed(KeyEvent e) {
		return (e.stateMask & SWT.MODIFIER_MASK) == SWT.COMMAND;
	}

	/**
	 * Specifies the preferred size of the popup window.
	 *
	 * @return the size in pixel
	 */
	protected int getPreferrredPopupHeight() {
		return 450;
	}

	/**
	 * creates the popup window.
	 *
	 * @return a new {@link PopupWindow}
	 */
	protected PopupWindow createPopupWindow() {
		return new PopupWindow(text, getPreferrredPopupHeight(), SWT.RESIZE, true);
	}

	/**
	 * Constructs a new {@link RichTextControlSWTRenderer}.
	 *
	 * @param vElement the view model element to be rendered
	 * @param viewContext the view context
	 * @param reportService The {@link ReportService}
	 * @param emfFormsDatabinding The {@link EMFFormsDatabinding}
	 * @param emfFormsLabelProvider The {@link EMFFormsLabelProvider}
	 * @param vtViewTemplateProvider The {@link VTViewTemplateProvider}
	 * @param emfFormsEditSupport The {@link EMFFormsEditSupport}
	 */
	@Inject
	public RichTextControlSWTRenderer(
		VControl vElement,
		ViewModelContext viewContext,
		ReportService reportService,
		EMFFormsDatabinding emfFormsDatabinding,
		EMFFormsLabelProvider emfFormsLabelProvider,
		VTViewTemplateProvider vtViewTemplateProvider, EMFFormsEditSupport emfFormsEditSupport) {
		super(vElement, viewContext, reportService, emfFormsDatabinding, emfFormsLabelProvider, vtViewTemplateProvider,
			emfFormsEditSupport);
	}

	/**
	 * {@inheritDoc}
	 *
	 * Databinding is set to SWT.Modify, because we cannot guarantee that the focus of the text field is lost after the
	 * popup windows is closed.
	 *
	 * @see org.eclipse.emf.ecp.view.spi.core.swt.renderer.TextControlSWTRenderer#bindValue(org.eclipse.swt.widgets.Control,
	 *      org.eclipse.core.databinding.observable.value.IObservableValue,
	 *      org.eclipse.core.databinding.DataBindingContext, org.eclipse.core.databinding.UpdateValueStrategy,
	 *      org.eclipse.core.databinding.UpdateValueStrategy)
	 */
	@Override
	protected Binding bindValue(Control text, IObservableValue modelValue, DataBindingContext dataBindingContext,
		UpdateValueStrategy targetToModel, UpdateValueStrategy modelToTarget) {
		final IObservableValue value = WidgetProperties.text(SWT.NONE).observe(this.text);
		binding = dataBindingContext.bindValue(value, modelValue, targetToModel, modelToTarget);
		return binding;
	}

	@Override
	protected Control createSWTControl(Composite parent) {
		final Composite composite = new Composite(parent, SWT.NONE);
		GridLayoutFactory.fillDefaults().numColumns(1).equalWidth(true).applyTo(composite);
		text = new StyledText(composite, getTextWidgetStyle());
		text.setData(CUSTOM_VARIANT, getTextVariantID());
		text.setEditable(true);
		text.setToolTipText(getTextMessage());
		final GridDataFactory gdf = GridDataFactory.fillDefaults().align(SWT.FILL, SWT.CENTER)
			.grab(true, true).span(1, 1);
		final EMFFormsEditSupport editSupport = getEMFFormsEditSupport();
		if (editSupport.isMultiLine(getVElement().getDomainModelReference(), getViewModelContext().getDomainModel())) {
			gdf.hint(0, getTextHeightHint());// set x hint to enable wrapping
		}
		textGridData = gdf.create();
		text.setLayoutData(textGridData);
		text.addMouseListener(new OpenPopupHandler(text));
		text.addTraverseListener(new TraverseListener() {

			@Override
			public void keyTraversed(TraverseEvent e) {
				e.doit = true;

			}
		});
		text.addKeyListener(new KeyListener() {

			@Override
			public void keyReleased(KeyEvent e) {

			}

			@Override
			public void keyPressed(KeyEvent e) {

				if (isOpenKey(e)) {
					boolean selectAll = false;
					if ((isCommandKeyPressed(e) || isCtrlKeyPressed(e)) && e.keyCode == 'a') {
						selectAll = true;
					}
					openPopUp(text, selectAll);
				}

			}
		});

		text.addModifyListener(new ModifyListener() {

			@Override
			public void modifyText(ModifyEvent e) {
				textGridData.heightHint = getTextHeightHint();
				text.setLayoutData(textGridData);
				EMFFormsSWTLayoutUtil.adjustParentSize(text);
			}

		});
		return composite;
	}

	/**
	 * Determines, whether the popup shall be opened on a specific KeyEvent on the regular Text Control. By default the
	 * popup opens on any key except SHIFT and ALT, but additional keys can be added by overriding the method.
	 *
	 * @param e the {@link KeyEvent} on the regular {@link Text}
	 * @return whether the popup should open
	 * @since 1.14
	 */
	protected boolean isOpenKey(KeyEvent e) {
		if (e.keyCode == SWT.SHIFT) {
			return false;
		}
		if (e.keyCode == SWT.ALT) {
			return false;
		}
		if (e.keyCode == SWT.CTRL) {
			return false;
		}
		if (e.keyCode == SWT.COMMAND) {
			return false;
		}

		return true;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.core.swt.AbstractControlSWTRenderer#postInit()
	 */
	@Override
	protected void postInit() {
		super.postInit();
	}

	/**
	 * The preferred height in pixels for the text control.
	 *
	 * @return the height hint
	 * @see GridData#heightHint
	 */
	protected int getTextHeightHint() {
		if (text == null || text.isDisposed()) {
			return -1;
		}
		final int lineCount = text.getLineCount();
		int height = lineCount * text.getLineHeight();

		final int maxHeight = getMaxTextHeight();
		final int minHeight = getMinTextHeight();
		if (height > maxHeight) {
			height = maxHeight;
		} else if (height < minHeight) {
			height = minHeight;
		}
		return height;
	}

	/**
	 * The maximum height in pixels for the text control.
	 *
	 * @return the maximum height
	 */
	protected int getMaxTextHeight() {
		if (text == null || text.isDisposed()) {
			return -1;
		}

		return getMaxVisibleLines() * text.getLineHeight();
	}

	/**
	 * The minimum height in pixels for the text control.
	 *
	 * @return the minimum height
	 */
	protected int getMinTextHeight() {
		if (text == null || text.isDisposed()) {
			return -1;
		}
		return getMinVisibleLines() * text.getLineHeight();
	}

	/**
	 * The minimum number of visible lines in the text control.
	 *
	 * @return the minimum visible lines
	 */
	protected int getMinVisibleLines() {
		return 2;
	}

	/**
	 * The maximum number of visible lines in the text control.
	 *
	 * @return the maximum visible lines
	 */
	protected int getMaxVisibleLines() {
		return 10;
	}

}
