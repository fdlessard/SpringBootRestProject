package io.fdlessard.codebites.order.services;

import io.fdlessard.codebites.order.domain.SalesOrder;
import io.fdlessard.codebites.order.repositories.SalesOrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by fdlessard on 16-07-29.
 */
@Service
public class SalesOrderServiceImpl implements SalesOrderService {

    public static final String NULL_SALES_ORDER_MSG = "Null salesOrder";
    public static final String NULL_ID_MSG = "Null id";

    private static final Logger LOGGER = LoggerFactory.getLogger(SalesOrderServiceImpl.class);

    @Resource
    @Autowired
    private SalesOrderRepository salesOrderRepository;

    @Override
    @Transactional
    public void createSalesOrder(SalesOrder salesOrder) {

        if (salesOrder == null) {
            throw new IllegalArgumentException(NULL_SALES_ORDER_MSG);
        }

        salesOrderRepository.save(salesOrder);
    }

    @Override
    public SalesOrder getSalesOrder(long id) {

        return  salesOrderRepository.findOne(id);

    }

    @Override
    public Iterable<SalesOrder> getAllSalesOrder() {

        return salesOrderRepository.findAll();
    }

    @Override
    @Transactional
    public void deleteSalesOrder(long id) {

        salesOrderRepository.delete(id);
    }

    @Override
    @Transactional
    public SalesOrder updateSalesOrder(SalesOrder salesOrder) {

        if (salesOrder == null) {
            throw new IllegalArgumentException(NULL_SALES_ORDER_MSG);
        }
        LOGGER.info(salesOrder.toString());

        return salesOrderRepository.save(salesOrder);
    }
}
