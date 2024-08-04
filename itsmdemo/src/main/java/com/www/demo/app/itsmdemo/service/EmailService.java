package com.www.demo.app.itsmdemo.service;


import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender emailSender;

    private Map<String, String> otpStorage = new HashMap<>();
    private Map<String, Long> otpExpiry = new HashMap<>();

    public void sendSimpleMessage(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        emailSender.send(message);
    }

    public String generateSecureOTP() {
        SecureRandom secureRandom = new SecureRandom();
        int otp = 100000 + secureRandom.nextInt(900000); // Generates a random number between 100000 and 999999
        return String.valueOf(otp);
    }

    public void sendOtpEmail(String to) {
        String otp = generateSecureOTP();
        otpStorage.put(to, otp);
        otpExpiry.put(to, System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(10)); // OTP valid for 10 minutes

        String subject = "Your OTP for Verification";
        String text = "Dear User,\n\nYour OTP for verification is: " + otp + "\n\nPlease use this OTP to complete your verification process. The OTP is valid for 10 minutes.\n\nThank you!";
        sendSimpleMessage(to, subject, text);
    }

    public boolean verifyOtp(String email, String otp) {
        String storedOtp = otpStorage.get(email);
        Long expiryTime = otpExpiry.get(email);

        if (storedOtp != null && storedOtp.equals(otp) && System.currentTimeMillis() <= expiryTime) {
            otpStorage.remove(email);
            otpExpiry.remove(email);
            return true;
        }

        return false;
    }

    public void sendTransactionStatusEmail(String to, boolean isSuccess, String transactionId, String amount, String reason) {
        String subject;
        String text;
        if (isSuccess) {
            subject = "Transaction Completed Successfully";
            text = "Dear User,\n\nYour transaction with ID " + transactionId + " for the amount of " + amount + " has been completed successfully.\n\nThank you for using our service!";
        } else {
            subject = "Transaction Failed";
            text = "Dear User,\n\nUnfortunately, your transaction with ID " + transactionId + " for the amount of " + amount + " has failed.\n\nReason: " + reason + "\n\nPlease try again or contact customer support for assistance.";
        }
        sendSimpleMessage(to, subject, text);
    }
    


    public void sendFraudAlertEmail(String email, String transactionId, String reason) {
        String subject = "Fraud Alert: Suspicious Transaction Detected";
        String content = "Dear User,\n\n" +
                         "We have detected a suspicious transaction with ID: " + transactionId + ".\n" +
                         "Reason: " + reason + ".\n\n" +
                         "If you did not initiate this transaction, please contact our support team immediately.\n\n" +
                         "Regards,\n" +
                         "Your Bank";
        sendSimpleMessage(email,subject,content);
    }
}
