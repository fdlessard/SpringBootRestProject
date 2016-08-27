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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@RunWith(SpringRunner.class)
@TestPropertySource(locations = "classpath:integration_test.properties")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class SalesOrderServiceIT {

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
        Assert.assertEquals( "2016-08-01 12:00:00.0",  salesOrder.getDate());
        Assert.assertEquals(new BigDecimal(10.00), salesOrder.getTotal());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetSalesOrderWithNullId() throws Exception {

        SalesOrder salesOrder = salesOrderService.getSalesOrder(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDeleteSalesOrderWithNullId() throws Exception {

       salesOrderService.deleteSalesOrder(null);
    }


    @Test
    public void testDeleteSalesOrderWithNonExistingId() throws Exception {

        salesOrderService.deleteSalesOrder(null);
    }

}