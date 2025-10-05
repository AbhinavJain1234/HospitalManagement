package com.abhinav.hospitalManagement.HospitalManagement.service;

import com.abhinav.hospitalManagement.HospitalManagement.entity.Insurance;
import com.abhinav.hospitalManagement.HospitalManagement.entity.Patient;
import com.abhinav.hospitalManagement.HospitalManagement.repository.InsuranceRepository;
import com.abhinav.hospitalManagement.HospitalManagement.repository.PatientRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InsuranceService {

    private final InsuranceRepository insuranceRepository;
    private final PatientRepository patientRepository;

    @Transactional
    public Patient assignInsuranceToPatient(Insurance insurance, Long patientId){
        Patient patient=patientRepository.findById(patientId).orElseThrow(EntityNotFoundException::new);
        patient.setInsurance(insurance);
        insurance.setPatient(patient);// to maintain bidirectional consistent
        return patient;
    }

    @Transactional
    public Patient disassociateInsuranceFromPatient(Long patientId){
        Patient patient=patientRepository.findById(patientId).orElseThrow(EntityNotFoundException::new);
        patient.setInsurance(null);
        return patient;
    }
}
