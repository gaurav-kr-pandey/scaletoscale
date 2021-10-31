package com.s2s.scaletoscale.Controller;


import com.s2s.scaletoscale.cache.OtpService;
import com.s2s.scaletoscale.models.dto.EmailDTO;
import com.s2s.scaletoscale.models.request.UserProfile;
import com.s2s.scaletoscale.utils.EmailHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@Controller
@RequestMapping("/email")
public class EmailController {

    private static final Map<String,String> SIGNUP_ATTRIBUTE = new HashMap<>();
    private static final Map<String,String> LOGIN_ATTRIBUTE = new HashMap<>();

    @PostConstruct
    void init(){
        SIGNUP_ATTRIBUTE.put("id1","signupForm");
        SIGNUP_ATTRIBUTE.put("id2","loginForm");
        SIGNUP_ATTRIBUTE.put("id3","btn2");
        SIGNUP_ATTRIBUTE.put("id4","btn1");
        LOGIN_ATTRIBUTE.put("id1","loginForm");
        LOGIN_ATTRIBUTE.put("id2","signupForm");
        LOGIN_ATTRIBUTE.put("id3","btn1");
        LOGIN_ATTRIBUTE.put("id4","btn2");
    }

    @Autowired
    private OtpService otpService;

    @Autowired
    private EmailHelper emailHelper;

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
    public String verifyEmail(@RequestParam("email") String email, @RequestParam("otp") String otp) throws ExecutionException {
        System.out.println("*************************************************************");
        System.out.println("Email : "+email+", OTP : "+otp);
        if(otpService.isCorrectOtp(email,otp)){
            System.out.println("Verify User");
        }else{
            System.out.println("OTP expired");
        }
        System.out.println("*************************************************************");
        return "redirect:/";
    }

    @GetMapping("/otp")
    public String verifyEmail(@ModelAttribute("user") UserProfile user, @RequestParam("email") String email, Model model) throws InterruptedException {
        String otp = otpService.generateOtp()+"";
        otpService.storeOtp(email,otp);
        String to = email;
        String subject = "Secret Code - "+otp;
        String body = emailHelper.constructOtpEmailBody(otp+"",to);
        EmailDTO emailDTO = new EmailDTO(to,subject,body);
        emailHelper.processEmail(emailDTO);
        model.addAttribute("msg","OTP sent to your email "+ email);
        model.addAllAttributes(SIGNUP_ATTRIBUTE);
        return "signup";
    }

}
