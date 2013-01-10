package com.bh.derma.images.ui.util;

import org.eclipse.swt.graphics.ImageData;

public class ImageMergerUtility {

	public static final short HORIZONTAL = 0;

	public static final short VERTICAL = 1;
	

	public static ImageData merge(ImageData sourceData1, ImageData sourceData2,
			short alignment) {
		ImageData targetData = null;
		if(sourceData2 == null) {
			targetData = new ImageData(sourceData1.width, sourceData1.height,
									   sourceData1.depth, sourceData1.palette);
			
			return targetData;
		}

		switch (alignment) {
		case HORIZONTAL:
			// If alignment is Horizontal,
			// imgData3 width should be equal to the sum of width of images
			// imgData1 & imgData2
			// imgData3 height should be equal to height of imgData1
			targetData = new ImageData(sourceData1.width + sourceData2.width,
				   sourceData1.height, sourceData1.depth, sourceData1.palette);
			
			merge(sourceData1, sourceData2, targetData, sourceData1.width,
					sourceData1.y);
			break;

		case VERTICAL:
			// If alignment is Vertical,
			// imgData3 height should be equal to the sum of height of images
			// imgData1 & imgData2
			// imgData3 width should be equal to the width of imgData1
			targetData = new ImageData(sourceData1.width, sourceData1.height
					+ sourceData2.height, sourceData1.depth,
					sourceData1.palette);
			merge(sourceData1, sourceData2, targetData, sourceData1.x,
					sourceData1.height);
			break;
		}

		return targetData;
	}
	
	public static ImageData merge(ImageData sourceData1, ImageData sourceData2,
			String targetPath, short alignment) {
		
		ImageData targetData = null;

		switch (alignment) {

		case HORIZONTAL:
			// If alignment is Horizontal,
			// imgData3 width should be equal to the sum of width of images
			// imgData1 & imgData2
			// imgData3 height should be equal to height of imgData1
			targetData = new ImageData(sourceData1.width + sourceData2.width,
					sourceData1.height, sourceData1.depth, sourceData1.palette);
			merge(sourceData1, sourceData2, targetData, sourceData1.width,
					sourceData1.y);
			break;

		case VERTICAL:
			// If alignment is Vertical,
			// imgData3 height should be equal to the sum of height of images
			// imgData1 & imgData2
			// imgData3 width should be equal to the width of imgData1
			targetData = new ImageData(sourceData1.width, sourceData1.height
					+ sourceData2.height, sourceData1.depth,
					sourceData1.palette);
			merge(sourceData1, sourceData2, targetData, sourceData1.x,
					sourceData1.height);
			break;
		}
		
		return targetData;
	}

	public static ImageData merge(String sourcePath1, String sourcePath2,
			String targetPath, short alignment) {
		ImageData sourceData1 = new ImageData(sourcePath1);
		ImageData sourceData2 = new ImageData(sourcePath2);
		ImageData targetData = null;

		switch (alignment) {

		case HORIZONTAL:
			// If alignment is Horizontal,
			// imgData3 width should be equal to the sum of width of images
			// imgData1 & imgData2
			// imgData3 height should be equal to height of imgData1
			targetData = new ImageData(sourceData1.width + sourceData2.width,
					sourceData1.height, sourceData1.depth, sourceData1.palette);
			merge(sourceData1, sourceData2, targetData, sourceData1.width,
					sourceData1.y);
			break;

		case VERTICAL:
			// If alignment is Vertical,
			// imgData3 height should be equal to the sum of height of images
			// imgData1 & imgData2
			// imgData3 width should be equal to the width of imgData1
			targetData = new ImageData(sourceData1.width, sourceData1.height
					+ sourceData2.height, sourceData1.depth,
					sourceData1.palette);
			merge(sourceData1, sourceData2, targetData, sourceData1.x,
					sourceData1.height);
			break;
		}

//		if (targetData == null)
//			return targetData;
		
		return targetData;

		// Export the image as a JPEG.
//		ImageLoader imageLoader = new ImageLoader();
//		imageLoader.data = new ImageData[] { targetData };
//		imageLoader.save(targetPath, SWT.IMAGE_JPEG);
	}

	private static void merge(ImageData sourceData1, ImageData sourceData2,
			ImageData targetData, int startX, int startY) {
		// Merge the 1st Image
		merge(sourceData1, targetData, sourceData1.x, sourceData1.x);

		// Merge the 2nd Image
		merge(sourceData2, targetData, startX, startY);
	}

	private static void merge(ImageData sourceData, ImageData targetData,
			int startX, int startY) {
		int i;
		i = sourceData.x;

		for (; i < sourceData.width; i++) {
			int j = sourceData.y;
			for (; j < sourceData.height; j++) {
				targetData.setPixel(startX + i, startY + j, sourceData
						.getPixel(i, j));
			}
		}
	}
}
