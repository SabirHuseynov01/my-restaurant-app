package org.example.restaurantservice.repository;

import org.example.restaurantservice.entity.RestaurantEntity;
import org.springframework.data.jpa.repository.JpaRepository;



public interface RestaurantRepository extends JpaRepository<RestaurantEntity, Long> {
}
