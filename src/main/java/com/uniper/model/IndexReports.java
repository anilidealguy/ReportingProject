package com.uniper.model;

import java.util.List;

public class IndexReports {
	private String date;
	private String seperator = "";
	private List<Report> listReports;
	
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getSeperator() {
		return seperator;
	}
	public void setSeperator(String seperator) {
		this.seperator = seperator;
	}
	public List<Report> getListReports() {
		return listReports;
	}
	public void setListReports(List<Report> listReports) {
		this.listReports = listReports;
	}
	
	
	
}
