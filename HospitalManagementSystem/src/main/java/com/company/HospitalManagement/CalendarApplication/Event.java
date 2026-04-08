package com.company.HospitalManagement.CalendarApplication;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name="event")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class Event {

    // Standard Getters and Setters
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String title;
    private String description;
    private LocalDateTime start;
    private LocalDateTime finish;

}
