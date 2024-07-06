package com.example.app.entity;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

@Entity
@Table(name = "workers")
public class Worker {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    // Many To One: Worker -> Role
    //@ManyToOne(fetch = FetchType.LAZY)
    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    // Many To Many: Worker <-> WorkRelations
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "worker_and_relations",
            joinColumns = @JoinColumn(name = "worker_id"),
            inverseJoinColumns = @JoinColumn(name = "work_relations_id")
    )
    private List<WorkRelations> workRelationsList = new ArrayList<>();

    // One To Many: Worker -> Computer
    @OneToMany(mappedBy = "worker", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Computer> computerList = new ArrayList<>();

    public Worker() {}

    public Worker(Long id, String firstName, String lastName, Role role, List<WorkRelations> workRelationsList,
                  List<Computer> computerList) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
        this.workRelationsList = workRelationsList;
        this.computerList = computerList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public List<WorkRelations> getWorkRelationsList() {
        return workRelationsList;
    }

    public void setWorkRelationsList(List<WorkRelations> workRelationsList) {
        this.workRelationsList = workRelationsList;
    }

    public List<Computer> getComputerList() {
        return computerList;
    }

    public void setComputerList(List<Computer> computerList) {
        this.computerList = computerList;
    }
}

