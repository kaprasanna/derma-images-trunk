package com.bh.derma.images.internal.snippets;

import java.io.File;

import org.eclipse.nebula.widgets.gallery.DefaultGalleryGroupRenderer;
import org.eclipse.nebula.widgets.gallery.DefaultGalleryItemRenderer;
import org.eclipse.nebula.widgets.gallery.Gallery;
import org.eclipse.nebula.widgets.gallery.GalleryItem;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageLoader;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.program.Program;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class NebulaGalleryTry {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Display display = new Display();
		Image itemImage = new Image(display, Program
				.findProgram("jpg").getImageData()); //$NON-NLS-1$

		Shell parent = new Shell(display);
		parent.setLayout(new FillLayout());
		
		final Gallery gallery = new Gallery(parent, SWT.V_SCROLL | SWT.VIRTUAL);
		gallery.setVirtualGroups(true);


		// Group Renderer
		DefaultGalleryGroupRenderer gr = new DefaultGalleryGroupRenderer();
		gr.setItemSize(64, 64);
		gr.setMinMargin(3);
		//Item Renderer
		DefaultGalleryItemRenderer ir = new DefaultGalleryItemRenderer();

		gallery.setGroupRenderer(gr);
		gallery.setItemRenderer(ir);
		
		gallery.setItemCount(1);
		
		GalleryItem parentItem = gallery.getItem(0);
		//use a folder name
		if(parentItem.getParentItem() == null)
		{
			parentItem.setText("C:\\Users\\pk022878\\Pictures\\Bannerghatta national park\\Temp");
			//add the contents of the folder
			File f = new File("C:\\Users\\pk022878\\Pictures\\Bannerghatta national park\\Temp");
			File[] contents = f.listFiles();
			
			//set number of items in this group 
			parentItem.setItemCount(contents.length);
			
			for(int i =0; i < contents.length; i++)
			{
				String imgFilePath = contents[i].getAbsolutePath();
				
				ImageLoader loader = new ImageLoader();
				GalleryItem subItem = parentItem.getItem(i);
				Image img = new Image(parent.getDisplay(), loader.load(imgFilePath)[0]);
				subItem.setImage(img);
				
			}			
		}
		parent.pack();
		parent.open();
		while (!parent.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}

		if (itemImage != null)
			itemImage.dispose();
		display.dispose();
	}

}
