package com.snews.webservice.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private UserDetailsService userDetailsService;

	@Bean
	public JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter() throws Exception {
		JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter = new JwtAuthenticationTokenFilter();
		jwtAuthenticationTokenFilter.setAuthenticationManager(authenticationManager());
		return jwtAuthenticationTokenFilter;
	}

	@Bean
	public RestAuthenticationEntryPoint restServicesEntryPoint() {
		return new RestAuthenticationEntryPoint();
	}

	@Bean
	public CustomAccessDeniedHandler customAccessDeniedHandler() {
		return new CustomAccessDeniedHandler();
	}

	@Bean
	PasswordEncoder PasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(PasswordEncoder());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().ignoringAntMatchers("/api/**");
		http.authorizeRequests().antMatchers("/api/login**").permitAll();
		http.authorizeRequests().antMatchers("/api/files/**").permitAll();
		http.authorizeRequests().antMatchers(HttpMethod.GET, "/api/topics").permitAll();
		http.authorizeRequests().antMatchers(HttpMethod.GET, "/api/topics/**").permitAll();
		http.authorizeRequests().antMatchers(HttpMethod.GET, "/api/recruitments").permitAll();
		http.authorizeRequests().antMatchers(HttpMethod.GET, "/api/recruitments/**").permitAll();
		
		http.authorizeRequests().antMatchers(HttpMethod.GET, "/api/pagination**").permitAll();
		
		http.antMatcher("/api/**").httpBasic().authenticationEntryPoint(restServicesEntryPoint()).and()
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
				.authorizeRequests()
				.antMatchers(HttpMethod.GET, "/api/**").access("hasAuthority('R_ADMIN') or hasAuthority('R_MOD') or hasAuthority('R_SUSER')")
				.antMatchers(HttpMethod.POST, "/api/**").access("hasAuthority('R_ADMIN') or hasAuthority('R_MOD') or hasAuthority('R_SUSER')")
				.antMatchers(HttpMethod.PUT, "/api/**").access("hasAuthority('R_ADMIN') or hasAuthority('R_MOD') or hasAuthority('R_SUSER')")
				.antMatchers(HttpMethod.DELETE, "/api/**").access("hasAuthority('R_ADMIN') or hasAuthority('R_MOD') or hasAuthority('R_SUSER')").and()
				.addFilterBefore(jwtAuthenticationTokenFilter(), UsernamePasswordAuthenticationFilter.class)
				.exceptionHandling().accessDeniedHandler(customAccessDeniedHandler());


	}

}
