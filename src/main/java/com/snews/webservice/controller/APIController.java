package com.snews.webservice.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import com.snews.webservice.entities.*;
import com.snews.webservice.files.StorageFileNotFoundException;
import com.snews.webservice.repository.*;
import com.snews.webservice.repository.result.AccountDTO;
import com.snews.webservice.repository.result.EventDTO;
import com.snews.webservice.service.JwtService;
import com.snews.webservice.service.ModelMapperService;
import com.snews.webservice.service.StorageService;
import com.snews.webservice.utilities.AttendeeImportUtil;
import com.snews.webservice.service.AccountService;

@RestController
@RequestMapping(path = "/api")
public class APIController {
	// ########################################Rest##########################################
	@Autowired
	private AccountRepository accountRepository;
	@Autowired
	private AttendeeRepository attendeeRepository;
	@Autowired
	private EventRepository eventRepository;
	@Autowired
	private LabelInfoRepository labelInfoRepository;
	@Autowired
	private PaginationRepository paginationRepository;
	@Autowired
	private RecruitmentRepository recruitmentRepository;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private TopicRepository topicRepository;
	@Autowired
	private AccountService accountService;
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private JwtService jwtService;
	@Autowired
	private ModelMapperService modelMapperService;
	@Autowired
	private org.springframework.security.crypto.password.PasswordEncoder PasswordEncoder;
	// ########################################File##########################################
	private final StorageService storageService;
	@Autowired
	Environment environment;

	@Autowired
	public APIController(StorageService storageService) {
		this.storageService = storageService;
	}

	// #############################_Topic_##################################################
	@GetMapping(path = "/topics")
	public @ResponseBody Iterable<Topic> getAllTopics(@RequestParam(value = "_page") int page,
			@RequestParam(value = "_limit") int limit) {
		return topicRepository.findAllByPage(PageRequest.of(page - 1, limit, new Sort(Sort.Direction.DESC, "id")))
				.getContent();
	}

	@GetMapping(path = "/topics/{id}")
	public @ResponseBody Topic getTopic(@PathVariable int id) {
		return topicRepository.findById(id).get();
	}

