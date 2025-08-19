package org.example.restaurantservice.model.request;


import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.restaurantservice.model.enums.CategoryType;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateRestaurantRequest {
    @NotBlank(message = "Name cannot be blank")
    public String name;

    @NotBlank(message = "Address cannot be blank")
    public String address;

    @NotBlank(message = "City cannot be blank")
    public String city;

    @NotBlank(message = "Category cannot be blank")
    public CategoryType category;
}

