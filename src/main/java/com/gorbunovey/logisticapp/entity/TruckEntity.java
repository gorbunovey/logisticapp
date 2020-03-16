package com.gorbunovey.logisticapp.entity;

import lombok.*;
import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "TRUCK")
public class TruckEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "REG_NUMBER", nullable = false, unique = true)
    private String regNumber;

    @Column(name = "CREW", nullable = false)
    private int crew;

    @Column(name = "CAPACITY", nullable = false)
    private float capacity;

    @Column(name = "STATE", nullable = false)
    private boolean state;

    @ManyToOne(fetch = FetchType.LAZY)
    private CityEntity city;

}
