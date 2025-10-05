package com.abhinav.hospitalManagement.HospitalManagement.entity;


import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,unique = true,length = 50)
    private String name;

    @OneToOne
    private Doctor headDoctor;

    @ManyToMany
    private Set<Doctor> doctors=new HashSet<>();
}
