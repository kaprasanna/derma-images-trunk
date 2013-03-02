package com.bh.derma.images.model;

import com.bh.derma.images.impl.Patient;


public class PatientFactory {
	
	private static PatientFactory INSTANCE = null;
	
	private PatientFactory() {
	}
	
	private static void createInstance() {
		INSTANCE = new PatientFactory();
	}
	
	public static synchronized PatientFactory getInstance() {
		if (INSTANCE == null) {
			createInstance();
		}
		return INSTANCE;
	}
	
	public IPatient create(String id, String name, Object type) {
		return new Patient(id, name, type);
	}
}
