package com.company.HospitalManagement.prescription;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface prescriptionRepository extends JpaRepository<prescription, Integer> {

    // Matches the field 'patientName' in your prescription entity
    List<prescription> findByPatientName(String patientName);

    // Additional helpful query for doctors
    List<prescription> findByDoctorName(String doctorName);
}