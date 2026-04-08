package com.company.HospitalManagement.appointment;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name="appointment")
@DynamicUpdate
@Data                // Automatically generates all Getters, Setters, toString, etc.
@Builder             // Useful for creating test data: appointment.builder().doctorName("bijoy").build()
@AllArgsConstructor  // Required for Builder
@NoArgsConstructor
@Setter
@Getter// Required for JPA
public class appointment {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="appointment_id")
    private Integer appointment_id;

    @Column(name="patientName")
    private String patientName;

    @Column(name="doctor_name")
    private String doctorName;

    @Column(name="appointment_date")
    private String date;

    @Column(name="prescription")
    private String prescription;

    @Column(name="confirmed")
    private String confirmed;

}