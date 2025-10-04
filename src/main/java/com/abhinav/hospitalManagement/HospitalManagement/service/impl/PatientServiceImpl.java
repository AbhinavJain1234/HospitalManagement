package com.abhinav.hospitalManagement.HospitalManagement.service.impl;

import com.abhinav.hospitalManagement.HospitalManagement.entity.Patient;
import com.abhinav.hospitalManagement.HospitalManagement.repository.PatientRepository;
import com.abhinav.hospitalManagement.HospitalManagement.service.PatientService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {
    private final PatientRepository patientRepository;

    public Patient findPatientById(Long Id){
        Patient p1=patientRepository.findById(Id).orElseThrow();
        Patient p2=patientRepository.findById(Id).orElseThrow();
        System.out.println(p1==p2);
        return p1;
    }

    //query will be run once only as it will bw stored in persistence layer
    @Transactional
    public Patient findPatientByIdWithTransaction(Long Id){
        Patient p1=patientRepository.findById(Id).orElseThrow();
        Patient p2=patientRepository.findById(Id).orElseThrow();
        System.out.println(p1==p2);
        //p1.setName("Rahul");
        //update query will be run automatically to prevent dirty write
        return p1;
    }
}
