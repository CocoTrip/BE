package io.cocotrip.domain.trip.application.dto;

import java.time.LocalDate;
import java.util.List;

import io.cocotrip.domain.trip.persistence.Plan;
import io.cocotrip.domain.trip.persistence.Trip;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TripResponse {

	private String tripId;
	private Long userId;
	// private String title;
	private String area;
	private int travelers;
	private int pets;
	private LocalDate startDate;
	private LocalDate endDate;
	private List<Plan> plans;

	public TripResponse(final Trip trip) {
		this.tripId = trip.getId();
		this.userId = trip.getUserId();
		// this.title = trip.getTitle();
		this.area = trip.getArea();
		this.travelers = trip.getTravelers();
		this.pets = trip.getPets();
		this.startDate = trip.getStartDate();
		this.endDate = trip.getEndDate();
		this.plans = trip.getPlans();
	}
}
