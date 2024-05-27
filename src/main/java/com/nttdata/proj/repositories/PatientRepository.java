package com.nttdata.proj.repositories;

import com.nttdata.proj.entities.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

}
