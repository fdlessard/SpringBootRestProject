package com.lessard.codesamples.order.controllers;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by fdlessard on 16-09-04.
 */
public class SalesOrderError {

    private String code;

    private String message;

    public SalesOrderError(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("code", code)
                .append("message", message)
                .toString();
    }
}
