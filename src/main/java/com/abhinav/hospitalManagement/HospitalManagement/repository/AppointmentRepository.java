package com.abhinav.hospitalManagement.HospitalManagement.repository;

import com.abhinav.hospitalManagement.HospitalManagement.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.Repository;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
}