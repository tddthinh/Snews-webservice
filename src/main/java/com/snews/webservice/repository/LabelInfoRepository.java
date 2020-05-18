package com.snews.webservice.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.snews.webservice.entities.LabelInfo;

@Repository
public interface LabelInfoRepository extends CrudRepository<LabelInfo, Integer> {
	@Query("select lb from LabelInfo lb where lb.lbContent = :content")
	LabelInfo findAllByContent(@Param("content") String content);
}
