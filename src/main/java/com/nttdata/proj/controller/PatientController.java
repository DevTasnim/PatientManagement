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
import java.util.Optional;

@RestController
@RequestMapping("/patients")
public class PatientController {
    @Autowired
    PatientService patientService;

    @GetMapping
    public List<Patient> getAllPatients(){
        return patientService.getAllPatients();
    }

    @PostMapping
    public ResponseEntity<?> createPatient(@RequestBody Patient patient){
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
    @PutMapping
    public ResponseEntity<?> updatePatient(@PathVariable long id , @RequestBody Patient patient){
        Patient existingPatient = patientService.getPatientById(id);
        if (existingPatient == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Patient not found with id : " +id);
        }
        Patient updatedPatient = patientService.updatePatient(id, patient);
        return ResponseEntity.ok(updatedPatient);

    }
    @GetMapping("/by-doctor")
    public ResponseEntity<Map<Doctor, List<Patient>>> getPatientsByDoctor() {
        Map<Doctor, List<Patient>> patientsByDoctor = patientService.getPatientsByDoctor();
        return ResponseEntity.ok(patientsByDoctor);
    }


}
