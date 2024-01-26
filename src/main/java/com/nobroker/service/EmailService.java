package com.nobroker.service;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

public interface EmailService  {

    public  String generateOtp();

    public Map<String, String> sendOtpEmail(String email);

    public void sendEmail(String to, String subject, String text);

}
