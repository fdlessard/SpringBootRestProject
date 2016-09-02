package com.lessard.codesamples.order.controllers;


import io.restassured.http.ContentType;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.core.IsEqual.equalTo;

@RunWith(SpringRunner.class)
@TestPropertySource(locations = "classpath:integration_test.properties")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class SalesOrderControllerIT {

    public static final String DATE_FORMAT = "dd-MM-yyyy hh:mm:ss z";

    @Value("${server.port}")
    private String port;

    private String url;

    private Date today = Calendar.getInstance().getTime();

    @Before
    public void setup() {
        url = "http://localhost:" + port + "/RestSpringBootApp/salesorders/";
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testGetSalesOrder() throws Exception {

        when().
                get(url + "100").
                then().statusCode(200).
                body("id", equalTo(100)).
                body("version", equalTo(1)).
                body("description", equalTo("SalesOrder 100 Int")).
                body("total", equalTo("10.00")).
                body("date", equalTo("01-08-2017 12:00:00 EST")).
                contentType(ContentType.JSON);
    }

   @Test
    public void testGetSalesOrderWithNonExistingId() throws Exception {

        when().
                get(url + "1000").
                then().statusCode(404).
                contentType(ContentType.JSON);
    }

    @Test
    public void testGetSalesOrderWithInvalidId() throws Exception {

        when().
                get(url + "aaaa").
                then().statusCode(404).
                contentType(ContentType.JSON);
    }

    @Test
    public void testGetAllSalesOrder() throws Exception {

        when().
                get(url).
                then().statusCode(200).body("id", hasItems(100, 200, 300)).
                contentType(ContentType.JSON);
    }

    @Test
    public void testCreateSalesOrder() throws Exception {

        DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
        String todayStr = dateFormat.format(today);

        String postStr = "{\"description\":\"SalesOrder IT\", \"date\": " + "\"" + todayStr + "\",\"total\": \"10.00\" }";

        given().contentType(ContentType.JSON).body(postStr).
                when().post(url).
                then().statusCode(201);
    }

    @Test
    public void testCreateSalesOrderWithInvalidAmount() throws Exception {

        DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
        String todayStr = dateFormat.format(today);

        String postStr = "{\"description\":\"SalesOrder IT\", \"date\": " + "\"" + todayStr + "\",\"total\": \"-10.00\" }";

        given().contentType(ContentType.JSON).body(postStr).
                when().post(url).
                then().statusCode(400);
    }
    
    @Test
    public void testCreateSalesOrderWithAlreadyExistingSalesOrder() throws Exception {

        DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
        String todayStr = dateFormat.format(today);

        String postStr = "{\"id\" : \"100\", \"description\":\"SalesOrder IT\", \"date\": " + "\"" + todayStr + "\",\"total\": \"10.00\" }";

        given().contentType(ContentType.JSON).body(postStr).
                when().post(url).
                then().statusCode(201);
    }

    @Test
    public void testUpdateSalesOrder() throws Exception {

        DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
        String todayStr = dateFormat.format(today);

        String putStr = "{\"id\": \"100\",\"description\":\"SalesOrder Updated\", \"date\":" + "\"" + todayStr + "\",\"total\": \"10.00\" }";

        given().contentType(ContentType.JSON).body(putStr).
                when().put(url).
                then().statusCode(200);
    }

    @Test
    public void testDeleteSalesOrderWithNonExistingId() throws Exception {

        when().
                delete(url + "1000").
                then().statusCode(404).
                contentType(ContentType.JSON);
    }

    //@Test
    public void testDeleteSalesOrderWithInvalidId() throws Exception {

        when().
                delete(url + "aaaa").
                then().statusCode(404).
                contentType(ContentType.JSON);
    }
}