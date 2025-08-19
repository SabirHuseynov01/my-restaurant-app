package org.example.restaurantservice.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.restaurantservice.model.enums.CategoryType;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RestaurantResponse {
    private String id;
    private String name;
    private String address;
    private String city;
    private CategoryType category;
    private Boolean isOpen;


}
