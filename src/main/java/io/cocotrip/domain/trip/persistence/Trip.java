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

	@Field("userid")
	private Long userId;

	private String area;

	private int travelers;

	private int pets;

	private LocalDate startDate;

	private LocalDate endDate;

	private String title;

	private List<Plan> plans = new ArrayList<>();

	@Builder
	public Trip(
		final Long userId,
		final String title,
		final String area,
		final int travelers,
		final int pets,
		final LocalDate startDate,
		final LocalDate endDate
	) {
		this.userId = userId;
		this.title = title != null ? title : (startDate + " "+ area);
		this.area = area;
		this.travelers = travelers;
		this.pets = pets;
		this.startDate = startDate;
		this.endDate = endDate;
	}

}
