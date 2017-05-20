package com.uniper.dao;

import java.util.List;

import com.uniper.model.LineType;

public interface LineTypeDAO {
	
	public void saveOrUpdate(LineType lineType);
	
	public void delete(int id);
	
	public LineType get(int id);
	
	public List<LineType> list();

}
