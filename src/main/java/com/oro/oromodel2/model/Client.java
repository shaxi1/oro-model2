package com.oro.oromodel2.model;

import com.oro.oromodel2.enums.TicketType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "client")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "client_id")
    private long id;

    @Column(name = "login", nullable = false, unique = true)
    private String login;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Enumerated(EnumType.STRING)
    @Column(name = "ticket_type")
    private TicketType ticketType;
}
