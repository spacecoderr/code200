package com.company.HospitalManagement.appointment;

import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.DynamicUpdate;

@Getter
@Entity
@Table(name="appointment")
@DynamicUpdate
public class appointment {

    // Getters and Setters...
    // Ensure these remain exactly as they are to avoid breaking Thymeleaf links!
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="appointment_id")
    private Integer appointment_id;

    @Column(name="patientName") // Matches database 'patientName'
    private String patientName;

    @Column(name="doctor_name") // Matches database 'doctor_name'
    private String doctorName;

    @Column(name="appointment_date") // Matches database 'appointment_date'
    private String date; // THIS is what Thymeleaf needs to see!

    @Column(name="prescription")
    private String prescription;

    @Column(name="confirmed")
    private String confirmed;

    // Default Constructor
    public appointment() {}

    // Full Constructor
    public appointment(Integer appointment_id, String patientName, String doctorName, String date,
                       String prescription, String confirmed) {
        this.appointment_id = appointment_id;
        this.patientName = patientName;
        this.doctorName = doctorName;
        this.date = date;
        this.prescription = prescription;
        this.confirmed = confirmed;
    }

    public void setAppointment_id(Integer appointment_id) { this.appointment_id = appointment_id; }

    public void setPatientName(String patientName) { this.patientName = patientName; }

    public void setDoctorName(String doctorName) { this.doctorName = doctorName; }

    public void setDate(String date) { this.date = date; }

    public void setPrescription(String prescription) { this.prescription = prescription; }

    public void setConfirmed(String confirmed) { this.confirmed = confirmed; }
}