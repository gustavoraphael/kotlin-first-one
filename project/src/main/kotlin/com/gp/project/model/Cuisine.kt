package com.gp.project.model

import jakarta.persistence.Entity
import jakarta.persistence.Id

@Entity
data class Cuisine @JvmOverloads constructor(
    @Id
    val id: Int? = null,
    val name: String? = null
)
