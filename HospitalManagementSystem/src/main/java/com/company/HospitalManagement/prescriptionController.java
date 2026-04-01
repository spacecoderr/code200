package com.company.HospitalManagement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/prescriptions")
public class prescriptionController {

    @Autowired
    private prescriptionService service;

    @Autowired
    private appointmentService appointmentService;

    @GetMapping("/viewPrescription")
    public String viewPrescription(Model model, Authentication auth) {
        List<prescription> prescriptions = service.findByPatientName(auth.getName());
        model.addAttribute("prescriptions", prescriptions);
        return "viewPrescriotions"; // Ensure this matches templates/viewPrescriotions.html
    }

    @PostMapping("/save") // Changed to PostMapping and fixed the path
    public String savePrescription(@ModelAttribute("prescription") prescription prescription,
                                   Authentication auth,
                                   RedirectAttributes ra) {
        // 1. Update the appointment status to 'prescribed'
        appointmentService.setPrescription("prescribed", prescription.getAppointmentID());

        // 2. Attach the current doctor's name
        prescription.setDoctorName(auth.getName());

        // 3. Save the prescription record
        service.save(prescription);

        ra.addFlashAttribute("message", "Prescription successfully saved!");
        ra.addFlashAttribute("alertClass", "alert-success");

        return "redirect:/doctors/doctorAppointments";
    }
}