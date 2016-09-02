package com.lessard.codesamples.order.services;

import com.lessard.codesamples.order.domain.SalesOrder;

/**
 * Created by fdlessard on 16-08-12.
 */
public interface SalesOrderService {

    void createSalesOrder(SalesOrder salesOrder);

    SalesOrder getSalesOrder(long id);

    Iterable<SalesOrder> getAllSalesOrder();

    void deleteSalesOrder(long id);

    SalesOrder updateSalesOrder(SalesOrder salesOrder);
}
