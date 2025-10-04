package com.abhinav.hospitalManagement.HospitalManagement;

import com.abhinav.hospitalManagement.HospitalManagement.entity.Patient;
import com.abhinav.hospitalManagement.HospitalManagement.repository.PatientRepository;
import com.abhinav.hospitalManagement.HospitalManagement.service.impl.PatientServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
        Patient patient= patientServiceImpl.findPatientByIdWithTransaction(1L);
        System.out.println(patient);

    }

}
