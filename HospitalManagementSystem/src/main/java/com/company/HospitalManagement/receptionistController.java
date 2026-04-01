package com.company.HospitalManagement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@RequestMapping("/receptionist")
public class receptionistController {

    @Autowired
    private appointmentService service;

    @Autowired
    private EventJpaRepository eventRepository;

    @GetMapping("/receptionistAppointments")
    public String showReceptionistAppointments(Model model) {
        List<appointment> listAppointments = service.listAll();
        model.addAttribute("listAppointments", listAppointments);
        return "receptionistAppointments";
    }

    @GetMapping("/confirm")
    public String showConfirmForm(@RequestParam("id") Integer id, Model model) {
        model.addAttribute("appointmentId", id);
        return "confirm";
    }

    @GetMapping("/receptionistSchedule")
    public String receptionistSchedule(Model model) {
        List<Event> events = eventRepository.findAll();
        model.addAttribute("events", events);
        return "receptionistSchedule";
    }

    @GetMapping("/createInvoice")
    public String createInvoice(@RequestParam(value = "id", required = false) Integer id,
                                @RequestParam(value = "patient", required = false) String patient,
                                Model model) {
        invoice i = new invoice();
        i.setAppointmentID(id);
        i.setPatientName(patient);
        model.addAttribute("invoice", i);
        return "invoice";
    }
}