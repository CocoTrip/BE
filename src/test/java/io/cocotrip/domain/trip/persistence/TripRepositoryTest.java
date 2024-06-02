package io.cocotrip.domain.trip.persistence;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;

import autoparams.AutoSource;

@DataMongoTest
class TripRepositoryTest {

	@Autowired
	private MongoTemplate mongoTemplate;

	@Autowired
	private TripRepository tripRepository;

	@Nested
	@DisplayName("여행 조회")
	class GetTrip {

		@DisplayName("여행 상세 조회 시, 정상적으로 상세 일정이 조회된다.")
		@ParameterizedTest
		@AutoSource
		void getTrip_success(final Trip trip) {
			// given
			mongoTemplate.save(trip);

			// when
			final var result = tripRepository.findById(trip.getId());

			// then
			assertThat(result).isNotNull();
		}
	}
}