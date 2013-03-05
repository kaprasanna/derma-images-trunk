package com.bh.derma.images.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.bh.derma.images.model.ISeries;

public class Series implements ISeries {
	private String seriesID;
	private String seriesName;
	private String notes;
	private List<Object> photos;
	private String parentStudyID;
	private Date seriesTime;

	public Series(Object type) {
	}
	/* (non-Javadoc)
	 * @see com.bh.derma.images.model.ISeries#getSeriesID()
	 */
	@Override
	public String getSeriesID() {
		return seriesID;
	}
	/* (non-Javadoc)
	 * @see com.bh.derma.images.model.ISeries#setSeriesID(java.lang.String)
	 */
	@Override
	public void setSeriesID(String seriesID) {
		this.seriesID = seriesID;
	}
	
	/* (non-Javadoc)
	 * @see com.bh.derma.images.model.ISeries#getNotes()
	 */
	@Override
	public String getNotes() {
		return notes;
	}
	/* (non-Javadoc)
	 * @see com.bh.derma.images.model.ISeries#setNotes(java.lang.String)
	 */
	@Override
	public void setNotes(String notes) {
		this.notes = notes;
	}
	/* (non-Javadoc)
	 * @see com.bh.derma.images.model.ISeries#getPhotos()
	 */
	@Override
	public List<Object> getPhotos() {
		return photos;
	}
	/* (non-Javadoc)
	 * @see com.bh.derma.images.model.ISeries#setPhotos(java.util.List)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void setPhotos(Object photos) {
		this.photos = (List<Object>) photos;
	}
	
	@Override
	public void setParentStudyID(String parentStudyID) {
		this.parentStudyID = parentStudyID;
	}
	
	/**
	 * @return the parentStudy
	 */
	public String getParentStudyID() {
		return parentStudyID;
	}
	
	/**
	 * @return the seriesTime
	 */
	@Override
	public Date getSeriesTime() {
		return seriesTime;
	}
	
	/**
	 * @param seriesTime the seriesTime to set
	 */
	@Override
	public void setSeriesTime(Date seriesTime) {
		this.seriesTime = seriesTime;
	}
	
	@Override
	public String toString() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(seriesTime);
		String seriesDateStr = String.valueOf(cal.get(Calendar.HOUR));
		seriesDateStr += "-" + String.valueOf(cal.get(Calendar.MINUTE));
		seriesDateStr += "-" + String.valueOf(cal.get(Calendar.SECOND));
		
		return seriesName.concat(" ").concat(seriesDateStr);
	}
	
	@Override
	public String getSeriesName() {
		return seriesName;
	}
	
	@Override
	public void setSeriesName(String seriesName) {
		this.seriesName = seriesName;
	}
}
