package com.bh.derma.images.model;

import java.util.Date;
import java.util.List;

public interface ISeries {

	public String getSeriesID();

	public void setSeriesID(String seriesID);

	public String getSeriesName();

	public void setSeriesName(String seriesName);

	public String getNotes();

	public void setNotes(String notes);

	public List<Object> getPhotos();

	public void setPhotos(Object photos);
	
	public String getParentStudyID();
	
	public void setParentStudyID(String parentStudyID);
	
	public void setSeriesTime(Date date);
	
	public Date getSeriesTime();

}