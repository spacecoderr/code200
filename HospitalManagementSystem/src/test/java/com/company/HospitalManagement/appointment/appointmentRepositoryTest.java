package com.company.HospitalManagement.appointment;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class appointmentRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private appointmentRepository appointmentRepository;

    private appointment testAppointment;

    @BeforeEach
    public void setUp() {
        // Using the Builder we added to the appointment class
        testAppointment = appointment.builder()
                .patientName("John Doe")
                .doctorName("Dr. Smith")
                .date("2026-04-15")
                .confirmed("pending")
                .prescription("yes")
                .build();
    }

    @Test
    public void testSaveAppointment() {
        appointment saved = appointmentRepository.save(testAppointment);
        entityManager.flush();

        assertThat(saved).isNotNull();
        // Updated: matches field name 'appointment_id'
        assertThat(saved.getAppointment_id()).isNotNull();
        assertThat(saved.getPatientName()).isEqualTo("John Doe");
    }

    @Test
    public void testFindAppointmentById() {
        appointment saved = appointmentRepository.save(testAppointment);
        entityManager.flush();
        Integer appointmentId = saved.getAppointment_id();

        Optional<appointment> found = appointmentRepository.findById(appointmentId);

        assertThat(found).isPresent();
        assertThat(found.get().getPatientName()).isEqualTo("John Doe");
    }

    @Test
    public void testFindAppointmentByIdNotFound() {
        Optional<appointment> found = appointmentRepository.findById(999);
        assertThat(found).isEmpty();
    }

    @Test
    public void testFindByPatientName() {
        appointmentRepository.save(testAppointment);

        appointment appointment2 = appointment.builder()
                .patientName("Jane Doe")
                .doctorName("Dr. Jones")
                .date("2026-04-16")
                .confirmed("pending")
                .build();

        appointmentRepository.save(appointment2);
        entityManager.flush();

        List<appointment> results = appointmentRepository.findByPatientName("John Doe");

        assertThat(results).isNotEmpty();
        assertThat(results.get(0).getPatientName()).isEqualTo("John Doe");
    }

    @Test
    public void testFindByDoctorName() {
        appointmentRepository.save(testAppointment);
        entityManager.flush();

        List<appointment> results = appointmentRepository.findByDoctorName("Dr. Smith");

        assertThat(results).isNotEmpty();
        assertThat(results.get(0).getDoctorName()).isEqualTo("Dr. Smith");
    }

    @Test
    public void testDeleteAppointment() {
        appointment saved = appointmentRepository.save(testAppointment);
        entityManager.flush();
        Integer appointmentId = saved.getAppointment_id();

        appointmentRepository.deleteById(appointmentId);
        entityManager.flush();

        Optional<appointment> deleted = appointmentRepository.findById(appointmentId);
        assertThat(deleted).isEmpty();
    }

    @Test
    public void testFindAllAppointments() {
        appointmentRepository.save(testAppointment);

        appointment appointment2 = appointment.builder()
                .patientName("Jane Doe")
                .doctorName("Dr. Jones")
                .build();

        appointmentRepository.save(appointment2);
        entityManager.flush();

        List<appointment> results = appointmentRepository.findAll();

        assertThat(results).isNotEmpty();
        assertThat(results.size()).isGreaterThanOrEqualTo(2);
    }
}