	@RequestMapping(path = "/topics", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Topic> addTopic(@RequestBody Topic topic, HttpServletRequest req) {
		Account account = getAccountByCurrentToken(req);
		topic.setAccount(account);
		topic.setTpDate(new Date());
		Topic newTopic = topicRepository.save(topic);
		return ResponseEntity.ok(newTopic);
	}

	@RequestMapping(path = "/topics/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public ResponseEntity<Integer> deleteTopic(@PathVariable int id) {
		topicRepository.deleteById(id);
		return ResponseEntity.ok(id);
	}

	@RequestMapping(path = "/topics/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<Topic> editTopic(@PathVariable int id, @RequestBody Topic topic) {
		if (existsTopicID(id)) {
			topic.setTpId(id);
			Account account = topicRepository.findById(id).get().getAccount();
			topic.setAccount(account);
			Topic newTopic = topicRepository.save(topic);
			return ResponseEntity.ok(newTopic);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	private boolean existsTopicID(int ID) {
		return ID != 0 & topicRepository.existsById(ID);
	}

	// #############################_Recruitment_##################################################
	@GetMapping(path = "/recruitments")
	public @ResponseBody List<Recruitment> getAllRecruitment(@RequestParam(value = "_page") int page,
			@RequestParam(value = "_limit") int limit) {
		return recruitmentRepository.findAllByPage(PageRequest.of(page - 1, limit, new Sort(Sort.Direction.DESC, "id")))
				.getContent();
	}
	@GetMapping(path = "/recruitments/field")
	public @ResponseBody List<String> getAllField() {
		return recruitmentRepository.findAllField();
	}
	@GetMapping(path = "/recruitments/company")
	public @ResponseBody List<String> getAllCompany() {
		return recruitmentRepository.findAllCompany();
	}
	@GetMapping(path = "/recruitments/vacancy")
	public @ResponseBody List<String> getAllVacancy() {
		return recruitmentRepository.findAllVacancy();
	}
	@GetMapping(path = "/recruitments/field/totalup")
	public @ResponseBody List<RecruitmentStatistics> totalUpField(@RequestParam(value = "_start") @DateTimeFormat(pattern="yyyy-MM-dd") Date start,
			@RequestParam(value = "_end") @DateTimeFormat(pattern="yyyy-MM-dd") Date end) {
		return recruitmentRepository.totalUpByField(start, end);
	}
	@GetMapping(path = "/recruitments/{id}")
	public @ResponseBody Recruitment getRecruitment(@PathVariable int id) {
		return recruitmentRepository.findById(id).get();
	}

	@RequestMapping(path = "/recruitments", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Recruitment> addRecruitment(@RequestBody Recruitment recrmt, HttpServletRequest req) {
		Account account = getAccountByCurrentToken(req);
		recrmt.setAccount(account);
		recrmt.setdate(new Date());
		Recruitment newRecrmtc = recruitmentRepository.save(recrmt);
		return ResponseEntity.ok(newRecrmtc);
	}

	@RequestMapping(path = "/recruitments/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public ResponseEntity<Integer> deleteRecruitment(@PathVariable int id) {
		recruitmentRepository.deleteById(id);
		return ResponseEntity.ok(id);
	}

	@RequestMapping(path = "/recruitments/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<Recruitment> editRecruitment(@PathVariable int id, @RequestBody Recruitment recrmt) {
		if (existsRecruitmentID(id)) {
			recrmt.setId(id);
			Account account = recruitmentRepository.findById(id).get().getAccount();
			recrmt.setAccount(account);
			Recruitment newRecrmt = recruitmentRepository.save(recrmt);
			return ResponseEntity.ok(newRecrmt);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	private boolean existsRecruitmentID(int ID) {
		return ID != 0 & recruitmentRepository.existsById(ID);
	}

	// #############################_Account_##################################################

	@RequestMapping(value = "accounts", method = RequestMethod.GET) // Map ONLY GET Requests
	public @ResponseBody List<AccountDTO> getAllAccount(@RequestParam(value = "_page") int page,
			@RequestParam(value = "_limit") int limit) {
		TypeMap<Account, AccountDTO> mapper = modelMapperService.AccountToDTO();
		List<AccountDTO> accList = StreamSupport.stream(
				accountRepository.findAllByPage(PageRequest.of(page - 1, limit, new Sort(Sort.Direction.DESC, "id")))
						.getContent().spliterator(), // 0
				false).map(acc -> mapper.map(acc)).collect(Collectors.toList());
		return accList;
	}

	@GetMapping(path = "/accounts/me") // Map ONLY GET Requests
	public @ResponseBody AccountDTO getAccountMe(HttpServletRequest req) {
		Account acc = getAccountByCurrentToken(req);
		if (acc == null)
			return null;
		AccountDTO dto = modelMapperService.AccountToDTO().map(acc);
		dto.setToken(req.getHeader("authorization"));
		return modelMapperService.AccountToDTO().map(acc);
	}

	@RequestMapping(path = "/accounts", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<AccountDTO> addAccount(@RequestBody AccountDTO dto, HttpServletRequest req) {
		removeErrorAccount(dto);
		validateAccountInfo(dto);
		if (accountRepository.findByUsername(dto.getUsername()) != null) {
			dto.setErrorUsername("tài khoản đã tồn tại");
			dto.setError(true);
		}
		if (accountRepository.findByEmail(dto.getEmail()) != null) {
			dto.setErrorEmail("email đã tồn tại");
			dto.setError(true);
		}
		if (dto.getPassword() == null || dto.getPassword().length() < 6) {
			dto.setErrorPassword("chưa nhập mật khẩu");
			dto.setError(true);
		}

		if (!dto.getError()) {
			Account account = modelMapperService.DTOToAccount().map(dto);
			account.setUPassword(PasswordEncoder.encode(dto.getPassword()));
			Account newAcc = accountRepository.save(account);
			return ResponseEntity.ok(modelMapperService.AccountToDTO().map(newAcc));
		} else {
			return ResponseEntity.ok().body(dto);
		}
	}

	@RequestMapping(path = "/accounts/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public ResponseEntity<Integer> deleteAccount(@PathVariable int id, HttpServletRequest req) {
		Account acc = accountRepository.findById(id).get();
		if (!acc.getRole().getRName().equals("R_ADMIN")) {
			accountRepository.deleteById(id);
			return ResponseEntity.ok(id);
		} else {
			return ResponseEntity.notFound().build();
		}

	}

	@RequestMapping(path = "/accounts/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<AccountDTO> editAccount(@PathVariable int id, @RequestBody AccountDTO dto,
			HttpServletRequest req) {
		Account acc = getAccountByCurrentToken(req);

		if (existsAccountID(id) && acc.getRole().getRId() == 1) {
			removeErrorAccount(dto);
			validateAccountInfo(dto);
			if (!dto.getError()) {
				Account account = accountRepository.findById(id).get();
				account.setUEmail(dto.getEmail());
				if (dto.getPassword() != null && !dto.getPassword().isEmpty())
					account.setUPassword(PasswordEncoder.encode(dto.getPassword()));
				account.setUFullname(dto.getFullname());
				account.setRole(dto.getRole());
				accountRepository.save(account);
				return ResponseEntity.ok(modelMapperService.AccountToDTO().map(account));
			} else {
				return ResponseEntity.ok(dto);
			}
		} else {
			return ResponseEntity.badRequest().build();
		}

	}

	private void removeErrorAccount(AccountDTO dto) {
		dto.setError(false);
		dto.setErrorEmail("");
		dto.setErrorFullname("");
		dto.setErrorPassword("");
		dto.setErrorUsername("");
	}

	private boolean existsAccountID(int ID) {
		return ID != 0 & accountRepository.existsById(ID);
	}

	private void validateAccountInfo(AccountDTO dto) {
		if (dto.getUsername() == null || dto.getUsername().length() < 8) {
			dto.setError(true);
			dto.setErrorUsername("tên tài khoản phải từ 8 ký tự");
		}
		if (dto.getEmail() == null || dto.getEmail().indexOf("@") == -1) {
			dto.setError(true);
			dto.setErrorEmail("email không hợp lệ");
		}
		if (dto.getPassword() != null && !dto.getPassword().isEmpty() && dto.getPassword().length() < 6) {
			dto.setError(true);
			dto.setErrorPassword("mật khẩu phải từ 6 ký tự");
		}
		if (dto.getFullname() == null || dto.getFullname().length() < 8) {
			dto.setError(true);
			dto.setErrorFullname("họ và tên không hợp lệ");
		}
	}

	// #############################_Event_##################################################

	@GetMapping(path = "/events")
	public @ResponseBody List<EventDTO> getAllEvents(@RequestParam(value = "_page") int page,
			@RequestParam(value = "_limit") int limit, HttpServletRequest req) {
		if (getAccountByCurrentToken(req).getRole().getRId() != 1)
			return null;
		TypeMap<Event, EventDTO> mapper = modelMapperService.EventToDTO();
		List<EventDTO> evtList = StreamSupport.stream(
				eventRepository.findAllByPage(PageRequest.of(page - 1, limit, new Sort(Sort.Direction.DESC, "id")))
						.getContent().spliterator(), // 0
				false).map(evt -> mapper.map(evt)).collect(Collectors.toList());
		return evtList;
	}

	@GetMapping(path = "/events/my")
	public @ResponseBody List<EventDTO> getAllMyEvents(@RequestParam(value = "_page") int page,
			@RequestParam(value = "_limit") int limit, HttpServletRequest req) {
		TypeMap<Event, EventDTO> mapper = modelMapperService.EventToDTO();
		Account acc = getAccountByCurrentToken(req);
		List<EventDTO> evtList = StreamSupport.stream(
				eventRepository.findAllByPage(PageRequest.of(page - 1, limit, new Sort(Sort.Direction.DESC, "id")), acc)
						.getContent().spliterator(), // 0
				false).map(evt -> mapper.map(evt)).collect(Collectors.toList());
		return evtList;
	}

	@GetMapping(path = "/events/{id}/attendees")
	public @ResponseBody List<Attendee> getAttendeesInEvent(@PathVariable int id,
			@RequestParam(value = "_page") int page, @RequestParam(value = "_limit") int limit,
			HttpServletRequest req) {
		AccountDTO you = getAccountMe(req);
		Account own = eventRepository.findAccountByEventID(id);
		if (own != null & you != null && you.getId() == own.getUId()) {
			List<Attendee> attList = StreamSupport.stream(eventRepository
					.findAttendeeByEventID(PageRequest.of(page - 1, limit, new Sort(Sort.Direction.DESC, "id")), id)
					.getContent().spliterator(), // 0
					false).collect(Collectors.toList());
			attList.forEach(e -> e.setEventId(id));
			return attList;
		} else {
			return Collections.emptyList();
		}
	}

	@GetMapping(path = "/events/{id}/attendees/search")
	public @ResponseBody List<Attendee> searchAttendeesInEvent(@PathVariable int id,
			@RequestParam(value = "_page") int page, @RequestParam(value = "_limit") int limit,
			@RequestParam(value = "_text") String search, HttpServletRequest req) {
		AccountDTO you = getAccountMe(req);
		Account own = eventRepository.findAccountByEventID(id);
		if (own != null & you != null && you.getId() == own.getUId()) {
			List<Attendee> attList = StreamSupport.stream(
					eventRepository
							.findAttendeeByEventIDAndSearchString(
									PageRequest.of(page - 1, limit, new Sort(Sort.Direction.DESC, "id")), id, search)
							.getContent().spliterator(), // 0
					false).collect(Collectors.toList());
			attList.forEach(e -> e.setEventId(id));
			return attList;
		} else {
			return Collections.emptyList();
		}
	}

	@RequestMapping(value = "/events/{id}/attendees/import", method = RequestMethod.POST)
	public ResponseEntity<Progress> importAttendee(@PathVariable int id, @RequestParam("file") MultipartFile excel,
			HttpServletRequest req) {
		storageService.store(excel);
		String fileName = excel.getOriginalFilename();
		File file = storageService.load(fileName).toFile();
		ArrayList<Attendee> attendees = new ArrayList<>();
		HashMap<Integer, LabelInfo> mapping = new HashMap<>();
		try {
			AttendeeImportUtil.readWorkbook(file, attendees, mapping);
			mapping.forEach((k, v) -> {
				LabelInfo oldLabel = labelInfoRepository.findAllByContent(v.getLbContent());
				if (oldLabel != null) {
					v.setLbId(oldLabel.getLbId());
				} else {
					LabelInfo newLabel = labelInfoRepository.save(v);
					v.setLbId(newLabel.getLbId());
				}
			});
			int total = attendees.size();
			int added = 0;
			for (Attendee a : attendees) {
				a.setEventId(id);
				if (!addAttendee(a, req).getBody().isError())
					added += 1;
			}
			return ResponseEntity.ok().body(new Progress(added, total));
		} catch (EncryptedDocumentException | InvalidFormatException | IOException e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(new Progress(0, 0));
		}

	}

	@GetMapping(path = "/events/{id}/attendees/count")
	public @ResponseBody ResponseEntity<Integer> getAttendeesCount(@PathVariable int id) {

		return ResponseEntity.ok(eventRepository.countAttendeeByEventId(id));
	}

	@GetMapping(path = "/events/{id}/attendees/count/search")
	public @ResponseBody ResponseEntity<Integer> getAttendeesCount(@PathVariable int id,
			@RequestParam(value = "_text") String search) {

		return ResponseEntity.ok(eventRepository.countAttendeeByEventIDAndSearchString(id, search));
	}

	@RequestMapping(path = "/events", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<EventDTO> addEvent(@RequestBody EventDTO dto, HttpServletRequest req) {
		removeErrorEvent(dto);
		validateEventInfo(dto);
		if (!dto.isError()) {
			Event evt = modelMapperService.DTOToEvent().map(dto);
			Account account = getAccountByCurrentToken(req);
			evt.setAccount(account);
			Event newEvt = eventRepository.save(evt);
			return ResponseEntity.ok(modelMapperService.EventToDTO().map(newEvt));
		} else {
			return ResponseEntity.ok().body(dto);
		}
	}

	@RequestMapping(path = "/events/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<EventDTO> editEvent(@PathVariable int id, @RequestBody EventDTO dto, HttpServletRequest req) {
		if (existsEventID(id)) {
			removeErrorEvent(dto);
			validateEventInfo(dto);
			if (!dto.isError()) {
				Event evt = eventRepository.findById(id).get();
				evt.setEDate(dto.getDate());
				evt.setEName(dto.getName());
				evt.setEEnd(dto.getEnd_time());
				evt.setEStart(dto.getStart_time());
				evt.seteLocation(dto.getLocation());
				evt.seteTimeout(dto.getTimeout());
				Event newEvt = eventRepository.save(evt);
				return ResponseEntity.ok(modelMapperService.EventToDTO().map(newEvt));
			} else {
				return ResponseEntity.ok(dto);
			}
		} else {
			return ResponseEntity.badRequest().build();
		}
	}

	@RequestMapping(path = "/events/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public ResponseEntity<Integer> deleteEvent(@PathVariable int id, HttpServletRequest req) {
		if (eventRepository.findById(id).get().getAccount().getUId() == getAccountByCurrentToken(req).getUId()) {
			eventRepository.deleteById(id);
			return ResponseEntity.ok(id);
		} else {
			return ResponseEntity.badRequest().body(0);
		}
	}

	@RequestMapping(path = "/events/{id}/statistics", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<AttendeeStatistics> attendeeStatistics(@PathVariable int id, HttpServletRequest req) {
		Event evt = eventRepository.findById(id).get();
		if (evt != null) {
			AttendeeStatistics as = calculateAttendeeStatistics(evt);
			return ResponseEntity.ok(as);
		}
		return ResponseEntity.badRequest().build();

	}

	private AttendeeStatistics calculateAttendeeStatistics(Event evt) {
		int present = 0, absent = 0, in = 0, out = 0;
		for (Attendee a : evt.getAttendees()) {
			if (a.isIn() && a.isOut())
				present++;
			else if (!a.isIn() && !a.isOut())
				absent++;
			else if (a.isIn() && !a.isOut())
				in++;
			else
				out++;
		}
		return new AttendeeStatistics(present, absent, in, out);
	}

	@RequestMapping(path = "/events/{id}/state", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<EventTimeState> eventState(@PathVariable int id, HttpServletRequest req) {
		Event evt = eventRepository.findById(id).get();
		if (evt != null) {
			EventTimeState state = calculateEventState(evt.getEDate(), evt.getEStart(), evt.getEEnd(),
					evt.geteTimeout());
			return ResponseEntity.ok(state);
		}
		return ResponseEntity.badRequest().build();

	}

	private EventTimeState calculateEventState(Date evtDate, Date start, Date end, int Timeout) {
		Calendar cWithoutTime = Calendar.getInstance();
		cWithoutTime.set(Calendar.HOUR_OF_DAY, 0);
		cWithoutTime.set(Calendar.MINUTE, 0);
		cWithoutTime.set(Calendar.SECOND, 0);
		cWithoutTime.set(Calendar.MILLISECOND, 0);

		Date today = cWithoutTime.getTime();
		EventTimeState state = new EventTimeState();
		state.setCompareTodayDate(evtDate.compareTo(today));
		// < 0 evt before today
		// = 0 evt equals today
		// > 0 evt after today
		Calendar cal = Calendar.getInstance();
		cal.set(1970, 0, 1);
		Long evtStartTime = start.getTime();
		Long evtEndTime = end.getTime() + Timeout * 60 * 1000;
		Long todayTime = cal.getTimeInMillis();
		int i;
		if (evtStartTime > todayTime) {
			i = -1;
		} else if (evtEndTime < todayTime) {
			i = 1;
		} else {
			i = 0;
		}
		state.setCompareTodayTime(i);
		return state;
	}

	@GetMapping(path = "/events/{id}/muster")
	@ResponseBody
	public ResponseEntity<Attendee> eventMuster(@PathVariable int id, @RequestParam(value = "_rifd") String rfid,
			@RequestParam(value = "_state") String state, HttpServletRequest req) {
		int ownId = getAccountByCurrentToken(req).getUId();

		Attendee att = attendeeRepository.findByRFIDAndEventIdAndOwnId(rfid, id, ownId);
		if (att != null) {
			if (state.equals("in")) {
				validateAttendeeIn(att);
			} else {
				validateAttendeeOut(att);
			}
			attendeeRepository.save(att);
			return ResponseEntity.ok(att);
		}
		att = new Attendee();
		att.setError(true);
		att.setErrorRfid("RFID không tìm thấy");
		return ResponseEntity.ok(att);
	}

	private void validateAttendeeOut(Attendee att) {
		if (att.isOut()) {
			att = new Attendee();
			att.setError(true);
			att.setErrorRfid("RFID đã điểm danh vào");
		} else {
			att.setOut(true);
		}
	}

	private void validateAttendeeIn(Attendee att) {
		if (att.isIn()) {
			att = new Attendee();
			att.setError(true);
			att.setErrorRfid("RFID đã điểm danh ra");
		} else {
			att.setIn(true);
		}
	}

	private boolean existsEventID(int ID) {
		return ID != 0 & eventRepository.existsById(ID);
	}

	private void removeErrorEvent(EventDTO dto) {
		dto.setError(false);
		dto.setErrorDate("");
		dto.setErrorName("");
		dto.setErrorTime("");
	}

	private void validateEventInfo(EventDTO dto) {
		if (dto.getEnd_time().compareTo(dto.getStart_time()) <= 0) {
			dto.setError(true);
			dto.setErrorTime("giờ kết thúc phải sau giờ bắt đầu");
		}
		Calendar cWithoutTime = Calendar.getInstance();
		cWithoutTime.set(Calendar.HOUR_OF_DAY, 0);
		cWithoutTime.set(Calendar.MINUTE, 0);
		cWithoutTime.set(Calendar.SECOND, 0);
		cWithoutTime.set(Calendar.MILLISECOND, 0);

		Date today = cWithoutTime.getTime();
		if (dto.getDate().compareTo(today) < 0) {
			dto.setError(true);
			dto.setErrorDate("ngày không hợp lệ");

		}
		if (dto.getName() == null || dto.getName().isEmpty()) {
			dto.setError(true);
			dto.setErrorName("chưa nhập tên sự kiện");
		}
	}

	// #############################_Attendee_##################################################
	@GetMapping(path = "/attendees")
	public @ResponseBody Iterable<Attendee> getAllAttendees() {
		return attendeeRepository.findAll();
	}

	@RequestMapping(path = "/attendees", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Attendee> addAttendee(@RequestBody Attendee dto, HttpServletRequest req) {
		removeErrorAttendee(dto);
		Account acc = getAccountByCurrentToken(req);
		validateAttendeeInfo(dto);
		validateAttendeeCodeExists(dto, acc);
		validateAttendeeRFIDExists(dto, acc);
		if (!dto.isError()) {
			int ownId = acc.getUId();
			dto.setOwnId(ownId);

			Attendee newAtt = attendeeRepository.save(dto);

			int evtId = dto.getEventId();
			Event evt = eventRepository.findById(evtId).get();
			evt.getAttendees().add(newAtt);
			eventRepository.save(evt);
			return ResponseEntity.ok(newAtt);
		} else {
			return ResponseEntity.ok(dto);
		}
	}

	@RequestMapping(path = "/attendees/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public ResponseEntity<Integer> deleteAttendee(@PathVariable int id, HttpServletRequest req) {
		Attendee att = attendeeRepository.findById(id).get();
		Account acc = getAccountByCurrentToken(req);
		if (att.getOwnId() == acc.getUId()) {
			attendeeRepository.deleteById(id);
			return ResponseEntity.ok(id);
		} else {
			return ResponseEntity.badRequest().build();
		}
	}

	@RequestMapping(path = "/attendees/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<Attendee> editAttendee(@PathVariable int id, @RequestBody Attendee dto,
			HttpServletRequest req) {
		if (existsAttendeeID(id)) {
			dto.setAId(id);
			removeErrorAttendee(dto);
			Account acc = getAccountByCurrentToken(req);
			validateAttendeeInfo(dto);
			validateAttendeeCodeExists(dto, acc);
			validateAttendeeRFIDExists(dto, acc);
			if (!dto.isError()) {
				Attendee att = attendeeRepository.findById(id).get();
				att.setACode(dto.getACode());
				att.setAEmail(dto.getAEmail());
				att.setAFullname(dto.getAFullname());
				att.setARfid(dto.getARfid());
				att.setLabelInfos(dto.getLabelInfos());
				Attendee newAtt = attendeeRepository.save(att);
				return ResponseEntity.ok(newAtt);
			} else {
				return ResponseEntity.ok(dto);
			}
		} else {
			return ResponseEntity.badRequest().build();
		}
	}

	private void removeErrorAttendee(Attendee dto) {
		dto.setError(false);
		dto.setErrorCode("");
		dto.setErrorEmail("");
		dto.setErrorFullname("");
		dto.setErrorRfid("");
	}

	private boolean existsAttendeeID(int ID) {
		return ID != 0 & attendeeRepository.existsById(ID);
	}

	private void validateAttendeeInfo(Attendee dto) {
		if (dto.getACode() == null || dto.getACode().isEmpty()) {
			dto.setError(true);
			dto.setErrorCode("chưa nhập mã số");
		}
		if (dto.getAEmail() != null && dto.getAEmail().indexOf("@") == -1) {
			dto.setError(true);
			dto.setErrorEmail("email không hợp lệ");
		}
		if (dto.getAFullname() == null || dto.getAFullname().length() < 8) {
			dto.setError(true);
			dto.setErrorFullname("họ và tên không hợp lệ");
		}
		if (dto.getARfid() == null || dto.getARfid().isEmpty()) {
			dto.setError(true);
			dto.setErrorRfid("chưa nhập mã RFID");
		}
	}

	private void validateAttendeeCodeExists(Attendee dto, Account acc) {
		String code = dto.getACode();
		int evtId = dto.getEventId();
		int ownId = acc.getUId();
		Attendee attCheck = attendeeRepository.findByCodeAndEventIdAndOwnId(code, evtId, ownId);
		if (attCheck != null && attCheck.getAId() != dto.getAId()) {
			dto.setExists(true);
			dto.setError(true);
			dto.setErrorCode("mã số đã tồn tại");
		}
	}

	private void validateAttendeeRFIDExists(Attendee dto, Account acc) {
		String rfid = dto.getARfid();
		int evtId = dto.getEventId();
		int ownId = acc.getUId();
		Attendee attCheck = attendeeRepository.findByRFIDAndEventIdAndOwnId(rfid, evtId, ownId);
		if (attCheck != null && attCheck.getAId() != dto.getAId()) {
			dto.setError(true);
			dto.setErrorRfid("mã RFID đã tồn tại");
		}
	}

	// #############################_Label_##################################################
	@GetMapping(path = "/labels")
	public @ResponseBody Iterable<LabelInfo> getAllLabels() {
		return labelInfoRepository.findAll();
	}

	// #############################_Pagination_##################################################
	@GetMapping(path = "/pagination")
	public @ResponseBody Pagination getPagination(@RequestParam(value = "_id", required = true) String id) {
		return paginationRepository.findById(id);
	}

	// #############################_Role_##################################################
	@GetMapping(path = "/roles")
	public @ResponseBody Iterable<Role> getAllRoles() {
		return roleRepository.findAll();
	}

	// #############################_Login_##################################################
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public AccountDTO login(@RequestBody Account user) {

		final Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		final String token = jwtService.generateTokenLogin(user.getUsername());

		Account acc = accountRepository.findByUsername(user.getUsername());
		accountService.store(token, acc);
		AccountDTO accDTO = modelMapperService.AccountToDTO().map(acc);
		accDTO.setToken(token);

		return accDTO;
	}

	@GetMapping(path = "/logout")
	public ResponseEntity<Integer> logout(HttpServletRequest req) {
		String token = req.getHeader("authorization");
		accountService.remove(token);
		return ResponseEntity.ok(0);

	}

	private Account getAccountByCurrentToken(HttpServletRequest req) {
		String token = req.getHeader("authorization");
		return accountService.loadUserByToken(token);
	}

	// #######################################################################################

	@RequestMapping(value = "/files", method = RequestMethod.GET)
	public List<Object> listUploadedFiles(Model model) throws IOException {

		return storageService.loadAll()
				.map(path -> MvcUriComponentsBuilder
						.fromMethodName(FileUploadController.class, "serveFile", path.getFileName().toString()).build()
						.toString())
				.collect(Collectors.toList());
	}

	@RequestMapping(value = "/files/{filename:.+}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Resource> serveFile(@PathVariable String filename) {

		Resource file = storageService.loadAsResource(filename);
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
				.body(file);
	}

	@RequestMapping(value = "/files", method = RequestMethod.POST)
	public Link handleFileUpload(@RequestParam("file") MultipartFile file) {

		storageService.store(file);
		String fileName = file.getOriginalFilename();
		String path = MvcUriComponentsBuilder.fromMethodName(APIController.class, "serveFile",
				storageService.load(fileName).getFileName().toString()).build().toString();
		return new Link(path);
	}

	@ExceptionHandler(StorageFileNotFoundException.class)
	public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
		return ResponseEntity.notFound().build();
	}
}
