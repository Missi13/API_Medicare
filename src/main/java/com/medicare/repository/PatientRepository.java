package com.medicare.repository;

import com.medicare.domain.Patient;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Patient entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
    @Query("select patient from Patient patient where patient.user.login = ?#{principal.username}")
    List<Patient> findByUserIsCurrentUser();
}
