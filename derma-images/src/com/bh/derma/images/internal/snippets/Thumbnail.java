package com.bh.derma.images.internal.snippets;

import java.io.File;

import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTException;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;

public class Thumbnail {
	public static void main (String [] args) {
		final Display display = new Display ();
		final Shell shell = new Shell (display);
		Button button = new Button (shell, SWT.PUSH);
		button.setText ("Load Images ...");
		final ScrolledComposite sc = new ScrolledComposite (shell, SWT.H_SCROLL |
				SWT.V_SCROLL);
		final Composite comp = new Composite (sc, SWT.NONE);
		comp.setLayout (new RowLayout (SWT.VERTICAL));
		sc.setContent (comp);
		FormLayout layout = new FormLayout ();
		FormData scData = new FormData ();
		scData.left = new FormAttachment (0, 0);
		scData.right = new FormAttachment (100, 0);
		scData.top = new FormAttachment (button, 0);
		scData.bottom = new FormAttachment (100, 0);
		sc.setLayoutData (scData);
		shell.setLayout (layout);
		final String [] path = new String [] {"C:/Users/pk022878/Pictures/From Prasanna-HTC/Saved pictures"};
		button.addListener (SWT.Selection, new Listener () {
			public void handleEvent (Event e) {
				DirectoryDialog dialog = new DirectoryDialog (shell);
				dialog.setFilterPath (path [0]);
//				if (dialog.open () == null) return;
				Control [] children = comp.getChildren ();
				for (int i=0; i<children.length; i++) {
					children [i].dispose ();
				}
				File file = new File (path [0]);
				File [] files = file.listFiles ();
				for (int i= 0; i<files.length; i++) {
					if (!files [i].isDirectory()) {
						final Image image;
						try {
							image = new Image (display, files [i].getAbsolutePath ());
						} catch (SWTException exception) {
							continue;
						}
						final Composite c = new Composite (comp, SWT.BORDER);
						Listener listener = new Listener () {
							public void handleEvent (Event e) {
								switch (e.type) {
								case SWT.Dispose: image.dispose (); break;
								case SWT.Paint: {
									Rectangle rect = c.getClientArea ();
									Rectangle bounds = image.getBounds ();
									int x = 0, y = 0, width = 0, height = 0;
									if (bounds.width > bounds.height) {
										width = rect.width;
										height = bounds.height * rect.height / bounds.width;
										y = (rect.height - height) / 2;
									} else {
										height = rect.height;
										width = bounds.width * rect.width / bounds.height;
										x = (rect.width - width) / 2;
									}
									e.gc.drawImage (image, 0, 0, bounds.width, bounds.height, x, y,
											width, height);
								}
								}
							}
						};
						c.addListener (SWT.Dispose, listener);
						c.addListener (SWT.Paint, listener);
						c.setLayoutData (new RowData (200, 200));
					}
				}
				comp.pack ();
				sc.layout ();
			}
		});
		shell.setSize (300, 300);
		shell.open ();
		while (!shell.isDisposed ()) {
			if (!display.readAndDispatch ()) display.sleep ();
		}
		display.dispose ();
	}
}