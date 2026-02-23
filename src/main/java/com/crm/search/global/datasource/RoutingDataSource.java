package com.crm.search.global.datasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.transaction.support.TransactionSynchronizationManager;

/*
 * AbstractRoutingDataSource
 * 실행 시점에 어느 DB로 갈지 결정한다. (스위치 역할)
 */
public class RoutingDataSource extends AbstractRoutingDataSource {
    @Override
    protected Object determineCurrentLookupKey() {

        // if transaction is readOnly, using READ
        if(TransactionSynchronizationManager.isCurrentTransactionReadOnly()){
            return DataSourceType.READ;
        }

        return DataSourceType.WRITE;
    }
}
