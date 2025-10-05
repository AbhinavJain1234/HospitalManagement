package com.abhinav.hospitalManagement.HospitalManagement.repository;

import com.abhinav.hospitalManagement.HospitalManagement.entity.Insurance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.Repository;

public interface InsuranceRepository extends JpaRepository<Insurance, Long> {
}