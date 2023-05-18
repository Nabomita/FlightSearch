package com.demo.flight.search.service;

import java.util.List;
import java.util.Optional;

import com.demo.flight.search.constants.SortOptionEnum;
import com.demo.flight.search.model.Flight;

public interface FlightSeachService {

	public List<Flight> getFlights(String origin, String destination,
			Optional<SortOptionEnum> priceSort, Optional<SortOptionEnum> durationSort);
}
