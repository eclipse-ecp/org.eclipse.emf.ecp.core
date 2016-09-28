/*******************************************************************************
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Johannes Faltermeier - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.spi.swt.control.text.richtext.renderer;

import javax.inject.Inject;

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
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Text;

/**
 * The text control renderer allows to enter text while given autocompletion proposals. Moreover it is possible to
 * select a proposed value from a combo box.
 *
 * @author jfaltermeier
 *
 */
public class RichTextControlSWTRenderer extends TextControlSWTRenderer {
	private GridData textGridData;
	private Text text;

	/**
	 * @author jonas
	 *
	 */
	private final class OpenPopupHandler implements MouseListener {
		private final Text text;

		private OpenPopupHandler(Text text) {
			this.text = text;
		}

		@Override
		public void mouseUp(MouseEvent e) {
			// do nothing
		}

		@Override
		public void mouseDown(MouseEvent e) {

			final PopupWindow popupWindow = createPopupWindow();

			final Text innerText = new Text(popupWindow.getContent(),
				SWT.MULTI | SWT.WRAP | SWT.V_SCROLL | SWT.H_SCROLL);
			final GridData layoutData = new GridData(SWT.FILL, SWT.FILL, true, true);
			layoutData.heightHint = getPreferrredPopupHeight();
			innerText.setLayoutData(layoutData);
			innerText.setSize(300, getPreferrredPopupHeight());
			popupWindow.getContent().pack();
			innerText.setText(text.getText());
			innerText.setSelection(text.getCaretPosition());

			innerText.addFocusListener(new FocusListener() {
				@Override
				public void focusLost(FocusEvent e) {
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
					text.setText(innerText.getText());
				}
			});
			popupWindow.open();

		}

		@Override
		public void mouseDoubleClick(MouseEvent e) {
			// do nothing
		}
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

	@Override
	protected Control createSWTControl(Composite parent) {
		final Composite composite = new Composite(parent, SWT.NONE);
		GridLayoutFactory.fillDefaults().numColumns(1).equalWidth(true).applyTo(composite);
		text = new Text(composite, getTextWidgetStyle());
		text.setData(CUSTOM_VARIANT, getTextVariantID());
		text.setEditable(false);
		text.setMessage(getTextMessage());
		final GridDataFactory gdf = GridDataFactory.fillDefaults().align(SWT.FILL, SWT.CENTER)
			.grab(true, true).span(1, 1);
		final EMFFormsEditSupport editSupport = getEMFFormsEditSupport();
		if (editSupport.isMultiLine(getVElement().getDomainModelReference(), getViewModelContext().getDomainModel())) {
			gdf.hint(50, getTextHeightHint());// set x hint to enable wrapping
		}
		textGridData = gdf.create();
		text.setLayoutData(textGridData);
		text.addMouseListener(new OpenPopupHandler(text));
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
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.core.swt.AbstractControlSWTRenderer#postInit()
	 */
	@Override
	protected void postInit() {
		// TODO Auto-generated method stub
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
