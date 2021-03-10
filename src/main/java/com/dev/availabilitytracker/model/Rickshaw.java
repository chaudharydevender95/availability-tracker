package com.dev.availabilitytracker.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter @Setter
@Entity
@Table(name = "rickshaw",schema = "public")
public class Rickshaw {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "driver_name")
    private String driverName;
    private String registration;
    private double lat, lng;
}
