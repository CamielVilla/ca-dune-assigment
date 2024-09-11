package com.camielvr.duneassignment.service;

import com.camielvr.duneassignment.domain.dto.InvoiceDTO;
import com.camielvr.duneassignment.domain.dto.SpiceOrderDTO;
import com.camielvr.duneassignment.domain.requests.SpiceOrderRequest;

import java.util.List;

public interface SpiceService {

    void orderSpice(final SpiceOrderRequest spiceOrderRequest);

    InvoiceDTO getSpiceOrderInvoice(final Long id);

    List<SpiceOrderDTO> getAllSpiceOrders();

    List<String> getAllSpiceCustomers();
}
