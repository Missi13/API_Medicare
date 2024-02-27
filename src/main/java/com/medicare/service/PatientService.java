package com.medicare.service;

import com.medicare.domain.Patient;
import com.medicare.domain.User;
import com.medicare.repository.PatientRepository;
import com.medicare.repository.UserRepository;
import com.medicare.security.SecurityUtils;
import com.medicare.service.dto.PatientDTO;
import com.medicare.service.dto.UserDTO;
import com.medicare.service.mapper.PatientMapper;
import java.util.Optional;

import com.medicare.web.rest.errors.BadRequestAlertException;
import com.medicare.web.rest.errors.ErrorConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Patient}.
 */
@Service
@Transactional
public class PatientService {

    private final Logger log = LoggerFactory.getLogger(PatientService.class);

    private final PatientRepository patientRepository;
    private final UserRepository userRepository;

    private final PatientMapper patientMapper;

    public PatientService(PatientRepository patientRepository, UserRepository userRepository, PatientMapper patientMapper) {
        this.patientRepository = patientRepository;
        this.userRepository = userRepository;
        this.patientMapper = patientMapper;
    }

    /**
     * Save a patient.
     *
     * @param patientDTO the entity to save.
     * @return the persisted entity.
     */
    public PatientDTO save(PatientDTO patientDTO) {
        log.debug("Request to save Patient : {}", patientDTO);

        Optional<String> currentUserLogin = SecurityUtils.getCurrentUserLogin();
        if (currentUserLogin != null){
            User currentUser = userRepository.findUserByLogin(currentUserLogin);
            Patient patient = patientMapper.toEntity(patientDTO);
            patient.setUser(currentUser);
            patient = patientRepository.save(patient);
            return patientMapper.toDto(patient);
        } else {
            throw new BadRequestAlertException("invalid date", "", ErrorConstants.INVALID_DATE);
        }

    }

    /**
     * Update a patient.
     *
     * @param patientDTO the entity to save.
     * @return the persisted entity.
     */
    public PatientDTO update(PatientDTO patientDTO) {
        log.debug("Request to update Patient : {}", patientDTO);
        Patient patient = patientMapper.toEntity(patientDTO);
        patient = patientRepository.save(patient);
        return patientMapper.toDto(patient);
    }

    /**
     * Partially update a patient.
     *
     * @param patientDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<PatientDTO> partialUpdate(PatientDTO patientDTO) {
        log.debug("Request to partially update Patient : {}", patientDTO);

        return patientRepository
            .findById(patientDTO.getId())
            .map(existingPatient -> {
                patientMapper.partialUpdate(existingPatient, patientDTO);

                return existingPatient;
            })
            .map(patientRepository::save)
            .map(patientMapper::toDto);
    }

    /**
     * Get all the patients.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<PatientDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Patients");
        return patientRepository.findAll(pageable).map(patientMapper::toDto);
    }

    /**
     * Get all the patients.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<PatientDTO> findByUserIsCurrentUser(Pageable pageable) {
        log.debug("Request to get all Patients by CurrentUser");
        return patientRepository.findByUserIsCurrentUser(pageable).map(patientMapper::toDto);




    }

    /**
     * Get one patient by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PatientDTO> findOne(Long id) {
        log.debug("Request to get Patient : {}", id);
        return patientRepository.findById(id).map(patientMapper::toDto);
    }

    /**
     * Delete the patient by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Patient : {}", id);
        patientRepository.deleteById(id);
    }
}
