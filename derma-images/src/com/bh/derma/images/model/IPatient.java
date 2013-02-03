package com.bh.derma.images.model;

public interface IPatient {

	public String getId();

	public void setId(String id);

	public String getName();

	public void setName(String name);

	public IStudy[] getStudies();

}