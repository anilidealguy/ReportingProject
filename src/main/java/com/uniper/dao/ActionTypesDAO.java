package com.uniper.dao;

import java.util.List;

import com.uniper.model.ActionTypes;

public interface ActionTypesDAO {
	public void saveOrUpdate(ActionTypes actionTypes);
	
	public void delete(int id);
	
	public ActionTypes get(int id);
	
	public List<ActionTypes> list();
}
