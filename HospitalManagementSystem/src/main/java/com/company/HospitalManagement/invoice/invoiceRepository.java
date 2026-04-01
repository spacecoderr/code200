package com.company.HospitalManagement.invoice;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface invoiceRepository extends JpaRepository<invoice, Integer> {

    // Finds all invoices for a specific patient
    List<invoice> findByPatientName(String patientName);

    // Finds a specific invoice linked to an appointment ID
    List<invoice> findByAppointmentID(Integer appointmentID);
}