package com.nttdata.proj.controller;


import com.nttdata.proj.entities.Doctor;
import com.nttdata.proj.entities.Patient;
import com.nttdata.proj.services.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/patients")
public class PatientController {
    @Autowired
    PatientService patientService;

    @GetMapping
    public List<Patient> getAllPatients(){
        return patientService.getAllPatients();
    }

    @PostMapping
    public ResponseEntity<Patient> createPatient(@RequestBody Patient patient){
        Patient createdPatient = patientService.createPatient(patient);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPatient);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPatientById(@PathVariable long id){
        Patient patient = patientService.getPatientById(id);
        if (patient == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Patient not found with id:"+id);
        }
        return ResponseEntity.ok(patient);
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> updatePatient(@PathVariable long id , @RequestBody Patient patient){
        Patient existingPatient = patientService.getPatientById(id);
        if (existingPatient == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Patient not found with id : " +id);
        }
        Patient updatedPatient = patientService.updatePatient(id, patient);
        return ResponseEntity.ok(updatedPatient);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePatient(@PathVariable long id) {
        try {
            patientService.deletePatient(id);
            return ResponseEntity.status(HttpStatus.OK).body("Patient deleted successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/bydoctor")
    public ResponseEntity<Map<Doctor, List<Patient>>> getPatientsByDoctor() {
        Map<Doctor, List<Patient>> patientsByDoctor = patientService.getPatientsByDoctor();
        return ResponseEntity.ok(patientsByDoctor);
    }

    @GetMapping("/byAgeRange")
    public ResponseEntity<Map<String, List<Patient>>> getPatientsByAgeRange() {
        Map<String, List<Patient>> patientsByAgeRange = patientService.getPatientsByAgeRange();
        return new ResponseEntity<>(patientsByAgeRange, HttpStatus.OK);
    }

    @PostMapping("/assignDoctor")
    public ResponseEntity<Void> assignDoctorToPatient(@RequestBody List<Patient> patients) {
        patientService.savePatients(patients);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
