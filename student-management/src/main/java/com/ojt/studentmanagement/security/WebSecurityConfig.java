package com.ojt.studentmanagement.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

	@Bean
	public UserDetailsService userDetailsService() {
		return new CustomUserDetailsService();
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationManager authManager(HttpSecurity http, BCryptPasswordEncoder bCryptPasswordEncoder,
			UserDetailsService userDetailService) throws Exception {
		return http.getSharedObject(AuthenticationManagerBuilder.class).userDetailsService(userDetailsService())
				.passwordEncoder(bCryptPasswordEncoder).and().build();
	}

	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService());
		authProvider.setPasswordEncoder(passwordEncoder());

		return authProvider;
	}
	
	@Bean
	public AuthenticationSuccessHandler successHandler() {
	    SimpleUrlAuthenticationSuccessHandler handler = new SimpleUrlAuthenticationSuccessHandler();
	    handler.setUseReferer(true);
	    return handler;
	}

	@Bean
	public SecurityFilterChain configure(HttpSecurity http) throws Exception {

		http.authorizeRequests()
				.antMatchers("/user/**").hasAnyAuthority("User", "Admin")
				.antMatchers("/admin/**").hasAuthority("Admin")
				.and()
				.formLogin().loginPage("/login").permitAll().usernameParameter("email").defaultSuccessUrl("/user/dashboard")
				.and()
				.logout()
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/login")
				.deleteCookies("JSESSIONID").invalidateHttpSession(false)
				
		;
		http.csrf().disable();
		return http.build();
	}

}
