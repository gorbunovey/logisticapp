package com.gorbunovey.logisticapp.model;

import javax.validation.constraints.*;
import java.util.Objects;

public class Driver {

    @NotNull
    @Min(value = 0)
    private int id;
    @NotBlank
    @Size(min = 2, max = 45)
    private String firstName;
    @NotBlank
    @Size(min = 2, max = 45)
    private String lastName;
    private String patronymicName;
    @NotBlank
    @Size(min = 2, max = 20)
    private String status;
    @NotNull
    @Min(value = 0)
    @Max(value = 176)
    private int hours;

    public Driver() {
    }

    public Driver(int id, String firstName, String lastName, String patronymicName, String status, int hours) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.patronymicName = patronymicName;
        this.status = status;
        this.hours = hours;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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
        if (!(o instanceof Driver)) return false;
        Driver driver = (Driver) o;
        return id == driver.id &&
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
