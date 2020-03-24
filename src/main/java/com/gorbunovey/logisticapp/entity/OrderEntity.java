package com.gorbunovey.logisticapp.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(exclude = {"drivers", "wayPoints"})
@ToString(exclude = {"drivers", "wayPoints"})
@Entity
@Table(name = "ORDERS")
public class OrderEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "STATUS", nullable = false)
    private boolean active;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TRUCK_ID")
    private TruckEntity truck;

    @ManyToMany
    @JoinTable(
            name = "ORDER_DRIVERS",
            joinColumns = @JoinColumn(name = "ORDER_ID"),
            inverseJoinColumns = @JoinColumn(name = "DRIVER_ID"))
    private List<DriverEntity> drivers = new ArrayList<>();

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WayPointEntity> wayPoints = new ArrayList<>();

    public void addWayPoint(WayPointEntity wayPoint) {
        wayPoints.add(wayPoint);
        wayPoint.setOrder(this);
    }

    public void removeWayPoint(WayPointEntity wayPoint) {
        wayPoints.remove(wayPoint);
        wayPoint.setOrder(null);
    }
}
