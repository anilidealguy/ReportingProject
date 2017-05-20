package com.uniper.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.uniper.dao.LineTypeDAO;
import com.uniper.dao.LineTypeDAOImpl;
import com.uniper.dao.MailDAO;
import com.uniper.dao.MailDAOImpl;
import com.uniper.model.Line;
import com.uniper.model.LineType;
import com.uniper.model.Mail;
import com.uniper.utills.ArchiveFile;
import com.uniper.utills.Execute;
import com.uniper.utills.ReportScreenshot;

@Controller
@Scope("session")
public class MailController {
	
	private MailDAO mailDAO = new MailDAOImpl(MainController.getDataSource());
	private LineTypeDAO linetypeDAO = new LineTypeDAOImpl(MainController.getDataSource());
	private static Logger s_logger = Logger.getLogger(Execute.class);
	
	
	@RequestMapping(value = "/newMail", method = RequestMethod.GET)
	public ModelAndView newMail(@ModelAttribute("mails") Mail mail){
		ModelAndView mav = new ModelAndView();
		
		mav.addObject("mail", mail);
				
		List<LineType> lineTypes = linetypeDAO.list();
		
		mav.addObject("lineTypesList", lineTypes);
		
		mav.setViewName("NewMail");
		return mav;
	}
	
	/*@RequestMapping(value="/takeScreenShot", method=RequestMethod.POST)
	public ModelAndView takeScreenShot(@ModelAttribute("String") String retVal, HttpServletRequest request){
		ModelAndView mav = new ModelAndView();
		Report rep = (Report) request.getAttribute("initialReport");
		System.out.println(rep.getName());
		return new ModelAndView("redirect:/index.html");
	}*/
	
	
	/*@RequestMapping(value="/takeScreenShot", method=RequestMethod.GET)
	public ModelAndView takeScreenShot(@ModelAttribute("String"), HttpServletRequest request){
		//ModelAndView mav = new ModelAndView();
		//Mail mail = (Mail) request.getSession().getAttribute("initialReport");
		//System.out.println(retVal);
		//mail.getLines().get(0).setArg2(retVal);
		
		//mailDAO.saveOrUpdate(mail);
		ModelAndView mav = new ModelAndView();
		String path = "C:\\Anil\\screenshot.png";
		mav.addObject("imgSrc", path);
		return new ModelAndView();
	}*/
	
	@RequestMapping(value="/capture", method=RequestMethod.POST)
	public ModelAndView capture(@RequestParam String retVal, HttpServletRequest request){
		//ModelAndView mav = new ModelAndView();
		Mail mail = (Mail) request.getSession().getAttribute("initialMail");
		//System.out.println(retVal);
		List<Line> lines = mail.getLines();
		LineTypeDAO lineType = new LineTypeDAOImpl(MainController.getDataSource());
		for (int i=0; i < lines.size(); i++) {
			if(lineType.get(lines.get(i).getLineType()).getName().equals("Image")){
				lines.get(i).setArg2(retVal);
				System.out.println("Index : " + i + " Arg : " + lines.get(i).getArg2());
			}
				
		}
		mail.getLines().get(0).setArg2(retVal);
		
		mailDAO.saveOrUpdate(mail);
		return new ModelAndView("redirect:/index.html");
	}
	
	@RequestMapping(value="/takeScreenShot", method=RequestMethod.POST)
	public ModelAndView takeScreenShot(@RequestParam String retVal, HttpServletRequest request){
		//ModelAndView mav = new ModelAndView();
		/*Mail mail = (Mail) request.getSession().getAttribute("initialMail");
		ReportScreenshot.getScreenshot(mail.getLines().)
		System.out.println(retVal);*/
		return new ModelAndView();
	}
	
	@RequestMapping(value="/saveMail", method=RequestMethod.POST)
	public ModelAndView saveMail(@ModelAttribute("mails") Mail mail, HttpServletRequest request){
		
		mail.getLines().remove(0);
		System.out.println(mail.getLines().size());
		List<Line> lines = mail.getLines();
		LineTypeDAO lineType = new LineTypeDAOImpl(MainController.getDataSource());
		
		ModelAndView mav = new ModelAndView();
		
		String view = "/index";
		for (Line line : lines) {
			int lineTypeFromLine = line.getLineType();
			System.out.println("Line Type Int : " + lineTypeFromLine);
			String lineTypeStr = lineType.get(lineTypeFromLine).getName();
			System.out.println(lineTypeStr);
			if(lineTypeStr.equals("Image")){
				request.getSession().setAttribute("initialMail", mail);
				String fileName = ReportScreenshot.getScreenshot(line.getArg1());
				s_logger.info(fileName);
				
				view="/takeScreenShot";
				mav.setViewName(view);
				mav.addObject("imgSrc", fileName);
				
				return mav;
			}
		}

		request.getSession().setAttribute("initialMail", mail);
		
		mailDAO.saveOrUpdate(mail);
		mav.setViewName("redirect:/index.html");
		return mav;
	}
	
	
	/*@RequestMapping(value = "/helloWorld", method = RequestMethod.GET)
	public ModelAndView printWelcome(@ModelAttribute("reports") Reports reports) {

		ModelAndView mav = new ModelAndView("NewReport");
		mav.addObject("message", "Hello World!!!");
		return mav;
	}
	
	@RequestMapping(value="/lazyLoadRow", method=RequestMethod.POST)
	public ModelAndView lazyRowAdd(@ModelAttribute("reports") Reports reports){
		//Reports reps = new Reports();
		ModelAndView mav = new ModelAndView("lazyLoadRow");
		//mav.addObject("reps", reps);
		System.out.println("Name : " + reports.getName());
		System.out.println("To List : " + reports.getToList());
		List<Line> lines = reports.getLines();
		for (Line line : lines) {
			System.out.println(line.getBeforeText());
		}
		return mav;
	}*/

}
