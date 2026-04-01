package com.company.HospitalManagement;

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
        if (eventRepository.count() == 0) {

            // Creating a schedule for Today
            LocalDateTime today = LocalDateTime.now();

            eventRepository.save(Event.builder()
                    .name("Dr. Bijoy")
                    .title("Senior Cardiologist")
                    .description("Cardiology Outpatient Clinic - Room 402")
                    .start(today.withHour(8).withMinute(0))  // 08:00 AM Today
                    .finish(today.withHour(16).withMinute(0)) // 04:00 PM Today
                    .build());

            eventRepository.save(Event.builder()
                    .name("Dr. Smith")
                    .title("ER Trauma Lead")
                    .description("Emergency Room Duty - Critical Care Unit")
                    .start(today.withHour(12).withMinute(0)) // 12:00 PM Today
                    .finish(today.withHour(20).withMinute(0)) // 08:00 PM Today
                    .build());

            System.out.println(">>> HMS V2.0: Daily Schedule Initialized Successfully.");
        }
    }
}