package com.abhinav.hospitalManagement.HospitalManagement.repository;

import com.abhinav.hospitalManagement.HospitalManagement.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepository extends JpaRepository<Patient,Long> {
}
