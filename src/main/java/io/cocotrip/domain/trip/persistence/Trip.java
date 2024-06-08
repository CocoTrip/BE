package io.cocotrip.domain.trip.persistence;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;

@Document("trip")
@Getter
public class Trip {

	@Id
	private String id;

	@Field("user_id")
	private Long userId;

	private String area;

	private int travelers;

	private int pets;

	private LocalDate startDate;

	private LocalDate endDate;

	private List<Plan> plans = new ArrayList<>();

	@Builder
	public Trip(
		final Long userId,
		final String area,
		final int travelers,
		final int pets,
		final LocalDate startDate,
		final LocalDate endDate
	) {
		this.userId = userId;
		this.area = area;
		this.travelers = travelers;
		this.pets = pets;
		this.startDate = startDate;
		this.endDate = endDate;
	}

}
