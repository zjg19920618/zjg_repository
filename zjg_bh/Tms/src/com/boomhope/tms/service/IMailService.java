package com.boomhope.tms.service;

import java.io.IOException;

import javax.mail.MessagingException;

import com.boomhope.tms.model.Email;

public interface IMailService {

	/**
	 * 
	 * @param email
	 * @throws MessagingException
	 * @throws IOException
	 */
	public void sendMail(Email email) throws MessagingException, IOException;
	
	/**
	 * 
	 * @param email
	 */
	public void sendMailByAsynchronousMode(final Email email);
	
	/**
	 * 
	 * @param email
	 * @throws MessagingException
	 * @throws IOException
	 */
	public void sendMailBySynchronizationMode(Email email) throws MessagingException, IOException;
}
