package com.example.app.entity;

import javax.persistence.*;

@Entity
@Table(name = "computers")
public class Computer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String serialNumber;

    // One To Many: Worker -> Computer
    //@ManyToOne(fetch = FetchType.LAZY)
    @ManyToOne
    @JoinColumn(name = "worker_id")
    private Worker worker;

    public Computer() {}

    public Computer(Long id, String model) {
        this.id = id;
        this.serialNumber = model;
    }

    public Long getId() {
        return id;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String model) {
        this.serialNumber = model;
    }

}

