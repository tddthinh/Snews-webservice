package com.snews.webservice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.snews.webservice.entities.Account;
import com.snews.webservice.entities.Attendee;

@Repository
public interface AttendeeRepository extends CrudRepository<Attendee, Integer> {
	
	@Query("select a from Attendee a where a.ownId = :ownId")
	Page<Account> findAllByOwnId(Pageable pageable, @Param("ownId") int ownId);

	@Query("select a from Event e join e.attendees a where e.id = :evtId and a.ownId = :ownId and a.aCode = :code")
	Attendee findByCodeAndEventIdAndOwnId(@Param("code") String code, @Param("evtId") int evtId, @Param("ownId") int ownId);
	
	@Query("select a from Event e join e.attendees a where e.id = :evtId and a.ownId = :ownId and a.aRfid = :rfid")
	Attendee findByRFIDAndEventIdAndOwnId(@Param("rfid") String rfid, @Param("evtId") int evtId, @Param("ownId") int ownId);
}
