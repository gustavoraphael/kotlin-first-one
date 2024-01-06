package com.gp.project.controller

import com.gp.project.dto.RestaurantDTO
import com.gp.project.dto.RestaurantSearchCriteriaDTO
import com.gp.project.service.RestaurantSearchService
import com.gp.project.service.RestaurantCreateService
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/api/restaurants")
class RestaurantController(
    private val service: RestaurantCreateService,
    private val searchService: RestaurantSearchService
) {

    @PostMapping("/upload")
    fun uploadCsv(@RequestParam("file") file: MultipartFile): String {
        return service.save(file)
    }

    @GetMapping("/")
    fun searchRestaurants(@ModelAttribute criteria: RestaurantSearchCriteriaDTO): List<RestaurantDTO> {
        return searchService.findBestMatchedRestaurants(criteria)
    }
}
