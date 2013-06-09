package com.bh.derma.images.ui.util;

import org.eclipse.jface.action.ContributionItem;
import org.eclipse.jface.action.IContributionManager;
import org.eclipse.jface.action.StatusLineLayoutData;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.FontMetrics;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

public class StatusLineContribution extends ContributionItem {
	public final static int DEFAULT_CHAR_WIDTH = 40;
	public final static int DEFAULT_CHAR_HEIGHT = 40;
	
	private int widthHint = -1;

	private int heightHint = -1;

	private String text = "";

	Label label;
	
	public StatusLineContribution(String id) {
		this(id, DEFAULT_CHAR_WIDTH);
	}
	
	public StatusLineContribution(String id, int defaultCharWidth) {
		super(id);
		this.widthHint = defaultCharWidth;
		
		setVisible(false); // no text to start with
	}
	
	@Override
	public void dispose() {
		super.dispose();
	}

	@Override
	public void fill(Composite parent) {
		super.fill(parent);
		
		label = new Label(parent, SWT.SHADOW_NONE);
		
		GC gc = new GC(parent);
		gc.setFont(parent.getFont());
		FontMetrics fm = gc.getFontMetrics();
		Point extent = gc.textExtent(text);

		int nwidthHint;
		
		if (widthHint > 0) {
			nwidthHint = fm.getAverageCharWidth() * widthHint;
		} else {
			nwidthHint = extent.x;
		}
		heightHint = fm.getHeight();
		gc.dispose();

		StatusLineLayoutData statusLineLayoutData = new StatusLineLayoutData();
		statusLineLayoutData.widthHint = nwidthHint;
		statusLineLayoutData.heightHint = heightHint;
		label.setLayoutData(statusLineLayoutData);
		label.setText(text);
		
		System.out.println("parent location : " + parent.getLocation());
		System.out.println("label size : " + label.getSize());
	}
	
	public void setText(String text) {
		if (text != null){
			this.text = text;

			if (label != null && !label.isDisposed())
				label.setText(this.text);
			System.out.println("label size before : " + label.getSize());

			if (this.text.length() == 0) {
				if (isVisible()) {
					setVisible(false);
					IContributionManager contributionManager = getParent();

					if (contributionManager != null)
						contributionManager.update(true);
				}
			} else {
				if (!isVisible()) {
					setVisible(true);
					IContributionManager contributionManager = getParent();
					if (contributionManager != null)
						contributionManager.update(true);
				}
			}

			GC gc = new GC(label.getParent());
			gc.setFont(label.getParent().getFont());
			FontMetrics fm = gc.getFontMetrics();
			Point extent = gc.textExtent(text);

			int nwidthHint;
			
			if (widthHint > 0) {
				nwidthHint = fm.getAverageCharWidth() * widthHint;
			} else {
				nwidthHint = extent.x;
			}
			heightHint = fm.getHeight();
			gc.dispose();

			StatusLineLayoutData statusLineLayoutData = new StatusLineLayoutData();
			statusLineLayoutData.widthHint = nwidthHint;
			statusLineLayoutData.heightHint = heightHint;
			label.setLayoutData(statusLineLayoutData);
			
			System.out.println("parent location before : " + label.getParent().getLocation());
			
			Point currentLocation = label.getParent().getLocation();
			label.getParent().setLocation(currentLocation.x - nwidthHint, currentLocation.y);
			
//			System.out.println("parent location after : " + label.getParent().getLocation());
			System.out.println("label size after : " + label.getSize());
			System.out.println("expected width : " + nwidthHint);
		}
	}	
}
