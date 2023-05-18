package com.demo.flight.search.common.util;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.util.CollectionUtils;

import com.demo.flight.search.constants.SortOptionEnum;
import com.demo.flight.search.entity.FlightDetails;
import com.demo.flight.search.model.Flight;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * CommonUtil : Common utility class
 *
 */
@Slf4j
public class CommonUtil {
	/**
	 * Sort Flight List By Price
	 * 
	 * @param priceSort
	 * @param flightList
	 * @return List of Flight
	 */
	public static List<Flight> sortFlightLstByPrice(SortOptionEnum priceSort, List<Flight> flightList) {
		if (SortOptionEnum.DESC.equals(priceSort)) {
			flightList = flightList.stream().sorted(Comparator.comparingDouble(Flight::getPrice).reversed())
					.collect(Collectors.toList());
		} else if (SortOptionEnum.ASCE.equals(priceSort)) {
			flightList = flightList.stream().sorted(Comparator.comparingDouble(Flight::getPrice))
					.collect(Collectors.toList());
		}
		log.info("In sortFlightLstByPrice :{}", flightList);
		return flightList;
	}

	/**
	 * Sort Flight List By Duration
	 * 
	 * @param durationSort
	 * @param flightLst
	 * @return List of Flight
	 */
	public static List<Flight> sortFlightLstByDuration(SortOptionEnum durationSort, List<Flight> flightLst) {
		if (SortOptionEnum.DESC.equals(durationSort)) {
			flightLst = flightLst.stream().sorted(Comparator.comparing(Flight::getDuration).reversed())
					.collect(Collectors.toList());
		} else if (SortOptionEnum.ASCE.equals(durationSort)) {
			flightLst = flightLst.stream().sorted(Comparator.comparing(Flight::getDuration))
					.collect(Collectors.toList());
		}
		log.info("In sortFlightLstByDuration :{}", flightLst);

		return flightLst;
	}

	/**
	 * Map FlightDetails entity to Flight model class
	 * 
	 * @param flightDetails
	 * @return List of Flight
	 */
	public static List<Flight> mapFlightEntityToModel(List<FlightDetails> flightDetails) {
		List<Flight> flightList = null;
		if (!CollectionUtils.isEmpty(flightDetails)) {
			flightList = flightDetails.stream().filter(Objects::nonNull)
					.collect(
							Collectors.mapping(
									fs -> new Flight(fs.getId(),fs.getFlightNumber(), fs.getOrigin(), fs.getDestination(),
											fs.getDepartureTime(), fs.getArrivalTime(), fs.getPrice()),
									Collectors.toList()));
		}
		return flightList;
	}

	/**
	 * This method will first Sort Flight List By Price then Duration
	 * 
	 * @param priceSort
	 * @param durationSort
	 * @param flightList
	 * @return List of Flight
	 */
	public static List<Flight> sortFlightLstByPriceDuration(SortOptionEnum priceSort, SortOptionEnum durationSort,
			List<Flight> flightList) {
		if (SortOptionEnum.DESC.equals(priceSort)) {
			if (SortOptionEnum.DESC.equals(durationSort)) {
				flightList = flightList.stream()
						.sorted(Comparator.comparingDouble(Flight::getPrice)
								.thenComparing(Comparator.comparing(Flight::getDuration).reversed()))
						.collect(Collectors.toList());
			} else if (SortOptionEnum.ASCE.equals(durationSort)) {
				flightList = flightList.stream().sorted(Comparator.comparingDouble(Flight::getPrice).reversed()
						.thenComparing(Comparator.comparing(Flight::getDuration))).collect(Collectors.toList());
			}
		}
		if (SortOptionEnum.ASCE.equals(priceSort)) {
			if (SortOptionEnum.DESC.equals(durationSort)) {
				flightList = flightList.stream()
						.sorted(Comparator.comparingDouble(Flight::getPrice)
								.thenComparing(Comparator.comparing(Flight::getDuration).reversed()))
						.collect(Collectors.toList());
			} else if (SortOptionEnum.ASCE.equals(durationSort)) {
				flightList = flightList.stream().sorted(Comparator.comparingDouble(Flight::getPrice)
						.thenComparing(Comparator.comparing(Flight::getDuration))).collect(Collectors.toList());
			}
		}
		log.info("In sortFlightLstByPriceDuration :{}", flightList);
		return flightList;
	}
}
