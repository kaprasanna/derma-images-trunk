package com.bh.derma.images.ui.model;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

public class ThumbnailWidget {
	private Composite imageParentComposite;
	private Composite imageComposite;
	private Button checkBox;
	private Text imageDescriptionText;
	private Image image;
	private String selectedPhotoFilePath;
	
	public String getSelectedPhotoFilePath() {
		return selectedPhotoFilePath;
	}
	public void setSelectedPhotoFilePath(String selectedPhotoFilePath) {
		this.selectedPhotoFilePath = selectedPhotoFilePath;
	}
	public Composite getImageParentComposite() {
		return imageParentComposite;
	}
	public void setImageParentComposite(Composite imageParentComposite) {
		this.imageParentComposite = imageParentComposite;
	}
	public Composite getImageComposite() {
		return imageComposite;
	}
	public void setImageComposite(Composite imageComposite) {
		this.imageComposite = imageComposite;
	}
	public Button getCheckBox() {
		return checkBox;
	}
	public void setCheckBox(Button checkBox) {
		this.checkBox = checkBox;
	}
	public Text getImageDescriptionText() {
		return imageDescriptionText;
	}
	public void setImageDescriptionText(Text imageDescriptionText) {
		this.imageDescriptionText = imageDescriptionText;
	}
	public Image getImage() {
		return image;
	}
	public void setImage(Image thumbnail) {
		this.image = thumbnail;
	}	
}
