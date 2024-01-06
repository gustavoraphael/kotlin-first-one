package com.gp.project.repository

import com.gp.project.model.Cuisine
import org.springframework.data.jpa.repository.JpaRepository

interface CuisineRepository : JpaRepository<Cuisine, Int>
