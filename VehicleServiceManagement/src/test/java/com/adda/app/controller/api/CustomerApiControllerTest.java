package com.adda.app.controller.api;

import com.adda.app.dto.CustomerDto;
import com.adda.app.dto.CustomerRequestDto;
import com.adda.app.entity.Customer;
import com.adda.app.mapper.EntityDtoMapper;
import com.adda.app.service.CustomerService;
import com.adda.app.service.VehicleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerApiControllerTest {

    @Mock
    private CustomerService customerService;

    @Mock
    private VehicleService vehicleService;

    @Mock
    private EntityDtoMapper mapper;

    @InjectMocks
    private CustomerApiController customerApiController;

    private Customer customer;
    private CustomerDto customerDto;
    private CustomerRequestDto requestDto;

    @BeforeEach
    void setUp() {
        customer = new Customer();
        customer.setId(1L);
        customer.setName("John");

        customerDto = CustomerDto.builder()
                .id(1L)
                .name("John")
                .build();

        requestDto = CustomerRequestDto.builder()
                .name("John")
                .phone("9999999999")
                .email("john@example.com")
                .build();
    }

    @Test
    void testGetAllCustomers() {
        when(customerService.findAll()).thenReturn(List.of(customer));
        when(mapper.toCustomerDto(customer)).thenReturn(customerDto);

        ResponseEntity<List<CustomerDto>> response = customerApiController.getAll(null);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals("John", response.getBody().get(0).getName());
    }

    @Test
    void testCreateCustomer() {
        when(mapper.toCustomerEntity(requestDto)).thenReturn(customer);
        when(customerService.save(customer)).thenReturn(customer);
        when(mapper.toCustomerDto(customer)).thenReturn(customerDto);

        ResponseEntity<CustomerDto> response = customerApiController.create(requestDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1L, response.getBody().getId());
    }
}
