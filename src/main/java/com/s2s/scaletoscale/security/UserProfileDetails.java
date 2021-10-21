package com.s2s.scaletoscale.security;

import com.s2s.scaletoscale.entities.UserProfile;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

public class UserProfileDetails implements UserDetails {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	
	private UserProfile userProfile;
		
	public UserProfileDetails() {
	}

	public UserProfileDetails(UserProfile UserProfile) {
		this.userProfile = UserProfile;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Arrays.stream(userProfile.getRole().split(","))
				.map(SimpleGrantedAuthority::new).collect(Collectors.toList());
	}

	@Override
	public String getPassword() {
		return userProfile.getPassword();
	}

	@Override
	public String getUsername() {
		return userProfile.getEmail();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
