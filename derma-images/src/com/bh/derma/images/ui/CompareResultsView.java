package com.bh.derma.images.ui;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageLoader;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IViewSite;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;

import com.bh.derma.images.ui.util.ImageMergerUtility;
import com.bh.derma.images.ui.util.StateFields;
import com.bh.derma.images.ui.util.Util;

public class CompareResultsView extends ViewPart {

	public static final String ID = "com.bh.derma.images.ui.CompareResultsView";
	
	private Image[] images;
	
	private int numberofColumns;
	private int numberOfRows;
	private boolean restoreMode;
	
	public void setRestoreMode(boolean restoreMode) {
		this.restoreMode = restoreMode;
	}
	
	public void setImages(Image[] images) {
		this.images = images;
	}
	
	@Override
	public void init(IViewSite site) throws PartInitException {
		super.init(site);
		this.images = StateFields.getInstance().getImages();
		if(images != null && images.length > 0) {
			restoreMode = true;
		} else {
			restoreMode = false;
		}
	}
	
	public CompareResultsView() {

	}
	
	private Composite thumbnailGridComposite;
	private ScrolledComposite thumbnailGridScrolledComposite;
	
	public Composite getThumbnailGridComposite() {
		return thumbnailGridComposite;
	}

	public void setThumbnailGridComposite(Composite thumbnailGridComposite) {
		this.thumbnailGridComposite = thumbnailGridComposite;
	}

	public ScrolledComposite getThumbnailGridScrolledComposite() {
		return thumbnailGridScrolledComposite;
	}

	public void setThumbnailGridScrolledComposite(
			ScrolledComposite thumbnailGridScrolledComposite) {
		this.thumbnailGridScrolledComposite = thumbnailGridScrolledComposite;
	}

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
		
		if(restoreMode) {
			populateCompareResultsComposite();
		}
		
