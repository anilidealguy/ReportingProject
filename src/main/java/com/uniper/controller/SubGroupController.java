package com.uniper.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.uniper.dao.GroupDAO;
import com.uniper.dao.GroupDAOImpl;
import com.uniper.dao.SubGroupDAO;
import com.uniper.dao.SubGroupDAOImpl;
import com.uniper.model.Group;
import com.uniper.model.SubGroup;


@Controller
public class SubGroupController {
	
	private SubGroupDAO subGroupDAO = new SubGroupDAOImpl(MainController.getDataSource());
	private GroupDAO groupDAO = new GroupDAOImpl(MainController.getDataSource());
	
	@RequestMapping(value = "/newSubGroup", method = RequestMethod.GET)
	public ModelAndView newSubGroup(ModelAndView model){
		SubGroup newSubGroup = new SubGroup();
		
		List<Group> groups = groupDAO.list();
		System.out.println(groups.size());
		model.addObject("subGroup", newSubGroup);
		model.addObject("groupsList", groups);
		model.setViewName("NewSubGroup");
		return model;
	}
	
	@RequestMapping(value="/saveSubGroup", method=RequestMethod.POST)
	public ModelAndView saveSubGroup(@ModelAttribute SubGroup subGroup){
		subGroupDAO.saveOrUpdate(subGroup);
		return new ModelAndView("redirect:/index.html");
	}

}
