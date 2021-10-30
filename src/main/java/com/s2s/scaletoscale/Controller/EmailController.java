package com.s2s.scaletoscale.Controller;


import com.s2s.scaletoscale.cache.OtpService;
import com.s2s.scaletoscale.models.dto.EmailDTO;
import com.s2s.scaletoscale.utils.EmailHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Controller
@RequestMapping("/email")
public class EmailController {

    @Autowired
    private OtpService otpService;

    @Autowired
    private EmailHelper emailHelper;

    @PostConstruct
    void inti() throws InterruptedException {
        System.out.println("*****************************************************");
        long start = System.currentTimeMillis();
        System.out.println(start);
        int i = 10;
        while(i-->0){
            int otp = otpService.generateOtp();
            String to = "gaurav17p@gmail.com";
            String subject = "Secret Code - "+otp;
            String body = emailHelper.constructOtpEmailBody(otp+"",to);
            EmailDTO emailDTO = new EmailDTO(to,subject,body);
            emailHelper.processEmail(emailDTO);
        }
        long end = System.currentTimeMillis();
        System.out.println(end);
        long diff = new Date(start).getTime() - new Date(end).getTime();//as given
        long seconds = TimeUnit.MILLISECONDS.toSeconds(diff);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(diff);
        System.out.println("Minutes : "+minutes);
        System.out.println("Seconds : "+seconds);
        System.out.println("*****************************************************");
    }

    @GetMapping("/verify")
    public String verifyEmail(@RequestParam("email") String email, @RequestParam("otp") String otp){
        System.out.println("*************************************************************");
        System.out.println("Email : "+email+", OTP : "+otp);
        System.out.println("*************************************************************");
        return "redirect:/";
    }

}
