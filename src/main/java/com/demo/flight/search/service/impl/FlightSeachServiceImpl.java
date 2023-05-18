package com.demo.flight.search.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.demo.flight.search.common.util.CommonUtil;
import com.demo.flight.search.constants.SortOptionEnum;
import com.demo.flight.search.entity.FlightDetails;
import com.demo.flight.search.exception.FlightDetailsNotFoundException;
import com.demo.flight.search.model.Flight;
import com.demo.flight.search.repository.FlightSearchRepository;
import com.demo.flight.search.service.FlightSeachService;

import lombok.extern.slf4j.Slf4j;

/**
 * Service class to fetch flight details from repository. FlightSeachServiceImpl
 *
 */
@Service
@Slf4j
public class FlightSeachServiceImpl implements FlightSeachService {

	@Autowired
	FlightSearchRepository flightsearchRepo;

	/**
	 * Get Service to fetch flight details from repository.
	 * 
	 * @return list of Flight
	 */
	@Override
	public List<Flight> getFlights(String origin, String destination, Optional<SortOptionEnum> priceSort,
			Optional<SortOptionEnum> durationSort) {
		List<FlightDetails> flightDetails = null;
		List<Flight> flightList = null;
		Boolean isDuration = false;
		boolean isPrice = false;

		if (!origin.isBlank() && !destination.isBlank()) {
			log.info("In getFlightlist. Origin:{} ,Destination: {}", origin, destination);
			flightDetails = flightsearchRepo.findAllByOriginAndDestination(origin, destination);
			if (CollectionUtils.isEmpty(flightDetails)) {
				log.error("In getFlightlist method. Record Not Found for origin:{} & destination: {}", origin,
						destination);
				throw new FlightDetailsNotFoundException(
						"Record Not Found for origin: " + origin + " & destination:" + destination);
			} else {
				flightList = CommonUtil.mapFlightEntityToModel(flightDetails);
			}
		} else {
			throw new FlightDetailsNotFoundException(
					"Record Not Found for origin: " + origin + " & destination:" + destination);
		}
		isDuration = durationSort.isPresent() && !CollectionUtils.isEmpty(flightList);
		isPrice = priceSort.isPresent() && !CollectionUtils.isEmpty(flightList);
		if (Boolean.TRUE.equals(isPrice) && Boolean.TRUE.equals(isDuration)) {
			flightList = CommonUtil.sortFlightLstByPriceDuration(priceSort.get(), durationSort.get(), flightList);
		} else {
			if (Boolean.TRUE.equals(isDuration)) {
				log.info("In getAllFlightList.Duration Sort in {} order", durationSort);
				flightList = CommonUtil.sortFlightLstByDuration(durationSort.get(), flightList);
			}
			if (Boolean.TRUE.equals(isPrice)) {
				log.info("In getAllFlightList. Price Sort in {} order", priceSort);
				flightList = CommonUtil.sortFlightLstByPrice(priceSort.get(), flightList);
			}
		}

		return flightList;
	}

}
