package com.adda.app.mapper;

import com.adda.app.dto.*;
import com.adda.app.entity.*;
import com.adda.app.enums.BookingStatus;
import com.adda.app.enums.JobCardStatus;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Central Data Transfer Object (DTO) Mapper.
 *
 * <p>This component provides bidirectional transformations between JPA domain entities
 * and decoupled DTOs (Request, Response, and Summary representations).</p>
 *
 * <p>Architectural Objectives:
 * <ul>
 *   <li><b>Decoupling:</b> Shield internal database schema details and JPA proxies from external API consumers.</li>
 *   <li><b>Circular Reference Prevention:</b> Break bi-directional loops (e.g., Customer <-> Vehicle, JobCard <-> Invoice).</li>
 *   <li><b>Lazy Loading Safety:</b> Extract required fields safely before transactions close, avoiding {@code LazyInitializationException}.</li>
 *   <li><b>Business Calculations:</b> Compute runtime aggregates such as line totals, estimated labor/parts costs, and outstanding invoice balances.</li>
 * </ul>
 * </p>
 */
@Component
public class EntityDtoMapper {

    // ================= CUSTOMER MAPPERS ================= //

    public CustomerSummaryDto toCustomerSummaryDto(Customer customer) {
        if (customer == null) return null;
        return CustomerSummaryDto.builder()
                .id(customer.getId())
                .name(customer.getName())
                .phone(customer.getPhone())
                .email(customer.getEmail())
                .build();
    }

    public CustomerDto toCustomerDto(Customer customer) {
        if (customer == null) return null;
        List<VehicleSummaryDto> vehicleDtos = Collections.emptyList();
        if (customer.getVehicles() != null) {
            vehicleDtos = customer.getVehicles().stream()
                    .map(this::toVehicleSummaryDto)
                    .toList();
        }

        return CustomerDto.builder()
                .id(customer.getId())
                .name(customer.getName())
                .phone(customer.getPhone())
                .email(customer.getEmail())
                .address(customer.getAddress())
                .createdAt(customer.getCreatedAt())
                .vehicles(vehicleDtos)
                .build();
    }

    public Customer toCustomerEntity(CustomerRequestDto dto) {
        if (dto == null) return null;
        Customer customer = new Customer();
        customer.setName(dto.getName());
        customer.setPhone(dto.getPhone());
        customer.setEmail(dto.getEmail());
        customer.setAddress(dto.getAddress());
        return customer;
    }

    // ================= VEHICLE MAPPERS ================= //

    public VehicleSummaryDto toVehicleSummaryDto(Vehicle vehicle) {
        if (vehicle == null) return null;
        return VehicleSummaryDto.builder()
                .id(vehicle.getId())
                .registrationNumber(vehicle.getRegistrationNumber())
                .make(vehicle.getMake())
                .model(vehicle.getModel())
                .fuelType(vehicle.getFuelType())
                .manufacturingYear(vehicle.getManufacturingYear())
                .odometer(vehicle.getOdometer())
                .build();
    }

    public VehicleDto toVehicleDto(Vehicle vehicle) {
        if (vehicle == null) return null;
        Long customerId = null;
        String customerName = null;
        String customerPhone = null;
        CustomerSummaryDto customerSummary = null;

        if (vehicle.getCustomer() != null) {
            customerId = vehicle.getCustomer().getId();
            customerName = vehicle.getCustomer().getName();
            customerPhone = vehicle.getCustomer().getPhone();
            customerSummary = toCustomerSummaryDto(vehicle.getCustomer());
        }

        int bookingsCount = vehicle.getBookings() != null ? vehicle.getBookings().size() : 0;
        int jobCardsCount = vehicle.getJobCards() != null ? vehicle.getJobCards().size() : 0;

        return VehicleDto.builder()
                .id(vehicle.getId())
                .customerId(customerId)
                .customerName(customerName)
                .customerPhone(customerPhone)
                .customer(customerSummary)
                .registrationNumber(vehicle.getRegistrationNumber())
                .make(vehicle.getMake())
                .model(vehicle.getModel())
                .fuelType(vehicle.getFuelType())
                .manufacturingYear(vehicle.getManufacturingYear())
                .odometer(vehicle.getOdometer())
                .totalBookings(bookingsCount)
                .totalJobCards(jobCardsCount)
                .build();
    }

