package com.medicare.repository;

import com.medicare.domain.Patient;

import java.util.List;

import com.medicare.service.dto.PatientDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Patient entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

    @Query("select patient from Patient patient where patient.user.login = ?#{principal.username}")
    Page<Patient> findByUserIsCurrentUser(Pageable pageable);
}
