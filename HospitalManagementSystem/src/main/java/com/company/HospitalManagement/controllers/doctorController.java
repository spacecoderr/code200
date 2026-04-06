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
@RequestMapping("/doctors")
public class doctorController {

    @Autowired
    private appointmentService service;

    @GetMapping("/doctorAppointments")
    public String showDoctorAppointments(Model model, Authentication authentication) {
        String doctorName = authentication.getName();
        List<appointment> doctorAppointments = service.findByDoctorName(doctorName);
        model.addAttribute("doctorAppointments", doctorAppointments);
        return "doctorAppointments";
    }

    @GetMapping("/createPrescription")
    public String showPrescriptionForm(@RequestParam("id") Integer id, Model model) {
        // Fetch the existing appointment
        appointment appt = service.getAppointmentById(id);

        // Pass it to the model as 'prescription' to match th:object in varsha.html
        model.addAttribute("prescription", appt);
        return "varsha";
    }

    @PostMapping("/savePrescription")
    public String savePrescription(@RequestParam("appointment_id") Integer id,
                                   @RequestParam("prescription") String prescriptionText, // Changed parameter name to match HTML
                                   RedirectAttributes ra) {

        // 1. Fetch the existing appointment
        appointment appt = service.getAppointmentById(id);

        // 2. Update the prescription field
        appt.setPrescription(prescriptionText);

        // 3. Save the updated appointment
        service.save(appt);

        ra.addFlashAttribute("message", "Prescription for " + appt.getPatientName() + " saved successfully!");
        ra.addFlashAttribute("alertClass", "alert-success");

        return "redirect:/doctors/doctorAppointments";
    }
}