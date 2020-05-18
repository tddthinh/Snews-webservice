package com.snews.webservice.service;

import java.util.HashMap;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.snews.webservice.config.JwtAuthenticationTokenFilter;
import com.snews.webservice.entities.Account;
import com.snews.webservice.repository.AccountRepository;

@Service
public class AccountService implements UserDetailsService {
	@Autowired
	private AccountRepository accountRepository;
	private HashMap<String, Account> storedAccount = new HashMap<>();
	
	static Log log = LogFactory.getLog(JwtAuthenticationTokenFilter.class.getName());
	
	@Override
	public Account loadUserByUsername(String username) {		
		return accountRepository.findByUsername(username);
	}
	
	public Account loadUserByToken(String token) {
		return storedAccount.get(token);
	}
	public void store(String token, Account account) {
		storedAccount.put(token, account);
	}
	public void remove(String token) {
		storedAccount.remove(token);
	}
}