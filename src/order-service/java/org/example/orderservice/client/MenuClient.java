package org.example.orderservice.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.example.orderservice.dto.MenuItemDTO;
import org.example.orderservice.exception.ClientException;
import org.example.orderservice.exception.ErrorResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
@RequiredArgsConstructor
public class MenuClient {
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @Value("${client.urls.menu-service}")
    private String menuServiceUrl;

    @SneakyThrows
    public MenuItemDTO getMenuItemById(Long id) {
        log.info("ActionLog.MenuClient.getMenuItemById.start - id: {}", id);
        var url = String.format(menuServiceUrl + "/d%",id);

        try {
            var menuItem = restTemplate.getForObject(url, MenuItemDTO.class);
            log.info("ActionLog.MenuClient.getMenuItemById.end - menuItem: {}", menuItem);
            return menuItem;
        }catch (HttpStatusCodeException exception){
            log.error("ActionLog.MenuClient.getMenuItemById.error - id: {}",id);
            var errorResponse = objectMapper.readValue(exception.getResponseBodyAsString(), ErrorResponse.class);
            throw new ClientException(
                    errorResponse.getCode(),
                    errorResponse.getMessage(),
                    exception.getStatusCode().value());
        }

    }
}
