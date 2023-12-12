package com.weekytripstravelcrm.repository;

import com.weekytripstravelcrm.entity.CustomerOrderEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends CrudRepository<CustomerOrderEntity, String> {

    @Query("SELECT MAX(CAST(substring(o.customerOrderId, 4) AS long)) FROM CustomerOrderEntity o")
    Long findMaxCustomerOrderId();
    List<CustomerOrderEntity> findByCustomer_CustomerID(String customerId);
}
