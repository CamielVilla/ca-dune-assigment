package com.camielvr.duneassignment.service.impl;

import com.camielvr.duneassignment.config.AppConfig;
import com.camielvr.duneassignment.domain.entities.Invoice;
import com.camielvr.duneassignment.domain.entities.SpiceOrder;
import com.camielvr.duneassignment.domain.requests.SpiceOrderRequest;
import com.camielvr.duneassignment.exception.ResourceNotFoundException;
import com.camielvr.duneassignment.repository.SpiceOrderRepository;
import com.camielvr.duneassignment.service.InvoiceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SpiceOrderServiceImplTest {
    @Mock
    private SpiceOrderRepository spiceOrderRepository;

    @Mock
    private InvoiceService invoiceService;

    @Mock
    private AppConfig appConfig;

    @InjectMocks
    private SpiceOrderServiceImpl spiceOrderService;

    @Captor
    private ArgumentCaptor<SpiceOrder> spiceOrderArgumentCaptor;

    private List<String> customers;

    private SpiceOrderRequest spiceOrderRequest;

    private Invoice invoice;

    private SpiceOrder spiceOrder;

    @BeforeEach
    void setUp() {
        customers = List.of("Customer1", "Customer2", "Customer3");
        invoice = createInvoice();
        spiceOrder = createSpiceOrder(invoice);
    }

    @Test
    void orderSpice_shouldSuccceedWhenRequestIsCorrect() {
        spiceOrderRequest = createSpiceOrderRequest("Customer1", 5);

        when(invoiceService.createInvoice(any())).thenReturn(invoice);

        spiceOrderService.orderSpice(spiceOrderRequest);

        verify(spiceOrderRepository, times(1)).save(spiceOrderArgumentCaptor.capture());

        var savedSpiceOrder = spiceOrderArgumentCaptor.getValue();

        assertEquals("Customer1", savedSpiceOrder.getCustomerName());
        assertEquals("Spice", savedSpiceOrder.getDescription());
        assertEquals("Kg", savedSpiceOrder.getQuantityType());
        assertEquals(5, savedSpiceOrder.getQuantity());
        assertEquals(1L, savedSpiceOrder.getInvoice().getId());
    }

    @Test
    void getSpiceOrderInvoice_shouldSucceed() {
        when(spiceOrderRepository.findById(1L)).thenReturn(Optional.of(spiceOrder));

        var result = spiceOrderService.getSpiceOrderInvoice(1L);

        assertNotNull(result);
        assertEquals(55L, result.getInvoiceId());
    }

    @Test
    void getSpiceOrderInvoice_shouldFailWhenOrderNotFound() {
        when(spiceOrderRepository.findById(1L)).thenReturn(Optional.empty());

        var result = assertThrows(ResourceNotFoundException.class, () -> spiceOrderService.getSpiceOrderInvoice(1L));

        assertEquals("spice order not found with id 1", result.getMessage());
    }

    @Test
    void getAllSpiceOrders_shouldSucceed() {
        when(spiceOrderRepository.findAll()).thenReturn(List.of(spiceOrder));

        var result = spiceOrderService.getAllSpiceOrders();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Customer1", result.get(0).getCustomerName());
        assertEquals(5, result.get(0).getQuantity());
    }

    @Test
    void getAllSpiceOrders_shouldNotThrowErrorWhenNoOrdersAreFound() {
        when(spiceOrderRepository.findAll()).thenReturn(List.of());

        var result = spiceOrderService.getAllSpiceOrders();

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    public void testGetAllSpiceCustomers_shouldSucceed() {
        when(appConfig.getCustomers()).thenReturn(customers);

        List<String> result = spiceOrderService.getAllSpiceCustomers();

        assertEquals(3, result.size());
        assertEquals("Customer1", result.get(0));
        assertEquals("Customer2", result.get(1));
        assertEquals("Customer3", result.get(2));
    }

    private SpiceOrder createSpiceOrder(Invoice invoice) {
        SpiceOrder spiceOrder = new SpiceOrder();
        spiceOrder.setId(1L);
        spiceOrder.setInvoice(invoice);
        spiceOrder.setOrderDate(LocalDateTime.of(2024, 6, 15, 12, 0));
        spiceOrder.setQuantity(5);
        spiceOrder.setCustomerName("Customer1");
        spiceOrder.setQuantityType("quantityType");
        spiceOrder.setDescription("description");
        return spiceOrder;
    }

    private SpiceOrderRequest createSpiceOrderRequest(String customerName, Integer quantity) {
        SpiceOrderRequest spiceOrderRequest = new SpiceOrderRequest();
        spiceOrderRequest.setCustomerName(customerName);
        spiceOrderRequest.setQuantity(quantity);
        return spiceOrderRequest;
    }

    private Invoice createInvoice() {
        Invoice invoice = new Invoice();
        invoice.setId(55L);
        return invoice;
    }
}