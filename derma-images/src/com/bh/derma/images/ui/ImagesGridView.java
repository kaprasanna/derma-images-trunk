package com.bh.derma.images.ui;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseWheelListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.ui.part.ViewPart;

public class ImagesGridView extends ViewPart {

	public static final String ID = "com.bh.derma.images.ui.ImagesGridView"; //$NON-NLS-1$
	private Composite thumbnailGridComposite;
	private ScrolledComposite thumbnailGridScrolledComposite;

	public Composite getThumbnailGridComposite() {
		return thumbnailGridComposite;
	}
	
	public ScrolledComposite getViewerScrolledComposite() {
		return thumbnailGridScrolledComposite;
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
		
		thumbnailGridScrolledComposite = new ScrolledComposite(
									thumbnailComposite, SWT.H_SCROLL | SWT.V_SCROLL);
		thumbnailGridScrolledComposite.setExpandHorizontal(true);
		thumbnailGridScrolledComposite.setExpandVertical(true);
		GridData data = new GridData(GridData.GRAB_HORIZONTAL | GridData.FILL_BOTH);
		data.horizontalSpan = 2;
		thumbnailGridScrolledComposite.setLayoutData(data);
		thumbnailGridScrolledComposite.setSize(thumbnailGridScrolledComposite.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		
		thumbnailGridComposite = new Composite(thumbnailGridScrolledComposite, SWT.BORDER);
		GridLayout thumbnailGridCompositeGL = new GridLayout(3, false);
		thumbnailGridComposite.setLayout(thumbnailGridCompositeGL);
		GridData thumbnailGridCompositeGD = new GridData(GridData.FILL_BOTH);
		thumbnailGridCompositeGD.horizontalSpan=2;
		thumbnailGridComposite.setLayoutData(thumbnailGridCompositeGD);
		
//		Label imageLabel1 = new Label(thumbnailGridComposite, SWT.BORDER);
//		imageLabel1.setImage(new Image(Display.getDefault(), "C:\\Users\\pk022878\\Pictures\\Photo-ID.png"));
		
		Point size = thumbnailGridComposite.computeSize(SWT.DEFAULT, SWT.DEFAULT);
		thumbnailGridScrolledComposite.setMinSize(size);
		
		thumbnailGridScrolledComposite.setContent(thumbnailGridComposite);
		thumbnailGridScrolledComposite.layout(true);
		
		Button btnRemoveSelected = new Button(thumbnailComposite, SWT.NONE);
		btnRemoveSelected.setBounds(22, 618, 108, 25);
		btnRemoveSelected.setText("Remove Selected");
		
		thumbnailGridScrolledComposite.addListener(SWT.Activate, new Listener() {
			public void handleEvent(Event e) {
				thumbnailGridScrolledComposite.setFocus();
			}
		});
		
//		thumbnailGridScrolledComposite.addMouseWheelListener(new MouseWheelListener() {
//			@Override
//			public void mouseScrolled(MouseEvent e) {
//				thumbnailGridScrolledComposite.setFocus();
//			}
//		});
		
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