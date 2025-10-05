package com.abhinav.hospitalManagement.HospitalManagement.service;

import com.abhinav.hospitalManagement.HospitalManagement.entity.Appointment;
import com.abhinav.hospitalManagement.HospitalManagement.entity.Doctor;
import com.abhinav.hospitalManagement.HospitalManagement.entity.Patient;
import com.abhinav.hospitalManagement.HospitalManagement.repository.AppointmentRepository;
import com.abhinav.hospitalManagement.HospitalManagement.repository.DoctorRepository;
import com.abhinav.hospitalManagement.HospitalManagement.repository.PatientRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AppointmentService {
    private final AppointmentRepository appointmentRepository;
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;

    @Transactional
    public Appointment createNewAppointment(Appointment appointment,Long doctorId,Long patientId){
        Doctor doctor=doctorRepository.findById(doctorId).orElseThrow(EntityNotFoundException::new);
        Patient patient=patientRepository.findById(patientId).orElseThrow(EntityNotFoundException::new);

        if(appointment.getId()!=null)throw new IllegalArgumentException("Appointment Should not have been in DB");

        appointment.setPatient(patient);
        appointment.setDoctor(doctor);

        patient.getAppointments().add(appointment);//won't affect jpa as such just to maintain bidirectionality
        doctor.getAppointments().add(appointment);

        appointmentRepository.save(appointment);
        
        return appointment;
    }

    @Transactional
    public Appointment reAssignAppointmentToAnotherDoctor(Long appointmentId,Long doctorId){
        Appointment appointment=appointmentRepository.findById(appointmentId).orElseThrow(EntityNotFoundException::new);
        Doctor doctor=doctorRepository.findById(doctorId).orElseThrow(EntityNotFoundException::new);

        appointment.setDoctor(doctor);

        return appointment;
    }
}
