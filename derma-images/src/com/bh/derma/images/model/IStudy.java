package com.bh.derma.images.model;

import java.util.Date;

public interface IStudy {
	
	public String getStudyName();
	
	public void setStudyName(String studyName);
	
	public Date getStudyDate();
	
	public void setStudyDate(Date studyDate);

	public Integer getNumberOfSeries();

	public void setNumberOfSeries(Integer numberOfSeries);

	public ISeries[] getAllSeries();

	public ISeries[] getSeries(String name, String notes);

}