package io.cocotrip.domain.trip.web;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.cocotrip.domain.trip.application.TripService;
import io.cocotrip.domain.trip.application.dto.PlanRequest;
import io.cocotrip.domain.trip.application.dto.TripRequest;
import io.cocotrip.domain.trip.application.dto.TripResponse;
import io.cocotrip.domain.trip.persistence.Trip;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/trips")
public class TripController {

	private final TripService tripService;

	/**
	 * 여행 생성
	 *
	 * @param  request 여행 생성 DTO
	 * @return TripId
	 */
	@PostMapping
	public ResponseEntity<String> createTrip(@RequestBody @Valid final TripRequest request) {
		final String tripId = tripService.createTrip(request);

		return ResponseEntity
			.status(HttpStatus.CREATED)
			.body(tripId);
	}

	/**
	 * 여행 일정 설계
	 *
	 * @param  tripId 	여행ID
	 * @param  request 	일정별 장소ID 리스트 DTO
	 * @return TripResponse 객체
	 */
	@PostMapping("/{tripId}")
	public ResponseEntity<TripResponse> planDesign(
		@PathVariable(value = "tripId", required = false) final String tripId,
		@RequestBody final List<PlanRequest> request
	) {
		final TripResponse response = tripService.planDesign(tripId, request);

		return ResponseEntity
			.status(HttpStatus.CREATED)
			.body(response);
	}

	/**
	 * 모든 여행 조회
	 *
	 * @param  userId	사용자ID (수정 필요)
	 * @return Trip 	리스트
	 */
	@GetMapping
	public ResponseEntity<List<Trip>> getAllTrips(final Long userId) {
		final List<Trip> trips = tripService.getAllTrips();

		return ResponseEntity
			.status(HttpStatus.OK)
			.body(trips);
	}

	/**
	 * 여행 상세 조회
	 *
	 * @param  userId 	사용자ID (수정 필요)
	 * @param  tripId 	생성된 여행ID
	 * @return TripResponse 객체
	 */
	@GetMapping("/{tripId}")
	public ResponseEntity<TripResponse> getTrip(
		final Long userId,
		@PathVariable("tripId") final String tripId
	)  {
		final TripResponse response = tripService.getTrip(tripId);

		return ResponseEntity
			.status(HttpStatus.OK)
			.body(response);
	}

}
