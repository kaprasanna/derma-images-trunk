package com.bh.derma.images.service;

import org.eclipse.core.runtime.IStatus;

import com.bh.derma.images.model.IPatient;
import com.bh.derma.images.model.ISeries;
import com.bh.derma.images.model.IStudy;
import com.bh.derma.images.model.Patient;
import com.bh.derma.images.model.Study;

public interface IPatientService {

	public IStatus saveNewPatient(IPatient patient);
	
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
	 * @param patient
	 * @param study
	 * @return
	 */
	public IStatus saveNewStudy(IStudy study, IPatient patient);
	
	public IStatus saveNewSeries(ISeries series, IStudy study, IPatient patient);
	
	public IPatient[] searchPatients(String name, String id);

}
