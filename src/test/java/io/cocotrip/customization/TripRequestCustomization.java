package io.cocotrip.customization;

import java.time.LocalDate;

import autoparams.ObjectQuery;
import autoparams.ResolutionContext;
import autoparams.generator.ObjectGeneratorBase;
import io.cocotrip.domain.trip.application.dto.TripRequest;

public class TripRequestCustomization extends ObjectGeneratorBase<TripRequest> {

	@Override
	protected TripRequest generateObject(ObjectQuery query, ResolutionContext context) {

		return new TripRequest(
		1L,
			"Weekend Trip",
			1,
			1,
			LocalDate.of(2024, 5, 1),
			LocalDate.of(2024, 5, 3)
		);
	}
}
