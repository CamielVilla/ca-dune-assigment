package com.camielvr.duneassignment.controller;

import com.camielvr.duneassignment.domain.dto.InvoiceDTO;
import com.camielvr.duneassignment.domain.dto.SpiceOrderDTO;
import com.camielvr.duneassignment.domain.requests.SpiceOrderRequest;
import com.camielvr.duneassignment.service.SpiceOrderService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("spices/orders")
public class SpiceOrderController {

    private final SpiceOrderService spiceOrderService;

    public SpiceOrderController(SpiceOrderService spiceOrderService) {
        this.spiceOrderService = spiceOrderService;
    }

    @PostMapping
    public ResponseEntity<Void> orderSpice(@Valid @RequestBody final SpiceOrderRequest spiceOrderRequest) {
        spiceOrderService.orderSpice(spiceOrderRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<List<SpiceOrderDTO>> getSpiceOrders() {
        return ResponseEntity.ok(spiceOrderService.getAllSpiceOrders());
    }

    @GetMapping("/{id}/invoice")
    public ResponseEntity<InvoiceDTO> getSpiceOrderInvoiceById(@PathVariable("id") final Long id) {
        return ResponseEntity.ok(spiceOrderService.getSpiceOrderInvoice(id));
    }

    @GetMapping("/customers")
    public ResponseEntity<List<String>> getCustomers() {
        return ResponseEntity.ok(spiceOrderService.getAllSpiceCustomers());
    }

}
