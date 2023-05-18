package com.demo.flight.search.service.test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.demo.flight.search.constants.SortOptionEnum;
import com.demo.flight.search.entity.FlightDetails;
import com.demo.flight.search.exception.FlightDetailsNotFoundException;
import com.demo.flight.search.model.Flight;
import com.demo.flight.search.repository.FlightSearchRepository;
import com.demo.flight.search.service.impl.FlightSeachServiceImpl;

@ExtendWith(MockitoExtension.class)
class FlightSeachServiceTest {
	@Mock
	FlightSearchRepository flightSearchRepo;
	@InjectMocks
	FlightSeachServiceImpl flightSearchService;

	@DisplayName("JUnit test to fetch flight details by origin and destination")
	@Test
	void testFlightListByOriginAndDestination() {
		List<FlightDetails> flightDetailList = this.buildTestFlightDetails();
		flightDetailList = flightDetailList.stream()
				.filter(f -> "BOM".equals(f.getOrigin()) && "DEL".equals(f.getDestination()))
				.collect(Collectors.toList());
		when(flightSearchRepo.findAllByOriginAndDestination("BOM", "DEL")).thenReturn(flightDetailList);
		List<Flight> flightList = this.flightSearchService.getFlights("BOM", "DEL", Optional.empty(), Optional.empty());
		assertThat(flightList).isNotEmpty()
				.allMatch(f -> "BOM".equals(f.getOrigin()) && "DEL".equals(f.getDestination()));
		verify(this.flightSearchRepo).findAllByOriginAndDestination("BOM", "DEL");
	}

	@DisplayName("JUnit test to Sort by price ASCE")
	@Test
	void testFlightListSortByPriceASCE() {
		List<FlightDetails> flightDetailList = this.buildTestFlightDetails();
		flightDetailList = flightDetailList.stream()
				.filter(f -> "DEL".equals(f.getOrigin()) && "BOM".equals(f.getDestination()))
				.collect(Collectors.toList());
		when(flightSearchRepo.findAllByOriginAndDestination("DEL", "BOM")).thenReturn(flightDetailList);
		List<Flight> flightList = this.flightSearchService.getFlights("DEL", "BOM", Optional.of(SortOptionEnum.ASCE),
				Optional.empty());
		assertThat(flightList).isSortedAccordingTo(Comparator.comparing(Flight::getPrice));
		verify(this.flightSearchRepo).findAllByOriginAndDestination("DEL", "BOM");
	}

	@DisplayName("JUnit test to Sort by price DESC")
	@Test
	void testFlightListSortByPriceDESC() {
		List<FlightDetails> flightDetailList = this.buildTestFlightDetails();
		flightDetailList = flightDetailList.stream()
				.filter(f -> "DEL".equals(f.getOrigin()) && "BOM".equals(f.getDestination()))
				.collect(Collectors.toList());
		when(flightSearchRepo.findAllByOriginAndDestination("DEL", "BOM")).thenReturn(flightDetailList);
		List<Flight> flightList = this.flightSearchService.getFlights("DEL", "BOM", Optional.of(SortOptionEnum.DESC),
				Optional.empty());
		assertThat(flightList).isSortedAccordingTo(Comparator.comparing(Flight::getPrice).reversed());
		verify(this.flightSearchRepo).findAllByOriginAndDestination("DEL", "BOM");
	}

	@DisplayName("JUnit test to Sort by Duration DESC")
	@Test
	void testFlightListSortByDurationDESC() {
		List<FlightDetails> flightDetailList = this.buildTestFlightDetails();
		flightDetailList = flightDetailList.stream()
				.filter(f -> "DEL".equals(f.getOrigin()) && "BOM".equals(f.getDestination()))
				.collect(Collectors.toList());
		when(flightSearchRepo.findAllByOriginAndDestination("DEL", "BOM")).thenReturn(flightDetailList);
		List<Flight> flightList = this.flightSearchService.getFlights("DEL", "BOM", Optional.empty(),
				Optional.of(SortOptionEnum.DESC));
		assertThat(flightList).isSortedAccordingTo(Comparator.comparing(Flight::getDuration).reversed());
		verify(this.flightSearchRepo).findAllByOriginAndDestination("DEL", "BOM");
	}

	@DisplayName("JUnit test to Sort by Duration ASCE")
	@Test
	void testFlightListSortByDurationASCE() {
		List<FlightDetails> flightDetailList = this.buildTestFlightDetails();
		flightDetailList = flightDetailList.stream()
				.filter(f -> "DEL".equals(f.getOrigin()) && "BOM".equals(f.getDestination()))
				.collect(Collectors.toList());
		when(flightSearchRepo.findAllByOriginAndDestination("DEL", "BOM")).thenReturn(flightDetailList);
		List<Flight> flightList = this.flightSearchService.getFlights("DEL", "BOM", Optional.empty(),
				Optional.of(SortOptionEnum.ASCE));
		assertThat(flightList).isSortedAccordingTo(Comparator.comparing(Flight::getDuration));
		verify(this.flightSearchRepo).findAllByOriginAndDestination("DEL", "BOM");
	}

	@DisplayName("JUnit test for Record not found exception for given inputs" )
	@Test
	void testFlightListNotFound() {
		List<FlightDetails> flightDetailList = this.buildTestFlightDetails();
		flightDetailList = flightDetailList.stream()
				.filter(f -> "KOL".equals(f.getOrigin()) && "BOM".equals(f.getDestination()))
				.collect(Collectors.toList());
		when(flightSearchRepo.findAllByOriginAndDestination("KOL", "BOM")).thenReturn(flightDetailList);
		assertThrows(FlightDetailsNotFoundException.class,()->
				this.flightSearchService.getFlights("KOL", "BOM", Optional.empty(), Optional.of(SortOptionEnum.ASCE)));
		verify(this.flightSearchRepo).findAllByOriginAndDestination("KOL", "BOM");
	}
	
	
	private List<FlightDetails> buildTestFlightDetails() {
		List<FlightDetails> flightList = new ArrayList<FlightDetails>();
		FlightDetails flightDetails = new FlightDetails();
		flightDetails.setId(1);
		flightDetails.setFlightNumber("GA01");
		flightDetails.setOrigin("BOM");
		flightDetails.setDestination("DEL");
		flightDetails.setArrivalTime(LocalTime.parse("20:30"));
		flightDetails.setDepartureTime(LocalTime.parse("21:30"));
		flightDetails.setPrice(80.0);
		flightList.add(flightDetails);
		flightDetails = new FlightDetails();
		flightDetails.setId(1);
		flightDetails.setFlightNumber("GB02");
		flightDetails.setOrigin("DEL");
		flightDetails.setDestination("BOM");
		flightDetails.setArrivalTime(LocalTime.parse("22:30"));
		flightDetails.setDepartureTime(LocalTime.parse("03:30"));
		flightDetails.setPrice(100.0);
		flightList.add(flightDetails);
		flightDetails = new FlightDetails();
		flightDetails.setId(1);
		flightDetails.setFlightNumber("GC01");
		flightDetails.setOrigin("DEL");
		flightDetails.setDestination("BOM");
		flightDetails.setArrivalTime(LocalTime.parse("21:30"));
		flightDetails.setDepartureTime(LocalTime.parse("03:30"));
		flightDetails.setPrice(100.0);
		flightList.add(flightDetails);
		return flightList;
	}
}
