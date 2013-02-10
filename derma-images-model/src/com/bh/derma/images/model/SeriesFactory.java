package com.bh.derma.images.model;

import com.bh.derma.images.impl.Series;

public class SeriesFactory {
	private static SeriesFactory INSTANCE = null;

	private SeriesFactory() {
	}

	private static void createInstance() {
		INSTANCE = new SeriesFactory();
	}

	public static synchronized SeriesFactory getInstance() {
		if (INSTANCE == null) {
			createInstance();
		}
		return INSTANCE;
	}

	public ISeries create(Object type) {
		return new Series(type);
	}
}
