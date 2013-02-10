package com.bh.derma.images.preference;

import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.FontFieldEditor;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import com.bh.derma.images.internal.Activator;

public class DermaImagesPreferencePage extends FieldEditorPreferencePage
		implements IWorkbenchPreferencePage {

	FontFieldEditor font;
	public DermaImagesPreferencePage() {
		super(FieldEditorPreferencePage.GRID);
		IPreferenceStore prefStore = Activator.getDefault().getPreferenceStore();
		setPreferenceStore(prefStore);
	}

	public DermaImagesPreferencePage(int style) {
		super(style);
	}

	public DermaImagesPreferencePage(String title, int style) {
		super(title, style);
	}

	public DermaImagesPreferencePage(String title, ImageDescriptor image,
			int style) {
		super(title, image, style);
	}
	
	@Override
	protected void createFieldEditors() {
		font = new FontFieldEditor(
				"selectedfont",
				"&Label Font:",
		 		getFieldEditorParent());
		addField(font);
	}

	@Override
	public void init(IWorkbench workbench) {
	}

}