    public Vehicle toVehicleEntity(VehicleRequestDto dto, Customer customer) {
        if (dto == null) return null;
        Vehicle vehicle = new Vehicle();
        vehicle.setCustomer(customer);
        vehicle.setRegistrationNumber(dto.getRegistrationNumber());
        vehicle.setMake(dto.getMake());
        vehicle.setModel(dto.getModel());
        vehicle.setFuelType(dto.getFuelType());
        vehicle.setManufacturingYear(dto.getManufacturingYear());
        vehicle.setOdometer(dto.getOdometer());
        return vehicle;
    }

    // ================= SERVICE BOOKING MAPPERS ================= //

    public ServiceBookingDto toServiceBookingDto(ServiceBooking booking) {
        if (booking == null) return null;
        Long vehicleId = null;
        String regNo = null;
        String make = null;
        String model = null;
        Long customerId = null;
        String customerName = null;
        String customerPhone = null;

        if (booking.getVehicle() != null) {
            Vehicle v = booking.getVehicle();
            vehicleId = v.getId();
            regNo = v.getRegistrationNumber();
            make = v.getMake();
            model = v.getModel();
            if (v.getCustomer() != null) {
                customerId = v.getCustomer().getId();
                customerName = v.getCustomer().getName();
                customerPhone = v.getCustomer().getPhone();
            }
        }

        return ServiceBookingDto.builder()
                .id(booking.getId())
                .vehicleId(vehicleId)
                .vehicleRegistrationNumber(regNo)
                .vehicleMake(make)
                .vehicleModel(model)
                .customerId(customerId)
                .customerName(customerName)
                .customerPhone(customerPhone)
                .bookingDate(booking.getBookingDate())
                .complaint(booking.getComplaint())
                .requestedService(booking.getRequestedService())
                .status(booking.getStatus())
                .build();
    }

    public ServiceBooking toServiceBookingEntity(ServiceBookingRequestDto dto, Vehicle vehicle) {
        if (dto == null) return null;
        ServiceBooking booking = new ServiceBooking();
        booking.setVehicle(vehicle);
        booking.setBookingDate(dto.getBookingDate());
        booking.setComplaint(dto.getComplaint());
        booking.setRequestedService(dto.getRequestedService());
        booking.setStatus(BookingStatus.BOOKED);
        return booking;
    }

    // ================= SPARE PART MAPPERS ================= //

    public SparePartDto toSparePartDto(SparePart part) {
        if (part == null) return null;
        boolean lowStock = part.getStockQuantity() != null &&
                part.getLowStockThreshold() != null &&
                part.getStockQuantity() <= part.getLowStockThreshold();

        return SparePartDto.builder()
                .id(part.getId())
                .partCode(part.getPartCode())
                .name(part.getName())
                .description(part.getDescription())
                .unitPrice(part.getUnitPrice())
                .stockQuantity(part.getStockQuantity())
                .lowStockThreshold(part.getLowStockThreshold())
                .isLowStock(lowStock)
                .build();
    }

    public SparePart toSparePartEntity(SparePartRequestDto dto) {
        if (dto == null) return null;
        SparePart part = new SparePart();
        part.setPartCode(dto.getPartCode());
        part.setName(dto.getName());
        part.setDescription(dto.getDescription());
        part.setUnitPrice(dto.getUnitPrice());
        part.setStockQuantity(dto.getStockQuantity() != null ? dto.getStockQuantity() : 0);
        part.setLowStockThreshold(dto.getLowStockThreshold() != null ? dto.getLowStockThreshold() : 5);
        return part;
    }

    // ================= JOB CARD ITEM & PART MAPPERS ================= //

    public JobCardItemDto toJobCardItemDto(JobCardItem item) {
        if (item == null) return null;
        return JobCardItemDto.builder()
                .id(item.getId())
                .jobCardId(item.getJobCard() != null ? item.getJobCard().getId() : null)
                .description(item.getDescription())
                .quantity(item.getQuantity())
                .unitPrice(item.getUnitPrice())
                .itemType(item.getItemType())
                .totalPrice(item.getTotalPrice())
                .build();
    }

