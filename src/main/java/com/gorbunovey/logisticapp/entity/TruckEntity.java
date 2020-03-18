package com.gorbunovey.logisticapp.entity;

import lombok.*;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

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
    private boolean active;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CITY_ID")
    private CityEntity city;

    @OneToMany(mappedBy = "truck")
    private Set<OrderEntity> orders;

}
