package com.gorbunovey.logisticapp.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Data
@EqualsAndHashCode(exclude = "orderCargos")
@ToString(exclude = "orderCargos")
@Entity
@Table(name = "CARGO")
public class CargoEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "NUMBER")
    private long number;

    @Column(name = "TITLE", nullable = false)
    private String title;

    @Column(name = "WEIGHT", nullable = false)
    private int weight;

    @Column(name = "STATUS", nullable = false)
    private String status;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "CITY_FROM")
    private CityEntity cityFrom;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "CITY_TO")
    private CityEntity cityTo;

    @OneToMany(mappedBy = "cargo")
    Set<OrderCargosEntity> orderCargos;
}
