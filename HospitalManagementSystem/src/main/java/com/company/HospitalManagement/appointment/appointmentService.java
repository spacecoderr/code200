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
    private appointmentRepository bookAppointment;

    // --- NEW: Added this for your Prescription Controller ---
    public appointment getAppointmentById(Integer id) {
        return bookAppointment.findById(id)
                .orElseThrow(() -> new RuntimeException("Appointment not found with id: " + id));
    }

    public List<appointment> listAll(){
        return bookAppointment.findAll();
    }

    public void save(appointment appointment) {
        bookAppointment.save(appointment);
    }

    public void delete(Integer id) {
        bookAppointment.deleteById(id);
    }

    public void setConfirmation(String confirmation, Integer id) {
        bookAppointment.setConfirmation(confirmation, id);
    }

    // This is used by your savePrescription method in the controller
    public int setPrescription(String prescription, Integer id) {
        return bookAppointment.setPrescription(prescription, id);
    }

    public Optional<appointment> get(Integer id) {
        return bookAppointment.findById(id);
    }

    public List<appointment> findByPatientName(String patientName) {
        return bookAppointment.findByPatientName(patientName);
    }

    public List<appointment> findByDoctorName(String doctorName) {
        return bookAppointment.findByDoctorName(doctorName);
    }

    public List<appointment> findByDate(String date, String doctorName){
        return bookAppointment.findByDate(date, doctorName);
    }
}