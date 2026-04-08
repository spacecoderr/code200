package com.company.HospitalManagement.prescription;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test; // Added missing Test import
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat; // Use AssertJ for better readability

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class prescriptionRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private prescriptionRepository repository;

    private prescription testRx;

    @BeforeEach
    public void setUp() {
        // Create a test prescription object
        testRx = new prescription();
        testRx.setPatientName("Varsha");
        testRx.setDoctorName("Dr. Bijoy");
        testRx.setDescription("Paracetamol 500mg - twice daily for 3 days.");
        testRx.setAppointmentID(101);
    }

    @Test
    public void testSaveAndFindByPatientName() {
        // Arrange: Persist the data
        entityManager.persist(testRx);
        entityManager.flush();

        // Act: Search for the patient
        List<prescription> results = repository.findByPatientName("Varsha");

        // Assert: Verify results
        assertThat(results).isNotEmpty();
        assertThat(results.get(0).getDescription()).contains("Paracetamol");
        assertThat(results.get(0).getPatientName()).isEqualTo("Varsha");
    }

    @Test
    public void testFindByDoctorName() {
        // Arrange
        entityManager.persist(testRx);
        entityManager.flush();

        // Act
        List<prescription> results = repository.findByDoctorName("Dr. Bijoy");

        // Assert
        assertThat(results).isNotEmpty();
        assertThat(results.get(0).getDoctorName()).isEqualTo("Dr. Bijoy");
    }

    @Test
    public void testEmptyResultsForUnknownPatient() {
        // Act
        List<prescription> results = repository.findByPatientName("Unknown User");

        // Assert
        assertThat(results).isEmpty();
    }
}