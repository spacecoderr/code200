package com.company.HospitalManagement.appointment;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AppointmentServiceTest {

    @Mock
    private appointmentRepository appointmentRepository;

    @InjectMocks
    private appointmentService appointmentService;

    private appointment testAppt;

    @BeforeEach
    public void setUp() {
        testAppt = appointment.builder()
                .appointment_id(1)
                .patientName("Varsha")
                .doctorName("Dr. Bijoy")
                .prescription("yes")
                .build();
    }

    @Test
    public void testSaveAppointment() {
        // Arrange
        when(appointmentRepository.save(any(appointment.class))).thenReturn(testAppt);

        // Act: This line NO LONGER throws an incompatible types error
        appointment saved = appointmentService.save(testAppt);

        // Assert
        assertThat(saved).isNotNull();
        assertThat(saved.getPatientName()).isEqualTo("Varsha");
        verify(appointmentRepository, times(1)).save(testAppt);
    }

    @Test
    public void testGetAppointmentById() {
        // Arrange
        when(appointmentRepository.findById(1)).thenReturn(Optional.of(testAppt));

        // Act
        appointment found = appointmentService.getAppointmentById(1);

        // Assert
        assertThat(found).isNotNull();
        assertThat(found.getAppointment_id()).isEqualTo(1);
    }
}