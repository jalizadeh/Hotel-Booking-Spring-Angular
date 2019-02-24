package com.linkedin.learning.rest;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.linkedin.learning.model.request.ReservationRequest;
import com.linkedin.learning.model.response.ReservationResponse;

@RestController
@RequestMapping(ResourceConstants.ROOM_RESERVATION_V1)
public class ReservationResource {
	
	@RequestMapping(path = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<ReservationResponse> getAvailableRooms(
			@RequestParam("checkin")
			@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
			LocalDate checkin,
			@RequestParam("checkout")
			@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
			LocalDate chekout
			){
		return new ResponseEntity<>(new ReservationResponse(), HttpStatus.OK);
	}
	
	
	 @RequestMapping(path = "", method = RequestMethod.POST, 
			 produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
	         consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	 public ResponseEntity<ReservationResponse> createReservation(
	        @RequestBody
	        ReservationRequest reservationRequest) {

		 return new ResponseEntity<>(new ReservationResponse(), HttpStatus.CREATED);
	 }
	 
	 
	 @RequestMapping(path = "", method = RequestMethod.PUT, 
			 produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
	         consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	 public ResponseEntity<ReservationResponse> updateReservation(
	        @RequestBody
	        ReservationRequest reservationRequest) {

		 return new ResponseEntity<>(new ReservationResponse(), HttpStatus.OK);
	 }
	 
	 
	 @RequestMapping(path = "/{reservationId}",
			 method = RequestMethod.DELETE)
	 public ResponseEntity<Void> deleteReservation(
	        @PathVariable
	        long reservationId) {

		 return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	 }
}
