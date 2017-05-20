package com.uniper.dao;

import java.util.List;

import com.uniper.model.Report;

public interface ReportDAO {
	
	public void saveOrUpdate(Report report);
	
	public void delete(int id);
	
	public Report get(int id);
	
	public List<Report> list();

}
