package com.company.HospitalManagement;

import jakarta.persistence.*;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name="invoice") // Fixed: Changed table name from 'prescription' to 'invoice' to match class name
@DynamicUpdate
public class invoice {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="invoice_id")
    private Integer invoiceID;

    @Column(name="patient_name")
    private String patientName;

    @Column(name="appointment_id")
    private Integer appointmentID;

    @Column(name="invoice_details") // Renamed field to avoid SQL keyword conflicts
    private String invoiceDetails;

    public invoice() {}

    public invoice(Integer invoiceID, String patientName, Integer appointmentID, String invoiceDetails) {
        this.invoiceID = invoiceID;
        this.patientName = patientName;
        this.appointmentID = appointmentID;
        this.invoiceDetails = invoiceDetails;
    }

    public Integer getInvoiceID() { return invoiceID; }
    public void setInvoiceID(Integer invoiceID) { this.invoiceID = invoiceID; }

    public String getPatientName() { return patientName; }
    public void setPatientName(String patientName) { this.patientName = patientName; }

    public Integer getAppointmentID() { return appointmentID; }
    public void setAppointmentID(Integer appointmentID) { this.appointmentID = appointmentID; }

    public String getInvoiceDetails() { return invoiceDetails; }
    public void setInvoiceDetails(String invoiceDetails) { this.invoiceDetails = invoiceDetails; }
}