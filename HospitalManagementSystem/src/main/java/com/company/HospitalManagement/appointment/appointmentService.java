package com.company.HospitalManagement.appointment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class appointmentService {

    @Autowired
    private appointmentRepository appointmentRepo; // Renamed from 'bookAppointment' for clarity

    /**
     * Required for Prescription Controller and Tests.
     * Returns the appointment or throws an error if not found.
     */
    public appointment getAppointmentById(Integer id) {
        return appointmentRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Appointment not found with id: " + id));
    }

    /**
     * Returns the saved appointment.
     * This fixes the "void cannot be converted" error in your tests!
     */
    public appointment save(appointment appt) {
        return appointmentRepo.save(appt);
    }

    public List<appointment> listAll() {
        return appointmentRepo.findAll();
    }

    public void delete(Integer id) {
        appointmentRepo.deleteById(id);
    }

    public void setConfirmation(String confirmation, Integer id) {
        appointmentRepo.setConfirmation(confirmation, id);
    }

    public int setPrescription(String prescription, Integer id) {
        return appointmentRepo.setPrescription(prescription, id);
    }

    // --- Cleaned up Duplicate Methods ---

    public List<appointment> findByPatientName(String patientName) {
        return appointmentRepo.findByPatientName(patientName);
    }

    public List<appointment> findByDoctorName(String doctorName) {
        return appointmentRepo.findByDoctorName(doctorName);
    }

    public List<appointment> findByDate(String date, String doctorName) {
        return appointmentRepo.findByDate(date, doctorName);
    }
}