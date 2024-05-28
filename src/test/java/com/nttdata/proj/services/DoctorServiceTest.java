package com.nttdata.proj.services;

import com.nttdata.proj.entities.Doctor;
import com.nttdata.proj.repositories.DoctorRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


class DoctorServiceTest {
    @Mock
    private DoctorRepository doctorRepository;

    @InjectMocks
    private DoctorService doctorService;


    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
     void testGetAllDoctors() {
        List<Doctor> doctors = Arrays.asList(
                new Doctor(1l, "test", "tt", "Cardiologist", 10),
                new Doctor(1l, "test1", "tt", "Dermatologist", 8)
        );
        when(doctorRepository.findAll()).thenReturn(doctors);

        List<Doctor> result = doctorService.getAllDoctors();

        assertEquals(2, result.size());
        assertEquals("test", result.get(0).getFirstName());
        assertEquals("tt", result.get(0).getLastName());
    }

    @Test
     void testCreateDoctor() {
        Doctor doctor = new Doctor(null, "test", "tt", "Cardiologist", 10);
        Doctor savedDoctor = new Doctor(1L, "test", "tt", "Cardiologist", 10);

        when(doctorRepository.save(any())).thenReturn(savedDoctor);

        Doctor result = doctorService.createDoctor(doctor);

        assertNotNull(result);
        assertEquals(1L, result.getDoctorId().longValue());
        assertEquals("test", result.getFirstName());
        assertEquals("tt", result.getLastName());

    }
     @Test
      void testGetDoctorById() {
         Doctor doctor = new Doctor(1L, "test", "tt", "Cardiologist", 10);
         when(doctorRepository.findById(1L)).thenReturn(Optional.of(doctor));

         Doctor result = doctorService.getDoctorById(1L);

         assertNotNull(result);
         assertEquals(1L, result.getDoctorId().longValue());
         assertEquals("test", result.getFirstName());
         assertEquals("tt", result.getLastName());
     }

     @Test
      void testUpdateDoctor() {
         Doctor existingDoctor = new Doctor(1L, "test", "tt", "Cardiologist", 10);
         Doctor updatedDoctor = new Doctor(1L, "test", "tt", "Cardiologist", 15);

         when(doctorRepository.existsById(1L)).thenReturn(true);
         when(doctorRepository.save(any())).thenReturn(updatedDoctor);

         Doctor result = doctorService.updateDoctor(1L, updatedDoctor);

         assertNotNull(result);
         assertEquals(1L, result.getDoctorId().longValue());
         assertEquals(15, result.getYearsOfExperience());
     }

     @Test
      void testDeleteDoctor() {
         long doctorId = 1L;
         when(doctorRepository.existsById(doctorId)).thenReturn(true);
         doctorService.deleteDoctor(doctorId);
         verify(doctorRepository, times(1)).deleteById(doctorId);
     }

}