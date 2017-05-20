package com.uniper.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.uniper.model.Group;
import com.uniper.model.SubGroup;

@Repository
public interface SubGroupDAO {
	public void saveOrUpdate(SubGroup subGroup);
	
	public void delete(int subGroupId);
	
	public Group get(String SubGroupName);
	
	public List<SubGroup> list();

}
