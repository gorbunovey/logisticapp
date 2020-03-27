package com.gorbunovey.logisticapp.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

@Data
@EqualsAndHashCode(exclude = "driver")
@ToString(exclude = "driver")
@Entity
@Table(name = "USER")
public class UserEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "FIRST_NAME", nullable = false)
    private String firstName;

    @Column(name = "LAST_NAME", nullable = false)
    private String lastName;

    @Column(name = "BIRTHDAY")
    private Date birthday; // пока исключил

    @Column(name = "EMAIL", nullable = false, unique = true)
    private String email;

    @Column(name = "PASSWORD", nullable = false)
    private String password;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ROLE_ID")
    private RoleEntity role;

    // orphanRemoval = true - для автоматического удаления водителя, для которого удален его user
    // по идее cascade = CascadeType.ALL должна тоже это сделать
    @OneToOne(mappedBy = "user", orphanRemoval = true)
    private DriverEntity driver;

}
