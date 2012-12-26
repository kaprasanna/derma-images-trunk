package com.bh.derma.images.ui;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;

import com.bh.derma.images.internal.Activator;

public class NewPatientVisitView extends ViewPart {

	public static final String ID = "com.bh.derma.images.ui.NewPatientVisitView"; //$NON-NLS-1$
	private Text text;
	private Text text_1;
	private Table table;
	private Text text_2;
	private Text text_3;
	private Text text_4;

	public NewPatientVisitView() {
	}

	/**
	 * Create contents of the view part.
	 * @param parent
	 */
	@Override
	public void createPartControl(Composite parent) {
		FillLayout fillLayout = (FillLayout) parent.getLayout();
		Composite container = new Composite(parent, SWT.NONE);
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
		
		DateTime dateTime = new DateTime(grpNewVisit, SWT.BORDER);
		dateTime.setBounds(107, 51, 308, 24);
		
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
		
		Button btnSave_1 = new Button(grpNewVisit, SWT.NONE);
		btnSave_1.setBounds(10, 260, 75, 25);
		btnSave_1.setText("Save");
		
		Button btnLoad = new Button(grpNewVisit, SWT.NONE);
		btnLoad.setBounds(168, 209, 58, 25);
		btnLoad.setText("Load");
		
		btnLoad.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				ImagesGridView imagesGridView = (ImagesGridView) Activator.getView(
						PlatformUI.getWorkbench().getActiveWorkbenchWindow(),
															ImagesGridView.ID);
				Composite thumbnailGridComposite = imagesGridView.getThumbnailGridComposite();
				for(Control control : thumbnailGridComposite.getChildren()) {
					control.dispose();
				}
				int gridWidth = thumbnailGridComposite.getBounds().width;
				
				int numberOfColumns = gridWidth / 200 - 1;
				
				GridLayout thumbnailGridCompositeGL = new GridLayout(numberOfColumns, true);
				thumbnailGridComposite.setLayout(thumbnailGridCompositeGL);
				GridData thumbnailGridCompositeGD = new GridData(GridData.FILL_BOTH);
				thumbnailGridCompositeGD.horizontalSpan = 2;
				thumbnailGridComposite.setLayoutData(thumbnailGridCompositeGD);
				
				Composite comp1Parent = new Composite(thumbnailGridComposite, SWT.BORDER);
				GridLayout comp1ParentGL = new GridLayout(2, false);
				comp1Parent.setLayout(comp1ParentGL);
				GridData comp1ParentGD = new GridData();
				comp1ParentGD.grabExcessHorizontalSpace = true;
				comp1Parent.setLayoutData(comp1ParentGD);
				
				Composite comp1 = new Composite(comp1Parent, SWT.BORDER);
				GridData comp1Gl = new GridData(175, 200);
				comp1Gl.horizontalSpan = 2;
				comp1.setLayoutData(comp1Gl);
				
				Button chkBox1 = new Button(comp1Parent, SWT.CHECK);
				Text txt1 = new Text(comp1Parent, SWT.BORDER);
				GridData txt1Gl = new GridData(GridData.FILL_HORIZONTAL);
				txt1Gl.grabExcessHorizontalSpace = true;
				txt1.setLayoutData(txt1Gl);				
				
				Composite comp2 = new Composite(thumbnailGridComposite, SWT.BORDER);
				comp2.setLayoutData(new GridData(175, 200));
				
				MyListener comp1Listener = new MyListener(new Image(Display.getDefault(), "C:\\Users\\pk022878\\Pictures\\for_twitter.png"), comp1);
				comp1.addListener (SWT.Dispose, comp1Listener);
				comp1.addListener (SWT.Paint, comp1Listener);
				
				MyListener comp2Listener = new MyListener(new Image(Display.getDefault(), "C:\\Users\\pk022878\\Pictures\\Photo-ID.png"), comp2);
				comp2.addListener (SWT.Dispose, comp1Listener);
				comp2.addListener (SWT.Paint, comp2Listener);
				
				ScrolledComposite viewerScrolledComposite = imagesGridView.getViewerScrolledComposite();
				
				Point size = thumbnailGridComposite.computeSize(SWT.DEFAULT, SWT.DEFAULT);
				viewerScrolledComposite.setMinSize(size);
				
				viewerScrolledComposite.setContent(thumbnailGridComposite);
				viewerScrolledComposite.layout(true);
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
	
	public void showMessage(String message) {
		MessageDialog.openInformation(Display.getDefault().getActiveShell(), "Test", message);
	}
	
	class MyListener implements Listener {
		Image image;
		Composite c;
		
		public MyListener(Image image, Composite composite) {
			super();
			this.image = image;
			this.c = composite;
		}

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
						if(width < rect.width) width = rect.width;
					} else {
						height = rect.height;
						width = bounds.width * rect.width / bounds.height;
						if(width < rect.width) width = rect.width;
					}
					e.gc.drawImage (image, 0, 0, bounds.width, bounds.height,
														x, y,	width, height);
				}
			}
		}
	};
}