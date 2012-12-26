package com.bh.derma.images.ui;

import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

public class Perspective implements IPerspectiveFactory {

	public void createInitialLayout(IPageLayout layout) {
		layout.setEditorAreaVisible(false);
		layout.addView("com.bh.derma.images.ui.NewPatientVisitView", IPageLayout.LEFT, 0.30f, layout.getEditorArea());
		layout.addView("com.bh.derma.images.ui.ImagesGridView", IPageLayout.RIGHT, 0.0f, layout.getEditorArea());
	}
}
