package com.oro.oromodel2.model;

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
@Table(name = "hall")
public class Hall {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "hall_id")
    private long id;

    private String name;
}
