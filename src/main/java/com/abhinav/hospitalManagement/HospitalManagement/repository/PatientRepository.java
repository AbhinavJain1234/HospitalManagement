package com.abhinav.hospitalManagement.HospitalManagement.repository;

import com.abhinav.hospitalManagement.HospitalManagement.dto.BloodGroupCountResponseEntity;
import com.abhinav.hospitalManagement.HospitalManagement.entity.Patient;
import com.abhinav.hospitalManagement.HospitalManagement.entity.type.BloodGroupType;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<Patient,Long> {

    //--------QUERY METHODS------------

    // divided into "findBy"+"name"(here name can be any column name but it should be a col name
    Optional<Patient> findByName(String name);

    // Birthdate "OR" Email
    //Optional<Patient> findByBirthDateOrEmail gives error as multiple can be there so
    //can use list return list of user or empty list or findFirstByBirthDateOrEmail first encountered
    Optional<Patient> findFirstByBirthDateOrEmail(LocalDate date,String email);

    //Complete guide art https://docs.spring.io/spring-data/jpa/reference/jpa/query-methods.html



    //if we need more customization we use
    //----------- JPQL or Postgresql query---------------


    //using JPQL we have given custom query converted to sql by hibernate where ?1 says variable at 1st position
    //use Entity name
    @Query("SELECT p FROM Patient p WHERE p.bloodGroup = ?1" )
    List<Patient> findByBloodGroup(@Param("bloodGroup") BloodGroupType bloodGroupType);

    // this is how we can directly use variable name
    @Query("SELECT p FROM Patient p where p.birthDate > :birthDate")
    List<Patient> findByBornAfterDate(@Param("birthDate")LocalDate birthDate);


    //Use of Group By clause
//    @Query("SELECT p.bloodGroup,count(p.bloodGroup) FROM Patient p GROUP BY p.bloodGroup")
//    List<Object[]> countEachBloodGroupType();

    //Give sql query directly use table name
    @Query(value = "SELECT * FROM patient_table",nativeQuery = true)
    List<Patient> findAllPatients();

    //Update query
    // return no. of rows affected
    @Modifying
    @Transactional
    @Query("UPDATE Patient p SET p.name= :name where id= ?1")
    int updateNameWithId(@Param("id") Long id, @Param("name")String name);


    //Projection to BloodGroupResponseEntity - now we can return a json also
    //cant do project in native query
    @Query("SELECT new com.abhinav.hospitalManagement.HospitalManagement.dto.BloodGroupCountResponseEntity(p.bloodGroup, COUNT(p.id)) FROM Patient p GROUP BY p.bloodGroup")
    List<BloodGroupCountResponseEntity> countEachBloodGroupType();
}