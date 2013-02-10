package com.bh.derma.images.ui;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTException;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
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
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;

import com.bh.derma.images.internal.Activator;
import com.bh.derma.images.service.IPatientService;
import com.bh.derma.images.ui.model.ThumbnailWidget;
import com.bh.derma.images.ui.util.Util;

public class NewPatientView extends ViewPart {

	public static final String ID = "com.bh.derma.images.ui.NewPatientView";
	
	private List<String> loadedPhotosList;
	private List<String> selectedPhotosFilesList;
	private List<ThumbnailWidget> thumbnailWidgetList;
	
	private Composite newPatientVisitComposite;
	private ScrolledComposite scrolledComposite;
	
	public List<String> getSelectedPhotosFilesList() {
		return selectedPhotosFilesList;
	}
	
	public List<ThumbnailWidget> getThumbnailWidgetList() {
		return thumbnailWidgetList;
	}


	public NewPatientView() {
	}

	private void setFont(Font font) {
		for(Control control : newPatientVisitComposite.getChildren()) {
			control.setFont(font);
		}
	}
	
	@Override
	public void createPartControl(Composite parent) {
		Activator.getDefault().getPreferenceStore().addPropertyChangeListener(new IPropertyChangeListener() {
			
			@Override
			public void propertyChange(PropertyChangeEvent event) {
				if(event.getProperty() == "selectedfont") {
					Util.showMessage(event.getNewValue().getClass().getName());
					FontData[] fonts = (FontData[]) event.getNewValue();

					Font font = new Font(Display.getDefault(), fonts[0]);
					
					setFont(font);
				}
			}
		});
		
		final Composite composite = new Composite(parent, SWT.NONE);
		GridLayout compositeGL = new GridLayout(1, true);
		composite.setLayout(compositeGL);
		GridData compositeGD = new GridData(GridData.GRAB_HORIZONTAL | GridData.FILL_BOTH);
		composite.setLayoutData(compositeGD);
		composite.setSize(composite.computeSize(parent.getSize().x, SWT.DEFAULT));
		
		scrolledComposite = new ScrolledComposite(
									composite, SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
		scrolledComposite.setExpandHorizontal(true);
		scrolledComposite.setExpandVertical(true);
		GridData data = new GridData(GridData.GRAB_HORIZONTAL | GridData.FILL_BOTH);
		scrolledComposite.setLayoutData(data);
		scrolledComposite.setSize(scrolledComposite.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		
		newPatientVisitComposite = new Composite(scrolledComposite, SWT.NULL);
		GridLayout newPatientVisitCompositeGL = new GridLayout(1, false);
		newPatientVisitComposite.setLayout(newPatientVisitCompositeGL);
		GridData newPatientVisitCompositeGD = new GridData(GridData.FILL_BOTH);
		newPatientVisitComposite.setLayoutData(newPatientVisitCompositeGD);
		
		Group newPatientGroup = new Group(newPatientVisitComposite, SWT.NULL);
		newPatientGroup.setText("New / Search patient");
		GridLayout newPatientGroupGL = new GridLayout(4, true);
		newPatientGroup.setLayout(newPatientGroupGL);
		GridData newPatientGroupGD = new GridData(GridData.GRAB_HORIZONTAL | GridData.FILL_HORIZONTAL);
		newPatientGroupGD.verticalIndent = 0;
		newPatientGroup.setLayoutData(newPatientGroupGD);
		
		// new patient / search components
		Button radioButtonNewPatient = new Button(newPatientGroup, SWT.RADIO);
		radioButtonNewPatient.setText("New Patient");
		GridData radioBtnNewPatientGD = new GridData();
		radioBtnNewPatientGD.horizontalSpan = 2;
		radioButtonNewPatient.setLayoutData(radioBtnNewPatientGD);
		
		Button radioButtonSearch = new Button(newPatientGroup, SWT.RADIO);
		radioButtonSearch.setText("Search");
		GridData radioBtnSearchGD = new GridData();
		radioBtnSearchGD.horizontalSpan = 2;
		radioButtonSearch.setLayoutData(radioBtnSearchGD);

		Label lblName = new Label(newPatientGroup, SWT.NONE);
		lblName.setText("Name");
		GridData lblNameGD = new GridData();
		lblNameGD.horizontalSpan = 3;
		lblNameGD.verticalIndent = 5;
		lblName.setLayoutData(lblNameGD);
		
		Label lblId = new Label(newPatientGroup, SWT.NONE);
		lblId.setText("ID");
		GridData lblIdGD = new GridData();
		lblIdGD.horizontalSpan = 1;
		lblIdGD.verticalIndent = 5;
		lblId.setLayoutData(lblIdGD);
		
		Text txtName = new Text(newPatientGroup, SWT.BORDER);
		GridData txtNameGD = new GridData(GridData.FILL_HORIZONTAL);
		txtNameGD.horizontalSpan = 3;
		txtNameGD.grabExcessHorizontalSpace = true;
		txtName.setLayoutData(txtNameGD);
		
		Text txtID = new Text(newPatientGroup, SWT.BORDER);
		GridData txtIDGD = new GridData(GridData.FILL_HORIZONTAL);
		txtIDGD.grabExcessHorizontalSpace = true;
		txtID.setLayoutData(txtIDGD);
		
		Label lblStudyType = new Label(newPatientGroup, SWT.NONE);
		lblStudyType.setText("Study Type:");
		GridData lblStudyTypeGD = new GridData();
		lblStudyTypeGD.verticalIndent = 5;
		lblStudyType.setLayoutData(lblStudyTypeGD);
		
		final Combo selectStudyTypeForSearchingCombo = new Combo(newPatientGroup, SWT.READ_ONLY);
		GridData selectStudyTypeForSearchingComboGD = new GridData(GridData.FILL_HORIZONTAL);
		selectStudyTypeForSearchingComboGD.horizontalSpan = 2;
		selectStudyTypeForSearchingComboGD.verticalIndent = 5;
		selectStudyTypeForSearchingComboGD.grabExcessHorizontalSpace = true;
		selectStudyTypeForSearchingCombo.setLayoutData(selectStudyTypeForSearchingComboGD);
		
		final Button buttonSaveSearch = new Button(newPatientGroup, SWT.NONE);
		GridData buttonSaveSearchGD = new GridData(GridData.FILL_HORIZONTAL);
		buttonSaveSearchGD.horizontalSpan = 1;
		buttonSaveSearchGD.verticalIndent = 5;
		buttonSaveSearchGD.grabExcessHorizontalSpace = true;
		buttonSaveSearch.setLayoutData(buttonSaveSearchGD);
		buttonSaveSearch.setText("Save");
		
		// search result components
		TableViewer tableViewer = new TableViewer(newPatientGroup, SWT.BORDER | SWT.FULL_SELECTION);
		tableViewer.setColumnProperties(new String[] {"Name", "ID"});
		Table table = tableViewer.getTable();
		GridData tableGD = new GridData(GridData.FILL_HORIZONTAL | GridData.FILL_VERTICAL);
		tableGD.grabExcessHorizontalSpace = true;
		tableGD.horizontalSpan = 4;
		tableGD.verticalIndent = 10;
		tableGD.verticalSpan = 35;
		table.setLayoutData(tableGD);
		
		// select study combo
		Label lblSelectStudy = new Label(newPatientGroup, SWT.NONE);
		lblSelectStudy.setText("Select Study");
		GridData lblSelectStudyGD = new GridData();
		lblSelectStudyGD.verticalIndent = 5;
		lblSelectStudy.setLayoutData(lblSelectStudyGD);
		
		Combo selectStudyFromSearchResultsCombo = new Combo(newPatientGroup, SWT.READ_ONLY);
		GridData selectStudyForSearchComboGD = new GridData(GridData.FILL_HORIZONTAL);
		selectStudyForSearchComboGD.horizontalSpan = 3;
		selectStudyForSearchComboGD.verticalIndent = 5;
		selectStudyForSearchComboGD.grabExcessHorizontalSpace = true;
		selectStudyFromSearchResultsCombo.setLayoutData(selectStudyForSearchComboGD);

		// select series combo
		Label lblSelectSeries = new Label(newPatientGroup, SWT.NONE);
		lblSelectSeries.setText("Select Series");
		GridData lblSelectSeriesGD = new GridData();
		lblSelectSeriesGD.verticalIndent = 5;
		lblSelectSeries.setLayoutData(lblSelectSeriesGD);
		
		Combo selectSeriesCombo = new Combo(newPatientGroup, SWT.READ_ONLY);
		GridData selectSeriesComboGD = new GridData(GridData.FILL_HORIZONTAL);
		selectSeriesComboGD.horizontalSpan = 3;
		selectSeriesComboGD.verticalIndent = 5;
		selectSeriesComboGD.grabExcessHorizontalSpace = true;
		selectSeriesCombo.setLayoutData(selectSeriesComboGD);
		
		// new study controls
		Group newStudyGroup = new Group(newPatientVisitComposite, SWT.NULL);
		newStudyGroup.setText("New Study");
		GridLayout newStudyGroupGL = new GridLayout(4, true);
		newStudyGroup.setLayout(newStudyGroupGL);
		GridData newStudyGroupGD = new GridData(GridData.GRAB_HORIZONTAL | GridData.FILL_HORIZONTAL);
		newStudyGroupGD.verticalIndent = 10;
		newStudyGroup.setLayoutData(newStudyGroupGD);
		
		Label lblNewStudyName = new Label(newStudyGroup, SWT.NONE);
		lblNewStudyName.setText("Study Name");
		GridData lblNewStudyNameGD = new GridData();
		lblNewStudyNameGD.verticalIndent = 5;
		lblNewStudyName.setLayoutData(lblNewStudyNameGD);
		
		Text newStudyNameText = new Text(newStudyGroup, SWT.BORDER);
		GridData newStudyNameTextGD = new GridData(GridData.FILL_HORIZONTAL);
		newStudyNameTextGD.horizontalSpan = 3;
		newStudyNameTextGD.verticalIndent = 5;
		newStudyNameTextGD.grabExcessHorizontalSpace = true;
		newStudyNameText.setLayoutData(newStudyNameTextGD);

		Label lblNewStudyType = new Label(newStudyGroup, SWT.NONE);
		lblNewStudyType.setText("Study Type:");
		GridData lblNewStudyTypeGD = new GridData();
		lblNewStudyTypeGD.verticalIndent = 5;
		lblNewStudyType.setLayoutData(lblNewStudyTypeGD);
		
		Combo selectNewStudyTypeCombo = new Combo(newStudyGroup, SWT.READ_ONLY);
		GridData selectNewStudyTypeComboGD = new GridData(GridData.FILL_HORIZONTAL);
		selectNewStudyTypeComboGD.horizontalSpan = 2;
		selectNewStudyTypeComboGD.verticalIndent = 5;
		selectNewStudyTypeComboGD.grabExcessHorizontalSpace = true;
		selectNewStudyTypeCombo.setLayoutData(selectNewStudyTypeComboGD);
		
		DateTime dateTimeDate = new DateTime(newStudyGroup, SWT.BORDER | SWT.DATE);
		GridData dateTimeDateGD = new GridData(GridData.FILL_HORIZONTAL);
		dateTimeDateGD.grabExcessHorizontalSpace = true;
		dateTimeDateGD.verticalIndent = 5;
		dateTimeDate.setLayoutData(dateTimeDateGD);

		final Button btnSaveStudy = new Button(newStudyGroup, SWT.NONE);
		GridData btnSaveStudyGD = new GridData(GridData.FILL_HORIZONTAL);
		btnSaveStudyGD.horizontalSpan = 1;
		btnSaveStudyGD.verticalIndent = 5;
		btnSaveStudyGD.grabExcessHorizontalSpace = true;
		btnSaveStudy.setLayoutData(btnSaveStudyGD);
		btnSaveStudy.setText("Save Study");
		
		// new series controls
		Group newSeriesGroup = new Group(newPatientVisitComposite, SWT.NULL);
		newSeriesGroup.setText("New Series");
		GridLayout newSeriesGroupGL = new GridLayout(4, true);
		newSeriesGroup.setLayout(newSeriesGroupGL);
		GridData newSeriesGroupGD = new GridData(GridData.GRAB_HORIZONTAL | GridData.FILL_HORIZONTAL);
		newSeriesGroupGD.verticalIndent = 10;
		newSeriesGroup.setLayoutData(newSeriesGroupGD);
		
		Label lblNewSeriesName = new Label(newSeriesGroup, SWT.NONE);
		lblNewSeriesName.setText("Series Name");
		GridData lblNewSeriesNameGD = new GridData();
		lblNewSeriesNameGD.verticalIndent = 5;
		lblNewSeriesName.setLayoutData(lblNewSeriesNameGD);
		
		Text newSeriesNameText = new Text(newSeriesGroup, SWT.BORDER);
		GridData newSeriesNameTextGD = new GridData(GridData.FILL_HORIZONTAL);
		newSeriesNameTextGD.horizontalSpan = 3;
		newSeriesNameTextGD.verticalIndent = 5;
		newSeriesNameTextGD.grabExcessHorizontalSpace = true;
		newSeriesNameText.setLayoutData(newSeriesNameTextGD);

		Label lblNewSeriesTime = new Label(newSeriesGroup, SWT.NONE);
		lblNewSeriesTime.setText("Series Time");
		GridData lblNewSeriesTimeGD = new GridData();
		lblNewSeriesTimeGD.verticalIndent = 5;
		lblNewSeriesTime.setLayoutData(lblNewSeriesTimeGD);
		
		DateTime seriesTime = new DateTime(newSeriesGroup, SWT.BORDER | SWT.TIME);
		GridData seriesTimeGD = new GridData(GridData.FILL_HORIZONTAL);
		seriesTimeGD.horizontalSpan = 1;
		seriesTimeGD.grabExcessHorizontalSpace = true;
		seriesTimeGD.verticalIndent = 5;
		seriesTime.setLayoutData(seriesTimeGD);
		
		Label placeHolder = new Label(newSeriesGroup, SWT.NONE);
		GridData placeHolderGD = new GridData(GridData.FILL_HORIZONTAL);
		placeHolderGD.horizontalSpan = 2;
		placeHolderGD.grabExcessHorizontalSpace = true;
		placeHolder.setLayoutData(placeHolderGD);
		
		Label lblNewSeriesDescription = new Label(newSeriesGroup, SWT.NONE);
		lblNewSeriesDescription.setText("Series Description");
		GridData lblNewSeriesDescriptionGD = new GridData();
		lblNewSeriesDescriptionGD.grabExcessVerticalSpace = true;
		lblNewSeriesDescriptionGD.verticalSpan = 15;
		lblNewSeriesDescriptionGD.verticalAlignment = SWT.BEGINNING;
		lblNewSeriesDescriptionGD.verticalIndent = 5;
		lblNewSeriesDescription.setLayoutData(lblNewSeriesDescriptionGD);
		
		Text newSeriesNameDescription = new Text(newSeriesGroup, SWT.BORDER | SWT.V_SCROLL | SWT.MULTI);
		GridData newSeriesNameDescriptionGD = new GridData(GridData.FILL_HORIZONTAL |  GridData.FILL_VERTICAL);
		newSeriesNameDescriptionGD.horizontalSpan = 3;
		newSeriesNameDescriptionGD.verticalIndent = 5;
		newSeriesNameDescriptionGD.verticalSpan = 15;
		newSeriesNameDescriptionGD.grabExcessHorizontalSpace = true;
		newSeriesNameDescription.setLayoutData(newSeriesNameDescriptionGD);

		Label lblPhotosLocation = new Label(newSeriesGroup, SWT.NONE);
		lblPhotosLocation.setText("Photos Location");
		GridData lblPhotosLocationGD = new GridData();
		lblPhotosLocationGD.horizontalSpan = 1;
		lblPhotosLocationGD.verticalIndent = 5;
		lblPhotosLocation.setLayoutData(lblPhotosLocationGD);

		final Text textPhotosLocation = new Text(newSeriesGroup, SWT.BORDER);
		GridData textPhotosLocationGD = new GridData(GridData.FILL_HORIZONTAL);
		textPhotosLocationGD.horizontalSpan = 1;
		textPhotosLocationGD.verticalIndent = 5;
		textPhotosLocationGD.grabExcessHorizontalSpace = true;
		textPhotosLocation.setLayoutData(textPhotosLocationGD);

		final Button buttonBrowse = new Button(newSeriesGroup, SWT.NONE);
		GridData btnBrowseGD = new GridData(GridData.FILL_HORIZONTAL);
		btnBrowseGD.horizontalSpan = 1;
		btnBrowseGD.verticalIndent = 5;
		btnBrowseGD.grabExcessHorizontalSpace = true;
		buttonBrowse.setLayoutData(btnBrowseGD);
		buttonBrowse.setText("Browse");

		final Button buttonLoad = new Button(newSeriesGroup, SWT.NONE);
		GridData btnLoadGD = new GridData(GridData.FILL_HORIZONTAL);
		btnLoadGD.horizontalSpan = 1;
		btnLoadGD.verticalIndent = 5;
		btnLoadGD.grabExcessHorizontalSpace = true;
		buttonLoad.setLayoutData(btnLoadGD);
		buttonLoad.setText("Load");
		
		final Button buttnoSaveSeries = new Button(newSeriesGroup, SWT.NONE);
		GridData btnSaveSeriesGD = new GridData(GridData.FILL_HORIZONTAL);
		btnSaveSeriesGD.horizontalSpan = 1;
		btnSaveSeriesGD.verticalIndent = 5;
		btnSaveSeriesGD.grabExcessHorizontalSpace = true;
		buttnoSaveSeries.setLayoutData(btnSaveSeriesGD);
		buttnoSaveSeries.setText("Save Series");
		
		// set defaults
		radioButtonNewPatient.setSelection(true);
		selectStudyTypeForSearchingCombo.setEnabled(false);
		
		// set size
		Point size = newPatientVisitComposite.computeSize(SWT.DEFAULT, SWT.DEFAULT);
		scrolledComposite.setMinSize(size);
		scrolledComposite.setContent(newPatientVisitComposite);
		scrolledComposite.layout(true);
		
		scrolledComposite.addListener(SWT.Activate, new Listener() {
			public void handleEvent(Event e) {
				scrolledComposite.setFocus();
			}
		});
		
		// browse button clicked
		buttonBrowse.addSelectionListener(new SelectionAdapter() {
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
							textPhotosLocation.setText(lastSelectedFilePath);
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
		});
		
		// load button clicked
		buttonLoad.addSelectionListener(new SelectionAdapter() {
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
		});
		
		// save radio selected
		radioButtonNewPatient.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				selectStudyTypeForSearchingCombo.setEnabled(false);
				buttonSaveSearch.setText("Save");
			}
		});
		
		// search radio selected
		radioButtonSearch.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				selectStudyTypeForSearchingCombo.setEnabled(true);
				buttonSaveSearch.setText("Search");
			}
		});
		
		buttonSaveSearch.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				IPatientService pservice = Activator.getDefault().getPatientService();
				if(pservice != null) {
					IStatus status = pservice.saveNewPatient(null);
					if (status == Status.OK_STATUS) {
						Util.showMessage("Saved patient.\nOK Status!");
					}
				} else {
					Util.showMessage("Patient service is null :-(");					
				}
			}
		});
	}

	@Override
	public void setFocus() {
	}

}
