package com.snews.webservice.service;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Service;

import com.snews.webservice.entities.Account;
import com.snews.webservice.entities.Event;
import com.snews.webservice.repository.result.AccountDTO;
import com.snews.webservice.repository.result.EventDTO;

@Service
public class ModelMapperService {
	private ModelMapper mp = new ModelMapper();
	private TypeMap<Event, EventDTO> EventToDTO;
	private TypeMap<EventDTO, Event> DTOToEvent;
	private TypeMap<Account, AccountDTO>  AccToDTO;
	private TypeMap<AccountDTO, Account>  DTOToAcc;
	
	public ModelMapperService() {
		EventToDTO = mp.createTypeMap(Event.class, EventDTO.class)
				.addMapping(Event::getEId, EventDTO::setId)
				.addMapping(Event::getEName, EventDTO::setName)
				.addMapping(Event::getEDate, EventDTO::setDate)
				.addMapping(Event::getEStart, EventDTO::setStart_time)
				.addMapping(Event::getEEnd, EventDTO::setEnd_time)
				.addMapping(Event::geteLocation, EventDTO::setLocation)
				.addMapping(Event::geteTimeout, EventDTO::setTimeout)
				.addMapping(Event -> Event.getAccount().getUId(), EventDTO::setAccountId); //deep mappings
		DTOToEvent = mp.createTypeMap(EventDTO.class, Event.class)
				.addMapping(EventDTO::getId, Event::setEId)
				.addMapping(EventDTO::getName, Event::setEName)
				.addMapping(EventDTO::getDate, Event::setEDate)
				.addMapping(EventDTO::getStart_time, Event::setEStart)
				.addMapping(EventDTO::getEnd_time, Event::setEEnd)
				.addMapping(EventDTO::getLocation, Event::seteLocation)
				.addMapping(EventDTO::getTimeout, Event::seteTimeout);
		
		AccToDTO = mp.createTypeMap(Account.class, AccountDTO.class)
				.addMapping(Account::getUId, AccountDTO::setId)
				.addMapping(Account::getUUsername, AccountDTO::setUsername)
				.addMapping(Account::getUEmail, AccountDTO::setEmail)
				.addMapping(Account::getUFullname, AccountDTO::setFullname)
				.addMapping(Account::getUPassword, AccountDTO::setPassword)
				.addMapping(Account::getRole, AccountDTO::setRole);
		DTOToAcc = mp.createTypeMap(AccountDTO.class, Account.class)
				.addMapping(AccountDTO::getId, Account::setUId)
				.addMapping(AccountDTO::getUsername, Account::setUUsername)
				.addMapping(AccountDTO::getEmail, Account::setUEmail)
				.addMapping(AccountDTO::getFullname, Account::setUFullname)
				.addMapping(AccountDTO::getPassword, Account::setUPassword)
				.addMapping(AccountDTO::getRole, Account::setRole);
	}

	public TypeMap<Event, EventDTO> EventToDTO() {
		return EventToDTO;
	}
	public TypeMap<EventDTO, Event> DTOToEvent() {
		return DTOToEvent;
	}
	public TypeMap<Account, AccountDTO> AccountToDTO() {
		return AccToDTO;
	}
	public TypeMap<AccountDTO, Account> DTOToAccount() {
		return DTOToAcc;
	}
	
}
