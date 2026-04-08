package com.company.HospitalManagement.prescription;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name="prescription")
@DynamicUpdate
@Getter
@Setter
public class prescription {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="prescription_id")
    private Integer prescriptionID;

    @Column(name="patient_name")
    private String patientName;

    @Column(name="appointment_id")
    private Integer appointmentID;

    @Column(name="description", columnDefinition="TEXT")
    private String description;

    @Column(name="doctor_name")
    private String doctorName;

    public prescription() {}

    public prescription(String patientName, Integer appointmentID, String description, String doctorName) {
        this.patientName = patientName;
        this.appointmentID = appointmentID;
        this.description = description;
        this.doctorName = doctorName;
    }

    public Integer getPrescriptionID() { return prescriptionID; }
    public void setPrescriptionID(Integer prescriptionID) { this.prescriptionID = prescriptionID; }

    public String getPatientName() { return patientName; }
    public void setPatientName(String patientName) { this.patientName = patientName; }

    public Integer getAppointmentID() { return appointmentID; }
    public void setAppointmentID(Integer appointmentID) { this.appointmentID = appointmentID; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getDoctorName() { return doctorName; }
    public void setDoctorName(String doctorName) { this.doctorName = doctorName; }
}