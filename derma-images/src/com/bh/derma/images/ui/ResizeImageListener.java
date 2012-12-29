package com.bh.derma.images.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

class ResizeImageListener implements Listener {
	Image image;
	Composite c;
	int width;
	int height;
	boolean resize;
	
	public ResizeImageListener(Image image, Composite c, int width, int height, boolean resize) {
		super();
		this.image = image;
		this.c = c;
		this.width = width;
		this.height = height;
		this.resize = resize;
	}

	public ResizeImageListener(Image image, Composite composite, boolean resize) {
		super();
		this.image = image;
		this.c = composite;
		this.width = 0;
		this.height = 0;
		this.resize = resize;
	}

	public void handleEvent (Event e) {
		switch (e.type) {
			case SWT.Dispose: image.dispose (); break;
			case SWT.Paint: {
				Rectangle bounds = image.getBounds ();
				int x = 0, y = 0;
				if(resize) {
					Rectangle rect = c.getClientArea ();
					if (bounds.width > bounds.height) {
						width = rect.width;
						height = bounds.height * rect.height / bounds.width;
					} else {
						height = rect.height;
						width = bounds.width * rect.width / bounds.height;
					}
					if(width < rect.width) width = rect.width;
				}
				e.gc.drawImage (image, 0, 0, bounds.width, bounds.height,
														  x, y, width, height);
			}
		}
	}
}