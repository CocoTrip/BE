package io.cocotrip.domain.trip.application;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import autoparams.AutoSource;
import autoparams.customization.Customization;
import io.cocotrip.customization.TripRequestCustomization;
import io.cocotrip.domain.trip.application.dto.PlanRequest;
import io.cocotrip.domain.trip.application.dto.TripRequest;
import io.cocotrip.domain.trip.persistence.Trip;
import io.cocotrip.domain.trip.persistence.TripRepository;

@ExtendWith(MockitoExtension.class)
class TripServiceTest {

	@InjectMocks
	private TripService tripService;

	@Mock
	private TripRepository tripRepository;

	@Nested
	@DisplayName("여행 생성")
	class CreateTrip {

		@DisplayName("여행 생성 시, 성공적으로 동작한다.")
		@ParameterizedTest
		@AutoSource
		@Customization(TripRequestCustomization.class)
		void createTrip_success(final TripRequest request) {
			// given
			final Trip trip = Trip.builder()
				.userId(request.userId())
				.area(request.area())
				.travelers(request.travelers())
				.pets(request.pets())
				.startDate(request.startDate())
				.endDate(request.endDate())
				.build();

			given(tripRepository.save(any())).willReturn(trip);

			// when
			final var result = tripService.createTrip(request);

			// then
			assertThat(result).isEqualTo(trip.getId());
		}
	}

	// TODO: IndexOutOfBoundsException 해결
	@Nested
	@DisplayName("여행 일정 설계")
	class PlanDesign {

		@DisplayName("여행 일정 설계 시, 정상적으로 해당 여행 일정이 저장된다.")
		@ParameterizedTest
		@AutoSource
		void planDesign_success(final Trip trip, final List<PlanRequest> requests) {
			// given
			given(tripRepository.findById(any())).willReturn(Optional.of(trip));

			// TODO: 여행 일정별 방문할 장소 저장

			given(tripRepository.save(any())).willReturn(trip);

			// when
			final var result = tripService.planDesign("1L", requests);

			// then
			assertThat(result.getPlans()).isNotEmpty();
		}
	}

	@Nested
	@DisplayName("여행 조회")
	class GetTrip {

		@DisplayName("여행 상세 조회 시, 정상적으로 상세 일정이 조회된다.")
		@ParameterizedTest
		@AutoSource
		void getTrip_success(final Trip trip) {
			// given
			given(tripRepository.findById(any())).willReturn(Optional.of(trip));

			// when
			final var result = tripService.getTrip(trip.getId());

			// then
			assertThat(result).isNotNull();
		}
	}
}
