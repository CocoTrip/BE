package io.cocotrip.domain.trip.application.dto;

import java.util.List;

public record PlanRequest(
	List<Long> visit
) {
}
