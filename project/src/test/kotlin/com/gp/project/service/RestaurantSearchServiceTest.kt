package com.gp.project.service

import com.gp.project.dto.RestaurantSearchCriteriaDTO
import com.gp.project.model.Cuisine
import com.gp.project.model.Restaurant
import com.gp.project.model.toRestaurantDTO
import com.gp.project.repository.RestaurantRepository
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach

@ExtendWith(MockitoExtension::class)
class RestaurantSearchServiceTest {

    @Mock
    private lateinit var restaurantRepository: RestaurantRepository
    private lateinit var service: RestaurantSearchService

    @BeforeEach
    fun setUp() {
        service = RestaurantSearchService(restaurantRepository)
    }

    private val mockRestaurants = listOf(
        Restaurant(name = "Italiana", rating = 5, distance = 2, price = 25, cuisine = Cuisine(name = "Italiana")),
        Restaurant(name = "Francesa", rating = 4, distance = 5, price = 30, cuisine = Cuisine(name = "Francesa")),
        Restaurant(name = "Brasileira", rating = 3, distance = 1, price = 20, cuisine = Cuisine(name = "Brasileira")),
        Restaurant(name = "Mexicana", rating = 5, distance = 8, price = 45, cuisine = Cuisine(name = "Mexicana")),
        Restaurant(name = "Japonesa", rating = 2, distance = 3, price = 15, cuisine = Cuisine(name = "Japonesa"))
    )

    @Test
    fun testFindBestMatchedRestaurants() {
        val criteria = RestaurantSearchCriteriaDTO(
            name = null, // Sem restrição de nome
            customerRating = 3, // Classificação mínima do cliente de 3
            distance = 5, // Máximo de 5 milhas de distância
            price = 30, // Preço máximo de $30
            cuisine = null // Sem restrição de culinária
        )

        `when`(restaurantRepository.findAll()).thenReturn(mockRestaurants)

        val result = service.findBestMatchedRestaurants(criteria)

        assertEquals(3, result.size)
        assertTrue(result[0].distance <= result[1].distance)
    }

    @Test
    fun `should return empty list when no restaurants match`() {
        val criteria = RestaurantSearchCriteriaDTO(customerRating = 5, distance = 1, price = 10)

        `when`(restaurantRepository.findAll()).thenReturn(mockRestaurants)

        val result = service.findBestMatchedRestaurants(criteria)

        assertTrue(result.isEmpty())
    }

    @Test
    fun testFindRestaurantsByRating() {
        val criteria = RestaurantSearchCriteriaDTO(customerRating = 4)

        `when`(restaurantRepository.findAll()).thenReturn(mockRestaurants)

        val result = service.findBestMatchedRestaurants(criteria)

        assertTrue(result.all { it.customerRating >= 4 })
    }
}
