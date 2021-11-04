package com.s2s.scaletoscale.security;


import com.s2s.scaletoscale.constants.SecurityConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailService userDetailService;

	//Authentication
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailService);
	}
	
	//Password Encoding
	@Bean
	public PasswordEncoder getEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}
	
	//Authorization
	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.headers().frameOptions().disable().
				and().authorizeRequests()
		.antMatchers("/student/**").hasAnyRole(SecurityConstants.ROLE.STUDENT.name(), SecurityConstants.ROLE.ADMIN.name(), SecurityConstants.ROLE.PAID_STUDENT.name())
		.antMatchers("/student-paid/**").hasAnyRole( SecurityConstants.ROLE.ADMIN.name(), SecurityConstants.ROLE.PAID_STUDENT.name())
		.antMatchers("/admin/**").hasRole(SecurityConstants.ROLE.ADMIN.name())
        .antMatchers(SecurityConstants.LOGIN_PAGE).permitAll()
        .antMatchers(SecurityConstants.PERMIT_ALL_USER, SecurityConstants.ALL_USER_URL).permitAll()
		.and()
		.formLogin()
		.loginPage(SecurityConstants.LOGIN_PAGE)
		.loginProcessingUrl(SecurityConstants.AUTHENTICATE_USER_URL)
		.permitAll()
	.and()
	.logout().permitAll()
	.and()
	.exceptionHandling().accessDeniedPage(SecurityConstants.ACCESS_DENIED);
	}
}
