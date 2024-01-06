package com.gp.project.repository

import com.gp.project.model.Restaurant
import org.springframework.data.jpa.repository.JpaRepository


interface RestaurantRepository : JpaRepository<Restaurant, Long> {
    fun findByNameContaining(name: String): List<Restaurant>
}
