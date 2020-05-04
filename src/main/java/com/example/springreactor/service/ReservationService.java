package com.example.springreactor.service;


import com.example.springreactor.model.Reservation;
import com.example.springreactor.repository.ReservationRepository;
import io.r2dbc.postgresql.util.Assert;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.reactive.TransactionalOperator;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final TransactionalOperator transactionalOperator;

    Flux<Reservation> saveAll(String... names) {
        return transactionalOperator.transactional(
                Flux
                        .fromArray(names)
                        .map(name -> new Reservation(null, name))
                        .flatMap(reservationRepository::save)
                        .doOnNext(this::assertValid)
        );
    }

    private void assertValid(Reservation r) {
        Assert.isTrue(r.getName() != null && r.getName().length() > 0
                && Character.isUpperCase(r.getName().charAt(0)), "the name must start with a capital letter");
    }
}
