package com.adda.app.dto;

import com.adda.app.enums.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Transfer Object representing a customer invoice.
 *
 * <p>Flattens job card metadata, vehicle information, owner contact details,
 * itemized labor, spare parts, payment history, and computed outstanding balances.</p>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InvoiceDto {

    /** Primary key of the invoice. */
    private Long id;

    /** Unique human-readable invoice serial number (e.g., INV-20261023-0001). */
    private String invoiceNumber;

    // ================= Relational JobCard Details ================= //

    /** ID of the billed job card. */
    private Long jobCardId;

    /** Serial number of the billed job card. */
    private String jobCardNumber;

    /** Mechanic who serviced the vehicle. */
    private String mechanicName;

    // ================= Relational Vehicle Details ================= //

    /** ID of the serviced vehicle. */
    private Long vehicleId;

    /** License plate registration of the serviced vehicle. */
    private String vehicleRegistrationNumber;

    /** Vehicle manufacturer. */
    private String vehicleMake;

    /** Vehicle model. */
    private String vehicleModel;

    // ================= Relational Customer Details ================= //

    /** ID of the customer being billed. */
    private Long customerId;

    /** Name of the customer. */
    private String customerName;

    /** Customer phone number. */
    private String customerPhone;

    /** Customer email address. */
    private String customerEmail;

    // ================= Financial Billing Details ================= //

    /** Date the invoice was generated. */
    private LocalDate invoiceDate;

    /** Sum of all labor line item costs. */
    private BigDecimal laborTotal;

    /** Sum of all issued spare parts costs. */
    private BigDecimal partsTotal;

    /** Promotional or goodwill discount applied. */
    private BigDecimal discount;

    /** Calculated tax amount. */
    private BigDecimal taxAmount;

    /** Final payable grand total ((labor + parts - discount) + tax). */
    private BigDecimal grandTotal;

    /** Payment status (UNPAID, PARTIALLY_PAID, PAID). */
    private PaymentStatus paymentStatus;

    // ================= Relational Collections ================= //

    /** History of payments recorded against this invoice. */
    @Builder.Default
    private List<PaymentDto> payments = new ArrayList<>();

    /** Itemized labor and service operations. */
    @Builder.Default
    private List<JobCardItemDto> items = new ArrayList<>();

    /** Itemized spare parts used. */
    @Builder.Default
    private List<JobCardPartDto> parts = new ArrayList<>();

    // ================= Live Balance Computations ================= //

    /** Total sum of all payments received so far. */
    private BigDecimal paidAmount;

    /** Remaining unpaid balance (grandTotal - paidAmount). */
    private BigDecimal outstandingBalance;
}
