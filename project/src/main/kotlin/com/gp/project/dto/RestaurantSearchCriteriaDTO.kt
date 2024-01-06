package com.gp.project.dto

data class RestaurantSearchCriteriaDTO(
    val name: String? = null,
    val customerRating: Int? = null,
    val distance: Int? = null,
    val price: Int? = null,
    val cuisine: String? = null
)
