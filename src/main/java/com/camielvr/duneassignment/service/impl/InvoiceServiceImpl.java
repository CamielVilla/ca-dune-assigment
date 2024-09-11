package com.camielvr.duneassignment.service.impl;

import com.camielvr.duneassignment.domain.entities.Invoice;
import com.camielvr.duneassignment.domain.requests.InvoiceRequest;
import com.camielvr.duneassignment.service.InvoiceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class InvoiceServiceImpl implements InvoiceService {

    private static final Logger logger = LoggerFactory.getLogger(InvoiceServiceImpl.class);

    public InvoiceServiceImpl() {
    }

    @Override
    public Invoice createInvoice(final InvoiceRequest invoiceRequest) {
        this.validateRequest(invoiceRequest);

        logger.debug("Creating invoice for customer: {}", invoiceRequest.getCustomerName());

        final BigDecimal costWithoutTax = this.calculateCostWithoutTax(invoiceRequest.getQuantity(), invoiceRequest.getPricePerQuantity());

        final BigDecimal taxCost = this.calculateTaxCost(invoiceRequest.getTax(), costWithoutTax);

        final BigDecimal totalCost = this.calculateTotalCost(costWithoutTax, taxCost);

        final Invoice invoice = this.fromRequestToInvoice(invoiceRequest, costWithoutTax, taxCost, totalCost, invoiceRequest.getTax(), invoiceRequest.getPricePerQuantity());

        logger.debug("Invoice created successfully for customer: {}", invoiceRequest.getCustomerName());

        return invoice;
    }

    private void validateRequest(final InvoiceRequest invoiceRequest) {
        logger.debug("Validating InvoiceRequest: {}", invoiceRequest);

        if (invoiceRequest == null) {
            throw new IllegalArgumentException("Invoice request cannot be null.");
        }

        validateNonNullFields(invoiceRequest);
        validateQuantity(invoiceRequest.getQuantity());
    }

    private void validateNonNullFields(final InvoiceRequest invoiceRequest) {
        logger.debug("Validating non-null fields in InvoiceRequest: {}", invoiceRequest);

        if (invoiceRequest.getPricePerQuantity() == null ||
                invoiceRequest.getTax() == null ||
                invoiceRequest.getDescription() == null ||
                invoiceRequest.getCustomerName() == null ||
                invoiceRequest.getQuantityType() == null) {
            throw new IllegalArgumentException("Required fields cannot be null.");
        }
    }

    private void validateQuantity(final Integer quantity) {
        logger.debug("Validating quantity: {}", quantity);

        if (quantity == null || quantity < 1) {
            throw new IllegalArgumentException("Quantity must be greater than 0.");
        }
    }
    private BigDecimal calculateTotalCost(BigDecimal costWithoutTax, BigDecimal taxCost) {
        return costWithoutTax.add(taxCost);
    }

    private BigDecimal calculateTaxCost(BigDecimal currentTax, BigDecimal costWithoutTax) {
        final BigDecimal taxPercentage = currentTax.divide(new BigDecimal("100"));
        return costWithoutTax.multiply(taxPercentage);
    }

    private BigDecimal calculateCostWithoutTax(Integer quantity, BigDecimal currentPrice) {
        return currentPrice.multiply(BigDecimal.valueOf(quantity));
    }

    private Invoice fromRequestToInvoice(final InvoiceRequest invoiceRequest, final BigDecimal costWithoutTax, final BigDecimal taxCost, final BigDecimal totalCost, final BigDecimal currentTax, final BigDecimal currentPrice) {
        return Invoice.builder()
                .description(invoiceRequest.getDescription())
                .customerName(invoiceRequest.getCustomerName())
                .quantity(invoiceRequest.getQuantity())
                .quantityType(invoiceRequest.getQuantityType())
                .pricePerQuantity(currentPrice)
                .costWithoutTax(costWithoutTax)
                .tax(currentTax)
                .taxCost(taxCost)
                .totalCost(totalCost)
                .build();
    }
}
