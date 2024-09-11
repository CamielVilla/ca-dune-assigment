package com.camielvr.duneassignment.service;

import com.camielvr.duneassignment.domain.entities.Invoice;
import com.camielvr.duneassignment.domain.requests.InvoiceRequest;

import java.util.List;

public interface InvoiceService {

    Invoice createInvoice(final InvoiceRequest invoiceRequest);

}
