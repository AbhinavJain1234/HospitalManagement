package com.abhinav.hospitalManagement.HospitalManagement.repository;

import com.abhinav.hospitalManagement.HospitalManagement.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.Repository;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
}