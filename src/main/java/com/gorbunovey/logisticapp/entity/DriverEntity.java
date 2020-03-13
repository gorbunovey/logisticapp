package com.gorbunovey.logisticapp.entity;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Objects;

@Entity
@Table(name = "driver")
public class DriverEntity {

    @Id
    @NotNull
    @Digits(integer=9, fraction=0)
    @Column(name = "ID", nullable = false)
    private Integer id;

    @NotBlank
    @Size(min = 2, max = 45)
    @Column(name = "FIRST_NAME", nullable = false)
    private String firstName;

    @NotBlank
    @Size(min = 2, max = 45)
    @Column(name = "LAST_NAME", nullable = false)
    private String lastName;

    @Column(name = "PATRONYMIC_NAME")
    private String patronymicName;

    @NotBlank
    @Size(min = 2, max = 20)
    @Column(name = "STATUS", nullable = false)
    private String status;

    @NotNull
    @Digits(integer=3, fraction=0)
    @Column(name = "WORK_HOURS")
    private int hours;

    @ManyToOne(optional = false, fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "CITY_ID")
    private CityEntity city;

    @ManyToOne(optional = false, fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "TRUCK_REG_NUMBER")
    private TruckEntity truck;

    public DriverEntity() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPatronymicName() {
        return patronymicName;
    }

    public void setPatronymicName(String patronymicName) {
        this.patronymicName = patronymicName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public CityEntity getCity() {
        return city;
    }

    public void setCity(CityEntity city) {
        this.city = city;
    }

    public TruckEntity getTruck() {
        return truck;
    }

    public void setTruck(TruckEntity truck) {
        this.truck = truck;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DriverEntity)) return false;
        DriverEntity driver = (DriverEntity) o;
        return Objects.equals(id, driver.id) &&
                Objects.equals(firstName, driver.firstName) &&
                Objects.equals(lastName, driver.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName);
    }


    @Override
    public String toString() {
        return "Driver{" +
                "id=" + id +
                ", name='" + firstName + '\'' +
                ", surname='" + lastName + '\'' +
                ", hours=" + hours +
                ", status='" + status + '\'' +
                '}';
    }
}
