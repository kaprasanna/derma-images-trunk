package com.bh.derma.images.ui.util;

import java.util.List;

import org.eclipse.swt.graphics.Image;

public class StateFields {
	private static StateFields INSTANCE = null;
	private List<String> selectedPhotosFilesList;
	
	private StateFields() {
	}
	
	private static void createInstance() {
		INSTANCE = new StateFields();
	}
	
	public static synchronized StateFields getInstance() {
		if (INSTANCE == null) {
			createInstance();
		}
		return INSTANCE;
	}
	
	public Image[] getImages() {
		if(selectedPhotosFilesList != null)
			return Util.imageFilesToImages(selectedPhotosFilesList);
		else 
			return null;
	}	

	public void setSelectedPhotosFilesList(List<String> selectedPhotosFilesList) {
		this.selectedPhotosFilesList = selectedPhotosFilesList;
	}
}
