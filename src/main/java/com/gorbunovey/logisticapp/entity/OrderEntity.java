package com.gorbunovey.logisticapp.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Data
@Entity
@Table(name = "ORDER")
public class OrderEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "NUMBER", nullable = false, unique = true)
    private long number;

    @Column(name = "STATUS", nullable = false)
    private boolean status;

    @ManyToOne(fetch = FetchType.LAZY)
    private TruckEntity truck;

    @ManyToMany
    @JoinTable(
            name = "ORDER_DRIVERS",
            joinColumns = @JoinColumn(name = "ORDER_ID"),
            inverseJoinColumns = @JoinColumn(name = "DRIVER_ID"))
    private Set<DriverEntity> drivers;
}
