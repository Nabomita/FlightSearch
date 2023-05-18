package com.demo.flight.search.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.demo.flight.search.entity.FlightDetails;

@Repository
public interface FlightSearchRepository extends CrudRepository<FlightDetails, Long> {

	List<FlightDetails> findAllByOriginAndDestination(String origin, String destination);

}
