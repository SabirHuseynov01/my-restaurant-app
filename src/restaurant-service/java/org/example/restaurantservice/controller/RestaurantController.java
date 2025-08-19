package org.example.restaurantservice.controller;

import lombok.RequiredArgsConstructor;
import org.example.restaurantservice.model.request.CreateRestaurantRequest;
import org.example.restaurantservice.model.response.RestaurantResponse;
import org.example.restaurantservice.service.RestaurantService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/restaurants")
@RequiredArgsConstructor
public class RestaurantController {
    private final RestaurantService restaurantService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createdRestaurant(@RequestBody CreateRestaurantRequest request){
        restaurantService.createRestaurant(request);
    }

    @GetMapping
    public Page<RestaurantResponse> getAllRestaurants(@PageableDefault Pageable pageable) {
        return restaurantService.getAllRestaurants(pageable);
    }

    @GetMapping("/{id}")
    public RestaurantResponse getRestaurantById(@PathVariable Long id) {
        return restaurantService.getRestaurantById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRestaurant(@PathVariable Long id){
        restaurantService.deleteRestaurant(id);
    }
}
