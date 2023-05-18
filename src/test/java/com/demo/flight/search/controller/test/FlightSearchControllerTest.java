package com.demo.flight.search.controller.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.bind.MissingServletRequestParameterException;

import com.demo.flight.search.constants.SortOptionEnum;
import com.demo.flight.search.exception.FlightDetailsNotFoundException;

@SpringBootTest
@AutoConfigureMockMvc
class FlightSearchControllerTest {
	@Autowired
	private MockMvc mockMvc;

	@Test
	void should_return_flight_list_with_origin_and_destination() throws Exception {
		// when - action
		ResultActions response = mockMvc
				.perform(MockMvcRequestBuilders.get("/flights").param("origin", "BOM").param("destination", "DEL"));

		// then - verify the output
		response.andExpect(MockMvcResultMatchers.status().isOk());
		response.andDo(print()).andExpect(status().isOk()).andExpect(content().contentType("application/json"))
				.andExpect(jsonPath("$[0].flightNumber").exists()).andExpect(jsonPath("$[1].origin").value("BOM"))
				.andExpect(jsonPath("$[2].destination").value("DEL"));
	}

	@Test
	void testFlightListSortByPriceASC() throws Exception {
		// when - action
		ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/flights").param("origin", "BOM")
				.param("destination", "DEL").param("priceSort", SortOptionEnum.ASCE.toString()));

		// then - verify the output
		response.andExpect(MockMvcResultMatchers.status().isOk());
		response.andDo(print()).andExpect(status().isOk()).andExpect(content().contentType("application/json"))
				.andExpect(jsonPath("$.*").exists());
	}

	@Test
	void testFlightListSortByPriceDES() throws Exception {
		// when - action
		ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/flights").param("origin", "BOM")
				.param("destination", "DEL").param("priceSort", "DESC"));

		// then - verify the output
		response.andExpect(MockMvcResultMatchers.status().isOk());
		response.andDo(print()).andExpect(status().isOk()).andExpect(content().contentType("application/json"))
				.andExpect(jsonPath("$.*").exists());
	}

	@Test
	void testFlightListSortByDurationASC() throws Exception {
		// when - action
		ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/flights").param("origin", "BOM")
				.param("destination", "DEL").param("durationSort", "ASCE"));

		// then - verify the output
		response.andExpect(MockMvcResultMatchers.status().isOk());
		response.andDo(print()).andExpect(status().isOk()).andExpect(content().contentType("application/json"))
				.andExpect(jsonPath("$.*").exists());
	}

	@Test
	void testFlightListSortByDurationDES() throws Exception {
		// when - action
		ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/flights").param("origin", "BOM")
				.param("destination", "DEL").param("durationSort", "DESC"));

		// then - verify the output
		response.andExpect(MockMvcResultMatchers.status().isOk());
		response.andDo(print()).andExpect(status().isOk()).andExpect(content().contentType("application/json"))
				.andExpect(jsonPath("$.*").exists());
	}

	@Test
	void testFlightListMissingRequiredDestinationParam() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/flights").param("origin", "BOM"))
				.andExpect(MockMvcResultMatchers.status().isBadRequest())
				.andExpect(result -> assertTrue(
						result.getResolvedException() instanceof MissingServletRequestParameterException))
				.andExpect(result -> assertEquals(
						"Required request parameter 'destination' for method parameter type String is not present",
						result.getResolvedException().getMessage()));

	}

	@Test
	void testFlightListEmptyRequiredDestinationParam() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/flights").param("origin", "BOM").param("destination", ""))
				.andExpect(MockMvcResultMatchers.status().isBadRequest())
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof ConstraintViolationException))
				.andExpect(result -> assertEquals("flights.destination: must not be empty",
						result.getResolvedException().getMessage()));

	}

	@Test
	void testFlightListNotFoundForOrigin_Destination() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/flights").param("origin", "KOL").param("destination", "BOM"))
				.andExpect(MockMvcResultMatchers.status().isNotFound())
				.andExpect(
						result -> assertTrue(result.getResolvedException() instanceof FlightDetailsNotFoundException))
				.andExpect(result -> assertEquals("Record Not Found for origin: KOL & destination:BOM",
						result.getResolvedException().getMessage()));

	}

}
