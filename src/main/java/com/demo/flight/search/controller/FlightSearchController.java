package com.demo.flight.search.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.constraints.NotEmpty;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.demo.flight.search.constants.SortOptionEnum;
import com.demo.flight.search.model.Flight;
import com.demo.flight.search.service.FlightSeachService;

import io.swagger.annotations.ApiOperation;

/**
 * Controller to fetch flight details for Origin - Destination
 * FlightSearchController
 *
 */
@RestController
@Validated
public class FlightSearchController {
	private static final Logger logger = LoggerFactory.getLogger(FlightSearchController.class);

	@Autowired
	FlightSeachService flightSeachService;

	/**
	 * Get Api to fetch flight details
	 * 
	 * @param origin       : required
	 * @param destination  : required
	 * @param priceSort    : Optional field, to sort flight details by price ASCE /
	 *                     DESC order depending on input
	 * @param durationSort : : Optional field, to sort flight details by duration
	 *                     between origin and destination in ASCE / DESC order
	 *                     depending on input
	 * @return list of Flights
	 */
	@ApiOperation(value = "Get Api to fetch flight details from Origin - Destination."
			+ " \n User can supply extra parameters in the request to sort results based on price, duration.")
	@GetMapping(value = "/flights")
	@ResponseStatus(value = HttpStatus.OK)
	public List<Flight> flights(@RequestParam(required = true) @NotEmpty String origin,
			@RequestParam(required = true) @NotEmpty String destination,
			@RequestParam(required = false) Optional<SortOptionEnum> priceSort,
			@RequestParam(required = false) Optional<SortOptionEnum> durationSort) {
		logger.info("In FlightSearchController.getAllFlightDetails for origin:{} , destination {}", origin,
				destination);
		List<Flight> flightList = flightSeachService.getFlights(origin, destination, priceSort, durationSort);
		return flightList;

	}
}
