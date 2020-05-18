package com.snews.webservice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.snews.webservice.entities.Account;
import com.snews.webservice.entities.Attendee;
import com.snews.webservice.entities.Event;
@Repository
public interface EventRepository extends CrudRepository<Event, Integer> {
	@Query("SELECT e FROM Event e")
	Page<Event> findAllByPage(Pageable pageable);
	
	@Query("SELECT e FROM Event e where e.account = :a")
	Page<Event> findAllByPage(Pageable pageable, @Param("a") Account a);
	
	@Query("select count(a) from Event e join e.attendees a where e.id = :id")
	Integer countAttendeeByEventId(@Param("id") Integer id);
	
	
	@Query("SELECT e.account FROM Event e where e.id = :id")
	Account findAccountByEventID(@Param("id") int id);
	
	@Query("SELECT e.attendees FROM Event e where e.id = :id")
	Page<Attendee> findAttendeeByEventID(Pageable pageable, @Param("id") int id);
	
	@Query("select a from Event e join e.attendees a where e.id = :id and a.searchString LIKE '%' || :str || '%' ")
	Page<Attendee> findAttendeeByEventIDAndSearchString(Pageable pageable, @Param("id") int id, @Param("str") String search);
	
	@Query("select count(a) from Event e join e.attendees a where e.id = :id and a.searchString LIKE '%' || :str || '%' ")
	Integer countAttendeeByEventIDAndSearchString(@Param("id") int id, @Param("str") String search);
	
}
