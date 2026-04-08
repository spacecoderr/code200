package com.company.HospitalManagement.prescription;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PrescriptionServiceTest { // Capitalized P for naming convention

    @Mock
    private prescriptionRepository repository;

    @InjectMocks
    private prescriptionService service;

    private prescription testRx;

    @BeforeEach
    void setUp() {
        testRx = new prescription();
        testRx.setPatientName("Varsha");
        testRx.setDoctorName("Dr. Bijoy");
        // Ensure these setters match your entity fields (e.g., details, medicineName)
        testRx.setDescription("Take 1 tablet after meals.");
    }

    @Test
    void testSavePrescription() {
        // Arrange
        when(repository.save(any(prescription.class))).thenReturn(testRx);

        // Act
        prescription saved = service.save(testRx);

        // Assert
        assertThat(saved).isNotNull();
        assertThat(saved.getPatientName()).isEqualTo("Varsha");
        verify(repository, times(1)).save(testRx);
    }

    @Test
    void testFindByPatientName() {
        // Arrange
        List<prescription> list = Arrays.asList(testRx);
        when(repository.findByPatientName("Varsha")).thenReturn(list);

        // Act
        List<prescription> results = service.findByPatientName("Varsha");

        // Assert
        assertThat(results).isNotEmpty();
        assertThat(results.get(0).getDoctorName()).isEqualTo("Dr. Bijoy");
        verify(repository, times(1)).findByPatientName("Varsha");
    }

    @Test
    void testFindByDoctorName() {
        // Arrange
        List<prescription> list = Arrays.asList(testRx);
        when(repository.findByDoctorName("Dr. Bijoy")).thenReturn(list);

        // Act
        List<prescription> results = service.findByDoctorName("Dr. Bijoy");

        // Assert
        assertThat(results).hasSize(1);
        verify(repository, times(1)).findByDoctorName("Dr. Bijoy");
    }
}