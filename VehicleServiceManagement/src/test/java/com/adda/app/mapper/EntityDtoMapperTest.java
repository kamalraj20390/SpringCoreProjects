package com.adda.app.mapper;

import com.adda.app.dto.*;
import com.adda.app.entity.*;
import com.adda.app.enums.BookingStatus;
import com.adda.app.enums.FuelType;
import com.adda.app.enums.ItemType;
import com.adda.app.enums.JobCardStatus;
import com.adda.app.enums.PaymentMethod;
import com.adda.app.enums.PaymentStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EntityDtoMapperTest {

    private EntityDtoMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new EntityDtoMapper();
    }

    @Test
    void testCustomerMapping() {
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setName("John Doe");
        customer.setPhone("9876543210");
        customer.setEmail("john@example.com");
        customer.setAddress("123 Street");

        Vehicle vehicle = new Vehicle();
        vehicle.setId(10L);
        vehicle.setRegistrationNumber("MH12AB1234");
        vehicle.setMake("Toyota");
        vehicle.setModel("Corolla");
        vehicle.setFuelType(FuelType.PETROL);
        vehicle.setCustomer(customer);
        customer.setVehicles(List.of(vehicle));

        CustomerDto dto = mapper.toCustomerDto(customer);

        assertNotNull(dto);
        assertEquals(1L, dto.getId());
        assertEquals("John Doe", dto.getName());
        assertEquals(1, dto.getVehicles().size());
        assertEquals("MH12AB1234", dto.getVehicles().get(0).getRegistrationNumber());

        CustomerSummaryDto summaryDto = mapper.toCustomerSummaryDto(customer);
        assertNotNull(summaryDto);
        assertEquals("John Doe", summaryDto.getName());
    }

    @Test
    void testVehicleMapping() {
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setName("Alice");
        customer.setPhone("9998887776");

        Vehicle vehicle = new Vehicle();
        vehicle.setId(5L);
        vehicle.setRegistrationNumber("DL01XY9999");
        vehicle.setMake("Honda");
        vehicle.setModel("City");
        vehicle.setFuelType(FuelType.DIESEL);
        vehicle.setManufacturingYear(2021);
        vehicle.setOdometer(15000);
        vehicle.setCustomer(customer);

        VehicleDto dto = mapper.toVehicleDto(vehicle);

        assertNotNull(dto);
        assertEquals(5L, dto.getId());
        assertEquals("DL01XY9999", dto.getRegistrationNumber());
        assertEquals(1L, dto.getCustomerId());
        assertEquals("Alice", dto.getCustomerName());
        assertEquals("9998887776", dto.getCustomerPhone());
    }

    @Test
    void testServiceBookingMapping() {
        Customer customer = new Customer();
        customer.setId(2L);
        customer.setName("Bob");
        customer.setPhone("9876500000");

        Vehicle vehicle = new Vehicle();
        vehicle.setId(8L);
        vehicle.setRegistrationNumber("KA03MN4567");
        vehicle.setMake("Hyundai");
        vehicle.setModel("i20");
        vehicle.setCustomer(customer);

        ServiceBooking booking = new ServiceBooking();
        booking.setId(20L);
        booking.setVehicle(vehicle);
        booking.setBookingDate(LocalDateTime.now());
        booking.setComplaint("Engine noise");
        booking.setStatus(BookingStatus.CONFIRMED);

        ServiceBookingDto dto = mapper.toServiceBookingDto(booking);

        assertNotNull(dto);
        assertEquals(20L, dto.getId());
        assertEquals(8L, dto.getVehicleId());
        assertEquals("KA03MN4567", dto.getVehicleRegistrationNumber());
        assertEquals(2L, dto.getCustomerId());
        assertEquals("Bob", dto.getCustomerName());
        assertEquals(BookingStatus.CONFIRMED, dto.getStatus());
    }

    @Test
    void testJobCardAndInvoiceMappingWithTotals() {
        Customer customer = new Customer();
        customer.setId(3L);
        customer.setName("Charlie");
        customer.setPhone("9123456789");

        Vehicle vehicle = new Vehicle();
        vehicle.setId(9L);
        vehicle.setRegistrationNumber("MH02CD5678");
        vehicle.setMake("Tata");
        vehicle.setModel("Nexon");
        vehicle.setCustomer(customer);

        JobCard jobCard = new JobCard();
        jobCard.setId(100L);
        jobCard.setJobCardNumber("JC-20261023-0001");
        jobCard.setVehicle(vehicle);
        jobCard.setMechanicName("Dave");
        jobCard.setStatus(JobCardStatus.IN_PROGRESS);

        // Add JobCardItem
        JobCardItem item = new JobCardItem();
        item.setId(201L);
        item.setJobCard(jobCard);
        item.setDescription("Oil change labor");
        item.setQuantity(1);
        item.setUnitPrice(new BigDecimal("500.00"));
        item.setItemType(ItemType.LABOUR);
        jobCard.getItems().add(item);

        // Add JobCardPart
        SparePart part = new SparePart();
        part.setId(301L);
        part.setPartCode("OIL-5W30");
        part.setName("Engine Oil");
        part.setUnitPrice(new BigDecimal("1200.00"));

        JobCardPart jcp = new JobCardPart();
        jcp.setId(401L);
        jcp.setJobCard(jobCard);
        jcp.setSparePart(part);
        jcp.setQuantity(2);
        jcp.setUnitPrice(new BigDecimal("1200.00"));
        jobCard.getParts().add(jcp);

        JobCardDto jcDto = mapper.toJobCardDto(jobCard);

        assertNotNull(jcDto);
        assertEquals("JC-20261023-0001", jcDto.getJobCardNumber());
        assertEquals(1, jcDto.getItems().size());
        assertEquals(1, jcDto.getParts().size());
        assertEquals(new BigDecimal("500.00"), jcDto.getLaborTotal());
        assertEquals(new BigDecimal("2400.00"), jcDto.getPartsTotal());
        assertEquals(new BigDecimal("2900.00"), jcDto.getEstimatedTotal());

        // Invoice mapping test
        Invoice invoice = new Invoice();
        invoice.setId(500L);
        invoice.setInvoiceNumber("INV-20261023-0001");
        invoice.setJobCard(jobCard);
        invoice.setInvoiceDate(LocalDate.now());
        invoice.setLaborTotal(new BigDecimal("500.00"));
        invoice.setPartsTotal(new BigDecimal("2400.00"));
        invoice.setDiscount(new BigDecimal("100.00"));
        invoice.setTaxAmount(new BigDecimal("504.00"));
        invoice.setGrandTotal(new BigDecimal("3304.00"));
        invoice.setPaymentStatus(PaymentStatus.PARTIALLY_PAID);

        Payment payment = new Payment();
        payment.setId(601L);
        payment.setInvoice(invoice);
        payment.setAmount(new BigDecimal("1500.00"));
        payment.setPaymentMethod(PaymentMethod.UPI);
        payment.setPaymentDate(LocalDate.now());
        invoice.setPayments(List.of(payment));

        InvoiceDto invDto = mapper.toInvoiceDto(invoice);

        assertNotNull(invDto);
        assertEquals("INV-20261023-0001", invDto.getInvoiceNumber());
        assertEquals(100L, invDto.getJobCardId());
        assertEquals("MH02CD5678", invDto.getVehicleRegistrationNumber());
        assertEquals("Charlie", invDto.getCustomerName());
        assertEquals(new BigDecimal("1500.00"), invDto.getPaidAmount());
        assertEquals(new BigDecimal("1804.00"), invDto.getOutstandingBalance());
        assertEquals(1, invDto.getPayments().size());
    }
}
