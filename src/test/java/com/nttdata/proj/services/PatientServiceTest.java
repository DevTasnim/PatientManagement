package com.nttdata.proj.services;

import com.nttdata.proj.entities.Doctor;
import com.nttdata.proj.entities.Patient;
import com.nttdata.proj.repositories.DoctorRepository;
import com.nttdata.proj.repositories.PatientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class PatientServiceTest {

    @Mock
    private PatientRepository patientRepository;

    @Mock
    private DoctorRepository doctorRepository;
    @InjectMocks
    PatientService patientService;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);
    }
    @Test
     void testGetAllPatients() {
        List<Patient> patients = new ArrayList<>();
        patients.add(new Patient(1L, "test1", "tt", 20, "2004-01-01", "test", null));
        patients.add(new Patient(2L, "test2", "zz", 22, "2002-05-15", "test", null));

        when(patientRepository.findAll()).thenReturn(patients);

        List<Patient> result = patientService.getAllPatients();
        assertEquals(2, result.size());
        assertEquals("test1", result.get(0).getFirstName());
        assertEquals("tt", result.get(0).getLastName());
    }
    @Test
     void testCreatePatient() {
        Patient patient = new Patient(null, "test1", "tt", 20, "2004-01-01", "test", null);
        Patient savedPatient = new Patient(1L, "test1", "tt", 20, "2004-01-01", "test", null);

        when(patientRepository.save(any())).thenReturn(savedPatient);
        Patient result = patientService.createPatient(patient);

        assertEquals(1L, result.getPatientId().longValue());
        assertEquals("test1", result.getFirstName());
        assertEquals("tt", result.getLastName());
    }
    @Test
     void testGetPatientById() {
        long patientId = 1L;
        Patient patient = new Patient(patientId, "test1", "tt", 20, "2004-01-01", "test", null);

        when(patientRepository.findById(patientId)).thenReturn(Optional.of(patient));

        Patient result = patientService.getPatientById(patientId);
        assertEquals(patientId, result.getPatientId().longValue());
        assertEquals("test1", result.getFirstName());
        assertEquals("tt", result.getLastName());
        assertEquals(20,result.getAge());
    }

    @Test
    void testUpdatePatient() {
        long patientId = 1L;
        Patient patient = new Patient(patientId, "test1", "tt", 20, "2004-01-01", "test", null);

        when(patientRepository.existsById(patientId)).thenReturn(true);
        when(patientRepository.save(any())).thenReturn(patient);

        Patient result = patientService.updatePatient(patientId, patient);

        assertEquals(patientId, result.getPatientId().longValue());
        assertEquals("test1", result.getFirstName());
        assertEquals("tt", result.getLastName());
    }
    @Test
    void testDeletePatient() {
        long patientId = 1L;
        when(patientRepository.existsById(patientId)).thenReturn(true);
        patientService.deletePatient(patientId);
        verify(patientRepository, times(1)).deleteById(patientId);
    }

    @Test
    void testGetPatientsByDoctor() {
        Doctor doctor1 = new Doctor(1L, "test1", "tt", "Cardiologist", 10);
        Doctor doctor2 = new Doctor(2L, "test2", "zz", "Dermatologist", 8);

        List<Patient> patients = new ArrayList<>();
        patients.add(new Patient(1L, "Patient1", "yy", 25, "1999-01-01", "test", doctor1));
        patients.add(new Patient(2L, "Patient2", "zz", 35, "1989-05-15", "test", doctor2));
        patients.add(new Patient(3L, "Patient3", "nn", 22, "2002-05-15", "test", doctor1));

        when(patientRepository.findAll()).thenReturn(patients);
        Map<Doctor, List<Patient>> result = patientService.getPatientsByDoctor();
        assertEquals(2, result.size());
        assertEquals(2, result.get(doctor1).size());
        assertEquals(1, result.get(doctor2).size());
    }

    @Test
    void testGetPatientsByAgeRange() {
        List<Patient> patients = new ArrayList<>();
        patients.add(new Patient(1L, "Patient1", "sss", 10, "2014-01-01", "test", null));
        patients.add(new Patient(2L, "Patient2", "aaa", 25, "1999-05-15", "test", null));
        patients.add(new Patient(3L, "Patient3", "zzz", 45, "1979-05-15", "test", null));

        when(patientRepository.findAll()).thenReturn(patients);

        Map<String, List<Patient>> result = patientService.getPatientsByAgeRange();
        assertEquals(5, result.size());
        assertEquals(1, result.get("0-18").size());
        assertEquals(1, result.get("19-35").size());
        assertEquals(1, result.get("36-50").size());
        assertEquals(0, result.get("51-65").size());
        assertEquals(0, result.get("66+").size());
    }
    @Test
    void testSavePatients() {
        List<Patient> patients = new ArrayList<>();
        patients.add(new Patient(1L, "Patient1", "sss", 10, "2014-01-01", "test", null));
        patients.add(new Patient(2L, "Patient2", "aaa", 25, "1999-05-15", "test", null));
        patients.add(new Patient(3L, "Patient3", "zzz", 45, "1979-05-15", "test", null));

        patientService.savePatients(patients);
        verify(patientRepository, times(1)).saveAll(patients);
    }

}