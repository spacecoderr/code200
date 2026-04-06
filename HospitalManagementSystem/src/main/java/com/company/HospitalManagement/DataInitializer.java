package com.company.HospitalManagement;

import com.company.HospitalManagement.CalendarApplication.Event;
import com.company.HospitalManagement.CalendarApplication.EventJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private EventJpaRepository eventRepository;

    @Override
    public void run(String... args) {
        // IMPORTANT: If you want to see your NEW doctors (Ananya, Raj, etc.)
        // you MUST delete existing data from the database once,
        // otherwise this 'if' block will skip because count is not 0.
        if (eventRepository.count() == 0) {

            LocalDateTime today = LocalDateTime.now();

            // Dr. Bijoy
            eventRepository.save(Event.builder()
                    .name("Dr. Bijoy")
                    .title("Senior Cardiologist")
                    .description("Morning Cardiology Checkup - Room 402")
                    .start(today.withHour(9).withMinute(0))
                    .finish(today.withHour(12).withMinute(0))
                    .build());

            eventRepository.save(Event.builder()
                    .name("Dr. Bijoy")
                    .title("Senior Cardiologist")
                    .description("Evening Consultation - Room 405")
                    .start(today.withHour(17).withMinute(0))
                    .finish(today.withHour(19).withMinute(0))
                    .build());

            // Dr. Smith
            eventRepository.save(Event.builder()
                    .name("Dr. Smith")
                    .title("ER Trauma Lead")
                    .description("Night Emergency Shift - ICU")
                    .start(today.withHour(22).withMinute(0))
                    .finish(today.plusDays(1).withHour(6).withMinute(0))
                    .build());

            // Dr. House
            eventRepository.save(Event.builder()
                    .name("Dr. House")
                    .title("Diagnostic Medicine")
                    .description("Special Case Review - Room 101")
                    .start(today.withHour(10).withMinute(0))
                    .finish(today.withHour(12).withMinute(0))
                    .build());

            // Dr. Ananya
            eventRepository.save(Event.builder()
                    .name("Dr. Ananya")
                    .title("Neurologist")
                    .description("Neuro Consultation - Room 210")
                    .start(today.withHour(10).withMinute(0))
                    .finish(today.withHour(13).withMinute(0))
                    .build());

            // Dr. Raj
            eventRepository.save(Event.builder()
                    .name("Dr. Raj")
                    .title("Orthopedic Surgeon")
                    .description("Fracture Clinic - Room 115")
                    .start(today.withHour(8).withMinute(30))
                    .finish(today.withHour(11).withMinute(30))
                    .build());

            // Dr. Meera
            eventRepository.save(Event.builder()
                    .name("Dr. Meera")
                    .title("Pediatrician")
                    .description("Child Care Clinic - Room 101")
                    .start(today.withHour(9).withMinute(0))
                    .finish(today.withHour(12).withMinute(30))
                    .build());

            // Dr. Arjun
            eventRepository.save(Event.builder()
                    .name("Dr. Arjun")
                    .title("General Physician")
                    .description("General OPD - Room 12")
                    .start(today.withHour(8).withMinute(0))
                    .finish(today.withHour(11).withMinute(0))
                    .build());

            System.out.println(">>> HMS V2.0: Daily Schedule Initialized with ALL doctors.");
        }
    }
}