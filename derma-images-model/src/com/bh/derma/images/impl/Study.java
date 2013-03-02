package com.bh.derma.images.impl;

import java.util.Calendar;
import java.util.Date;

import com.bh.derma.images.model.ISeries;
import com.bh.derma.images.model.IStudy;

public class Study implements IStudy {
	private Integer numberOfSeries;	
	private String studyName;
	private Date studyDate;
	private String studyType;
	private String patientID;
	
	/**
	 * @return the studyType
	 */
	public String getStudyType() {
		return studyType;
	}
	/**
	 * @param studyType the studyType to set
	 */
	public void setStudyType(String studyType) {
		this.studyType = studyType;
	}
	
	public void setStudyName(String studyName) {
		this.studyName = studyName;
	}
	
	public void setStudyDate(Date studyDate) {
		this.studyDate = studyDate;
	}
	
	public Study(Object type) {
	}
	
	/* (non-Javadoc)
	 * @see com.bh.derma.images.model.IStudy#getNumberOfSeries()
	 */
	@Override
	public Integer getNumberOfSeries() {
		return numberOfSeries;
	}
	
	/* (non-Javadoc)
	 * @see com.bh.derma.images.model.IStudy#setNumberOfSeries(java.lang.Integer)
	 */
	@Override
	public void setNumberOfSeries(Integer numberOfSeries) {
		this.numberOfSeries = numberOfSeries;
	}
	
	/* (non-Javadoc)
	 * @see com.bh.derma.images.model.IStudy#getAllSeries()
	 */
	@Override
	public ISeries[] getAllSeries() {
		return null;
	}
	
	/* (non-Javadoc)
	 * @see com.bh.derma.images.model.IStudy#getSeries(java.lang.String, java.lang.String)
	 */
	@Override
	public ISeries[] getSeries(String name, String notes) {
		return null;
	}
	
	@Override
	public String getStudyName() {
		return studyName;
	}
	
	@Override
	public Date getStudyDate() {
		return studyDate;
	}
	
	@Override
	public String getPatientID() {
		return patientID;
	}
	
	/**
	 * @param patientID the patientID to set
	 */
	@Override
	public void setPatientID(String patientID) {
		this.patientID = patientID;
	}
	
	@Override
	public String toString() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(studyDate);
		String studyDateStr = String.valueOf(cal.get(Calendar.DAY_OF_MONTH));
		studyDateStr += "-" + String.valueOf(cal.get(Calendar.MONTH));
		studyDateStr += "-" + String.valueOf(cal.get(Calendar.YEAR));
		
		return studyName.concat(" ").concat(studyType).concat(" ").concat(studyDateStr);
	}
	
	@Override
	public String getStudyID() {
		return null;
	}
	
	@Override
	public void setStudyID(String studyID) {
	}
	
	
}
