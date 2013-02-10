package com.bh.derma.images.impl;

import com.bh.derma.images.model.IPatient;
import com.bh.derma.images.model.IStudy;

public class Patient implements IPatient {
	private String id;
	private String name;
	
	public Patient(Object type) {
	}
	/* (non-Javadoc)
	 * @see com.bh.derma.images.model.IPatient#getId()
	 */
	@Override
	public String getId() {
		return id;
	}
	/* (non-Javadoc)
	 * @see com.bh.derma.images.model.IPatient#setId(java.lang.String)
	 */
	@Override
	public void setId(String id) {
		this.id = id;
	}
	/* (non-Javadoc)
	 * @see com.bh.derma.images.model.IPatient#getName()
	 */
	@Override
	public String getName() {
		return name;
	}
	
	/* (non-Javadoc)
	 * @see com.bh.derma.images.model.IPatient#setName(java.lang.String)
	 */
	@Override
	public void setName(String name) {
		this.name = name;
	}
	
	/* (non-Javadoc)
	 * @see com.bh.derma.images.model.IPatient#getStudies()
	 */
	@Override
	public IStudy[] getStudies() {
		return null;
	}	
}
