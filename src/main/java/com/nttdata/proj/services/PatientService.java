package com.nttdata.proj.services;

import com.nttdata.proj.entities.Doctor;
import com.nttdata.proj.entities.Patient;
import com.nttdata.proj.repositories.DoctorRepository;
import com.nttdata.proj.repositories.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PatientService {

    @Autowired
    PatientRepository patientRepository;

    @Autowired
    DoctorRepository doctorRepository;

    public List<Patient> getAllPatients(){
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
    public void deletePatient(long id) {
        if (!patientRepository.existsById(id)) {
            throw new IllegalArgumentException("Patient with id: " + id + " does not exist");
        }
        patientRepository.deleteById(id);
    }
    public Map<Doctor, List<Patient>> getPatientsByDoctor() {
        List<Patient> patients = patientRepository.findAll();
        return patients.stream().collect(Collectors.groupingBy(Patient::getDoctor));
    }

    public Map<String, List<Patient>> getPatientsByAgeRange() {
        List<Patient> patients = patientRepository.findAll();
        Map<String, List<Patient>> result = new HashMap<>();

        result.put("0-18", patients.stream().filter(p -> p.getAge() <= 18).collect(Collectors.toList()));
        result.put("19-35", patients.stream().filter(p -> p.getAge() >= 19 && p.getAge() <= 35).collect(Collectors.toList()));
        result.put("36-50", patients.stream().filter(p -> p.getAge() >= 36 && p.getAge() <= 50).collect(Collectors.toList()));
        result.put("51-65", patients.stream().filter(p -> p.getAge() >= 51 && p.getAge() <= 65).collect(Collectors.toList()));
        result.put("66+", patients.stream().filter(p -> p.getAge() >= 66).collect(Collectors.toList()));

        return result;
    }
    public Patient assignDoctorToPatient(long patientId, long doctorId) {
        Patient patient = getPatientById(patientId);
        Doctor doctor = doctorRepository.findById(doctorId).orElseThrow(() -> new IllegalArgumentException("Doctor with id: " + doctorId + " does not exist"));
        patient.setDoctor(doctor);
        return patientRepository.save(patient);
    }
    public void savePatients(List<Patient> patients) {
        patientRepository.saveAll(patients);
    }
}
