package com.example.parks.models;

import jakarta.persistence.*;

@Entity(name="cities")
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    long id;

    @Column(name = "name")
    String name;

    @Column(name = "parks")
    int parks;

    public City(){

    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getParks() {
        return parks;
    }

    public void setParks(int parks) {
        this.parks = parks;
    }

}
