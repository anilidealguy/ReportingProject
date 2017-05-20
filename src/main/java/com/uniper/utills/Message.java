package com.uniper.utills;


public class Message {
	private String toList;
	private String ccList;
	private String subject;
	private String htmlBody;
	private String attachmentsLocations = "";
	private boolean sendableMessage;
	private boolean savableMessage;
	private String msgLocation;
	
	
	public String getToList() {
		return toList;
	}
	public void setToList(String toList) {
		this.toList = toList;
	}
	public String getCcList() {
		return ccList;
	}
	public void setCcList(String ccList) {
		this.ccList = ccList;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getHtmlBody() {
		return htmlBody;
	}
	public void setHtmlBody(String htmlBody) {
		this.htmlBody = htmlBody;
	}
	public String getAttachmentsLocations() {
		return attachmentsLocations;
	}
	public void setAttachmentsLocations(String attachmentsLocations) {
		this.attachmentsLocations = attachmentsLocations;
	}
	public boolean isSendableMessage() {
		return sendableMessage;
	}
	public void setSendableMessage(boolean sendableMessage) {
		this.sendableMessage = sendableMessage;
	}
	public boolean isSavableMessage() {
		return savableMessage;
	}
	public void setSavableMessage(boolean savableMessage) {
		this.savableMessage = savableMessage;
	}
	public String getMsgLocation() {
		return msgLocation;
	}
	public void setMsgLocation(String msgLocation) {
		this.msgLocation = msgLocation;
	}
	
	
}