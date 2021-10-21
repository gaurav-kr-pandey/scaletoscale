package com.s2s.scaletoscale.security;

import com.s2s.scaletoscale.entities.UserProfile;
import com.s2s.scaletoscale.repository.UserProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailService implements UserDetailsService {
	
	@Autowired
	private UserProfileRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		UserProfile user =userRepository.findByEmail(userName);

		if(user==null) {
			throw new UsernameNotFoundException("User not found.");
		}
		return new UserProfileDetails(user);
	}

}
