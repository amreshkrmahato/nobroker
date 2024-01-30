package com.nobroker.service;

import com.nobroker.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class EmailVerificationService {
    public static final Map<String, String> emailOtpMapping = new HashMap<>();

    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;

    public Map<String, String> verifyOtp(String email, String otp) {
        String storedOtp = emailOtpMapping.get(email);
        Map<String, String> response = new HashMap<>();
        if (storedOtp != null && storedOtp.equals(otp)) {
            User user = userService.getUserByEmail(email);
            if (user != null) {
                userService.verifyEmail(user);
                emailOtpMapping.remove(email);
                response.put("status", "sucess");
                response.put("message", "Email verifie sucessfully");
            } else {
                response.put("status", "error");
                response.put("message", "User not found");
            }

        } else {
            response.put("status", "error");
            response.put("message", "Invalid OTP");
        }
        return response;

    }

    public Map<String, String> sendOtpForLogin(String email) {
        if (userService.isEmailVerified(email)) {
            String otp = emailService.generateOtp();
            emailOtpMapping.put(email, otp);

            emailService.sendOtpEmail(email);

            Map<String, String> response = new HashMap<>();
            response.put("status", "sucess");
            response.put("message", "OTP send sucessfully");
            return response;
        }else{
            Map<String, String> response = new HashMap<>();
            response.put("status", "error");
            response.put("message", "Email is not verified!");
            return response;
        }
    }
    public Map<String, String> verifyOtpForLogin(String email, String otp){
        String storedOtp = emailOtpMapping.get(email);

        Map<String, String> response=new HashMap<>();
        if (storedOtp != null && storedOtp.equals(otp)) {
            emailOtpMapping.remove(email);
            //OTP valid
            response.put("status", "sucess");
            response.put("message", "OTP verified sucessfully");
        }else {
            response.put("status", "error");
            response.put("message", "Invalid OTP");
        }

        return response;
    }
}
