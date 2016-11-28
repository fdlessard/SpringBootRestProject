package io.fdlessard.codebites.order.controllers;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by fdlessard on 16-09-04.
 */
public class SalesOrderError {

    private int status;

    private int code;

    private String message;

    private String developerMessage;

    private String moreInfo;

    public SalesOrderError(int status, int code, String message, String developerMessage, String moreInfo) {
        this.status = status;
        this.code = code;
        this.message = message;
        this.developerMessage = developerMessage;
        this.moreInfo = moreInfo;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDeveloperMessage() {
        return developerMessage;
    }

    public void setDeveloperMessage(String developerMessage) {
        this.developerMessage = developerMessage;
    }

    public String getMoreInfo() {
        return moreInfo;
    }

    public void setMoreInfo(String moreInfo) {
        this.moreInfo = moreInfo;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("status", status)
                .append("code", code)
                .append("message", message)
                .append("developerMessage", developerMessage)
                .append("moreInfo", moreInfo)
                .toString();
    }
}
