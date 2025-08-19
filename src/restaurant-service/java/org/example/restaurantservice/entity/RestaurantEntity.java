package org.example.restaurantservice.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.restaurantservice.model.enums.CategoryType;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "restaurants")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RestaurantEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String address;
    private String city;
    private Boolean isOpen;

    @Enumerated(EnumType.STRING)
    private CategoryType category;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;


    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) return false;
        RestaurantEntity that = (RestaurantEntity) object;
        return Objects.equals(id, that.id) && Objects.equals(address, that.address) && Objects.equals(city, that.city) && Objects.equals(isOpen, that.isOpen) && category == that.category && Objects.equals(createdAt, that.createdAt) && Objects.equals(updatedAt, that.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
