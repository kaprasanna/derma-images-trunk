package com.bh.derma.images.ui;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;


class OriginalSizeImageDialog extends Dialog {
	private Image[] images;
	protected OriginalSizeImageDialog(Shell parentShell, Image[] images) {
		super(parentShell);
		this.images = images;
	}
	
	@Override
	protected Control createDialogArea(Composite parent) {
		final Composite imageBaseComposite = (Composite) super.createDialogArea(parent);
		GridData imageBaseCompositeData = new GridData(GridData.GRAB_HORIZONTAL | GridData.FILL_BOTH);
		imageBaseComposite.setLayoutData(imageBaseCompositeData);
		GridLayout imageBaseCompositeGL = new GridLayout(2, false);
		imageBaseComposite.setLayout(imageBaseCompositeGL);
		imageBaseComposite.setSize(imageBaseComposite.computeSize(parent.getSize().x, SWT.DEFAULT));
		
		ScrolledComposite scrolledComposite = new ScrolledComposite(
									imageBaseComposite, SWT.H_SCROLL | SWT.V_SCROLL);
		scrolledComposite.setExpandHorizontal(true);
		scrolledComposite.setExpandVertical(true);
		GridData scrolledCompositeGD = new GridData(GridData.GRAB_HORIZONTAL | GridData.FILL_BOTH);
		scrolledCompositeGD.horizontalSpan = 2;
		scrolledComposite.setLayoutData(scrolledCompositeGD);
		scrolledComposite.setSize(scrolledComposite.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		
		Composite imagesComposite = new Composite(scrolledComposite, SWT.BORDER);
		GridLayout imageCompositeGL = new GridLayout(1, false);
		imagesComposite.setLayout(imageCompositeGL);
		GridData imageCompositeGD = new GridData(GridData.FILL_BOTH);
		imagesComposite.setLayoutData(imageCompositeGD);
		
		int numberOfImages = images.length;
		if(numberOfImages > 1) {
			int possibleNumberOfImages = 0;
			int numberofColumns = 0;
			int numberOfRows = 0;
			if(numberOfImages % 2 != 0) {
				possibleNumberOfImages = numberOfImages + 1;
				numberOfRows = 2;
			} else {
				possibleNumberOfImages = numberOfImages;
				numberOfRows = numberOfImages / 2;
			}
			
			numberofColumns = possibleNumberOfImages / numberOfRows;
			int widthofEachImage = Display.getDefault().getBounds().width / numberofColumns;
			int heightOfEachImage = Display.getDefault().getBounds().height / numberOfRows;
			
			imagesComposite.setLayoutData(null);
			imagesComposite.setLayout(null);
			
			GridLayout imageCompositeGLMulti = new GridLayout(numberofColumns, false);
			imagesComposite.setLayout(imageCompositeGLMulti);
			
			
			for(final Image image : images) {
				Composite imageComposite = new Composite(imagesComposite, SWT.BORDER);
				GridData imageCompositeGridData = new GridData(widthofEachImage, heightOfEachImage);
				imageComposite.setLayoutData(imageCompositeGridData);
				
				imageComposite.addMouseListener(new MouseListener() {
					@Override
					public void mouseUp(MouseEvent e) {}

					@Override
					public void mouseDown(MouseEvent e) {}

					@Override
					public void mouseDoubleClick(MouseEvent e) {
						Dialog dialog = new OriginalSizeImageDialog(
								Display.getDefault().getActiveShell(), new Image[] {image});
						dialog.open();
					}
				});
				ResizeImageListener comp1Listener = new ResizeImageListener(
						image, imageComposite, widthofEachImage, heightOfEachImage, false);
				imageComposite.addListener (SWT.Dispose, comp1Listener);
				imageComposite.addListener (SWT.Paint, comp1Listener);
			}
		} else {
			Label imageLabel = new Label(imagesComposite, SWT.BORDER);
			imageLabel.setImage(images[0]);
		}
		
		Point size = imagesComposite.computeSize(SWT.DEFAULT, SWT.DEFAULT);
		scrolledComposite.setMinSize(size);
		
		scrolledComposite.setContent(imagesComposite);
		scrolledComposite.layout(true);
		
		return imageBaseComposite;
	}
	
	@Override
	protected Button createButton(Composite parent, int id, String label,
			boolean defaultButton) {
		return null;
	}
}