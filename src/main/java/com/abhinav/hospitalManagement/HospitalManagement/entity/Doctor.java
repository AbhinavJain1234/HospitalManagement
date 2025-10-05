package com.abhinav.hospitalManagement.HospitalManagement.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,length = 50)
    private String name;

    @Column(length = 100)
    private String specialization;

    @Column(nullable = false,unique = true)
    private String email;

    @OneToMany(mappedBy = "doctor")
    private List<Appointment> appointments;
}
