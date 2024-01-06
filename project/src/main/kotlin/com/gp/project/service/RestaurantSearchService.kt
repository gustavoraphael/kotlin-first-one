package com.gp.project.service

import com.gp.project.dto.RestaurantDTO
import com.gp.project.dto.RestaurantSearchCriteriaDTO
import com.gp.project.model.Restaurant
import com.gp.project.repository.RestaurantRepository
import org.springframework.stereotype.Service

@Service
class RestaurantSearchService(private val restaurantRepository: RestaurantRepository) {

    fun findBestMatchedRestaurants(dto: RestaurantSearchCriteriaDTO): List<RestaurantDTO> {
        validateInput(dto)

        return getAllRestaurants()
            .filter { it.matches(dto) }
            .sortedWith(compareBy({ it.distance }, { -it.customerRating }, { it.price }))
            .take(5)
    }

    private fun getAllRestaurants(): List<RestaurantDTO> =
        restaurantRepository.findAll().map { it.convertToDTO() }

    private fun Restaurant.convertToDTO(): RestaurantDTO =
        RestaurantDTO(name, rating, distance, price, cuisine?.name)

    private fun RestaurantDTO.matches(dto: RestaurantSearchCriteriaDTO): Boolean =
        (dto.name == null || name.contains(dto.name, true)) &&
                (dto.customerRating == null || customerRating >= dto.customerRating) &&
                (dto.distance == null || distance <= dto.distance) &&
                (dto.price == null || price <= dto.price) &&
                (dto.cuisine == null || cuisine?.contains(dto.cuisine, true) ?: false)

    private fun validateInput(dto: RestaurantSearchCriteriaDTO) {
        require(dto.customerRating == null || dto.customerRating in 1..5) { "Customer rating must be between 1 and 5." }
        require(dto.distance == null || dto.distance in 1..10) { "Distance must be between 1 and 10 miles." }
        require(dto.price == null || dto.price in 10..50) { "Price must be between $10 and $50." }
    }
}
