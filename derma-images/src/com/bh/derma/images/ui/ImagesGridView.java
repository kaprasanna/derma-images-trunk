package com.bh.derma.images.ui;

import java.util.ArrayList;
import java.util.List;

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
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;

import com.bh.derma.images.internal.Activator;
import com.bh.derma.images.ui.model.ThumbnailWidget;
import com.bh.derma.images.ui.util.StateFields;
import com.bh.derma.images.ui.util.Util;

public class ImagesGridView extends ViewPart {

	public static final String ID = "com.bh.derma.images.ui.ImagesGridView"; //$NON-NLS-1$
	private Composite thumbnailGridComposite;
	private ScrolledComposite thumbnailGridScrolledComposite;
	
	NewPatientVisitView newPatientVisitView =
			(NewPatientVisitView) Activator.getView(
					PlatformUI.getWorkbench().getActiveWorkbenchWindow(),
										   			  NewPatientVisitView.ID);

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
		
		Button btnCompareSelected = new Button(thumbnailComposite, SWT.NONE);
		btnCompareSelected.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				List<String> selectedPhotosFilesList = newPatientVisitView.getSelectedPhotosFilesList();
				if(selectedPhotosFilesList != null && (selectedPhotosFilesList.size() > 0)) {
					Image[] images = Util.imageFilesToImages(selectedPhotosFilesList);
					
					CompareResultsView compareResultsView = Util.getCompareResultsView();
					
					if(compareResultsView == null) {
						// TODO log
						return;
					}
					
					compareResultsView.setImages(images);
					
					// clear existing compare results
					Composite compareResultsComposite = compareResultsView.getThumbnailGridComposite();
					for(Control control : compareResultsComposite.getChildren()) {
						control.dispose();
					}

					compareResultsView.populateCompareResultsComposite();
					
					try {
						PlatformUI.getWorkbench().getActiveWorkbenchWindow().
								getActivePage().showView(CompareResultsView.ID);
					} catch (PartInitException e1) {
						e1.printStackTrace();
					}
					
					// cache. Remove references so that comparison happens only when pressed compare button.
					List<String> selectedPhotosFilesListToCache = new ArrayList<String>();
					selectedPhotosFilesListToCache.addAll(selectedPhotosFilesList);
					StateFields.getInstance().setSelectedPhotosFilesList(selectedPhotosFilesListToCache);
				}
				
			}
		});
		btnCompareSelected.setBounds(22, 657, 108, 25);
		btnCompareSelected.setText("Compare Selected");
		
		Button btnDeSelectAll = new Button(thumbnailComposite, SWT.NONE);
		btnDeSelectAll.setBounds(22, 700, 108, 25);
		btnDeSelectAll.setText("Deselect All");
		btnDeSelectAll.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				List<ThumbnailWidget> thumbnailWidgetList = newPatientVisitView.getThumbnailWidgetList();
				for(ThumbnailWidget thumbnailWidget : thumbnailWidgetList) {
					 Button checkBox = thumbnailWidget.getCheckBox();
					 if(checkBox.getSelection()) {
						 checkBox.setSelection(false);
					 }
				}
				newPatientVisitView.getSelectedPhotosFilesList().clear();
			}
		});

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
	 * Initialise the toolbar.
	 */
	private void initializeToolBar() {
		@SuppressWarnings("unused")
		IToolBarManager toolbarManager = getViewSite().getActionBars()
				.getToolBarManager();
	}

	/**
	 * Initialise the menu.
	 */
	private void initializeMenu() {
		@SuppressWarnings("unused")
		IMenuManager menuManager = getViewSite().getActionBars()
				.getMenuManager();
	}

	@Override
	public void setFocus() {
		// Set the focus
	}
}