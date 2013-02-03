package com.bh.derma.images.model;

import java.util.Date;

public class Study implements IStudy {
	private Integer numberOfSeries;	
	private String studyName;
	private Date studyDate;
	private String studyType;
	
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
	
}
