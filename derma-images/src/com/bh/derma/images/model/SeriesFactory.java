package com.bh.derma.images.model;

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
