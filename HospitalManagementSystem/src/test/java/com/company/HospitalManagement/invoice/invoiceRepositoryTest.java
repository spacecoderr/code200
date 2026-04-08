package com.company.HospitalManagement.invoice;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)

class InvoiceRepositoryTests {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private invoiceRepository invoiceRepository;

    private invoice testInvoice;

    @BeforeEach
    public void setUp() {
        testInvoice = new invoice();
        testInvoice.setPatientName("John Doe");
        testInvoice.setAppointmentID(1);
        testInvoice.setInvoiceDetails("Consultation Fee: $100, Lab Tests: $50");
    }

    @Test
    public void testSaveInvoice() {
        // Act
        invoice saved = invoiceRepository.save(testInvoice);
        entityManager.flush();

        // Assert
        assertThat(saved).isNotNull();
        assertThat(saved.getInvoiceID()).isNotNull();
        assertThat(saved.getPatientName()).isEqualTo("John Doe");
    }

    @Test
    public void testFindInvoiceById() {
        // Arrange
        invoice saved = invoiceRepository.save(testInvoice);
        entityManager.flush();
        Integer invoiceId = saved.getInvoiceID();

        // Act
        Optional<invoice> found = invoiceRepository.findById(invoiceId);

        // Assert
        assertThat(found).isPresent();
        assertThat(found.get().getPatientName()).isEqualTo("John Doe");
    }

    @Test
    public void testFindByPatientName() {
        // Arrange
        invoiceRepository.save(testInvoice);

        invoice invoice2 = new invoice();
        invoice2.setPatientName("Jane Doe");
        invoice2.setAppointmentID(2);
        invoice2.setInvoiceDetails("Consultation Fee: $150");
        invoiceRepository.save(invoice2);

        entityManager.flush();

        // Act
        List<invoice> results = invoiceRepository.findByPatientName("John Doe");

        // Assert
        assertThat(results).isNotEmpty();
        assertThat(results.get(0).getPatientName()).isEqualTo("John Doe");
    }

    @Test
    public void testFindByAppointmentID() {
        // Arrange
        invoiceRepository.save(testInvoice);
        entityManager.flush();

        // Act
        List<invoice> results = invoiceRepository.findByAppointmentID(1);

        // Assert
        assertThat(results).isNotEmpty();
        assertThat(results.get(0).getAppointmentID()).isEqualTo(1);
    }

    @Test
    public void testFindByPatientNameNotFound() {
        // Act
        List<invoice> results = invoiceRepository.findByPatientName("NonExistent");

        // Assert
        assertThat(results).isEmpty();
    }

    @Test
    public void testDeleteInvoice() {
        // Arrange
        invoice saved = invoiceRepository.save(testInvoice);
        entityManager.flush();
        Integer invoiceId = saved.getInvoiceID();

        // Act
        invoiceRepository.deleteById(invoiceId);
        entityManager.flush();
        Optional<invoice> deleted = invoiceRepository.findById(invoiceId);

        // Assert
        assertThat(deleted).isEmpty();
    }
}