package com.camielvr.duneassignment.service.impl;

import com.camielvr.duneassignment.domain.requests.InvoiceRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class InvoiceServiceImplTest {

    @InjectMocks
    private InvoiceServiceImpl invoiceService;

    private InvoiceRequest invoiceRequest;

    @BeforeEach
    void setUp() {
        invoiceRequest = createInvoiceRequest();
    }

    @Test
    void createInvoice_shouldSucceedWhenRequestIsCorrect() {
        var result = invoiceService.createInvoice(invoiceRequest);

        assertNotNull(result);
        assertEquals("description", result.getDescription());
        assertEquals(5, result.getQuantity());
        assertEquals(BigDecimal.valueOf(10), result.getTax());
        assertEquals("customerName", result.getCustomerName());
        assertEquals(BigDecimal.valueOf(3), result.getPricePerQuantity());
    }

    @Test
    void createInvoice_shouldFailWhenRequestIsEmpty() {
        invoiceRequest = null;

        var result = assertThrows(IllegalArgumentException.class, () -> invoiceService.createInvoice(invoiceRequest));

        assertEquals("Invoice request cannot be null.", result.getMessage());

    }
    @Test
    void createInvoice_shouldFailWhenParameterIsMissing() {
        invoiceRequest.setDescription(null);

        var result = assertThrows(IllegalArgumentException.class, () -> invoiceService.createInvoice(invoiceRequest));

        assertEquals("Required fields cannot be null.", result.getMessage());
    }

    @Test
    void createInvoice_shouldFailWhenQuantityIsLowerThan1() {
        invoiceRequest.setQuantity(0);

        var result = assertThrows(IllegalArgumentException.class, () -> invoiceService.createInvoice(invoiceRequest));

        assertEquals("Quantity must be greater than 0.", result.getMessage());
    }

    @Test
    void shouldCalculateCostWithoutTax() {
        var result = invoiceService.createInvoice(invoiceRequest);

        assertEquals(BigDecimal.valueOf(15), result.getCostWithoutTax());
    }

    @Test
    void shouldCalculateCostTaxCost() {
        var result = invoiceService.createInvoice(invoiceRequest);

        assertEquals(BigDecimal.valueOf(1.5), result.getTaxCost());
    }

    @Test
    void shouldCalculateTotalCost() {
        var result = invoiceService.createInvoice(invoiceRequest);

        assertEquals(BigDecimal.valueOf(16.5), result.getTotalCost());
    }

    private InvoiceRequest createInvoiceRequest() {
        return InvoiceRequest.builder()
                .description("description")
                .tax(BigDecimal.valueOf(10))
                .customerName("customerName")
                .pricePerQuantity(BigDecimal.valueOf(3))
                .quantity(5)
                .quantityType("type")
                .build();
    }
}