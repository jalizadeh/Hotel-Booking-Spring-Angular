package com.linkedin.learning.rest;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.linkedin.learning.converter.REtRRRConverter;
import com.linkedin.learning.entity.ReservationEntity;
import com.linkedin.learning.entity.RoomEntity;
import com.linkedin.learning.model.request.ReservationRequest;
import com.linkedin.learning.model.response.ReservableRoomResponse;
import com.linkedin.learning.model.response.ReservationResponse;
import com.linkedin.learning.repository.PageableRoomRepository;
import com.linkedin.learning.repository.ReservationRepository;
import com.linkedin.learning.repository.RoomRepository;

@RestController
@RequestMapping(ResourceConstants.ROOM_RESERVATION_V1)
@CrossOrigin
public class ReservationResource {
	
	@Autowired
	RoomRepository roomRepository;
	
	@Autowired
	PageableRoomRepository pageableRoomRepository;
	
	@Autowired
	REtRRRConverter REtResrvRespConverter;
	
	@Autowired
	ReservationRepository reservationRepository;
	
	//gives access to conversion bean
	@Autowired
	ConversionService conversionService;
	
	@RequestMapping(path = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public Page<ReservableRoomResponse> getAvailableRooms(
			@RequestParam(value="checkin", required = true)
			@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
			LocalDate checkin,
			@RequestParam("checkout")
			@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
			LocalDate chekout,
			Pageable pageable
			){
		
		Page<RoomEntity> roomEntityList = pageableRoomRepository.findAll(pageable);
		
		return roomEntityList.map(REtResrvRespConverter::convert);
	}
	
	@RequestMapping(path = "/{roomId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<RoomEntity> getRoomById(
			@PathVariable
			Long roomId) {
		RoomEntity roomEntity = roomRepository.findById(roomId).orElse(null);
		//RoomEntity roomEntity = roomRepository.getById(roomId);
		return new ResponseEntity<>(roomEntity, HttpStatus.OK);
	}
	
	
	 @RequestMapping(path = "", method = RequestMethod.POST, 
			 produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
	         consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	 public ResponseEntity<ReservationResponse> createReservation(
	        @RequestBody
	        ReservationRequest reservationRequest) {
		 
		 ReservationEntity reservationEntity = conversionService.convert(reservationRequest, ReservationEntity.class);
		 reservationRepository.save(reservationEntity);
		 
		 RoomEntity roomEntity = roomRepository.findById(reservationRequest.getRoomId()).orElse(null);
		 roomEntity.addReservation(reservationEntity);
		 roomRepository.save(roomEntity);
		 
		 ReservationResponse reservationResponse = conversionService.convert(reservationEntity, ReservationResponse.class);
		 
		 return new ResponseEntity<>(reservationResponse, HttpStatus.CREATED);
	 }
	 
	 
	 @RequestMapping(path = "", method = RequestMethod.PUT, 
			 produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
	         consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	 public ResponseEntity<ReservableRoomResponse> updateReservation(
	        @RequestBody
	        ReservationRequest reservationRequest) {

		 return new ResponseEntity<>(new ReservableRoomResponse(), HttpStatus.OK);
	 }
	 
	 
	 @RequestMapping(path = "/{reservationId}",
			 method = RequestMethod.DELETE)
	 public ResponseEntity<Void> deleteReservation(
	        @PathVariable
	        long reservationId) {

		 return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	 }
}
