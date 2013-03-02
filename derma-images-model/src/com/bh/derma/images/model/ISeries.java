package com.bh.derma.images.model;

import java.util.Date;
import java.util.List;

public interface ISeries {

	public String getSeriesID();

	public void setSeriesID(String seriesID);

	public String getName();

	public void setName(String name);

	public String getNotes();

	public void setNotes(String notes);

	public List<Object> getPhotos();

	public void setPhotos(Object photos);
	
	public IStudy getParentStudy();
	
	public void setParentStudy(IStudy parentStudy);
	
	public void setSeriesTime(Date date);
	
	public Date getSeriesTime();

}