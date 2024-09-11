package com.camielvr.duneassignment.service.impl;

import com.camielvr.duneassignment.config.AppConfig;
import com.camielvr.duneassignment.domain.dto.InvoiceDTO;
import com.camielvr.duneassignment.domain.dto.SpiceOrderDTO;
import com.camielvr.duneassignment.domain.entities.Invoice;
import com.camielvr.duneassignment.domain.entities.SpiceOrder;
import com.camielvr.duneassignment.domain.requests.InvoiceRequest;
import com.camielvr.duneassignment.domain.requests.SpiceOrderRequest;
import com.camielvr.duneassignment.exception.ResourceNotFoundException;
import com.camielvr.duneassignment.repository.SpiceOrderRepository;
import com.camielvr.duneassignment.service.InvoiceService;
import com.camielvr.duneassignment.service.SpiceService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class SpiceServiceImpl implements SpiceService {
    private final SpiceOrderRepository spiceOrderRepository;
    private final InvoiceService invoiceService;
    private final AppConfig appConfig;

    public SpiceServiceImpl(SpiceOrderRepository spiceOrderRepository, InvoiceService invoiceService, AppConfig appConfig) {
        this.spiceOrderRepository = spiceOrderRepository;
        this.invoiceService = invoiceService;
        this.appConfig = appConfig;
    }

    @Override
    public void orderSpice(final SpiceOrderRequest spiceOrderRequest) {
        final SpiceOrder spiceOrder = toSpiceOrder(spiceOrderRequest);

        final Invoice invoice = invoiceService.createInvoice(toInvoiceRequest(spiceOrder));

        spiceOrder.setInvoice(invoice);

        spiceOrderRepository.save(spiceOrder);
    }

    @Override
    public InvoiceDTO getSpiceOrderInvoice(Long id) {
        final Optional<SpiceOrder> spiceOrder = spiceOrderRepository.findById(id);

        if (spiceOrder.isEmpty()) {
            throw new ResourceNotFoundException("spice order not found with id" + id);
        }

        return toInvoiceDTO(spiceOrder.get().getInvoice());
    }

    @Override
    public List<SpiceOrderDTO> getAllSpiceOrders() {
        final List<SpiceOrder> spiceOrders = spiceOrderRepository.findAll();
        return spiceOrders.stream().map(this::toSpiceOrderDTO).toList();
    }

    @Override
    public List<String> getAllSpiceCustomers() {
        return appConfig.getCustomers();
    }

    private InvoiceDTO toInvoiceDTO(Invoice invoice) {
        return InvoiceDTO.builder()
                .invoiceId(invoice.getId())
                .description(invoice.getDescription())
                .customerName(invoice.getCustomerName())
                .pricePerKilogram(invoice.getPricePerKilogram())
                .quantity(invoice.getQuantity())
                .costWithoutTax(invoice.getCostWithoutTax())
                .tax(invoice.getTax())
                .taxCost(invoice.getTaxCost())
                .totalCost(invoice.getTotalCost())
                .build();
    }

    private InvoiceRequest toInvoiceRequest(final SpiceOrder spiceOrder) {
        return InvoiceRequest.builder()
                .customerName(spiceOrder.getCustomerName())
                .pricePerQuantity(appConfig.getCurrentSpicePrice())
                .tax(appConfig.getPadishahTax())
                .quantity(spiceOrder.getQuantity())
                .description(spiceOrder.getDescription())
                .build();
    }

    private SpiceOrderDTO toSpiceOrderDTO(final SpiceOrder spiceOrder) {
        return SpiceOrderDTO.builder()
                .id(spiceOrder.getId())
                .customerName(spiceOrder.getCustomerName())
                .orderDate(spiceOrder.getOrderDate())
                .quantity(spiceOrder.getQuantity())
                .build();
    }

    private SpiceOrder toSpiceOrder(final SpiceOrderRequest spiceOrderRequest) {
        return SpiceOrder.builder()
                .customerName(spiceOrderRequest.getCustomerName())
                .quantity(spiceOrderRequest.getQuantity())
                .orderDate(LocalDateTime.now())
                .build();
    }
}
