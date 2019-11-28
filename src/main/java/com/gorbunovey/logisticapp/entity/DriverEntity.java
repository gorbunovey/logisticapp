package com.gorbunovey.logisticapp.entity;

import com.gorbunovey.logisticapp.model.Driver;

import java.util.Objects;

public class DriverEntity {

    private int id;
    private String name;
    private String surname;
    private int hours;
    private String status;

    public DriverEntity() {
    }

    public DriverEntity(int id, String name, String surname, int hours, String status) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.hours = hours;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DriverEntity)) return false;
        DriverEntity driver = (DriverEntity) o;
        return id == driver.id &&
                Objects.equals(name, driver.name) &&
                Objects.equals(surname, driver.surname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, surname);
    }


    @Override
    public String toString() {
        return "DriverEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", hours=" + hours +
                ", status='" + status + '\'' +
                '}';
    }
}
