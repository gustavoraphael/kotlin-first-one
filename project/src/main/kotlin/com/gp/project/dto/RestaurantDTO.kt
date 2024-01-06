package com.gp.project.dto

data class RestaurantDTO(
    val name: String,
    val customerRating: Int,
    val distance: Int,
    val price: Int,
    val cuisine: String?
)

