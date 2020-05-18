package com.snews.webservice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.snews.webservice.entities.Account;

@Repository
public interface AccountRepository extends CrudRepository<Account, Integer> {
	@Query("SELECT r.accounts FROM Role r where r.rId = :rId ")
	Iterable<Account> findByRoleId(@Param("rId") Integer id);
	
	@Query("SELECT a FROM Account a where a.uUsername = :u ")
	Account findByUsername(@Param("u") String username);
	
	@Query("SELECT a FROM Account a where a.uEmail = :e ")
	Account findByEmail(@Param("e") String email);
	
	@Query("SELECT a FROM Account a")
	Page<Account> findAllByPage(Pageable pageable);
}
