package com.company.HospitalManagement.controllers;


import com.company.HospitalManagement.CalendarApplication.EventJpaRepository;
import com.company.HospitalManagement.appointment.appointment;
import com.company.HospitalManagement.appointment.appointmentService;
import com.company.HospitalManagement.invoice.invoice;
import com.company.HospitalManagement.invoice.invoiceservice;
import com.company.HospitalManagement.CalendarApplication.Event; // ADD THIS
import io.micrometer.observation.Observation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/appointments")
public class PatientController {
    @Autowired
    private EventJpaRepository eventRepository;

    @Autowired
    private appointmentService service;

    @Autowired
    private invoiceservice invoiceService;

    /**
     * View all appointments for the currently logged-in patient.
     */
    @GetMapping("/myAppointments")
    public String myAppointments(Model model, Authentication auth) {
        // Direct authentication injection ensures we fetch data for the right person
        List<appointment> listAppointments = service.findByPatientName(auth.getName());
        model.addAttribute("listAppointments", listAppointments);
        return "myAppointments";
    }

    /**
     * View all invoices for the currently logged-in patient.
     */
    @GetMapping("/myInvoices")
    public String myInvoices(Model model, Authentication auth) {
        List<invoice> listInvoices = invoiceService.findByPatient(auth.getName());
        model.addAttribute("listInvoices", listInvoices);
        return "myInvoices";
    }

    /**
     * Show the 'add.html' booking form.
     */
    @GetMapping("/add")
    public String showBookingForm(Model model) {
        // Fetch every single event shift from the database
        List<Event> allSchedules = eventRepository.findAll();

        // Debugging line: Look at your IntelliJ console to see if this is 0
        System.out.println("Doctors found in DB: " + allSchedules.size());

        model.addAttribute("appointment", new appointment());

        // We are naming the list "schedules" now
        model.addAttribute("schedules", allSchedules);
        return "add";
    }
    /**
     * Process the booking form. Redirects back to list with a success message.
     */
    @PostMapping("/save")
    public String saveAppointment(@ModelAttribute("appointment") appointment appt,
                                  Authentication auth,
                                  RedirectAttributes ra) {
        // Business logic: set patient name and default status
        appt.setPatientName(auth.getName());
        appt.setConfirmed("pending");

        service.save(appt);

        // Flash attributes survive the redirect for one page load
        ra.addFlashAttribute("message", "Appointment successfully created!");
        ra.addFlashAttribute("alertClass", "alert-success");

        return "redirect:/appointments/myAppointments";
    }

    /**
     * Handle appointment deletion.
     */
    @PostMapping("/cancel")
    public String cancelAppointment(@RequestParam("id") Integer id, RedirectAttributes ra) {
        service.delete(id);
        ra.addFlashAttribute("message", "Appointment cancelled successfully.");
        ra.addFlashAttribute("alertClass", "alert-info");
        return "redirect:/appointments/myAppointments";
    }
}