package io.cocotrip.domain.trip.application.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TripRequest(

	@NotNull
	Long userId,

	// @Nullable
	// String title,

	@NotBlank
	String area,

	@Min(1)
	int travelers,

	int pets,

	@NotNull
	LocalDate startDate,

	@NotNull
	LocalDate endDate
) {
}
