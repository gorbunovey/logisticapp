package com.gorbunovey.logisticapp.entity;

import lombok.*;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Data
@EqualsAndHashCode(exclude = { "driverHistory", "orders"})
@ToString(exclude = { "driverHistory", "orders"})
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

    // orphanRemoval="true" - для автоматической очистки истории при удалении драйвера
    // по идее cascade = CascadeType.ALL - должна сделать тоже самое
    @OneToMany(mappedBy = "driver", orphanRemoval = true)
    private Set<DriverHistoryEntity> driverHistory;

    @ManyToMany(mappedBy = "drivers")
    private Set<OrderEntity> orders;

}