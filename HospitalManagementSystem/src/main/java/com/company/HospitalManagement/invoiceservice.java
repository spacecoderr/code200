package com.company.HospitalManagement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
public class invoiceservice {

    @Autowired
    private invoiceRepository rep;

    public void save(invoice entity) {
        rep.save(entity);
    }

    public List<invoice> findAll() {
        return rep.findAll();
    }

    public List<invoice> findByPatient(String patientName) {
        return rep.findByPatientName(patientName);
    }

    public List<invoice> findByAppointment(Integer appointmentID) {
        return rep.findByAppointmentID(appointmentID);
    }
}