    public JobCardPartDto toJobCardPartDto(JobCardPart part) {
        if (part == null) return null;
        Long sparePartId = null;
        String code = null;
        String name = null;

        if (part.getSparePart() != null) {
            sparePartId = part.getSparePart().getId();
            code = part.getSparePart().getPartCode();
            name = part.getSparePart().getName();
        }

        return JobCardPartDto.builder()
                .id(part.getId())
                .jobCardId(part.getJobCard() != null ? part.getJobCard().getId() : null)
                .sparePartId(sparePartId)
                .sparePartCode(code)
                .sparePartName(name)
                .quantity(part.getQuantity())
                .unitPrice(part.getUnitPrice())
                .totalPrice(part.getTotalPrice())
                .build();
    }

    // ================= JOB CARD MAPPERS ================= //

    public JobCardDto toJobCardDto(JobCard jobCard) {
        if (jobCard == null) return null;

        Long vehicleId = null;
        String regNo = null;
        String make = null;
        String model = null;
        Long customerId = null;
        String customerName = null;
        String customerPhone = null;

        if (jobCard.getVehicle() != null) {
            Vehicle v = jobCard.getVehicle();
            vehicleId = v.getId();
            regNo = v.getRegistrationNumber();
            make = v.getMake();
            model = v.getModel();
            if (v.getCustomer() != null) {
                customerId = v.getCustomer().getId();
                customerName = v.getCustomer().getName();
                customerPhone = v.getCustomer().getPhone();
            }
        }

        Long bookingId = jobCard.getBooking() != null ? jobCard.getBooking().getId() : null;

        List<JobCardItemDto> itemDtos = new ArrayList<>();
        BigDecimal laborTotal = BigDecimal.ZERO;
        if (jobCard.getItems() != null) {
            for (JobCardItem item : jobCard.getItems()) {
                JobCardItemDto dto = toJobCardItemDto(item);
                itemDtos.add(dto);
                if (dto.getTotalPrice() != null) {
                    laborTotal = laborTotal.add(dto.getTotalPrice());
                }
            }
        }

        List<JobCardPartDto> partDtos = new ArrayList<>();
        BigDecimal partsTotal = BigDecimal.ZERO;
        if (jobCard.getParts() != null) {
            for (JobCardPart part : jobCard.getParts()) {
                JobCardPartDto dto = toJobCardPartDto(part);
                partDtos.add(dto);
                if (dto.getTotalPrice() != null) {
                    partsTotal = partsTotal.add(dto.getTotalPrice());
                }
            }
        }

        BigDecimal estimatedTotal = laborTotal.add(partsTotal);

        Long invoiceId = jobCard.getInvoice() != null ? jobCard.getInvoice().getId() : null;
        String invoiceNumber = jobCard.getInvoice() != null ? jobCard.getInvoice().getInvoiceNumber() : null;

        return JobCardDto.builder()
                .id(jobCard.getId())
                .jobCardNumber(jobCard.getJobCardNumber())
                .vehicleId(vehicleId)
                .vehicleRegistrationNumber(regNo)
                .vehicleMake(make)
                .vehicleModel(model)
                .customerId(customerId)
                .customerName(customerName)
                .customerPhone(customerPhone)
                .bookingId(bookingId)
                .mechanicName(jobCard.getMechanicName())
                .complaint(jobCard.getComplaint())
                .inspectionNotes(jobCard.getInspectionNotes())
                .status(jobCard.getStatus())
                .openedAt(jobCard.getOpenedAt())
                .targetCompletionDate(jobCard.getTargetCompletionDate())
                .completedAt(jobCard.getCompletedAt())
                .items(itemDtos)
                .parts(partDtos)
                .laborTotal(laborTotal)
                .partsTotal(partsTotal)
                .estimatedTotal(estimatedTotal)
                .invoiceId(invoiceId)
                .invoiceNumber(invoiceNumber)
                .build();
    }

    public JobCard toJobCardEntity(JobCardRequestDto dto, Vehicle vehicle, ServiceBooking booking) {
        if (dto == null) return null;
        JobCard jobCard = new JobCard();
        jobCard.setVehicle(vehicle);
        jobCard.setBooking(booking);
        jobCard.setMechanicName(dto.getMechanicName());
        jobCard.setComplaint(dto.getComplaint());
        jobCard.setInspectionNotes(dto.getInspectionNotes());
        jobCard.setTargetCompletionDate(dto.getTargetCompletionDate());
        jobCard.setOpenedAt(LocalDateTime.now());
        jobCard.setStatus(JobCardStatus.RECEIVED);
        return jobCard;
    }

