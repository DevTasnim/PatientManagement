package com.nttdata.proj.services;

import com.nttdata.proj.entities.Doctor;
import com.nttdata.proj.repositories.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
public class DoctorService {

    @Autowired
    DoctorRepository doctorRepository;

    public List<Doctor> getAllDoctors(){
        return doctorRepository.findAll();
    }

    public Doctor createDoctor(Doctor doctor){
        return doctorRepository.save(doctor);
    }
    public Doctor getDoctorById(long id){
         Optional<Doctor> doctorOptional = doctorRepository.findById(id);
         return doctorOptional.orElse(null);
    }

    public Doctor updateDoctor(long id , Doctor doctor){
        if (!doctorRepository.existsById(id)){
            throw new IllegalArgumentException("Doctor with id : " +id+ "does not exists");
        }
        doctor.setDoctorId(id);
        return doctorRepository.save(doctor);

    }
    public void deleteDoctor(long id) {
        if (!doctorRepository.existsById(id)) {
            throw new IllegalArgumentException("Doctor with id: " + id + " does not exist");
        }
        doctorRepository.deleteById(id);
    }



}
