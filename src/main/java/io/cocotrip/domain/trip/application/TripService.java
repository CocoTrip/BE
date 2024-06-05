package io.cocotrip.domain.trip.application;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.cocotrip.domain.trip.application.dto.PlanRequest;
import io.cocotrip.domain.trip.application.dto.TripRequest;
import io.cocotrip.domain.trip.application.dto.TripResponse;
import io.cocotrip.domain.trip.persistence.Plan;
import io.cocotrip.domain.trip.persistence.Trip;
import io.cocotrip.domain.trip.persistence.TripRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TripService {

	private final TripRepository tripRepository;

	/**
	 * 여행 생성
	 *
	 * @param request: 여행 생성 DTO
	 * @return TripResponse: 생성한 Trip 정보
	 */
	@Transactional
	public String createTrip(final TripRequest request) {

		// TODO: 회원과 비회원 분리

		final Trip trip = Trip.builder()
			.userId(request.userId())
			.title(request.title())
			.area(request.area())
			.travelers(request.travelers())
			.pets(request.pets())
			.startDate(request.startDate())
			.endDate(request.endDate())
			.build();

		tripRepository.save(trip);

		return trip.getId();
	}

	/**
	 * 여행 일정 설계
	 *
	 * TODO: 예외 처리
	 *
	 * @param tripId: 여행ID
	 * @param requests: 일정별 장소ID 리스트 DTO
	 * @return TripResponse: 일정별 장소 리스트가 추가된 Trip 정보
	 */
	@Transactional
	public TripResponse planDesign(final String tripId, final List<PlanRequest> requests) {
		final Trip trip	= tripRepository.findById(tripId)
			.orElseThrow();

		long duration = Period.between(trip.getStartDate(), trip.getEndDate()).getDays();

		for (int i = 0; i < duration; i++){

			LocalDate day = trip.getStartDate().plusDays(i);

			final Plan plan	= new Plan(
				day,
				requests.get(i).visit()
			);

			trip.getPlans().add(plan);
		}

		tripRepository.save(trip);

		return new TripResponse(trip);
	}

	/**
	 * TODO: 여행 일정 수정
	 *
	 * - 일정 기간을 넘는 계획 요청 시 → 예외 처리
	 */

	/**
	 * 모든 여행 조회
	 *
	 * TODO: 예외 처리
	 */
	public List<Trip> getAllTrips() {
		return tripRepository.findAll();
	}

	/**
	 * 여행 상세 조회
	 *
	 * @param tripId: 여행ID
	 * @return TripResponse: 상세 일정 정보
	 */
	public TripResponse getTrip(final String tripId) {
		final Trip trip = tripRepository.findById(tripId).orElseThrow();

		return new TripResponse(trip);
	}
}
