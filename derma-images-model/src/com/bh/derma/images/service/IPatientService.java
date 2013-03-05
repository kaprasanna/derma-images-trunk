package com.bh.derma.images.service;

import java.io.IOException;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.core.runtime.IStatus;

import com.bh.derma.images.model.IPatient;
import com.bh.derma.images.model.ISeries;
import com.bh.derma.images.model.IStudy;

public interface IPatientService {

	public IStatus saveNewPatient(IPatient patient) throws IOException;
	
	/**
	 * Update patient's name. ID can't be modified once entered.
	 * *For future*
	 * 
	 * @param patient
	 * @return
	 */
	public IStatus saveEditedPatient(IPatient patient);
	
	/**
	 * Add a {@link Study} / visit entry for the {@link Patient}
	 * 
	 * @param study
	 * @return
	 * @throws ParserConfigurationException 
	 * @throws IOException 
	 */
	public IStatus saveNewStudy(IStudy study) throws IOException;
	
	public IStatus saveNewSeries(ISeries series) throws IOException;
	
	public List<IPatient> searchPatients(String name, String id) throws Exception;

	public List<IStudy> getStudiesForPatient(IPatient selectedPatient) throws Exception;

	public List<ISeries> getSeriesForStudy(IStudy selectedStudy) throws Exception;

}
