package com.s2s.scaletoscale.Controller;

import com.s2s.scaletoscale.constants.SecurityConstants;
import com.s2s.scaletoscale.entities.UserProfile;
import com.s2s.scaletoscale.repository.UserProfileRepository;
import com.s2s.scaletoscale.security.UserProfileDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class UserProfileController {
	
	//@Autowired
	//private JavaMailSender javaMailSender;
	
	@Autowired
	private UserProfileRepository userRepository;


	@GetMapping("/signin")
	public String signIn(){
		return "signin";
	}

	@GetMapping("/signup")
	public String signUpPage(@ModelAttribute("user") UserProfile user, Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if(!(auth instanceof AnonymousAuthenticationToken))
			return "redirect:/";

		return "sign-up";
	}
	/*
	@PostMapping("/signup")
	public String signup(@ModelAttribute("user") UserProfile user,Model model) {

		String username=user.getEmail();
		com.s2s.scaletoscale.entities.UserProfile userProfile = new com.s2s.scaletoscale.entities.UserProfile();
		userProfile.se
		if(userRepository.findByEmail(username)==null) {
			if(user.getRole()==null || user.getRole() == "")
				user.setRole("ROLE_STUDENT");
			userRepository.save(user);
		}
		else {
			model.addAttribute("user",user);
			model.addAttribute("userNameExists", "'"+user.getUsername()+"'"+" not available.");
			return "sign-up";
		}
		return "redirect:/login";
	}
*/
	@GetMapping(SecurityConstants.LOGIN_PAGE)
	public String login(Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if(!(auth instanceof AnonymousAuthenticationToken) )
			return "redirect:/";
		return "login";
	}
	
	@GetMapping("/update/user")
	public String updateProfilePage(@ModelAttribute("user") UserProfile user,Model model) {
		user = getUser();
		model.addAttribute("user", user);
		return "update-user-profile";
	}

	/*
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
	*/
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
}
