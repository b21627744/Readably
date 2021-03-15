package com.readably;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;



public class Mail {
	
private String review;
private String firstname;
private String lastname;
private String mailAddress;
private String subject;


public String getSubject() {
	return subject;
}



public void setSubject(String subject) {
	this.subject = subject;
}



public String getReview() {
	return review;
}



public void setReview(String review) {
	this.review = review;
}



public String getFirstname() {
	return firstname;
}



public void setFirstname(String firstname) {
	this.firstname = firstname;
}



public String getLastname() {
	return lastname;
}



public void setLastname(String lastname) {
	this.lastname = lastname;
}



public String getMailAddress() {
	return mailAddress;
}



public void setMailAddress(String mailAddress) {
	this.mailAddress = mailAddress;
}




 
   
    
	public void sendMail(String firstname, String lastname, String mailAddress, String review, String subject) {
		    final String username = "readablyproject@gmail.com";
	        final String password = "readablypassword";
             Properties properties = new Properties();
             properties.put("mail.smtp.auth", "true");
             properties.put("mail.smtp.starttls.enable", "true");
             properties.put("mail.smtp.host", "smtp.gmail.com");
             properties.put("mail.smtp.port", "587");
 
             Session session = Session.getInstance(properties,
                           new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                           return new PasswordAuthentication(username, password);
                    }
             }); 
             try {
 
                    Message message = new MimeMessage(session);
                    message.setFrom(new InternetAddress(mailAddress));
                    message.setRecipients(Message.RecipientType.TO,InternetAddress.parse("readablyproject@gmail.com"));
                    message.setSubject(subject);
                    message.setContent("<p>"+mailAddress+ " </p>"+"<h2>"+firstname+" "+lastname+"</h2>"+"<p>"+review+" </p>","text/html");
                    Transport.send(message);
 
             } catch (MessagingException ex) {
                    throw new RuntimeException(ex);
             }
       }
	
	
	
	public void sendResetMail( String mailAddress) {
		 final String username = "readablyproject@gmail.com";
	        final String password = "readablypassword";
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        Session session = Session.getInstance(properties,
                      new javax.mail.Authenticator() {
               protected PasswordAuthentication getPasswordAuthentication() {
                      return new PasswordAuthentication(username, password);
               }
        }); 
        try {

               Message message = new MimeMessage(session);
               message.setFrom(new InternetAddress("readablyproject@gmail.com"));
               message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(mailAddress));
               message.setSubject("Password Reset Link");
               message.setText("http://localhost:8085/resetPassword"); 
               Transport.send(message);

        } catch (MessagingException ex) {
               throw new RuntimeException(ex);
        }
  }
	
}