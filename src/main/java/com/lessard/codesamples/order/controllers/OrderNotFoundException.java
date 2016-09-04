package com.lessard.codesamples.order.controllers;

/**
 * Created by fdlessard on 16-08-17.
 */
public class OrderNotFoundException extends RuntimeException {

    private final long orderId;

    public OrderNotFoundException(Long orderId) {
        this.orderId = orderId;
    }

    public Long getOrderId() {
        return orderId;
    }
}
