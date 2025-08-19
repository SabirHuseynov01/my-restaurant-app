package org.example.deliveryservice.repository;

import org.example.deliveryservice.entity.DeliveryEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface DeliveryRepository extends CrudRepository<DeliveryEntity,Long> {
    Optional<DeliveryEntity> findByOrderId(Long orderId);
}
