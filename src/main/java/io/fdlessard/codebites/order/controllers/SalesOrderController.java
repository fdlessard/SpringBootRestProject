package io.fdlessard.codebites.order.controllers;

import io.fdlessard.codebites.order.domain.SalesOrder;
import io.fdlessard.codebites.order.services.SalesOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.*;


/*

GET - Success -  OK: 200
GET - not found -  NOT_FOUND : 404
GET - null id  -  BAD REQUEST : 400
GET - invalid id - BAD REQUEST : 400

POST - Success - Created : 201
POST - invalid data - BAD REQUEST : 400
POST - duplicate
POST - cannot be saved


DELETE - Success -  OK: 200
DELETE - invalid id - BAD REQUEST : 400
DELETE - not found - NOT_FOUND : 404
DELETE - BAD REQUEST : 400


PUT (update) - Success OK: 200
PUT - invalid id
PUT - does not exist
PUT - could not be updated


 */

@RestController
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class SalesOrderController {

    @Autowired
    private SalesOrderService salesOrderService;

    public SalesOrderController(SalesOrderService salesOrderService) {
        this.salesOrderService = salesOrderService;
    }

    public SalesOrderService getSalesOrderService() {
        return salesOrderService;
    }

    public void setSalesOrderService(SalesOrderService salesOrderService) {
        this.salesOrderService = salesOrderService;
    }

    @RequestMapping(value = "/salesorders", method = RequestMethod.POST, produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public void createSalesOrder(@RequestBody SalesOrder salesOrder) {

        salesOrderService.createSalesOrder(salesOrder);
    }

    @RequestMapping(value = "/salesorders/{id}", method = RequestMethod.GET, produces = "application/json")
    public SalesOrder get(@PathVariable long id) {

        SalesOrder salesOrder = salesOrderService.getSalesOrder(id);

        if (salesOrder == null) {
            throw new OrderNotFoundException(id);
        }

        return salesOrder;
    }

    @RequestMapping(value = "/salesorders", method = RequestMethod.GET, produces = "application/json")
    public List<SalesOrder> getAll() {

        Iterable<SalesOrder> salesOrders = salesOrderService.getAllSalesOrder();

        Iterator<SalesOrder> iterator = salesOrders.iterator();

        List<SalesOrder> list = new ArrayList<>();
        iterator.forEachRemaining(list::add);

        return list;
    }

    @RequestMapping(value = "/salesorders/{id}", method = RequestMethod.DELETE, produces = "application/json")
    public void delete(@PathVariable long id) {

        salesOrderService.deleteSalesOrder(id);
    }

    @RequestMapping(value = "/salesorders", method = RequestMethod.PUT, produces = "application/json")
    public void updateSalesOrder(@RequestBody SalesOrder salesOrder) {

        salesOrderService.updateSalesOrder(salesOrder);
    }
}
