package com.bh.derma.images.ui;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTException;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbenchPartSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;

import com.bh.derma.images.internal.Activator;
import com.bh.derma.images.ui.model.ThumbnailWidget;

public class NewPatientVisitView extends ViewPart {

	public static final String ID = "com.bh.derma.images.ui.NewPatientVisitView"; //$NON-NLS-1$
	private Text text;
	private Text text_1;
	private Table table;
	private Text text_2;
	private Text text_3;
	private Text text_4;
	
	private List<String> loadedPhotosList;
	private List<String> selectedPhotosFilesList;
	private List<ThumbnailWidget> thumbnailWidgetList;
	private ScrolledComposite scrolledComposite;

	public NewPatientVisitView() {
	}
	
	public List<String> getSelectedPhotosFilesList() {
		return selectedPhotosFilesList;
	}
	
	public List<ThumbnailWidget> getThumbnailWidgetList() {
		return thumbnailWidgetList;
	}

	/**
	 * Create contents of the view part.
	 * @param parent
	 */
	@Override
	public void createPartControl(Composite parent) {
		scrolledComposite = new ScrolledComposite(
				parent, SWT.H_SCROLL | SWT.V_SCROLL);
		scrolledComposite.setExpandHorizontal(true);
		scrolledComposite.setExpandVertical(true);
		GridData data = new GridData(GridData.GRAB_HORIZONTAL | GridData.FILL_BOTH);
		data.horizontalSpan = 2;
		scrolledComposite.setLayoutData(data);
		scrolledComposite.setSize(scrolledComposite.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		
		Composite container = new Composite(scrolledComposite, SWT.NONE);
		container.setLayout(null);
		
		Group group = new Group(container, SWT.NONE);
		group.setBounds(10, 10, 426, 391);
		
		Label lblName = new Label(group, SWT.NONE);
		lblName.setBounds(10, 44, 37, 15);
		lblName.setText("Name");
		
		text = new Text(group, SWT.BORDER);
		text.setBounds(10, 65, 276, 21);
		
		Label lblId = new Label(group, SWT.NONE);
		lblId.setBounds(293, 44, 37, 15);
		lblId.setText("ID");
		
		text_1 = new Text(group, SWT.BORDER);
		text_1.setBounds(292, 65, 121, 21);
		
		final Button btnSave = new Button(group, SWT.NONE);
		btnSave.setBounds(10, 100, 75, 25);
		btnSave.setText("Save");
		
		Button btnNewPatient = new Button(group, SWT.RADIO);
		btnNewPatient.setBounds(10, 20, 90, 16);
		btnNewPatient.setText("New Patient");
		
		Button btnSearch = new Button(group, SWT.RADIO);
		btnSearch.setBounds(157, 20, 69, 16);
		btnSearch.setText("Search");
		
		TableViewer tableViewer = new TableViewer(group, SWT.BORDER | SWT.FULL_SELECTION);
		tableViewer.setColumnProperties(new String[] {"Name", "ID"});
		table = tableViewer.getTable();
		table.setBounds(10, 139, 406, 158);
		
		Label lblTotalVisits = new Label(group, SWT.NONE);
		lblTotalVisits.setBounds(10, 313, 104, 15);
		lblTotalVisits.setText("Total Visits: ");
		
		Label lblTotalPhotos = new Label(group, SWT.NONE);
		lblTotalPhotos.setBounds(130, 313, 96, 15);
		lblTotalPhotos.setText("Total photos: ");
		
		Label lblShowPhotosFrom = new Label(group, SWT.NONE);
		lblShowPhotosFrom.setBounds(10, 343, 104, 15);
		lblShowPhotosFrom.setText("Show photos from");
		
		Combo visitsListDropDown = new Combo(group, SWT.READ_ONLY);
		visitsListDropDown.setBounds(130, 343, 283, 23);
		
		Group grpNewVisit = new Group(container, SWT.NONE);
		grpNewVisit.setText("New Visit");
		grpNewVisit.setBounds(10, 451, 426, 295);
		
		Label lblNewLabel = new Label(grpNewVisit, SWT.NONE);
		lblNewLabel.setBounds(10, 27, 67, 15);
		lblNewLabel.setText("Visit Name");
		
		text_2 = new Text(grpNewVisit, SWT.BORDER);
		text_2.setBounds(107, 24, 308, 21);
		
		Label lblVisitDate = new Label(grpNewVisit, SWT.NONE);
		lblVisitDate.setBounds(10, 60, 55, 15);
		lblVisitDate.setText("Visit Date");
		
		DateTime dateTimeDate = new DateTime(grpNewVisit, SWT.BORDER | SWT.DATE);
		dateTimeDate.setBounds(107, 60, 100, 24);

		Label lblVisitTime = new Label(grpNewVisit, SWT.NONE);
		lblVisitTime.setBounds(207, 60, 55, 15);
		lblVisitTime.setText("Visit Time");
		
		DateTime dateTimeTime = new DateTime(grpNewVisit, SWT.BORDER | SWT.TIME);
		dateTimeTime.setBounds(262, 60, 100, 24);
		
		
		Label lblNotes = new Label(grpNewVisit, SWT.NONE);
		lblNotes.setBounds(10, 104, 55, 15);
		lblNotes.setText("Notes");
		
		text_3 = new Text(grpNewVisit, SWT.BORDER | SWT.V_SCROLL | SWT.MULTI);
		text_3.setBounds(108, 86, 307, 73);
		
		Label lblPhotosDirectory = new Label(grpNewVisit, SWT.NONE);
		lblPhotosDirectory.setBounds(10, 182, 93, 15);
		lblPhotosDirectory.setText("Photos Location");
		
		text_4 = new Text(grpNewVisit, SWT.BORDER);
		text_4.setBounds(109, 182, 306, 21);
		
		Button btnBrowse = new Button(grpNewVisit, SWT.NONE);
		btnBrowse.setBounds(107, 209, 55, 25);
		btnBrowse.setText("Browse");
		
		btnBrowse.addSelectionListener(new SelectionListener() {			
			@Override
			public void widgetSelected(SelectionEvent e) {
				final FileDialog dialog = new FileDialog (getSite().getShell(), SWT.OPEN | SWT.MULTI);
				final String lastSelectedFilePath = dialog.open();
				// create the structure for files
				Display.getDefault().asyncExec(new Runnable() {
					@Override
					public void run() {
						String[] selectedFileNames = dialog.getFileNames();
						if(selectedFileNames != null && selectedFileNames.length > 0) {
							String dirName = lastSelectedFilePath.substring(0,
									lastSelectedFilePath.lastIndexOf(File.separator) + 1);
							if(loadedPhotosList != null) {
								loadedPhotosList.clear();
							} else {
								loadedPhotosList = new ArrayList<String>();
							}
							for(String selectedFileName : selectedFileNames) {
								loadedPhotosList.add(dirName.concat(selectedFileName));
							}
						}
					}
				});
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});
		
		Button btnSave_1 = new Button(grpNewVisit, SWT.NONE);
		btnSave_1.setBounds(10, 260, 75, 25);
		btnSave_1.setText("Save");
		
		Button btnLoad = new Button(grpNewVisit, SWT.NONE);
		btnLoad.setBounds(168, 209, 58, 25);
		btnLoad.setText("Load");
		
		btnLoad.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(selectedPhotosFilesList == null) {
					selectedPhotosFilesList = new ArrayList<String>();
				} else {
					selectedPhotosFilesList.clear();
				}
				
				if(thumbnailWidgetList == null) {
					thumbnailWidgetList = new ArrayList<ThumbnailWidget>();
				} else {
					thumbnailWidgetList.clear();
				}
				
				ImagesGridView imagesGridView = (ImagesGridView) Activator.getView(
						PlatformUI.getWorkbench().getActiveWorkbenchWindow(),
															ImagesGridView.ID);
				Composite thumbnailGridComposite =
								imagesGridView.getThumbnailGridComposite();
				for(Control control : thumbnailGridComposite.getChildren()) {
					control.dispose();
				}
				int gridWidth = thumbnailGridComposite.getBounds().width;
				int numberOfColumns = gridWidth / 200 - 1;
				
				GridLayout thumbnailGridCompositeGL =
										new GridLayout(numberOfColumns, true);
				thumbnailGridComposite.setLayout(thumbnailGridCompositeGL);
				GridData thumbnailGridCompositeGD =
											new GridData(GridData.FILL_BOTH);
				thumbnailGridCompositeGD.horizontalSpan = 2;
				thumbnailGridComposite.setLayoutData(thumbnailGridCompositeGD);
				if(loadedPhotosList != null && loadedPhotosList.size() > 0) {
					// begin add photo composite, check and text fields
					for(ListIterator<String> selectedPhotosIterator =
							loadedPhotosList.listIterator(); selectedPhotosIterator.hasNext();) {
						String selectedPhotoFilePath = selectedPhotosIterator.next();
						// check if the file has a valid imagesList 
						final Image thumbnail;
						try {
							thumbnail = new Image(Display.getDefault(), selectedPhotoFilePath);
						} catch (SWTException ex) {
							selectedPhotosIterator.remove();
							continue;
						}
						Composite imageParentComposite = new Composite(thumbnailGridComposite, SWT.BORDER);
						GridLayout imageParentCompositeGridLayout = new GridLayout(2, false);
						imageParentComposite.setLayout(imageParentCompositeGridLayout);
						GridData imageParentCompositeGridData = new GridData();
						imageParentCompositeGridData.grabExcessHorizontalSpace = true;
						imageParentComposite.setLayoutData(imageParentCompositeGridData);
						
						final Composite imageComposite = new Composite(imageParentComposite, SWT.BORDER);
						GridData imageCompositeGridData = new GridData(175, 200);
						imageCompositeGridData.horizontalSpan = 2;
						imageComposite.setLayoutData(imageCompositeGridData);
						
						imageComposite.addMouseListener(new MouseListener() {
							@Override
							public void mouseUp(MouseEvent e) {	}

							@Override
							public void mouseDown(MouseEvent e) { }

							@Override
							public void mouseDoubleClick(MouseEvent e) {
								String selectedPhotosFile = ((ThumbnailWidget)imageComposite.getData()).getSelectedPhotoFilePath();
								Dialog dialog = new OriginalSizeImageDialog(
										Display.getDefault().getActiveShell(), new Image[] {new Image(Display.getDefault(), selectedPhotosFile)});
								dialog.open();
							}
						});				

						ResizeImageListener comp1Listener = new ResizeImageListener(thumbnail, imageComposite, true);
						imageComposite.addListener (SWT.Dispose, comp1Listener);
						imageComposite.addListener (SWT.Paint, comp1Listener);
						
						final Button checkBox = new Button(imageParentComposite, SWT.CHECK);
						Text imageDescriptionText = new Text(imageParentComposite, SWT.BORDER);
						GridData imageDescriptionTextGridData = new GridData(GridData.FILL_HORIZONTAL);
						imageDescriptionTextGridData.grabExcessHorizontalSpace = true;
						imageDescriptionText.setLayoutData(imageDescriptionTextGridData);
						
						ThumbnailWidget thumbnailWidget = new ThumbnailWidget();
						thumbnailWidget.setImageParentComposite(imageParentComposite);
						thumbnailWidget.setImageComposite(imageComposite);
						thumbnailWidget.setImageDescriptionText(imageDescriptionText);
						thumbnailWidget.setImage(thumbnail);
						thumbnailWidget.setCheckBox(checkBox);
						thumbnailWidget.setSelectedPhotoFilePath(selectedPhotoFilePath);
						
						checkBox.setData(thumbnailWidget);
						imageDescriptionText.setData(thumbnailWidget);
						imageComposite.setData(thumbnailWidget);
						
						thumbnailWidgetList.add(thumbnailWidget);
						
						checkBox.addSelectionListener(new SelectionListener() {
							@Override
							public void widgetSelected(SelectionEvent e) {
								String selectedPhotosFile = ((ThumbnailWidget)checkBox.getData()).getSelectedPhotoFilePath();
								if(checkBox.getSelection()) {
									selectedPhotosFilesList.add(selectedPhotosFile);
								} else {
									selectedPhotosFilesList.remove(selectedPhotosFile);
								}
							}
							
							@Override
							public void widgetDefaultSelected(SelectionEvent e) { }
						});
					}
					// end add photo composite, check and text fields

					ScrolledComposite thumbnailGridScrolledComposite =
									imagesGridView.getViewerScrolledComposite();

					Point size = thumbnailGridComposite.computeSize(
													SWT.DEFAULT, SWT.DEFAULT);
					thumbnailGridScrolledComposite.setMinSize(size);

					thumbnailGridScrolledComposite.setContent(thumbnailGridComposite);
					thumbnailGridScrolledComposite.layout(true);
				}
				// show image grid view
				try {
					PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().showView(ImagesGridView.ID);
				} catch (PartInitException e1) {
					e1.printStackTrace();
				}
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});

		createActions();
		initializeToolBar();
		initializeMenu();
		
		btnNewPatient.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				btnSave.setText("Save");
				
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		btnSearch.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				btnSave.setText("Search");
				
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		Point size = container.computeSize(SWT.DEFAULT, SWT.DEFAULT);
		scrolledComposite.setMinSize(size);
		
		scrolledComposite.setContent(container);
		scrolledComposite.layout(true);
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
		@SuppressWarnings("unused")
		IToolBarManager toolbarManager = getViewSite().getActionBars()
				.getToolBarManager();
	}

	/**
	 * Initialize the menu.
	 */
	private void initializeMenu() {
		@SuppressWarnings("unused")
		IMenuManager menuManager = getViewSite().getActionBars()
				.getMenuManager();
	}

	@Override
	public void setFocus() {
		// Set the focus
		scrolledComposite.setFocus();
	}
	
	public void showMessage(String message) {
		MessageDialog.openInformation(Display.getDefault().getActiveShell(), "Test", message);
	}
	
	@Override
	public IWorkbenchPartSite getSite() {
		return super.getSite();
	}
}