package org.example.restaurantservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.restaurantservice.exception.NotFoundException;
import org.example.restaurantservice.mapper.RestaurantMapper;
import org.example.restaurantservice.model.request.CreateRestaurantRequest;
import org.example.restaurantservice.model.response.RestaurantResponse;
import org.example.restaurantservice.repository.RestaurantRepository;
import org.example.restaurantservice.service.RestaurantService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class RestaurantServiceImpl implements RestaurantService {
    private final RestaurantRepository restaurantRepository;

    @Override
    public void createRestaurant(CreateRestaurantRequest request) {
        log.info("ActionLog.createRestaurant.start - request: {}", request);
        var entity = RestaurantMapper.toEntity(request);
        restaurantRepository.save(entity);
        log.info("ActionLog.createRestaurant.end - request: {}", entity.getId());
    }

    @Override
    public Page<RestaurantResponse> getAllRestaurants(Pageable pageable) {
        log.info("ActionLog.getAllRestaurants.start");
        var restaurantPage = restaurantRepository.findAll(pageable);
        log.info("ActionLog.getAllRestaurant.end - totalRestaurants: {}",restaurantPage.getTotalElements());
        return restaurantPage.map(RestaurantMapper::toResponse);
    }

    @Override
    public RestaurantResponse getRestaurantById(Long id){
        log.info("ActionLog.getRestaurantById.start - id: {}",id);
        var restaurantEntity = restaurantRepository.findById(id)
                .orElseThrow(()->
                {

                log.error("ActionLog.getRestaurantById.error - id: {}",id);
                return new NotFoundException("Restaurant not found with id:" + id);
                });
        log.info("ActionLog.deleteRestaurantById.end - restaurant: {}", restaurantEntity);
        return RestaurantMapper.toResponse(restaurantEntity);
    }

    @Override
    public void deleteRestaurant(Long id) {
        log.info("ActionLog.deleteRestaurant.start - id: {}", id);
        var restaurantEntity = restaurantRepository.findById(id)
                .orElseThrow(() ->
                {
                    log.error("ActionLog.deleteRestaurant.error - id: {}", id);
                    return new NotFoundException("Restaurant not found with id:" + id);
                });
        restaurantRepository.delete(restaurantEntity);
        log.info("ActionLog.deleteRestaurant.end - restaurantId: {}", id);
    }
}
