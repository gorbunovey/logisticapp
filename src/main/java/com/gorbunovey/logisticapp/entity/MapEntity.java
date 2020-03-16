package com.gorbunovey.logisticapp.entity;

import lombok.*;
import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "MAP")
public class MapEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "DISTANCE", nullable = false)
    private int distance;

    @ManyToOne(fetch = FetchType.LAZY)
    private CityEntity departurePoint;

    @ManyToOne(fetch = FetchType.LAZY)
    private CityEntity destinationPoint;

}
