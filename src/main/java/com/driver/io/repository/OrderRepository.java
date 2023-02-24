package com.driver.io.repository;

import com.driver.io.entity.OrderEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends CrudRepository<OrderEntity, Long> {
	OrderEntity findByOrderId(String orderId);

	@Query(value = "select * from orders",nativeQuery = true)
	List<OrderEntity> findAllOrders();
}
