package com.s2s.scaletoscale.utils;

import com.s2s.scaletoscale.cache.OtpService;
import com.s2s.scaletoscale.models.dto.EmailDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class EmailSender {

    @Autowired
    private JavaMailSender emailSender;


    public void sendSimpleMessage(EmailDTO emailDto) {
        try {
            String to = emailDto.getTo();
            String subject = emailDto.getSubject();
            String text = emailDto.getBody();
            MimeMessage message = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text, true);
            emailSender.send(message);
        }catch (MessagingException exception){
            System.out.println("**************************** Messaging Exception ****************");
            System.out.println(exception);
        }
    }
}
