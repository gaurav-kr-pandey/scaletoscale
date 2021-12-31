package com.s2s.scaletoscale.Controller;

import com.s2s.scaletoscale.cache.OtpService;
import com.s2s.scaletoscale.exception.UsernameNotAvailableException;
import com.s2s.scaletoscale.models.dto.UserProfileResetDto;
import com.s2s.scaletoscale.models.request.UserProfile;
import com.s2s.scaletoscale.service.UserProfileService;
import com.s2s.scaletoscale.utils.SecurityUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Controller
public class UserProfileController {

    @Autowired
    private OtpService otpService;
    
    @Autowired
    private ModelMapper modelMapper;
    
    @Autowired
    private SecurityUtils securityUtils;

    @Autowired
    private UserProfileService userProfileService;


    @GetMapping("/signup")
    public String signUpPage(@ModelAttribute("user") UserProfile user, Model model) {
        if (securityUtils.isUserLoggedIn())
            return "redirect:/";
        return "user/signup";
    }

    @GetMapping("/login")
    public String loginPage(@ModelAttribute("user") UserProfile user, Model model) {
        if (securityUtils.isUserLoggedIn())
            return "redirect:/";
        return "user/login";
    }
    
    
    
    @PostMapping("/signup")
    public String signup(@ModelAttribute("user") UserProfile user, Model model) {
        try {
            if (!user.getPassword().equals(user.getConfirmPassword())) {
                model.addAttribute("user", user);
                model.addAttribute("msg", "Password and Confirm Password is different.");
            } else {
                userProfileService.saveUserProfile(user);
                model.addAttribute("msg", "Profile Saved. You can login now.");
            }
        } catch (UsernameNotAvailableException e) {
            model.addAttribute("user", user);
            model.addAttribute("msg", user.getEmail() + " not available.");
        } catch (Exception e) {
            model.addAttribute("user", user);
            model.addAttribute("msg", "Something went wrong, try again");
        }
        return "user/signup";
    }

    @PostMapping("/update/user")
    public String updateUser(@ModelAttribute("user") UserProfile user, Model model) {
        try {
            if (!user.getPassword().equals(user.getConfirmPassword())) {
                model.addAttribute("user", user);
                model.addAttribute("msgType", false);
                model.addAttribute("msg", "Password and Confirm Password is different.");
            } else {
                userProfileService.updateUserProfile(user);
                model.addAttribute("msgType", true);
                model.addAttribute("msg", "Profile Updated. You can login now.");
            }
        } catch (Exception e) {
            model.addAttribute("user", user);
            model.addAttribute("msg", "Something went wrong, try again");
            System.out.println(e);
        }
        return "user/update-user-profile";
    }

    @GetMapping("/update/user")
    public String updateProfilePage(@ModelAttribute("user") UserProfile user, Model model) {
        com.s2s.scaletoscale.models.response.UserProfile userProfile = userProfileService.getUserProfile(securityUtils.getLoggedInUsername());
        model.addAttribute("user", userProfile);
        return "user/update-user-profile";
    }

    @PostMapping("/user/pass")
    public String changePassword(@ModelAttribute("user") UserProfileResetDto user, Model model) {
        try {
            com.s2s.scaletoscale.models.response.UserProfile userProfileRes = userProfileService.getUserProfile(user.getFpEmail());
            if (!isNotValidResetReq(user, userProfileRes, model)) {
                UserProfile userProfileReq = modelMapper.map(userProfileRes, UserProfile.class);
                userProfileReq.setPassword(user.getNewPassword());
                userProfileReq.setConfirmPassword(user.getConfirmNewPassword());
                userProfileService.updateUserProfile(userProfileReq);
                model.addAttribute("msg", "New Password Updated. You can login now.");
            }
        } catch (Exception e) {
            model.addAttribute("msg", "Something went wrong, try again");
        }
        model.addAttribute("user", new com.s2s.scaletoscale.models.response.UserProfile());
        return "user/login";
    }

    private boolean isNotValidResetReq(UserProfileResetDto user, com.s2s.scaletoscale.models.response.UserProfile userProfileRes, Model model) throws ExecutionException {
        if (!user.getNewPassword().equals(user.getConfirmNewPassword())) {
            model.addAttribute("msg", "Password and Confirm Password is different.");
            return true;
        } else if (userProfileRes == null) {
            model.addAttribute("msg", "User does not exist with email : " + user.getFpEmail());
            return true;
        } else if (!otpService.isCorrectOtp(user.getFpEmail(), user.getOtp())) {
            model.addAttribute("msg", "Incorrect OTP!");
            return true;
        }
        return false;
    }

}
