package com.gorbunovey.logisticapp.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CityDTO {

    @NotNull
    @Min(value = 0)
    private Long id;

    @NotBlank
    @Size(min = 2, max = 45)
    private String name;

    public CityDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
