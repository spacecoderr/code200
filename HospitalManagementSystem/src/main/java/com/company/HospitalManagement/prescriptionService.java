package com.company.HospitalManagement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
public class prescriptionService {

    @Autowired
    private prescriptionRepository repository;

    public void save(prescription p) {
        repository.save(p);
    }

    public List<prescription> findByPatientName(String patientName) {
        return repository.findByPatientName(patientName);
    }

    public List<prescription> findByDoctorName(String doctorName) {
        return repository.findByDoctorName(doctorName);
    }
}