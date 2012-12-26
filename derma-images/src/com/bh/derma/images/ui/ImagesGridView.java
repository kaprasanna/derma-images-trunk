package com.bh.derma.images.ui;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.part.ViewPart;

public class ImagesGridView extends ViewPart {

	public static final String ID = "com.bh.derma.images.ui.ImagesGridView"; //$NON-NLS-1$
	private Composite thumbnailGridComposite;
	private ScrolledComposite viewerScrolledComposite;

	public Composite getThumbnailGridComposite() {
		return thumbnailGridComposite;
	}
	
	public ScrolledComposite getViewerScrolledComposite() {
		return viewerScrolledComposite;
	}
	
	public ImagesGridView() {
	}

	/**
	 * Create contents of the view part.
	 * @param parent
	 */
	@Override
	public void createPartControl(Composite parent) {
		final Composite thumbnailComposite = new Composite(parent, SWT.NONE);
		GridLayout viewerCompositeGL = new GridLayout(2, false);
		thumbnailComposite.setLayout(viewerCompositeGL);
		GridData viewerCompositeData = new GridData(GridData.GRAB_HORIZONTAL | GridData.FILL_BOTH);
		viewerCompositeData.horizontalSpan = 2;
		thumbnailComposite.setLayoutData(viewerCompositeData);
		thumbnailComposite.setSize(thumbnailComposite.computeSize(parent.getSize().x, SWT.DEFAULT));
		
		viewerScrolledComposite = new ScrolledComposite(
									thumbnailComposite, SWT.H_SCROLL | SWT.V_SCROLL);
		viewerScrolledComposite.setExpandHorizontal(true);
		viewerScrolledComposite.setExpandVertical(true);
		GridData data = new GridData(GridData.GRAB_HORIZONTAL | GridData.FILL_BOTH);
		data.horizontalSpan = 2;
		viewerScrolledComposite.setLayoutData(data);
		viewerScrolledComposite.setSize(viewerScrolledComposite.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		
		thumbnailGridComposite = new Composite(viewerScrolledComposite, SWT.BORDER);
		GridLayout thumbnailGridCompositeGL = new GridLayout(3, false);
		thumbnailGridComposite.setLayout(thumbnailGridCompositeGL);
		GridData thumbnailGridCompositeGD = new GridData(GridData.FILL_BOTH);
		thumbnailGridCompositeGD.horizontalSpan=2;
		thumbnailGridComposite.setLayoutData(thumbnailGridCompositeGD);
		
//		Label imageLabel1 = new Label(thumbnailGridComposite, SWT.BORDER);
//		imageLabel1.setImage(new Image(Display.getDefault(), "C:\\Users\\pk022878\\Pictures\\Photo-ID.png"));
		
		Point size = thumbnailGridComposite.computeSize(SWT.DEFAULT, SWT.DEFAULT);
		viewerScrolledComposite.setMinSize(size);
		
		viewerScrolledComposite.setContent(thumbnailGridComposite);
		viewerScrolledComposite.layout(true);
		
		Button btnRemoveSelected = new Button(thumbnailComposite, SWT.NONE);
		btnRemoveSelected.setBounds(22, 618, 108, 25);
		btnRemoveSelected.setText("Remove Selected");
		
		Button btnNewButton = new Button(thumbnailComposite, SWT.NONE);
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