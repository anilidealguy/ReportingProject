package com.uniper.dao;

import java.util.List;

import com.uniper.model.Mail;

public interface MailDAO {
	
	public void saveOrUpdate(Mail mail);
		
	public void delete(int id);
	
	public Mail get(int id);
	
	public List<Mail> list();
}
