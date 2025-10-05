package com.abhinav.hospitalManagement.HospitalManagement;

import com.abhinav.hospitalManagement.HospitalManagement.entity.Patient;
import com.abhinav.hospitalManagement.HospitalManagement.entity.type.BloodGroupType;
import com.abhinav.hospitalManagement.HospitalManagement.repository.PatientRepository;
import com.abhinav.hospitalManagement.HospitalManagement.service.impl.PatientServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

@SpringBootTest
public class PatientTests {

    @Autowired
    private  PatientRepository patientRepository;

    @Autowired
    private PatientServiceImpl patientServiceImpl;
    @Test
    public void testPatientRepository(){
        List<Patient> patientList=patientRepository.findAll();
        System.out.println(patientList);
    }

    @Test
    public void testTransactionMethods(){
//        Patient patient= patientServiceImpl.findPatientByIdWithTransaction(1L);

        //Present By Default
//        Patient patient=patientRepository.findById(1L).orElseThrow(() -> new EntityNotFoundException("Patient not found with id: 1"));

        //custom Build
        // 1. find by name
        Patient patient=patientRepository.findByName("John Doe").orElseThrow();
        System.out.println(patient);
        // 2. find by birthDate or email
        patient=patientRepository.findFirstByBirthDateOrEmail(LocalDate.of(1974,7,30),"emily.davis@example.com").orElseThrow();
        System.out.println(patient);
        // 3. BloodGroupType with JPQL
        System.out.println("------findByBloodGroup-------");
        List<Patient> patients=patientRepository.findByBloodGroup(BloodGroupType.O_POSITIVE);
        for(Patient p:patients) System.out.println(p);

        System.out.println("------findByBornAfterDate-------");
        patients=patientRepository.findByBornAfterDate(LocalDate.of(1992,3,8));
        for(Patient p:patients) System.out.println(p);

        System.out.println("------countEachBloodGroupType-------");
        List<Object[]> ans=patientRepository.countEachBloodGroupType();
        for(Object[] obj:ans) System.out.println(obj[0]+" "+obj[1]);

        System.out.println("------findAllPatients-------");
        patients=patientRepository.findAllPatients();
        for(Patient p:patients) System.out.println(p);

        System.out.println("------Updating Patient-------");
        System.out.println(patientRepository.updateNameWithId(1L,"Abhinav Jain"));

    }
}