		thumbnailGridScrolledComposite.addListener(SWT.Activate, new Listener() {
			public void handleEvent(Event e) {
				thumbnailGridScrolledComposite.setFocus();
			}
		});
	}
	
	@Override
	public void setFocus() {
	}
	

	public void populateCompareResultsComposite() {
		Composite imagesComposite = new Composite(thumbnailGridComposite, SWT.BORDER);
		GridLayout imageCompositeGL = new GridLayout(1, false);
		imagesComposite.setLayout(imageCompositeGL);
		GridData imageCompositeGD = new GridData(GridData.FILL_BOTH);
		imagesComposite.setLayoutData(imageCompositeGD);
		
		int numberOfImages = images.length;
		if(numberOfImages > 1) {
			int possibleNumberOfImages = 0;
			numberofColumns = 0;
			numberOfRows = 0;
			if(numberOfImages % 2 != 0) {
				possibleNumberOfImages = numberOfImages + 1;
				numberOfRows = 2;
			} else {
				possibleNumberOfImages = numberOfImages;
				numberOfRows = numberOfImages / 2;
			}
			
			numberofColumns = possibleNumberOfImages / numberOfRows;
			int widthofEachImage = getShell().getBounds().width / numberofColumns;
			int heightOfEachImage = getShell().getBounds().height / numberOfRows;
			
			imagesComposite.setLayoutData(null);
			imagesComposite.setLayout(null);
			
			GridLayout imageCompositeGLMulti = new GridLayout(numberofColumns, false);
			imagesComposite.setLayout(imageCompositeGLMulti);
			
			
			for(final Image image : images) {
				Composite imageComposite = new Composite(imagesComposite, SWT.BORDER);
				GridData imageCompositeGridData = new GridData(widthofEachImage, heightOfEachImage);
				imageComposite.setLayoutData(imageCompositeGridData);
				
				imageComposite.addMouseListener(new MouseListener() {
					@Override
					public void mouseUp(MouseEvent e) {}

					@Override
					public void mouseDown(MouseEvent e) {}

					@Override
					public void mouseDoubleClick(MouseEvent e) {
						Dialog dialog = new OriginalSizeImageDialog(
								Display.getDefault().getActiveShell(), new Image[] {image});
						dialog.open();
					}
				});
				ResizeImageListener comp1Listener = new ResizeImageListener(
						image, imageComposite, widthofEachImage, heightOfEachImage, false);
				imageComposite.addListener (SWT.Dispose, comp1Listener);
				imageComposite.addListener (SWT.Paint, comp1Listener);
			}
		} else {
			Label imageLabel = new Label(imagesComposite, SWT.BORDER);
			imageLabel.setImage(images[0]);
		}

		Point size = thumbnailGridComposite.computeSize(SWT.DEFAULT, SWT.DEFAULT);
		thumbnailGridScrolledComposite.setMinSize(size);

		thumbnailGridScrolledComposite.setContent(thumbnailGridComposite);
		thumbnailGridScrolledComposite.layout(true);
	}
	
	private Shell getShell() {
		Shell located = null;
		Control control = Display.getDefault().getCursorControl();
		if (control != null) {
			located = control.getShell();
		}
		else {
			IWorkbenchWindow activeWindow = PlatformUI.getWorkbench().getActiveWorkbenchWindow();

			if (activeWindow == null) {
				activeWindow = PlatformUI.getWorkbench().getWorkbenchWindows()[0];
			}

			located = activeWindow.getShell();
		}

		return located;
	}
	
	Map<Integer, Image[]> imagesInRowsMap;

	public IStatus exportCompareResults() {
		if(images != null && images.length > 0) {
			imagesInRowsMap = new HashMap<Integer, Image[]>();

			final FileDialog dialog = new FileDialog (getSite().getShell(), SWT.SAVE);
			dialog.setFileName("merged.jpg");
			dialog.setFilterExtensions(new String[] {"*.jpg"});

			final String exportompareResultToFilePath = dialog.open();

			// create merged file.

			// split images array into arrays each of size equal to number of columns; put them all in a list 
			int counter = 0;
			for(int row = 0; row < numberOfRows; row++) {
				Image[] imagesForCurrentRow = Arrays.copyOfRange(images, counter, counter + numberofColumns);
				counter += numberofColumns;
				imagesInRowsMap.put(row, imagesForCurrentRow);
			}

			Map<Integer, ImageData> imageDataPerRow = new HashMap<Integer, ImageData>();

			for(int row = 0; row < numberOfRows; row++) {
				Image[] imagesInRow = imagesInRowsMap.get(row);
				ImageData targetDataForCurrentRow = null;
				for(Image[] currentRowImages : Util.getArrays(imagesInRow, 2)) {
					ImageData sourceData1 = null;
					ImageData sourceData2 = null;
					ImageData targetData1 = null;

					if(currentRowImages[0] != null && currentRowImages[1] != null) {
						sourceData1 = currentRowImages[0].getImageData();
						sourceData2 = currentRowImages[1].getImageData();

						try {
							targetData1 = ImageMergerUtility.merge(sourceData1, sourceData2, ImageMergerUtility.HORIZONTAL);
						} catch (Exception e) {
							MessageDialog.openError(getSite().getShell(), "Merge Pictures", "Could not merge");
							e.printStackTrace();
							return Status.CANCEL_STATUS;
						}

						if(targetDataForCurrentRow != null) {					
							targetDataForCurrentRow = ImageMergerUtility.merge(targetDataForCurrentRow, targetData1, ImageMergerUtility.HORIZONTAL);
						} else {
							targetDataForCurrentRow = targetData1;
						}
					}
					if(currentRowImages[0] == null || currentRowImages[1] == null) {
						if(currentRowImages[0] != null && currentRowImages[1] == null) {
							sourceData1 = currentRowImages[0].getImageData();
							if(targetDataForCurrentRow != null) {
								targetDataForCurrentRow = ImageMergerUtility.merge(targetDataForCurrentRow, sourceData1, ImageMergerUtility.HORIZONTAL);
							} else {
								// only one image?
								targetDataForCurrentRow = sourceData1;
							}
						} else if(currentRowImages[0] == null && currentRowImages[1] != null) {
							// should never happen ideally
							sourceData2 = currentRowImages[1].getImageData();
							if(targetDataForCurrentRow != null) {
								targetDataForCurrentRow = ImageMergerUtility.merge(targetDataForCurrentRow, sourceData2, ImageMergerUtility.HORIZONTAL);
							} else {
								// only one image?
								targetDataForCurrentRow = sourceData2;
							}
						}
					}
				}
				imageDataPerRow.put(row, targetDataForCurrentRow);
			}

			ImageData[] imageDataArray = new ImageData[imageDataPerRow.values().size()];
			imageDataPerRow.values().toArray(imageDataArray);

			ImageData targetData = null;
			if(numberOfRows > 1) {
				for(ImageData[] currentRowImageData : Util.getImageDataArrays(imageDataArray, 2)) {
					ImageData sourceData1 = null;
					ImageData sourceData2 = null;
					ImageData targetData1 = null;

					if(currentRowImageData[0] != null && currentRowImageData[1] != null) {
						sourceData1 = currentRowImageData[0];
						sourceData2 = currentRowImageData[1];
					}
					boolean update = true;

					if(currentRowImageData[0] == null || currentRowImageData[1] == null) {
						if(currentRowImageData[0] != null && currentRowImageData[1] == null) {
							sourceData1 = currentRowImageData[0];
							if(targetData != null) {
								sourceData2 = targetData;
								update = false;
							} else {
								// only one image?
								targetData = sourceData2;
							}
						} else if(currentRowImageData[0] == null && currentRowImageData[1] != null) {
							// should never happen ideally
							sourceData2 = currentRowImageData[1];
							if(targetData != null) {
								sourceData1 = targetData;
								update = false;
							} else {
								// only one image?
								targetData = sourceData1;
							}
						}
					}
					if(sourceData1 != null && sourceData2 != null) {
						targetData1 = ImageMergerUtility.merge(sourceData1, sourceData2, ImageMergerUtility.VERTICAL);
						if(targetData != null && update) {					
							targetData = ImageMergerUtility.merge(targetData, targetData1, ImageMergerUtility.VERTICAL);
						} else {
							targetData = targetData1;
						}
					}
				}
			} else {
				targetData = imageDataPerRow.get(0);
			}

			if(targetData != null) {
				ImageLoader imageLoader = new ImageLoader();
				imageLoader.data = new ImageData[] { targetData };
				imageLoader.save(exportompareResultToFilePath, SWT.IMAGE_JPEG);
			} else {
				MessageDialog.openError(getSite().getShell(), "Merge Pictures", "Could not merge");
			}

			return Status.OK_STATUS;
		} 
		
		return Status.CANCEL_STATUS;
	}
}
