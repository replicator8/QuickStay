package com.example.demo.dtos;

public class AddressDto {
    private String id;
    private String country;
    private String city;
    private String street;
    private String house;

    public AddressDto(String id, String country, String city, String street, String house) {
        this.id = id;
        this.country = country;
        this.city = city;
        this.street = street;
        this.house = house;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getHouse() {
        return house;
    }

    public void setHouse(String house) {
        this.house = house;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }
}
