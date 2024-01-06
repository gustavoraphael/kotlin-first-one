package com.gp.project.model

import com.gp.project.dto.RestaurantDTO
import jakarta.persistence.*

@Entity
data class Restaurant(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int? = null,
    val name: String = "",
    val rating: Int = 0,
    val distance: Int = 0,
    val price: Int = 0,

    @ManyToOne
    @JoinColumn(name = "cuisine_id")
    val cuisine: Cuisine? = null
)

fun Restaurant.toRestaurantDTO(): RestaurantDTO {
    return RestaurantDTO(
        name = this.name,
        customerRating = this.rating,
        distance = this.distance,
        price = this.price,
        cuisine = this.cuisine?.name
    )
}
