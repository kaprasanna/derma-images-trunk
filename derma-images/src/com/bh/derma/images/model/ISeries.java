package com.bh.derma.images.model;

import java.util.List;

public interface ISeries {

	public String getSeriesID();

	public void setSeriesID(String seriesID);

	public String getName();

	public void setName(String name);

	public String getNotes();

	public void setNotes(String notes);

	public List<Object> getPhotos();

	public void setPhotos(List<Object> photos);
	
	public Study getParentStudy();
	
	public void setParentStudy(Study parentStudy);

}