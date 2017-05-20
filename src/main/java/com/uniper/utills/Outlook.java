package com.uniper.utills;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Dispatch;

public class Outlook{
	
	public static void outlookInteraction(Message msg) {
		ActiveXComponent xl = new ActiveXComponent("Outlook.Application");
		//String msgLocation = "D:\\anil.msg";
		Dispatch xlo = xl.getObject();
		try {
			Dispatch mail = Dispatch.invoke(xlo, "CreateItem", Dispatch.Get,new Object[] { "0" }, new int[0]).toDispatch();
			Dispatch.put(mail, "To", msg.getToList());
			Dispatch.put(mail, "Cc", msg.getCcList());
			Dispatch.put(mail, "Subject", msg.getSubject());
			System.out.println("Outlook : "+System.getProperty("user.dir"));
			//String body = "<html><head><title>Test</title><head><body><img src=\"D:\\anil.jpg\"><a href=\"www.google.com\">Click Me</a></body></html>";
			Dispatch.put(mail, "HTMLBody", msg.getHtmlBody());
			String attachments = msg.getAttachmentsLocations();
			String[] attachmentsCount = attachments.split(";");
			for(int i=0; i<attachmentsCount.length; i++){
				String str = attachmentsCount[i].trim();
				if(str!=""){
					Dispatch attachs = Dispatch.get(mail, "Attachments").toDispatch();
					//String imageSrc = "D:\\test.html";
					Dispatch.call(attachs,"Add", attachmentsCount[i],6);
				}
			}
			
			if(msg.isSendableMessage()){
				Dispatch.call(mail, "Send");
			}
			else{
				Dispatch.call(mail, "SaveAs",msg.getMsgLocation());
				//Dispatch.call(mail, "SaveAs","/Message.msg");
				//Dispatch.call(mail, "SaveAs","D:/MVCWorkspace/workspace/UniperReporting/temp/Message.msg");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			//xl.invoke("Quit");
			System.out.println("Program Completed");	
		}
	}
	
	
}