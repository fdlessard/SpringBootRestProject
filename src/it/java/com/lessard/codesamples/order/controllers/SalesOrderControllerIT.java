package com.lessard.codesamples.order.controllers;


import io.restassured.http.ContentType;
import org.junit.Before;
import org.junit.Test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.core.IsEqual.equalTo;



public class SalesOrderControllerIT {

    public static final String URL_PREFIX = "http://localhost:8080/RestSpringBootApp/salesorders/";

    private Date today = Calendar.getInstance().getTime();

    @Before
    public void setup() {
    }

    @Test
    public void testGetSalesOrder() throws Exception {
        when().
                get(URL_PREFIX + "100").
                then().statusCode(200).body("id", equalTo(100)).
                contentType(ContentType.JSON);
    }

    @Test
    public void testGetSalesOrderWithNonExistingId() throws Exception {

        when().
                get(URL_PREFIX + "1000").
                then().statusCode(404).
                contentType(ContentType.JSON);
    }

    @Test
    public void testGetSalesOrderWithInvalidId() throws Exception {

        when().
                get(URL_PREFIX + "aaaa").
                then().statusCode(404).
                contentType(ContentType.JSON);
    }

    @Test
    public void testGetAllSalesOrder() throws Exception {

        when().
                get(URL_PREFIX).
                then().statusCode(200).body("id", hasItems(100, 200, 300)).
                contentType(ContentType.JSON);
    }

    @Test
    public void testCreateSalesOrder() throws Exception {

        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
        String todayStr = dateFormat.format(today);

        String postStr = "{\"description\":\"SalesOrder IT\", \"date\": " + "\"" + todayStr + "\",\"total\": \"10.00\" }";

        given().contentType(ContentType.JSON).body(postStr ).
            when().post(URL_PREFIX).
                then().statusCode(201);
    }

    @Test
    public void testCreateSalesOrderWithAlreadyExistingSalesOrder() throws Exception {

        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
        String todayStr = dateFormat.format(today);

        String postStr = "{\"id\" : \"100\", \"description\":\"SalesOrder IT\", \"date\": " + "\"" + todayStr + "\",\"total\": \"10.00\" }";

        given().contentType(ContentType.JSON).body(postStr ).
                when().post(URL_PREFIX).
                then().statusCode(201);
    }

    @Test
    public void testUpdateSalesOrder() throws Exception {

        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
        String todayStr = dateFormat.format(today);

        String putStr = "{\"id\": \"100\",\"description\":\"SalesOrder Updated\", \"date\":" + "\"" + todayStr + "\",\"total\": \"10.00\" }";

        given().contentType(ContentType.JSON).body(putStr).
                when().put(URL_PREFIX).
                then().statusCode(200);
    }


    @Test
    public void testDeleteSalesOrderWithNonExistingId() throws Exception {

        when().
                delete(URL_PREFIX +  "1000").
                then().statusCode(404).
                contentType(ContentType.JSON);
    }

    @Test
    public void testDeleteSalesOrderWithInvalidId() throws Exception {

        when().
                delete(URL_PREFIX + "aaaa").
                then().statusCode(404).
                contentType(ContentType.JSON);
    }
}