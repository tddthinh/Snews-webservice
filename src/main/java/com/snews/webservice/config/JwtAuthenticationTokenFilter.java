package com.snews.webservice.config;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

import com.snews.webservice.entities.Account;
import com.snews.webservice.service.AccountService;
import com.snews.webservice.service.JwtService;

public class JwtAuthenticationTokenFilter extends UsernamePasswordAuthenticationFilter {
	private final static String TOKEN_HEADER = "authorization";
	@Autowired
	private JwtService jwtService;
	@Autowired
	private AccountService accountService;
	static Log log = LogFactory.getLog(JwtAuthenticationTokenFilter.class.getName());
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		String authToken = httpRequest.getHeader(TOKEN_HEADER);

		if (jwtService.validateTokenLogin(authToken)) {
			
			String username = jwtService.getUsernameFromToken(authToken);
			Account user = accountService.loadUserByUsername(username);
			if (user != null) {
				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user,
						null, user.getAuthorities());
				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpRequest));
				SecurityContextHolder.getContext().setAuthentication(authentication);
				
			}
		}else {
			accountService.remove(authToken);
		}
		chain.doFilter(request, response);
	}
}