    // ================= PAYMENT MAPPERS ================= //

    public PaymentDto toPaymentDto(Payment payment) {
        if (payment == null) return null;
        Long invoiceId = payment.getInvoice() != null ? payment.getInvoice().getId() : null;
        String invoiceNum = payment.getInvoice() != null ? payment.getInvoice().getInvoiceNumber() : null;

        return PaymentDto.builder()
                .id(payment.getId())
                .invoiceId(invoiceId)
                .invoiceNumber(invoiceNum)
                .amount(payment.getAmount())
                .paymentMethod(payment.getPaymentMethod())
                .paymentDate(payment.getPaymentDate())
                .referenceNumber(payment.getReferenceNumber())
                .build();
    }

    // ================= INVOICE MAPPERS ================= //

    public InvoiceDto toInvoiceDto(Invoice invoice) {
        if (invoice == null) return null;

        Long jobCardId = null;
        String jobCardNumber = null;
        String mechanicName = null;
        Long vehicleId = null;
        String regNo = null;
        String make = null;
        String model = null;
        Long customerId = null;
        String customerName = null;
        String customerPhone = null;
        String customerEmail = null;

        List<JobCardItemDto> itemDtos = new ArrayList<>();
        List<JobCardPartDto> partDtos = new ArrayList<>();

        if (invoice.getJobCard() != null) {
            JobCard jc = invoice.getJobCard();
            jobCardId = jc.getId();
            jobCardNumber = jc.getJobCardNumber();
            mechanicName = jc.getMechanicName();

            if (jc.getItems() != null) {
                itemDtos = jc.getItems().stream().map(this::toJobCardItemDto).toList();
            }
            if (jc.getParts() != null) {
                partDtos = jc.getParts().stream().map(this::toJobCardPartDto).toList();
            }

            if (jc.getVehicle() != null) {
                Vehicle v = jc.getVehicle();
                vehicleId = v.getId();
                regNo = v.getRegistrationNumber();
                make = v.getMake();
                model = v.getModel();

                if (v.getCustomer() != null) {
                    Customer c = v.getCustomer();
                    customerId = c.getId();
                    customerName = c.getName();
                    customerPhone = c.getPhone();
                    customerEmail = c.getEmail();
                }
            }
        }

        List<PaymentDto> paymentDtos = new ArrayList<>();
        BigDecimal paidAmount = BigDecimal.ZERO;
        if (invoice.getPayments() != null) {
            for (Payment p : invoice.getPayments()) {
                PaymentDto pDto = toPaymentDto(p);
                paymentDtos.add(pDto);
                if (p.getAmount() != null) {
                    paidAmount = paidAmount.add(p.getAmount());
                }
            }
        }

        BigDecimal grandTotal = invoice.getGrandTotal() != null ? invoice.getGrandTotal() : BigDecimal.ZERO;
        BigDecimal outstandingBalance = grandTotal.subtract(paidAmount).setScale(2, RoundingMode.HALF_UP);

        return InvoiceDto.builder()
                .id(invoice.getId())
                .invoiceNumber(invoice.getInvoiceNumber())
                .jobCardId(jobCardId)
                .jobCardNumber(jobCardNumber)
                .mechanicName(mechanicName)
                .vehicleId(vehicleId)
                .vehicleRegistrationNumber(regNo)
                .vehicleMake(make)
                .vehicleModel(model)
                .customerId(customerId)
                .customerName(customerName)
                .customerPhone(customerPhone)
                .customerEmail(customerEmail)
                .invoiceDate(invoice.getInvoiceDate())
                .laborTotal(invoice.getLaborTotal())
                .partsTotal(invoice.getPartsTotal())
                .discount(invoice.getDiscount())
                .taxAmount(invoice.getTaxAmount())
                .grandTotal(invoice.getGrandTotal())
                .paymentStatus(invoice.getPaymentStatus())
                .payments(paymentDtos)
                .items(itemDtos)
                .parts(partDtos)
                .paidAmount(paidAmount)
                .outstandingBalance(outstandingBalance)
                .build();
    }
}
