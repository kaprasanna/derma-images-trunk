package com.bh.derma.images.impl;

import com.bh.derma.images.model.IStudy;

public class StudyFactory {
	private static StudyFactory INSTANCE = null;

	private StudyFactory() {
	}

	private static void createInstance() {
		INSTANCE = new StudyFactory();
	}

	public static synchronized StudyFactory getInstance() {
		if (INSTANCE == null) {
			createInstance();
		}
		return INSTANCE;
	}

	public IStudy create(Object type) {
		return new Study(type);
	}
}
