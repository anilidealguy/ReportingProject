package com.uniper.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.uniper.model.Group;

@Repository
public interface GroupDAO {
	public void saveOrUpdate(Group group);
	
	public void delete(int groupId);
	
	public Group get(String name);
	
	public List<Group> list();

}
