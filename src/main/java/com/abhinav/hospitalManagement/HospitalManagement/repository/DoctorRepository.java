package com.abhinav.hospitalManagement.HospitalManagement.repository;

import com.abhinav.hospitalManagement.HospitalManagement.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
public interface DoctorRepository extends JpaRepository<Doctor, Long> {
}