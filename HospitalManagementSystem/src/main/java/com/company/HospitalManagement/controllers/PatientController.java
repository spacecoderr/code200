package com.company.HospitalManagement.controllers;

import com.company.HospitalManagement.appointment.appointment;
import com.company.HospitalManagement.appointment.appointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/appointments")
public class PatientController {

    @Autowired
    private appointmentService service;

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
     * Show the 'add.html' booking form.
     */
    @GetMapping("/add")
    public String showBookingForm(Model model) {
        model.addAttribute("appointment", new appointment());
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