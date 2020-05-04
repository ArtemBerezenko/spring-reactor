package com.example.springreactor.repository;

import com.example.springreactor.model.Reservation;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface ReservationRepository extends R2dbcRepository<Reservation, Integer> {

    @Query("SELECT * FROM reservation WHERE name = $1")
    Flux<Reservation> findAllByName(String name);
}
