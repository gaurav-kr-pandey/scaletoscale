package com.s2s.scaletoscale.Controller;

import com.s2s.scaletoscale.exception.UsernameNotAvailableException;
import com.s2s.scaletoscale.models.request.UserProfile;
import com.s2s.scaletoscale.service.UserProfileService;
import com.s2s.scaletoscale.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Controller
public class UserProfileController {

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
	private SecurityUtils securityUtils;

	@Autowired
	private UserProfileService userProfileService;


	@GetMapping("/signup")
	public String signUpPage(@ModelAttribute("user") UserProfile user, Model model) {
		if(securityUtils.isUserLoggedIn())
			return "redirect:/";

		model.addAllAttributes(SIGNUP_ATTRIBUTE);
		return "signup";
	}

	@PostMapping("/signup")
	public String signup(@ModelAttribute("user") UserProfile user,Model model) {
		try {
			if(!user.getPassword().equals(user.getConfirmPassword())){
				model.addAttribute("user",user);
				SIGNUP_ATTRIBUTE.put("msg","Password and Confirm Password is different.");
			}else {
				userProfileService.saveUserProfile(user);
				SIGNUP_ATTRIBUTE.put("msg","Profile Saved. You can login now.");
			}
		} catch(UsernameNotAvailableException e) {
			model.addAttribute("user",user);
			SIGNUP_ATTRIBUTE.put("msg", user.getEmail()+" not available.");
		}catch (Exception e){
			model.addAttribute("user",user);
			SIGNUP_ATTRIBUTE.put("msg","Something went wrong, try again");
		}
		model.addAllAttributes(SIGNUP_ATTRIBUTE);
		return "signup";
	}

	@GetMapping("/login")
	public String login(@ModelAttribute("user") UserProfile user, Model model) {
		if(securityUtils.isUserLoggedIn() )
			return "redirect:/";
		model.addAllAttributes(LOGIN_ATTRIBUTE);
		return "signup";
	}

	/*

	@GetMapping("/update/user")
	public String updateProfilePage(@ModelAttribute("user") UserProfile user,Model model) {
		user = getUser();
		model.addAttribute("user", user);
		return "update-user-profile";
	}


	@PostMapping("/update/user/profile")
	public String updateUserProfile(@ModelAttribute("user") UserProfile user,Model model) {
						
		UserProfile userTemp= getUser();
		
		if(!user.getPassword().equals(user.getConfirmPassword())) {
			model.addAttribute("colleges", colleges);
			model.addAttribute("user", userTemp);
			model.addAttribute("passValid", "Password and confirm password must match.");
			return "update-user-profile";
		}
		
		model.addAttribute("colleges", colleges);
		model.addAttribute("user", userTemp);
		userRepository.save(userTemp);
		model.addAttribute("msg", "Profile Updated Successfully.");
		return "update-user-profile";
	}

	@GetMapping("/login/password")
	public String forgotPassword(@RequestParam("email") String email, Model model) {
		UserProfile user = userRepository.findByEmail(email);
		if(user == null) {
			model.addAttribute("msg", "Incorrect username.");
			return "login";
		}
		else {
			SimpleMailMessage msg = new SimpleMailMessage();
	        msg.setFrom("gaurav17p@gmail.com");
	        msg.setTo(user.getEmail());
	        msg.setSubject("Your Password | S2S");
	        msg.setText("Hi, "+user.getFirstName()+" "+user.getLastName()+" \r\n"+"Your username and password is :"
	        		+ "\r\n"
	        		+ "Username : "+user.getEmail()+"\r\n"
	        		+"Password : "+user.getPassword()+"\r\n"
	        		+"If this is not you, change your password or inform at info@ilpeducation.in");

		}

		model.addAttribute("msg", "Password sent at your email associated with "+user.getEmail());
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if(!(auth instanceof AnonymousAuthenticationToken) )
			return "redirect:/";
		return "login";
    }
	
	private UserProfile getUser() {
		UserProfileDetails userDetails = (UserProfileDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return userRepository.findByEmail(userDetails.getUsername());
	}

	 */
}
