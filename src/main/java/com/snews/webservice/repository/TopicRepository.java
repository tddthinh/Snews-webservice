package com.snews.webservice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.snews.webservice.entities.Topic;

@Repository
public interface TopicRepository extends CrudRepository<Topic, Integer> {
	@Query("SELECT t FROM Topic t")
	Page<Topic> findAllByPage(Pageable pageable);
}
