package com.dozenx.web.core.auth.dao;


import com.dozenx.web.core.auth.bean.Active;

public interface ActiveMapper {
	public int countAll();
	
	public Active selectActiveById(String userId);

	public void insertActive(Active active);

	public void updateActive(Active active);

	public void deleteActive(int activeid);



}
