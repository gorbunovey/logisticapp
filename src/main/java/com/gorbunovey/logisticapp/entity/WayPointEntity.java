package com.gorbunovey.logisticapp.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "WAY_POINT")
public class WayPointEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "TYPE", nullable = false)
    private boolean type;

    @Column(name = "SEQ_NUMBER", nullable = false)
    private int seqNumber;

    @ManyToOne(optional = false)
    @JoinColumn(name = "ORDER_ID")
    private OrderEntity order;

    @ManyToOne(optional = false)
    @JoinColumn(name = "CARGO_ID")
    private CargoEntity cargo;
}
