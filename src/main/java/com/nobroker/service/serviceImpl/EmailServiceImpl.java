package com.nobroker.service.serviceImpl;


import com.nobroker.service.EmailService;
import com.nobroker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import static com.nobroker.service.EmailVerificationService.emailOtpMapping;

import java.util.HashMap;
import java.util.Map;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    private final UserService userService;


    public EmailServiceImpl(UserService userService) {
        this.javaMailSender= javaMailSender;
        this.userService = userService;
    }

    @Override
    public String generateOtp() {
        return String.format("%06d", new java.util.Random().nextInt(1000000));
    }

    @Override
    public Map<String, String> sendOtpEmail(String email) {
        String otp = generateOtp();
        emailOtpMapping.put(email, otp);
        sendEmail(email, "OTP for email verification", "Your OTP is: " +otp);


        Map<String, String> response= new HashMap<>();
        response.put("status", "success");
        response.put("message", "OTP sent sucessfully");
        return response;
    }

    @Override
    public void sendEmail(String to, String subject, String text) {
        SimpleMailMessage message =new SimpleMailMessage();
        message.setFrom("your.gmail@gmail.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        javaMailSender.send(message);
    }


}
