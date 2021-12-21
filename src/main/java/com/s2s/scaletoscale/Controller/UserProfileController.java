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
    private static final Map<String, String> SIGNUP_ATTRIBUTE = new HashMap<>();
    private static final Map<String, String> LOGIN_ATTRIBUTE = new HashMap<>();

    @PostConstruct
    void init() {
        SIGNUP_ATTRIBUTE.put("id1", "signupForm");
        SIGNUP_ATTRIBUTE.put("id2", "loginForm");
        SIGNUP_ATTRIBUTE.put("id3", "btn2");
        SIGNUP_ATTRIBUTE.put("id4", "btn1");
        LOGIN_ATTRIBUTE.put("id1", "loginForm");
        LOGIN_ATTRIBUTE.put("id2", "signupForm");
        LOGIN_ATTRIBUTE.put("id3", "btn1");
        LOGIN_ATTRIBUTE.put("id4", "btn2");
    }

    @Autowired
    private SecurityUtils securityUtils;

    @Autowired
    private UserProfileService userProfileService;


    @GetMapping("/signup")
    public String signUpPage(@ModelAttribute("user") UserProfile user, Model model) {
        if (securityUtils.isUserLoggedIn())
            return "redirect:/";

        model.addAllAttributes(SIGNUP_ATTRIBUTE);
        return "user/signup";
    }

    @PostMapping("/signup")
    public String signup(@ModelAttribute("user") UserProfile user, Model model) {
        try {
            if (!user.getPassword().equals(user.getConfirmPassword())) {
                model.addAttribute("user", user);
                SIGNUP_ATTRIBUTE.put("msg", "Password and Confirm Password is different.");
            } else {
                userProfileService.saveUserProfile(user);
                SIGNUP_ATTRIBUTE.put("msg", "Profile Saved. You can login now.");
            }
        } catch (UsernameNotAvailableException e) {
            model.addAttribute("user", user);
            SIGNUP_ATTRIBUTE.put("msg", user.getEmail() + " not available.");
        } catch (Exception e) {
            model.addAttribute("user", user);
            SIGNUP_ATTRIBUTE.put("msg", "Something went wrong, try again");
        }
        model.addAllAttributes(SIGNUP_ATTRIBUTE);
        return "user/signup";
    }

    @PostMapping("/update/user")
    public String updateUser(@ModelAttribute("user") UserProfile user, Model model) {
        try {
            if (!user.getPassword().equals(user.getConfirmPassword())) {
                model.addAttribute("user", user);
                model.addAttribute("msgType", false);
                SIGNUP_ATTRIBUTE.put("msg", "Password and Confirm Password is different.");
            } else {
                userProfileService.updateUserProfile(user);
                model.addAttribute("msgType", true);
                SIGNUP_ATTRIBUTE.put("msg", "Profile Updated. You can login now.");
            }
        } catch (Exception e) {
            model.addAttribute("user", user);
            SIGNUP_ATTRIBUTE.put("msg", "Something went wrong, try again");
            System.out.println(e);
        }
        model.addAllAttributes(SIGNUP_ATTRIBUTE);
        return "user/update-user-profile";
    }

    @GetMapping("/login")
    public String login(@ModelAttribute("user") UserProfile user, Model model) {
        if (securityUtils.isUserLoggedIn())
            return "redirect:/";
        model.addAllAttributes(LOGIN_ATTRIBUTE);
        return "user/signup";
    }


    @GetMapping("/update/user")
    public String updateProfilePage(@ModelAttribute("user") UserProfile user, Model model) {
        com.s2s.scaletoscale.models.response.UserProfile userProfile = userProfileService.getUserProfile(securityUtils.getLoggedInUsername());
        model.addAttribute("user", userProfile);
        return "user/update-user-profile";
    }

    @PostMapping("/user/pass")
    public String changePassword(@ModelAttribute("user") UserProfileResetDto user, Model model) {
        model.addAllAttributes(LOGIN_ATTRIBUTE);
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
            LOGIN_ATTRIBUTE.put("msg", "Something went wrong, try again");
        }
        model.addAttribute("user", new com.s2s.scaletoscale.models.response.UserProfile());
        return "user/signup";
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
