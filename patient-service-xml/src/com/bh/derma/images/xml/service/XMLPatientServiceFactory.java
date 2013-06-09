package com.bh.derma.images.xml.service;

import org.eclipse.ui.services.AbstractServiceFactory;
import org.eclipse.ui.services.IServiceLocator;

import patient.service.xml.internal.XMLPatientService;

public class XMLPatientServiceFactory extends AbstractServiceFactory {

	public XMLPatientServiceFactory() {
	}

	@Override
	public Object create(Class serviceInterface, IServiceLocator parentLocator,
			IServiceLocator locator) {
		return new XMLPatientService();
	}

}
