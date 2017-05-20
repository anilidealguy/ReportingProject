package com.uniper.utills;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.HttpRequest;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

import com.uniper.controller.MainController;
import com.uniper.dao.ActionTypesDAO;
import com.uniper.dao.ActionTypesDAOImpl;
import com.uniper.dao.LineTypeDAO;
import com.uniper.dao.LineTypeDAOImpl;
import com.uniper.dao.MailDAO;
import com.uniper.dao.MailDAOImpl;
import com.uniper.model.ActionTypes;
import com.uniper.model.Line;
import com.uniper.model.Mail;
import com.uniper.model.Report;
import com.uniper.model.Steps;
import com.uniper.rest.util.RestApiUtils;
import com.uniper.tableau.bindings.TableauCredentialsType;

public class Execute implements Runnable {
	
	private static Logger s_logger = Logger.getLogger(Execute.class);

    private static Properties s_properties = new Properties();

    private static final RestApiUtils s_restApiUtils = RestApiUtils.getInstance();

    static {
        // Configures the logger to log to stdout
        BasicConfigurator.configure();
        System.out.println(System.getProperty("user.dir"));
        // Loads the values from configuration file into the Properties instance
        try {
            s_properties.load(new FileInputStream("config.properties"));
        } catch (IOException e) {
            s_logger.error("Failed to load configuration files.");
        }
    }

	private HttpServletRequest request;
	
