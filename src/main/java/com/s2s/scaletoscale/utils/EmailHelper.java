package com.s2s.scaletoscale.utils;

import com.s2s.scaletoscale.models.dto.EmailDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.mail.MessagingException;
import java.util.concurrent.*;

@Service
public class EmailHelper {

    @Autowired
    private EmailSender emailSender;

    @Autowired
    @Qualifier("emailConsumerThread")
    private ExecutorService emailConsumerThread;

    @Value("${domain}")
    String domain;

    @Value("${email.format.otp}") String otpEmailFormat;

    @PostConstruct
    void init(){
        sendEmail();
    }

    public static final BlockingQueue<EmailDTO> sendEmailStore = new LinkedBlockingQueue<>();

    public void sendEmail(){
        Runnable task = ()->{
            while(true) {
                try {
                    emailSender.sendSimpleMessage(sendEmailStore.take());
                } catch (MessagingException | InterruptedException e) {
                    System.out.println("Exception : " + e);
                }
            }
        };
        emailConsumerThread.submit(task);
    }

    public String constructOtpEmailBody(String otp, String email){
        return otpEmailFormat.replace("{{otp}}",otp+"").replace("{{url}}",domain+"email/verify"+"?email="+email+"&otp="+otp);
    }

    public void processEmail(EmailDTO emailDTO) throws InterruptedException {
        sendEmailStore.put(emailDTO);
    }

}
