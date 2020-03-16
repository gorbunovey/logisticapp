package com.gorbunovey.logisticapp.entity;

import lombok.*;
import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "DRIVER_HISTORY")
public class DriverHistoryEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "STATUS", nullable = false)
    private String status;

    @Column(name = "STATUS_TIME", nullable = false)
    private LocalDateTime statusTime;

    @ManyToOne(fetch = FetchType.LAZY)
    private DriverEntity driver;
}
