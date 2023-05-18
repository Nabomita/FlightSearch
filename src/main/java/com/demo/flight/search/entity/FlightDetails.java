package com.demo.flight.search.entity;

import java.time.LocalTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Flight")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FlightDetails {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column(name = "flight_number")
	private String flightNumber;
	@Column(name = "origin")
	private String origin;
	@Column(name = "destination")
	private String destination;
	@Column(name = "departure_time", columnDefinition = "TIME")
	private LocalTime departureTime;
	@Column(name = "arrival_time", columnDefinition = "TIME")
	private LocalTime arrivalTime;
	@Column(name = "price")
	private double price;
}
