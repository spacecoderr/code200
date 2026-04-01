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
        return "doctorAppointments"; // Removed .html extension
    }

    @GetMapping("/createPrescription")
    public String showPrescriptionForm(@RequestParam("id") Integer id, Model model) {
        model.addAttribute("appointmentId", id);
        return "createPrescription";
    }

    @PostMapping("/savePrescription")
    public String savePrescription(@RequestParam("id") Integer id,
                                   @RequestParam("prescription") String prescription,
                                   RedirectAttributes ra) {
        service.setPrescription(prescription, id);
        ra.addFlashAttribute("message", "Prescription updated successfully!");
        return "redirect:/doctors/doctorAppointments";
    }
}