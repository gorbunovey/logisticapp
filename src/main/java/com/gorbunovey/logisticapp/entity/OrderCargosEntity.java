package com.gorbunovey.logisticapp.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "ORDER_CARGOS")
public class OrderCargosEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long roleId;

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
