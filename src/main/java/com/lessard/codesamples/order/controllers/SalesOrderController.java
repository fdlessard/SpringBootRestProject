package com.lessard.codesamples.order.controllers;

import com.lessard.codesamples.order.domain.SalesOrder;
import com.lessard.codesamples.order.services.SalesOrderService;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.HandlerAdapter;

import javax.validation.ConstraintViolationException;
import java.util.*;

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

    @RequestMapping(value = "/test", method = RequestMethod.GET, produces = "application/json")
    public List<Map<String, Object>> test() {

        List<Map<String, Object>> mapList = new ArrayList<>();
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", "12345");
        HashMap<String, Object> nestedMap = new HashMap<>();
        nestedMap.put("amount", 10.00);
        nestedMap.put("currency", "EUR");
        map.put("price", nestedMap);
        map.put("description", "toto description");
        mapList.add(map);
        mapList.add(map);

        return mapList;

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


    // Exception handlers

    @ExceptionHandler(OrderNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public String orderNotFound(OrderNotFoundException e) {
        Long orderId = e.getOrderId();
        return "Order " + orderId + " not found !";
    }

    @ExceptionHandler(NumberFormatException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public String orderNotFound(NumberFormatException e) {
        return "Order not found !";
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public String orderAmountIsInvalid(ConstraintViolationException e) {
        return "Order amount is invalid !";
    }


    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public String orderAlreadyExist(Exception e) {
        return "Order already exist !";
    }



}
