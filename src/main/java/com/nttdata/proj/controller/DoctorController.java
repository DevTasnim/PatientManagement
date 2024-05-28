package com.nttdata.proj.controller;

import com.nttdata.proj.entities.Doctor;
import com.nttdata.proj.services.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/doctors")
@CrossOrigin(origins = "http://localhost:4200")
public class DoctorController {

    @Autowired
    DoctorService doctorService;

    @GetMapping
    public List<Doctor> getAllDoctors(){
        return doctorService.getAllDoctors();
    }

    @PostMapping
    public ResponseEntity<?> createDoctor(@RequestBody Doctor doctor){
        Doctor createdDoctor = doctorService.createDoctor(doctor);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdDoctor);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getDoctorById(@PathVariable long id){
        Doctor doctor = doctorService.getDoctorById(id);
        if (doctor == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Doctor not found with id:"+id);
        }
        return ResponseEntity.ok(doctor);
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> updateDoctor(@PathVariable long id , @RequestBody Doctor doctor){
        Doctor existingDoctor = doctorService.getDoctorById(id);
        if (existingDoctor == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Doctor not found with id : " +id);
        }
        Doctor updatedDoctor = doctorService.updateDoctor(id, doctor);
        return ResponseEntity.ok(updatedDoctor);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDoctor(@PathVariable long id) {
        try {
            doctorService.deleteDoctor(id);
            return ResponseEntity.status(HttpStatus.OK).body("Doctor deleted successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }


}
