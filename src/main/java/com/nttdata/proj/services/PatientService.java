package com.nttdata.proj.services;

import com.nttdata.proj.entities.Doctor;
import com.nttdata.proj.entities.Patient;
import com.nttdata.proj.repositories.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PatientService {

    @Autowired
    PatientRepository patientRepository;

    public List getAllPatients(){
        return patientRepository.findAll();
    }

    public Patient createPatient(Patient patient){
        return patientRepository.save(patient);
    }
    public Patient getPatientById(long id){
        Optional<Patient> patientOptional = patientRepository.findById(id);
        return patientOptional.orElse(null);
    }

    public Patient updatePatient(long id , Patient patient){
        if (!patientRepository.existsById(id)){
            throw new IllegalArgumentException("Patient with id : " +id+ "does not exists");
        }
        patient.setPatientId(id);
        return patientRepository.save(patient);

    }
    public void deletePatient(Long id) {
        patientRepository.deleteById(id);
    }
    public Map<Doctor, List<Patient>> getPatientsByDoctor() {
        List<Patient> patients = patientRepository.findAll();
        return patients.stream().collect(Collectors.groupingBy(Patient::getDoctor));
    }
}
