package com.company.HospitalManagement.invoice;

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
class InvoiceserviceTest { // Capitalized 'I' for naming convention

    @Mock
    private invoiceRepository invoiceRepository;

    @InjectMocks
    private invoiceservice invoiceService;

    private invoice testInvoice;

    @BeforeEach
    void setUp() {
        // Assuming your invoice entity has these fields
        testInvoice = new invoice();
        testInvoice.setPatientName("John Doe");
        testInvoice.setAppointmentID(101);
        testInvoice.setInvoiceDetails("Consultation Fee: $50");
    }

    @Test
    void testSaveInvoice() {
        // Arrange
        when(invoiceRepository.save(any(invoice.class))).thenReturn(testInvoice);

        // Act
        invoice saved = invoiceService.save(testInvoice);

        // Assert
        assertThat(saved).isNotNull();
        assertThat(saved.getPatientName()).isEqualTo("John Doe");
        verify(invoiceRepository, times(1)).save(testInvoice);
    }

    @Test
    void testFindByPatient() {
        // Arrange
        List<invoice> invoices = Arrays.asList(testInvoice);
        when(invoiceRepository.findByPatientName("John Doe")).thenReturn(invoices);

        // Act
        List<invoice> results = invoiceService.findByPatient("John Doe");

        // Assert
        assertThat(results).hasSize(1);
        assertThat(results.get(0).getAppointmentID()).isEqualTo(101);
        verify(invoiceRepository, times(1)).findByPatientName("John Doe");
    }

    @Test
    void testFindAll() {
        // Arrange
        when(invoiceRepository.findAll()).thenReturn(Arrays.asList(testInvoice));

        // Act
        List<invoice> results = invoiceService.findAll();

        // Assert
        assertThat(results).isNotEmpty();
        verify(invoiceRepository, times(1)).findAll();
    }
}