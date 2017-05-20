package com.uniper.model;

import java.util.List;

public class ListReport {
	private List<Report> listReports;
	private String date;
	private String seperator = "";

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
