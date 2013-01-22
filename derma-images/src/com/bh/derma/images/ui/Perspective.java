package com.bh.derma.images.ui;

import org.eclipse.ui.IFolderLayout;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

public class Perspective implements IPerspectiveFactory {

	public void createInitialLayout(IPageLayout layout) {
		layout.setEditorAreaVisible(false);
		String editorArea = layout.getEditorArea();
		layout.addView(NewPatientVisitView.ID, IPageLayout.LEFT, 0.30f, editorArea);

		 IFolderLayout topRight = layout.createFolder("topRight", IPageLayout.RIGHT, 0.30f, editorArea);
		 topRight.addView(ImagesGridView.ID);
		 topRight.addView(CompareResultsView.ID);
	}
}
