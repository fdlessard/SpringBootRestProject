package io.fdlessard.codebites.order.services;


import io.fdlessard.codebites.order.domain.SalesOrder;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ConstraintViolationException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

@RunWith(SpringRunner.class)
@Transactional
@TestPropertySource(locations = "classpath:integration_test.properties")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class SalesOrderServiceIT {

    public static final String DATE_FORMAT = "dd-MM-yyyy hh:mm:ss z";

    private Date today = Calendar.getInstance().getTime();

    @Autowired
    private SalesOrderService salesOrderService;

    @Before
    public void setup() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testGetAllSalesOrder() throws Exception {

        Iterable<SalesOrder> it = salesOrderService.getAllSalesOrder();
        Iterator<SalesOrder> iterator = it.iterator();

        List<SalesOrder> list = new ArrayList<SalesOrder>();
        iterator.forEachRemaining(list::add);

        Assert.assertFalse(list.isEmpty());
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

        Assert.assertEquals("01-08-2017 12:00:00 EST", dateStr);
        Assert.assertEquals("10.00", salesOrder.getTotal().toPlainString());
    }

    @Test(expected = ConstraintViolationException.class)
    public void testCreateSalesOrderWithInvalidSalesOrder() throws Exception {

        SalesOrder salesOrder = new SalesOrder(0l, 0l, "SalesOrder 0", today, new BigDecimal(-10.00));
        salesOrderService.createSalesOrder(salesOrder);
    }

    @Test
    public void testCreateSalesOrder() throws Exception {

        SalesOrder salesOrder = new SalesOrder(0l, 0l, "SalesOrder 0", today, new BigDecimal(10.00));
        salesOrderService.createSalesOrder(salesOrder);
    }

    //@Test
    public void testDeleteSalesOrderWithNonExistingId() throws Exception {

        salesOrderService.deleteSalesOrder(Long.valueOf(55));
    }
}