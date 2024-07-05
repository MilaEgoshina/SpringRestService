package com.example.app.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "relations")
public class WorkRelations {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @ManyToMany(mappedBy = "workRelationsList")
    private List<Worker> workerList;

    public WorkRelations(Long id, String name, List<Worker> workerList) {
        this.id = id;
        this.name = name;
        this.workerList = workerList;
    }

    public WorkRelations() {
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

    public List<Worker> getWorkerList() {
        return workerList;
    }

    public void setWorkerList(List<Worker> workerList) {
        this.workerList = workerList;
    }
}
