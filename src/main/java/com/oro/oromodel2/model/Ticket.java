package com.oro.oromodel2.model;

import com.oro.oromodel2.enums.TicketType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "ticket")
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ticket_id")
    private long id;

    @Enumerated(EnumType.STRING)
    private TicketType ticketType;

    @OneToOne(mappedBy = "ticket")
    private Reservation reservation;
}
