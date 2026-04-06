package com.company.HospitalManagement.prescription;

import com.company.HospitalManagement.appointment.appointment;
import com.company.HospitalManagement.appointment.appointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/prescriptions")
public class prescriptionController {

    @Autowired
    private prescriptionService rxService; // Renamed to avoid duplication

    @Autowired
    private appointmentService apptService; // Renamed to avoid duplication

    // FOR PATIENTS: To see their own medical history
    @GetMapping("/viewPrescription")
    public String viewMyPrescriptions(Model model, Authentication authentication) {
        // 1. Get the logged-in patient's username
        String patientName = authentication.getName();

        // 2. Fetch appointments for this patient using the injected apptService bean
        List<appointment> myAppointments = apptService.findByPatientName(patientName);

        // 3. Filter only those that HAVE a prescription written
        List<appointment> myPrescriptions = myAppointments.stream()
                .filter(a -> a.getPrescription() != null &&
                        !a.getPrescription().equals("yes") &&
                        !a.getPrescription().equals("no"))
                .collect(Collectors.toList());

        model.addAttribute("prescriptions", myPrescriptions);

        // Ensure you have viewPrescriptions.html in your templates folder
        return "viewPrescriptions";
    }

    // FOR DOCTORS: To save the data from the 'createPrescription' form
    @PostMapping("/save")
    public String savePrescription(@ModelAttribute("prescription") prescription prescriptionData,
                                   Authentication auth,
                                   RedirectAttributes ra) {

        // 1. Link the current logged-in Doctor's name
        prescriptionData.setDoctorName(auth.getName());

        // 2. Update the appointment status in the appointment table
        // This ensures the doctor dashboard shows the "Prescribed" badge
        apptService.setPrescription("prescribed", prescriptionData.getAppointmentID());

        // 3. Save the formal prescription record to the prescription table
        rxService.save(prescriptionData);

        ra.addFlashAttribute("message", "Prescription generated and saved to patient record!");
        ra.addFlashAttribute("alertClass", "alert-success");

        return "redirect:/doctors/doctorAppointments";
    }
}