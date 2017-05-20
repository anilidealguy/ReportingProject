package com.uniper.controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.gargoylesoftware.htmlunit.javascript.host.Map;
import com.gargoylesoftware.htmlunit.javascript.host.fetch.Request;
import com.steadystate.css.parser.selectors.AndConditionImpl;
import com.uniper.dao.ActionTypesDAO;
import com.uniper.dao.ActionTypesDAOImpl;
import com.uniper.dao.GroupDAO;
import com.uniper.dao.GroupDAOImpl;
import com.uniper.dao.MailDAO;
import com.uniper.dao.MailDAOImpl;
import com.uniper.dao.ReportDAO;
import com.uniper.dao.ReportDAOImpl;
import com.uniper.dao.SubGroupDAO;
import com.uniper.dao.SubGroupDAOImpl;
import com.uniper.model.ActionTypes;
import com.uniper.model.Group;
import com.uniper.model.Line;
import com.uniper.model.Mail;
import com.uniper.model.Report;
import com.uniper.model.Steps;
import com.uniper.model.SubGroup;
import com.uniper.rest.util.RestApiUtils;
import com.uniper.tableau.bindings.TableauCredentialsType;
import com.uniper.tableau.bindings.WorkbookListType;
import com.uniper.tableau.bindings.WorkbookType;
import com.uniper.utills.Execute;

import net.ucanaccess.jdbc.Session;

@Controller
@Scope("session")
public class ReportController {
	
	private GroupDAO groupDAO = new GroupDAOImpl(MainController.getDataSource());
	private SubGroupDAO subGroupDAO = new SubGroupDAOImpl(MainController.getDataSource());
	private ActionTypesDAO actionTypesDAO = new ActionTypesDAOImpl(MainController.getDataSource());
	private ReportDAO reportDAO = new ReportDAOImpl(MainController.getDataSource());
	private MailDAO mailDAO = new MailDAOImpl(MainController.getDataSource());
	
	private static Logger s_logger = Logger.getLogger(ReportController.class);

    private static Properties s_properties = new Properties();

    private static final RestApiUtils s_restApiUtils = RestApiUtils.getInstance();

    static {
        // Configures the logger to log to stdout
        BasicConfigurator.configure();

        // Loads the values from configuration file into the Properties instance
        try {
        	s_logger.info(System.getProperty("user.dir"));
            s_properties.load(new FileInputStream("config.properties"));
        } catch (IOException e) {
            s_logger.error("Failed to load configuration files.");
        }
    }
	
	
	@RequestMapping(value = "/newReport", method = RequestMethod.GET)
	public ModelAndView newReport(@ModelAttribute("reports") Report report){
		ModelAndView mav = new ModelAndView();
		
		mav.addObject("report", report);
		
		List<Group> groups = groupDAO.list();
		mav.addObject("groupsList", groups);
		
		List<SubGroup> subGroups = subGroupDAO.list();
		mav.addObject("subGroupsList", subGroups);
				
		List<ActionTypes> actionTypes = actionTypesDAO.list();
		mav.addObject("actionTypesList", actionTypes);
		
		
		
		String username = s_properties.getProperty("user.admin.name");
        String password = s_properties.getProperty("user.admin.password");
        String contentUrl = s_properties.getProperty("site.default.contentUrl");
        s_logger.info("User Name (props) : "  + username);
        s_logger.info("Password (props) : " + password);
        s_logger.info("Content Url(props) : " + contentUrl);
        
        // Signs in to server and saves the authentication token, site ID, and current user ID
        
        /*TableauCredentialsType credential = s_restApiUtils.invokeSignIn(username, password, contentUrl);
        WorkbookListType workbookListType = s_restApiUtils.invokeQueryWorkbooks(credential, credential.getSite().getId(), credential.getUser().getId());
        List<WorkbookType> workbooks = workbookListType.getWorkbook();
        for (WorkbookType workbookType : workbooks) {
			System.out.println(workbookType.getId() + "\t" + workbookType.getProject() + "\t" + workbookType.getName());
		}
        mav.addObject("workbooks", workbooks);
        s_logger.info("Credential " + credential.getUser().getId());
        s_restApiUtils.invokeSignOut(credential);*/
        
        List<Mail> mailList = mailDAO.list();
        mav.addObject("mailList", mailList);
        
        
		
		
		
		mav.setViewName("NewReport");
		return mav;
	}
	
	/*@RequestMapping(value="/takeScreenShot", method=RequestMethod.POST)
	public ModelAndView takeScreenShot(@ModelAttribute("String") String retVal, HttpServletRequest request){
		ModelAndView mav = new ModelAndView();
		Report rep = (Report) request.getAttribute("initialReport");
		System.out.println(rep.getName());
		return new ModelAndView("redirect:/index.html");
	}*/
	
	
	/*@RequestMapping(value="/takeScreenShot", method=RequestMethod.POST)
	public ModelAndView takeScreenShot(@RequestParam String retVal, HttpServletRequest request){
		//ModelAndView mav = new ModelAndView();
		Report rep = (Report) request.getSession().getAttribute("initialReport");
		System.out.println(retVal);
		return new ModelAndView("redirect:/index.html");
	}*/
	
	@RequestMapping(value="/saveReport", method=RequestMethod.POST)
	public ModelAndView saveReport(@ModelAttribute("reports") Report report, HttpServletRequest request){
		//ModelAndView mav = new ModelAndView();
		
		report.getSteps().remove(0);

		request.getSession().setAttribute("initialReport", report);
		//mav.setViewName("/index");
		
		reportDAO.saveOrUpdate(report);
		return new ModelAndView("redirect:/index.html");
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
