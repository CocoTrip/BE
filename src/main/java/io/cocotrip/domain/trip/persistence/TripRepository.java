package io.cocotrip.domain.trip.persistence;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface TripRepository extends MongoRepository<Trip, String> {
	List<Trip> findAllByUserId(final Long userId);
}
