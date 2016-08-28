package com.lessard.codesamples.order.services;


import com.lessard.codesamples.order.RestSpringBootApplication;
import com.lessard.codesamples.order.domain.SalesOrder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TimeZone;

@RunWith(SpringRunner.class)
@TestPropertySource(locations = "classpath:integration_test.properties")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class SalesOrderServiceIT {

    public static final String DATE_FORMAT = "dd-MM-yyyy hh:mm:ss z";

    @Autowired
    private SalesOrderService salesOrderService;

    @Before
    public void setup() {
    }

    @Test
    public void testGetAllSalesOrder() throws Exception {

        Iterable<SalesOrder> it = salesOrderService.getAllSalesOrder();
        Iterator<SalesOrder> iterator = it.iterator();

        List<SalesOrder> list = new ArrayList<SalesOrder>();
        iterator.forEachRemaining(list::add);

        Assert.assertEquals(3, list.size());
    }

    @Test
    public void testGetSalesOrder() throws Exception {

        SalesOrder salesOrder = salesOrderService.getSalesOrder(100l);

        Assert.assertNotNull(salesOrder);
        Assert.assertEquals(Long.valueOf(100), salesOrder.getId());
        Assert.assertEquals(Long.valueOf(1), salesOrder.getVersion());
        Assert.assertEquals("SalesOrder 100 Int", salesOrder.getDescription());


        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        sdf.setTimeZone(TimeZone.getTimeZone("EST"));
        String dateStr = sdf.format(salesOrder.getDate());


        Assert.assertEquals("01-08-2016 12:00:00 EST", dateStr);
        Assert.assertEquals("10.00", salesOrder.getTotal().toPlainString());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetSalesOrderWithNullId() throws Exception {

        SalesOrder salesOrder = salesOrderService.getSalesOrder(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDeleteSalesOrderWithNullId() throws Exception {

       salesOrderService.deleteSalesOrder(null);
    }


    //@Test
    public void testDeleteSalesOrderWithNonExistingId() throws Exception {

        salesOrderService.deleteSalesOrder(Long.valueOf(55));
    }

}