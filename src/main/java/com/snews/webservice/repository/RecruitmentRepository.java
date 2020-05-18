package com.snews.webservice.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.snews.webservice.entities.Recruitment;
import com.snews.webservice.entities.RecruitmentStatistics;

@Repository
public interface RecruitmentRepository extends CrudRepository<Recruitment, Integer> {
	@Query("SELECT t FROM Recruitment t")
	Page<Recruitment> findAllByPage(Pageable pageable);
	
	@Query("SELECT t.field FROM Recruitment t")
	List<String> findAllField();
	
	
	@Query("SELECT t.company FROM Recruitment t")
	List<String> findAllCompany();
	
	@Query("SELECT t.vacancy FROM Recruitment t")
	List<String> findAllVacancy();
	
	@Query("SELECT NEW com.snews.webservice.entities.RecruitmentStatistics(field, sum(quanity)) FROM Recruitment WHERE date BETWEEN :startDate AND :endDate group by field")
	List<RecruitmentStatistics> totalUpByField(@Param("startDate") Date start,@Param("endDate") Date end);
	
}
