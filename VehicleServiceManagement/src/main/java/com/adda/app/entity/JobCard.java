package com.adda.app.entity;

import com.adda.app.enums.JobCardStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "job_cards")
@Getter
@Setter
public class JobCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "job_card_number", nullable = false, unique = true)
    private String jobCardNumber;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "vehicle_id", nullable = false)
    private Vehicle vehicle;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "booking_id")
    private ServiceBooking booking;

    @NotBlank(message = "Mechanic name is required")
    @Column(name = "mechanic_name", nullable = false)
    private String mechanicName;

    @Column(name = "complaint", columnDefinition = "TEXT")
    private String complaint;

    @Column(name = "inspection_notes", columnDefinition = "TEXT")
    private String inspectionNotes;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private JobCardStatus status = JobCardStatus.RECEIVED;

    @Column(name = "opened_at", nullable = false)
    private LocalDateTime openedAt;

    @Column(name = "target_completion_date")
    private LocalDate targetCompletionDate;

    @Column(name = "completed_at")
    private LocalDateTime completedAt;

    @OneToMany(mappedBy = "jobCard", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<JobCardItem> items = new ArrayList<>();

    @OneToMany(mappedBy = "jobCard", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<JobCardPart> parts = new ArrayList<>();

    @OneToOne(mappedBy = "jobCard", fetch = FetchType.LAZY)
    private Invoice invoice;
}
