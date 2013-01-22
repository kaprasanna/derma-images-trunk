package com.bh.derma.images.ui.util;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import com.bh.derma.images.internal.Activator;
import com.bh.derma.images.ui.CompareResultsView;

public class Util {
	
	public static CompareResultsView getCompareResultsView() {
		IWorkbenchWindow activeWorkbenchWindow = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		CompareResultsView compareResultsView = (CompareResultsView)
				Activator.getView(activeWorkbenchWindow, CompareResultsView.ID);

		if(compareResultsView == null) {
			try {
				activeWorkbenchWindow.getActivePage().showView(
						CompareResultsView.ID, null, IWorkbenchPage.VIEW_CREATE);

				compareResultsView = (CompareResultsView) Activator.getView(
						activeWorkbenchWindow, CompareResultsView.ID);
			} catch (PartInitException e1) {
				e1.printStackTrace();
			}
		}
		
		return compareResultsView;
	}

	public static List<Image[]> getArrays(Image[] imagesInRow, int splitBy) {
		List<Image[]> listofArrays = new ArrayList<Image[]>();
		for(int i = 0; i < imagesInRow.length; i+=splitBy) {
			Image[] newArray = new Image[splitBy];
			for(int j = 0, k = i; j < splitBy; j++, k++) {
				if(k < imagesInRow.length)
					newArray[j] = imagesInRow[k];
				else
					newArray[j] = null;
			}
			listofArrays.add(newArray);
		}
		return listofArrays;
	}

	public static List<ImageData[]> getImageDataArrays(ImageData[] imagesInRow, int splitBy) {
		List<ImageData[]> listofArrays = new ArrayList<ImageData[]>();
		for(int i = 0; i < imagesInRow.length; i+=splitBy) {
			ImageData[] newArray = new ImageData[splitBy];
			for(int j = 0, k = i; j < splitBy; j++, k++) {
				if(k < imagesInRow.length)
					newArray[j] = imagesInRow[k];
				else
					newArray[j] = null;
			}
			listofArrays.add(newArray);
		}
		return listofArrays;
	}
	
	public static Image[] imageFilesToImages(List<String> selectedPhotosFilesList) {
		List<Image> selectedImagesList = null;
		if(selectedPhotosFilesList != null) {
			selectedImagesList = new ArrayList<Image>();
			for(String selectedPhotosFile : selectedPhotosFilesList) {
				Image image = new Image(Display.getDefault(), selectedPhotosFile);
				selectedImagesList.add(image);
			}
			return selectedImagesList.toArray(new Image[selectedImagesList.size()]);
		} else {
			return null;
		}
	}
	
	public static void showMessage(String message) {
		MessageDialog.openError(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), "Merge Pictures", message);
	}
}
