package edu.miu.cs.neptune.service;

public interface MailService {
    public void sendEmail(String MailFrom, String MailTo, String MailSubject, String MailContent);
}
