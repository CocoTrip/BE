package io.cocotrip.domain.trip.persistence;

import java.time.LocalDate;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
public class Plan {

	private LocalDate day;

	@Setter
	private List<Long> visit;

	public Plan(final LocalDate day, final List<Long> visit) {
		this.day = day;
		this.visit = visit;
	}
}
