package com.uniper.dao;

import java.util.List;

import com.uniper.model.Steps;

public interface StepsDAO {
	
	public void saveOrUpdate(Steps steps);
	
	public void delete(int reportId);
	
	public List<Steps> get(int reportId);
	
}
