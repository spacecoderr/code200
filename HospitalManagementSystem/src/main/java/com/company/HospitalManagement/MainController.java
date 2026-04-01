package com.company.HospitalManagement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.List;

@Controller
public class MainController {

    @Autowired
    private appointmentService service;

    @Autowired
    private invoiceservice invoiceService;

    @GetMapping({"/", "/main"})
    public String showMain() {
        return "main";
    }

    @GetMapping("/showPostLogin")
    public String showPostLogin(Authentication authentication) {
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        for (GrantedAuthority authority : authorities) {
            String role = authority.getAuthority();
            if (role.equals("ROLE_DOCTOR")) return "redirect:/doctors";
            if (role.equals("ROLE_RECEPTIONIST")) return "redirect:/receptionist";
            if (role.equals("ROLE_PATIENT")) return "redirect:/patients";
        }
        return "redirect:/main";
    }

    @GetMapping("/patients")
    public String showPatient(Model model, Authentication auth) {
        model.addAttribute("username", auth.getName());
        return "patients";
    }

    @GetMapping("/doctors")
    public String showDoctors(Model model, Authentication auth) {
        model.addAttribute("username", auth.getName());
        return "doctors";
    }

    @GetMapping("/receptionist")
    public String showReceptionist(Model model, Authentication auth) {
        model.addAttribute("username", auth.getName());
        return "receptionist";
    }

    @GetMapping("/add")
    public String newAppointment(Model model) {
        appointment appt = new appointment();
        appt.setConfirmed("Not yet confirmed");
        model.addAttribute("appointment", appt);
        return "add";
    }

    @PostMapping("/save") // Changed to PostMapping for security/standards
    public String saveAppointment(@ModelAttribute("appointment") appointment appointment,
                                  RedirectAttributes ra) {
        appointment.setConfirmed("Not yet confirmed");
        service.save(appointment);
        ra.addFlashAttribute("message", "Appointment booked! ID: " + appointment.getAppointment_id());
        ra.addFlashAttribute("alertClass", "alert-success");
        return "redirect:/patients";
    }

    @PostMapping("/cancel") // Changed to PostMapping
    public String cancel(@RequestParam("appointment_id") Integer id, RedirectAttributes ra) {
        service.delete(id);
        ra.addFlashAttribute("message", "Appointment canceled!");
        ra.addFlashAttribute("alertClass", "alert-success");
        return "redirect:/patients";
    }

    @PostMapping("/confirm") // Changed to PostMapping
    public String confirm(@RequestParam("appointment_id") Integer id, RedirectAttributes ra) {
        service.setConfirmation("confirmed", id);
        ra.addFlashAttribute("message", "Appointment confirmed!");
        return "redirect:/receptionist";
    }

    @GetMapping("/findbystart")
    public String showByDate(Model model, Authentication auth) {
        String today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
        List<appointment> appointments = service.findByDate(today, auth.getName());
        model.addAttribute("appointments", appointments);
        return "findbystart";
    }

    @PostMapping("/saveInvoice")
    public String saveInvoice(@ModelAttribute("invoice") invoice invoice, RedirectAttributes ra) {
        System.out.println("Received Invoice for Patient: " + invoice.getPatientName());
        System.out.println("Details: " + invoice.getInvoiceDetails());
        invoiceService.save(invoice);
        ra.addFlashAttribute("message", "Invoice created!");
        return "redirect:/receptionist/receptionistAppointments";
    }

    @GetMapping("/signup")
    public String showSignupPage(Model model) {
        // This line is MANDATORY to prevent the "Neither BindingResult nor plain target" error
        model.addAttribute("user", new User());
        return "signup";
    }
}