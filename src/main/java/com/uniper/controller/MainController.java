package com.uniper.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.support.HttpRequestHandlerServlet;
import org.springframework.web.servlet.ModelAndView;

import com.uniper.dao.ActionTypesDAO;
import com.uniper.dao.ActionTypesDAOImpl;
import com.uniper.dao.ReportDAO;
import com.uniper.dao.ReportDAOImpl;
import com.uniper.model.ActionTypes;
import com.uniper.model.IndexReports;
import com.uniper.model.ListReport;
import com.uniper.model.Report;
import com.uniper.model.Steps;
import com.uniper.utills.Execute;

@Controller
public class MainController {
	
	private ReportDAO reportDAO = new ReportDAOImpl(MainController.getDataSource());
	private ActionTypesDAO actionTypesDAO = new ActionTypesDAOImpl(MainController.getDataSource());
	
	
	public static DriverManagerDataSource getDataSource(){
		/*DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		dataSource.setUrl("jdbc:mysql://localhost:3306/reporting");
		dataSource.setUsername("root");
		dataSource.setPassword("");*/
		
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("net.ucanaccess.jdbc.UcanaccessDriver");
		String url = "jdbc:ucanaccess://";
		String db = "C:\\Anil\\DataBase\\reporting.accdb";
		dataSource.setUrl(url + db);
		dataSource.setUsername("root");
		dataSource.setPassword("");
		
		return dataSource;
	}
	
	@RequestMapping(value = "/index", method=RequestMethod.GET)
	public ModelAndView index(@ModelAttribute("listReports") ListReport listReports){
		ModelAndView mav = new ModelAndView();
		
		List<Report> reportList = reportDAO.list();
		List<ActionTypes> actionTypesList = actionTypesDAO.list();
		Map<Integer, String> actionTypesMap = new HashMap<>();
		for (ActionTypes actionTypes : actionTypesList) {
			actionTypesMap.put(actionTypes.getId(), actionTypes.getName());
		}
		mav.addObject("reportsList", reportList);
		mav.addObject("actionTypes", actionTypesMap);
		
		return mav;
	}
	
	@RequestMapping(value = "/Status", method=RequestMethod.GET)
	public ModelAndView status(){
		return new ModelAndView();
	}
	
	@RequestMapping(value = "/runReports", method=RequestMethod.POST)
	public ModelAndView runReports(@ModelAttribute("listReports") ListReport listReports, HttpServletRequest request){
		String date = listReports.getDate();
		String seperator = listReports.getSeperator();
		date = date.substring(0, 4) + seperator + date.substring(4, 6) + seperator + date.substring(6);
		List<Report> runReports = listReports.getListReports();
		List<Report> toRun = new ArrayList<>();
		for (Report report : runReports) {
			boolean firstTime = true;
			List<Steps> stps = report.getSteps();
			if(stps.size()>0){
				for (int i=0; i<stps.size(); i++) {
					if(stps.get(i).getActive() != null){
						if(firstTime){
							toRun.add(reportDAO.get(report.getId()));
							firstTime = false;
						}
						if (stps.get(i).getActive().equals("true")) {
							int index = toRun.size()-1;
							toRun.get(index).getSteps().get(i).setActive("true");
						}
					}
				}
			}
		}
		
		List<ActionTypes> actionTypesList = actionTypesDAO.list();
		Map<Integer, String> actionTypesMap = new HashMap<>();
		for (ActionTypes actionTypes : actionTypesList) {
			actionTypesMap.put(actionTypes.getId(), actionTypes.getName());
		}
		request.getSession().setAttribute("actionTypes", actionTypesMap);
		request.getSession().setAttribute("toRun",toRun);
		
		Execute execute = new Execute();
		execute.setRequest(request);
		execute.setDate(date);
		
		Thread t1 = new Thread(execute);
		t1.start();
		//execute.setToRun(toRun);

		//execute.run();
				
		/*for(IndexReports indexReports : listIndexReports.getListIndexReports()){
			List<String> actions = indexReports.getActions();
			System.out.println(indexReports.getId() + "\t" +indexReports.getGroupName() + "\t" + indexReports.getSubGroupName() + "\t" + indexReports.getReportName());
			for (String string : actions) {
				System.out.println(string);
			}
						
		}*/
/*		ModelAndView mav = new ModelAndView();
		mav.setViewName("/Status");*/
		return new ModelAndView("redirect:/Status.html");
	}
		
}
