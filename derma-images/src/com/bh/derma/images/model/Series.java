package com.bh.derma.images.model;

import java.util.List;

public class Series implements ISeries {
	private String seriesID;
	private String name;
	private String notes;
	private List<Object> photos;
	private Study parentStudy;

	/**
	 * @return the parentStudy
	 */
	public Study getParentStudy() {
		return parentStudy;
	}
	/**
	 * @param parentStudy the parentStudy to set
	 */
	public void setParentStudy(Study parentStudy) {
		this.parentStudy = parentStudy;
	}
	
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
	 * @see com.bh.derma.images.model.ISeries#getName()
	 */
	@Override
	public String getName() {
		return name;
	}
	/* (non-Javadoc)
	 * @see com.bh.derma.images.model.ISeries#setName(java.lang.String)
	 */
	@Override
	public void setName(String name) {
		this.name = name;
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
	@Override
	public void setPhotos(List<Object> photos) {
		this.photos = photos;
	}	
	
}
