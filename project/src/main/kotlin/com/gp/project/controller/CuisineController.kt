package com.gp.project.controller

import com.gp.project.service.CuisineCreateService
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/api/cuisine")
class CuisineController(private val service: CuisineCreateService) {

    @PostMapping("/upload")
    fun uploadCsv(@RequestParam("file") file: MultipartFile): String {
        return service.save(file)
    }
}
