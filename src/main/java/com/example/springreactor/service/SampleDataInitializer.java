package com.example.springreactor.service;

import com.example.springreactor.model.Reservation;
import com.example.springreactor.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Component
@RequiredArgsConstructor
@Log4j2
public class SampleDataInitializer {

    private final ReservationService reservationService;
    private final ReservationRepository reservationRepository;

    @EventListener(ApplicationReadyEvent.class)
    public void ready() {

        Flux<Reservation> reservations =  reservationService.saveAll("Oleg", "Stephan", "Jecky Chan");

        reservationRepository
                .deleteAll()
                .thenMany(reservations)
                .thenMany(this.reservationRepository.findAll())
                .subscribe(log::info);
    }
}