	private static String date;
	
	
	
	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}


	private List<Report> toRun;

	public List<Report> getToRun() {
		return toRun;
	}

	public void setToRun(List<Report> toRun) {
		this.toRun = toRun;
	}
	
	private String createBody(List<Line> lines, String date, String signature){
		StringBuilder htmlBody = new StringBuilder();
		
		
		final String newLine = "<br>";
		date = date.substring(0, 4) + "-" + date.substring(4, 6) + "-" + date.substring(6);
		
		LineTypeDAO lineType = new LineTypeDAOImpl(MainController.getDataSource());
		
		
				
		for (Line line : lines) {
			
			switch (lineType.get(line.getLineType()).getName()) {
			
			case "Plain":
				htmlBody.append(line.getArg1());
				htmlBody.append(newLine);
				break;
			
			case "Link":
				htmlBody.append(line.getArg1());
				htmlBody.append("<a href = \'" + line.getArg2() + "\'>" + line.getArg3() + "</a>" );
				htmlBody.append(line.getArg4());
				htmlBody.append(newLine);
				break;
			
			case "Blank":
				htmlBody.append(newLine);
				break;
				
			case "Image":
				String screenshot  = ReportScreenshot.getScreenshot(line.getArg1());
				String croppedImage = ReportScreenshot.getCroppedImage(screenshot, line.getArg2());
				//htmlBody.append("<img src =\'"+croppedImage+"\' width=500 height=300>");
				int width = 500;
				int height = 300;
				
				String arg3 = line.getArg3();
				String arg4 = line.getArg4();
				
				if(arg3 == null || arg3.compareTo("") !=0)	arg3 = "0";
				if(arg4 == null || arg4.compareTo("") !=0)	arg4 = "0";
				double widthFactor = (Integer.parseInt(arg3) * 0.5);
				double heightFactor = Integer.parseInt(arg3) * 0.5;
				
				width =(int)(width + width*widthFactor);
				height =(int)(height + height*heightFactor);
				htmlBody.append("<img src =\'"+"http:\\localhost:8080\\UniperReporting\\images\\screenshot_cropped.jpg"+"\' width=" + width + "height="+height+">");
				htmlBody.append(newLine);
				break;
			
			case "Headding":
				htmlBody.append("<h4>" + line.getArg1() + "</h4>");
				htmlBody.append(newLine);
				break;
				
			case "Signature":
				htmlBody.append("Thanks<br>" + signature);
				break;
			default:
				break;
			}
			
		}
		System.out.println(htmlBody.toString());
		return htmlBody.toString();
		
	}
	
	private Message createReport(Mail mail){
		String signature = "";
		Message msg = new Message();
		
		
		
		msg.setToList(mail.getToList());
		msg.setCcList(mail.getCcList());
		msg.setSubject(mail.getSubject().replaceFirst("#DATE#", date));
		msg.setSendableMessage(false);
		msg.setAttachmentsLocations("");
		//msg.setMsgLocation(arr[msgLocation]+reportName+date+".msg");
		//msg.setMsgLocation("C:\\Users\\A44082\\workspace\\UniperReporting\\temp\\" + mail.getId() + " " + date + ".msg");
		//System.out.println(request.getContextPath());
		msg.setMsgLocation(mail.getMsgLocation().replaceAll("#DATE#", date));
		Execute execute = new Execute();
		List<Line> lines = mail.getLines();
		String htmlBody = execute.createBody(lines, date, signature);
		msg.setHtmlBody(htmlBody);
		return msg;
	}
	
	public static void tableau(String workbookId, String filePath){
        // Sets the username, password, and content URL, which are all required
        // in the payload of a Sign In request
		filePath = filePath.replaceAll("#DATE#", date);
        String username = s_properties.getProperty("user.admin.name");
        String password = s_properties.getProperty("user.admin.password");
        String contentUrl = s_properties.getProperty("site.default.contentUrl");
        
        
        // Signs in to server and saves the authentication token, site ID, and current user ID
        TableauCredentialsType credential = s_restApiUtils.invokeSignIn(username, password, contentUrl);
        String currentSiteId = credential.getSite().getId();
        String currentUserId = credential.getUser().getId();

        s_logger.info(String.format("Authentication token: %s", credential.getToken()));
        s_logger.info(String.format("Site ID: %s", currentSiteId));
        s_logger.info(String.format("User ID: %s", currentUserId));

        workbookId = "153adbb1-70ef-4716-9746-360620930c1b";
        
        s_restApiUtils.downloadWorkbook(credential, credential.getSite().getId(), workbookId, filePath);
        s_restApiUtils.invokeSignOut(credential);
    }
	
	
	public void run(HttpServletRequest request){
		Execute ex = new Execute();
		@SuppressWarnings("unchecked")
		List<Report> toRunLocal = (List<Report>) request.getSession().getAttribute("toRun");
		System.out.println("Size : " + toRunLocal.size());
		for (Report report : toRunLocal) {
			List<Steps> steps = report.getSteps();
			steps.sort(new Comparator<Steps>() {
 
				@Override
				public int compare(Steps o1, Steps o2) {
					return Integer.compare(o1.getSequenceNumber(), o2.getSequenceNumber());
				}
			});
			
			ActionTypesDAO actionTypesDAO = new ActionTypesDAOImpl(MainController.getDataSource());
			List<ActionTypes> li = actionTypesDAO.list();
			Map<Integer, String> actionTypesMap = new HashMap<>();
			for (ActionTypes actionTypes : li) {
				actionTypesMap.put(actionTypes.getId(),	actionTypes.getName());
			}
			
			
			for (Steps step: steps) {
				String action = actionTypesMap.get(step.getActionType());
				if(step.getActive().equals("true")){
					step.setStatus(10);
					switch (action) {
					
					case "DownloadTableau":
						System.out.println("Execute:DownloadTableau");
						tableau(step.getArg1(),step.getArg2());
						break;
					
					case "UploadToSharepoint":
						try {
							System.out.println("Execute:UploadToSharepoint");
							ArchiveFile.copyFile(step.getArg1().replaceAll("#DATE#", date), step.getArg2());
						} catch (Exception e) {
							e.printStackTrace();
						}
						break;
						
					case "Mail":
						System.out.println("Execute:Mail");
						MailDAO mailDAO = new MailDAOImpl(MainController.getDataSource());
						Mail mail = mailDAO.get(Integer.parseInt(step.getArg1()));
						Message msg = ex.createReport(mail);
						Outlook.outlookInteraction(msg);
						break;
					default:
						break;
					}
				}
				/*try {
					Thread.sleep(1000*20);
				} catch (Exception e) {
					e.printStackTrace();
				}*/
				step.setStatus(1);
			}
		}
	}

	
	public void run() {
		Execute exe = new Execute();
		exe.run(request);
	}

}
