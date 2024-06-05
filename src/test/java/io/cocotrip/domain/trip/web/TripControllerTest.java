package io.cocotrip.domain.trip.web;

import static org.mockito.BDDMockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import autoparams.AutoSource;
import autoparams.customization.Customization;
import io.cocotrip.customization.TripRequestCustomization;
import io.cocotrip.domain.trip.application.TripService;
import io.cocotrip.domain.trip.application.dto.PlanRequest;
import io.cocotrip.domain.trip.application.dto.TripRequest;
import io.cocotrip.domain.trip.application.dto.TripResponse;
import io.cocotrip.domain.trip.persistence.Trip;

@WebMvcTest(TripController.class)
class TripControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private TripService tripService;

	@Nested
	@DisplayName("여행 생성")
	class CreateTrip {

		@DisplayName("여행 생성 시, 성공적으로 저장된다.")
		@ParameterizedTest
		@AutoSource
		@WithMockUser("user")
		@Customization(TripRequestCustomization.class)
		void createTrip_success(final TripRequest request) throws Exception {
			// given
			given(tripService.createTrip(any())).willReturn("1L");

			// when
			final var result = mockMvc.perform(
				post("/api/trips")
					.content(objectMapper.writeValueAsString(request))
					.contentType(MediaType.APPLICATION_JSON)
					.with(csrf())
			);

			// then
 			result.andExpect(status().isCreated());
		}
	}

	@Nested
	@DisplayName("여행 일정 설계")
	class PlanDesign {

		@DisplayName("여행 일정 설계 시, 성공적으로 일정이 추가된 여행 정보를 저장한다.")
		@ParameterizedTest
		@AutoSource
		@WithMockUser("user")
		void planDesign_success(final Trip trip, final List<PlanRequest> requests) throws Exception {
			// given
			final var response = new TripResponse(trip);

			given(tripService.planDesign(any(), anyList())).willReturn(response);

			// when
			final var result = mockMvc.perform(
				post("/api/trips/{tripId}", 1L)
					.content(objectMapper.writeValueAsString(requests))
					.contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON)
					.with(csrf())
			);

			// then
			result.andExpect(status().isCreated());
		}
	}

	@Nested
	@DisplayName("여행 일정 조회")
	class GetTrip {

		@DisplayName("모든 여행 조회 시, 성공적으로 여행 정보가 조회된다.")
		@ParameterizedTest
		@AutoSource
		@WithMockUser("user")
		void getAllTrips_success(final List<Trip> trips) throws Exception {
			// given
			given(tripService.getAllTrips()).willReturn(trips);

			// when
			final var result = mockMvc.perform(
				get("/api/trips")
					.contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON)
			);

			// then
			result.andExpect(status().isOk());
		}
	}

	@DisplayName("단건 여행 조회 시, 성공적으로 여행 상세 일정이 조회된다.")
	@ParameterizedTest
	@AutoSource
	@WithMockUser("user")
	void getTrip_success(final Trip trip) throws Exception {
		// given
		final var response = new TripResponse(trip);

		given(tripService.getTrip(any())).willReturn(response);

		// when
		final var result = mockMvc.perform(
			get("/api/trips/{tripId}", 1L)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
		);

		// then
		result.andExpect(status().isOk());
	}
}