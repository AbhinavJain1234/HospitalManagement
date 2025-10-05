package com.abhinav.hospitalManagement.HospitalManagement.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime appointmentTime;

    @Column(length = 500)
    private String reason;

    //as we don't was to propagate changes in appointment to patient and doctor therefore cascading not defined
    @ManyToOne
    @JoinColumn(name="patient_id",nullable = false)
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "doctor_id",nullable = false)
    private Doctor doctor;
}
