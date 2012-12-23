package com.bh.derma.images.ui;

import java.io.File;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.nebula.widgets.gallery.DefaultGalleryGroupRenderer;
import org.eclipse.nebula.widgets.gallery.DefaultGalleryItemRenderer;
import org.eclipse.nebula.widgets.gallery.Gallery;
import org.eclipse.nebula.widgets.gallery.GalleryItem;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageLoader;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.layout.GridData;

public class ImagesGridView extends ViewPart {

	public static final String ID = "com.bh.derma.images.ui.ImagesGridView"; //$NON-NLS-1$

	public ImagesGridView() {
	}

	/**
	 * Create contents of the view part.
	 * @param parent
	 */
	@Override
	public void createPartControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		
/*		
		// image grid
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
//		
//		gallery.addListener(SWT.Selection, new Listener() {
//			@Override
//			public void handleEvent(Event event) {
//				GalleryItem[] selectedItem = gallery.getSelection();
//			}
//		});

		
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
				subItem.setData(imgFilePath);
				subItem.setImage(img);
			}			
		}
		
		// image grid end*/
		Composite composite = new Composite(container, SWT.NONE);
		composite.setBounds(10, 10, 940, 589);
		composite.setLayout(new GridLayout(1, false));
		
		ScrolledComposite scrolledComposite = new ScrolledComposite(composite, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		GridData gd_scrolledComposite = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_scrolledComposite.widthHint = 910;
		gd_scrolledComposite.heightHint = 558;
		scrolledComposite.setLayoutData(gd_scrolledComposite);
		scrolledComposite.setExpandHorizontal(true);
		scrolledComposite.setExpandVertical(true);
		
		Button btnRemoveSelected = new Button(container, SWT.NONE);
		btnRemoveSelected.setBounds(22, 618, 108, 25);
		btnRemoveSelected.setText("Remove Selected");
		
		Button btnNewButton = new Button(container, SWT.NONE);
		btnNewButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
			}
		});
		btnNewButton.setBounds(22, 657, 108, 25);
		btnNewButton.setText("Compare Selected");

		createActions();
		initializeToolBar();
		initializeMenu();
	}

	/**
	 * Create the actions.
	 */
	private void createActions() {
		// Create the actions
	}

	/**
	 * Initialize the toolbar.
	 */
	private void initializeToolBar() {
		IToolBarManager toolbarManager = getViewSite().getActionBars()
				.getToolBarManager();
	}

	/**
	 * Initialize the menu.
	 */
	private void initializeMenu() {
		IMenuManager menuManager = getViewSite().getActionBars()
				.getMenuManager();
	}

	@Override
	public void setFocus() {
		// Set the focus
	}
}