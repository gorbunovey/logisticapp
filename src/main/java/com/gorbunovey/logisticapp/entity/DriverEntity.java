package com.gorbunovey.logisticapp.entity;

import lombok.*;
import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@EqualsAndHashCode(exclude = "orders")
@ToString(exclude = "orders")
@Entity
@Table(name = "DRIVER")
public class DriverEntity  implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "NUMBER", nullable = false, unique = true)
    private long number;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CITY_ID")
    private CityEntity city;

    @OneToOne(optional = false)
    @JoinColumn(name = "USER_ID", referencedColumnName = "ID")
    private UserEntity user;

    @Column(name = "STATUS", nullable = false)
    private String status;

    @Column(name = "HOURS", nullable = false)
    private int hours;

    @Column(name = "SHIFT", nullable = false)
    private boolean onShift;

    @Column(name = "SHIFT_TIME", nullable = false)
    private LocalDateTime lastShiftTime;

    @ManyToMany(mappedBy = "drivers")
    private Set<OrderEntity> orders;

}