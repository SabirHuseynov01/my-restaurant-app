package org.example.restaurantservice.mapper;

import lombok.experimental.UtilityClass;
import org.example.restaurantservice.entity.RestaurantEntity;
import org.example.restaurantservice.model.request.CreateRestaurantRequest;
import org.example.restaurantservice.model.response.RestaurantResponse;

@UtilityClass
public class RestaurantMapper {
    public static RestaurantEntity toEntity (CreateRestaurantRequest request) {
        return RestaurantEntity.builder()
            .name(request.getName())
                .address(request.getAddress())
                .city(request.getCity())
                .isOpen(Boolean.TRUE)
                .category(request.getCategory())
                .build();
    }

    public static RestaurantResponse toResponse(RestaurantEntity entity) {
        return RestaurantResponse.builder()
                .id(entity.getId().toString())
                .name(entity.getName())
                .address(entity.getAddress())
                .city(entity.getCity())
                .isOpen(entity.getIsOpen())
                .category(entity.getCategory())
                .build();
    }
}
