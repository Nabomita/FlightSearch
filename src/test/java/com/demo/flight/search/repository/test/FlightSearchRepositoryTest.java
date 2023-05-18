package com.demo.flight.search.repository.test;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.demo.flight.search.entity.FlightDetails;
import com.demo.flight.search.repository.FlightSearchRepository;

@DataJpaTest
@AutoConfigureTestDatabase
class FlightSearchRepositoryTest {

	@Autowired
	FlightSearchRepository flightSearchRepository;

	@Test
	void findAllByOriginAndDestination() {
		List<FlightDetails> flightDetailList = flightSearchRepository.findAllByOriginAndDestination("BOM", "DEL");
		assertThat(flightDetailList).isNotEmpty()
				.allMatch(f -> "BOM".equals(f.getOrigin()) && "DEL".equals(f.getDestination()));
	}
}
