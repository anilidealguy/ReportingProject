package com.uniper.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.uniper.dao.GroupDAO;
import com.uniper.dao.GroupDAOImpl;
import com.uniper.model.Group;

@Controller
public class GroupController {
	
	private GroupDAO groupDAO = new GroupDAOImpl(MainController.getDataSource());
	
	@RequestMapping(value = "/newGroup", method = RequestMethod.GET)
	public ModelAndView newGroup(ModelAndView model){
		Group newGroup = new Group();
		model.addObject("group", newGroup);
		model.setViewName("NewGroup");
		return model;
	}
	
	@RequestMapping(value = "/groupList", method = RequestMethod.GET)
	public ModelAndView newSubGroup(ModelAndView model){
		
		List<Group> groups = groupDAO.list();
		model.addObject("groupsList", groups);
		model.setViewName("GroupList");
		return model;
	}
	
	@RequestMapping(value="/saveGroup", method=RequestMethod.POST)
	public ModelAndView saveGroup(@ModelAttribute Group group){
		groupDAO.saveOrUpdate(group);
		return new ModelAndView("redirect:/index.html");
	}

}
