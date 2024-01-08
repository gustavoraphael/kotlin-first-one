package com.gp.project.service

import com.gp.project.model.Restaurant
import com.gp.project.repository.CuisineRepository
import com.gp.project.repository.RestaurantRepository
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.BufferedReader
import java.io.InputStreamReader

@Service
class RestaurantCreateService(
    private val restaurantRepository: RestaurantRepository,
    private val cuisineRepository: CuisineRepository
) {

    fun save(file: MultipartFile): String {
        try {
            val br = BufferedReader(InputStreamReader(file.inputStream))
            br.readLine() // ignore first line (header)

            var line: String? = br.readLine()

            while (line != null) {
                val data = line.split(",")

                val cuisine = cuisineRepository.findById(data[4].toInt())
                    .orElseThrow { Exception("Cuisine ID not found: ${data[4]}") }

                restaurantRepository.save(
                    Restaurant(
                        name = data[0],
                        rating = data[1].toInt(),
                        distance = data[2].toInt(),
                        price = data[3].toInt(),
                        cuisine = cuisine
                    )
                )
                line = br.readLine()
            }
            return "CSV data processed and saved successfully!"
        } catch (e: Exception) {
            throw Exception("error on process csv: ${e.message}")
        }
    }
}
