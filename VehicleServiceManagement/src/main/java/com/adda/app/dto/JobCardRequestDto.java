package com.adda.app.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

/**
 * Request payload DTO for creating a new workshop job card directly.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JobCardRequestDto {

    /** Primary key ID of the vehicle to service (required). */
    @NotNull(message = "Vehicle ID is required")
    private Long vehicleId;

    /** Optional reference to an appointment booking. */
    private Long bookingId;

    /** Name of the mechanic assigned to lead the job (required). */
    @NotBlank(message = "Mechanic name is required")
    private String mechanicName;

    /** Description of customer complaint or requested inspection. */
    private String complaint;

    /** Workshop technician inspection findings. */
    private String inspectionNotes;

    /** Estimated delivery / completion date. */
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate targetCompletionDate;
}
