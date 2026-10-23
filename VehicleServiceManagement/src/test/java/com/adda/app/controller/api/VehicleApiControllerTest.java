package com.adda.app.controller.api;

import com.adda.app.dto.VehicleDto;
import com.adda.app.dto.VehicleRequestDto;
import com.adda.app.entity.Customer;
import com.adda.app.entity.Vehicle;
import com.adda.app.enums.FuelType;
import com.adda.app.mapper.EntityDtoMapper;
import com.adda.app.service.BookingService;
import com.adda.app.service.CustomerService;
import com.adda.app.service.JobCardService;
import com.adda.app.service.VehicleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VehicleApiControllerTest {

    @Mock
    private VehicleService vehicleService;

    @Mock
    private CustomerService customerService;

    @Mock
    private BookingService bookingService;

    @Mock
    private JobCardService jobCardService;

    @Mock
    private EntityDtoMapper mapper;

    @InjectMocks
    private VehicleApiController vehicleApiController;

    private Customer customer;
    private Vehicle vehicle;
    private VehicleDto vehicleDto;
    private VehicleRequestDto requestDto;

    @BeforeEach
    void setUp() {
        customer = new Customer();
        customer.setId(2L);
        customer.setName("Jane");

        vehicle = new Vehicle();
        vehicle.setId(10L);
        vehicle.setRegistrationNumber("KA01AB1234");
        vehicle.setMake("Toyota");
        vehicle.setModel("Innova");
        vehicle.setCustomer(customer);

        vehicleDto = VehicleDto.builder()
                .id(10L)
                .customerId(2L)
                .customerName("Jane")
                .registrationNumber("KA01AB1234")
                .make("Toyota")
                .model("Innova")
                .build();

        requestDto = VehicleRequestDto.builder()
                .customerId(2L)
                .registrationNumber("KA01AB1234")
                .make("Toyota")
                .model("Innova")
                .fuelType(FuelType.DIESEL)
                .manufacturingYear(2022)
                .odometer(20000)
                .build();
    }

    @Test
    void testGetVehicleById() {
        when(vehicleService.findById(10L)).thenReturn(vehicle);
        when(mapper.toVehicleDto(vehicle)).thenReturn(vehicleDto);

        ResponseEntity<VehicleDto> response = vehicleApiController.getById(10L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("KA01AB1234", response.getBody().getRegistrationNumber());
        assertEquals("Jane", response.getBody().getCustomerName());
    }

    @Test
    void testCreateVehicle() {
        when(customerService.findById(2L)).thenReturn(customer);
        when(mapper.toVehicleEntity(requestDto, customer)).thenReturn(vehicle);
        when(vehicleService.save(vehicle)).thenReturn(vehicle);
        when(mapper.toVehicleDto(vehicle)).thenReturn(vehicleDto);

        ResponseEntity<VehicleDto> response = vehicleApiController.create(requestDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("KA01AB1234", response.getBody().getRegistrationNumber());
        assertEquals(2L, response.getBody().getCustomerId());
    }
}
