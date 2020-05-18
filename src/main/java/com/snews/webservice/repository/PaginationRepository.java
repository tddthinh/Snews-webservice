package com.snews.webservice.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.snews.webservice.entities.Pagination;

@Repository
public interface PaginationRepository extends CrudRepository<Pagination, Integer> {
	@Query("SELECT p FROM Pagination p where p.pId = :name ")
	Pagination findById(@Param("name") String name);
}
