package com.bh.derma.images.model;

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
	
	public IPatient create(Object type) {
		return new Patient(type);
	}
}
