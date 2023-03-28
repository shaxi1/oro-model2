package com.oro.oromodel2.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "reservation")
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "reservation_id")
    private long id;

    @ManyToOne
    @JoinColumn(name = "screening_id")
    private Screening screening;

    @OneToOne
    @JoinColumn(name = "ticket_id")
    private Ticket ticket;

    private long seatNumber;

    private LocalDateTime reservationDate;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;
}
