package com.camielvr.duneassignment.service.impl;

import com.camielvr.duneassignment.domain.entities.Invoice;
import com.camielvr.duneassignment.domain.requests.InvoiceRequest;
import com.camielvr.duneassignment.service.InvoiceService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class InvoiceServiceImpl implements InvoiceService {


    public InvoiceServiceImpl() {
    }

    @Override
    public Invoice createInvoice(final InvoiceRequest invoiceRequest) {
        final BigDecimal costWithoutTax = this.calculateCostWithoutTax(invoiceRequest.getQuantity(), invoiceRequest.getPricePerQuantity());

        final BigDecimal taxCost = this.calculateTaxCost(invoiceRequest.getTax(), costWithoutTax);

        final BigDecimal totalCost = this.calculateTotalCost(costWithoutTax, taxCost);

        return this.fromOrderToInvoice(invoiceRequest, costWithoutTax, taxCost, totalCost, invoiceRequest.getTax(), invoiceRequest.getPricePerQuantity());
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

    private Invoice fromOrderToInvoice(final InvoiceRequest invoiceRequest, final BigDecimal costWithoutTax, final BigDecimal taxCost, final BigDecimal totalCost, final BigDecimal currentTax, final BigDecimal currentPrice) {
        return Invoice.builder()
                .description(invoiceRequest.getDescription())
                .customerName(invoiceRequest.getCustomerName())
                .quantity(invoiceRequest.getQuantity())
                .pricePerKilogram(currentPrice)
                .costWithoutTax(costWithoutTax)
                .tax(currentTax)
                .taxCost(taxCost)
                .totalCost(totalCost)
                .build();
    }
}
