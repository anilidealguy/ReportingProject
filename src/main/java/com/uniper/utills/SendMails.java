package com.uniper.utills;

public class SendMails {
	public static void main(String[] args) {
		//String url = "https://public.tableau.com/en-us/s/gallery/changing-diseases";
		//String image = ReportScreenshot.getScreenshot(url);
		//ReportScreenshot.getScreenshot(url);
		System.out.println("Screenshot Completed");
		Message savableMsg = new Message();
		savableMsg.setToList("achebrolu@sapient.com; anilidealguy@gmail.com");
		savableMsg.setCcList("achebrolu@sapient.com; anilidealguy@gmail.com");
		savableMsg.setSubject("Regression Testing | Savable Mail");
		savableMsg.setHtmlBody("<html><head><title>Test</title><head><body>Hi all, <br><br> Here is the screenshot of tableau report<br><br><img src=\"D:\\screenshot_cropped.jpg\"><br><br>Thanks,<br>Anil Kumar Chebrolu</body></html>");
		savableMsg.setSendableMessage(false);
		savableMsg.setMsgLocation("D:\\SavableMessage.msg");
		Outlook.outlookInteraction(savableMsg);
		System.out.println("message saved");
		
		Message sendableMsg = new Message();
		sendableMsg.setToList("achebrolu@sapient.com; anilidealguy@gmail.com");
		sendableMsg.setCcList("achebrolu@sapient.com; anilidealguy@gmail.com");
		sendableMsg.setSubject("Regression Testing | Savable Mail");
		sendableMsg.setHtmlBody("<html><head><title>Test</title><head><body>Hi Team,<br><br> PFA the Tableau report <a href=\"www.google.com\"><br><br>Click Me</a><br>Thanks,<br><br>Anil Kumar Chebrolu</body></html>");
		sendableMsg.setSendableMessage(true);
		String attachmentsLocations = "D:\\SavableMessage.msg";
		sendableMsg.setAttachmentsLocations(attachmentsLocations);
		//sendableMsg.setMsgLocation("D:\\SavableMessage.msg");
		Outlook.outlookInteraction(sendableMsg);
		System.out.println("message sent");
		
		
	}
}
