package com.adda.app.dto;

import com.adda.app.enums.JobCardStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Transfer Object representing a comprehensive workshop Job Card.
 *
 * <p>Captures all repair activity details, labor operations, replaced parts,
 * associated customer &amp; vehicle relations, and real-time cost accumulations.</p>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JobCardDto {

    /** Primary key of the job card. */
    private Long id;

    /** Unique human-readable job card number (e.g., JC-20261023-0001). */
    private String jobCardNumber;

    // ================= Relational Vehicle Details ================= //

    /** ID of the vehicle under repair. */
    private Long vehicleId;

    /** License plate / registration number of the vehicle. */
    private String vehicleRegistrationNumber;

    /** Vehicle manufacturer. */
    private String vehicleMake;

    /** Vehicle model name. */
    private String vehicleModel;

    // ================= Relational Customer Details ================= //

    /** ID of the vehicle owner. */
    private Long customerId;

    /** Full name of the vehicle owner. */
    private String customerName;

    /** Contact phone number of the vehicle owner. */
    private String customerPhone;

    // ================= Relational Booking Details ================= //

    /** ID of the origin service booking if converted from an appointment. */
    private Long bookingId;

    // ================= Repair Operations & Metadata ================= //

    /** Name of the mechanic assigned to the repair job. */
    private String mechanicName;

    /** Initial customer complaint or reported defect. */
    private String complaint;

    /** Inspection observations noted by the workshop mechanic. */
    private String inspectionNotes;

    /** Current repair workflow status (RECEIVED, DIAGNOSIS, IN_PROGRESS, etc.). */
    private JobCardStatus status;

    /** Timestamp when the job card was officially opened. */
    private LocalDateTime openedAt;

    /** Target completion date estimated for delivery. */
    private LocalDate targetCompletionDate;

    /** Timestamp when work was completed and vehicle delivered. */
    private LocalDateTime completedAt;

    // ================= Relational Line Items & Parts ================= //

    /** List of labor and service tasks logged for this repair. */
    @Builder.Default
    private List<JobCardItemDto> items = new ArrayList<>();

    /** List of spare parts issued and consumed by this repair. */
    @Builder.Default
    private List<JobCardPartDto> parts = new ArrayList<>();

    // ================= Financial Aggregates ================= //

    /** Total cost of all labor line items. */
    private BigDecimal laborTotal;

    /** Total cost of all issued spare parts. */
    private BigDecimal partsTotal;

    /** Estimated overall cost (laborTotal + partsTotal). */
    private BigDecimal estimatedTotal;

    // ================= Relational Invoice Details ================= //

    /** Unique ID of the generated invoice, if billed. */
    private Long invoiceId;

    /** Serial invoice number (e.g., INV-20261023-0001), if generated. */
    private String invoiceNumber;
}
