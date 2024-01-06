package com.gp.project.service

import com.gp.project.model.Cuisine
import com.gp.project.repository.CuisineRepository
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.BufferedReader
import java.io.InputStreamReader

@Service
class CuisineCreateService(
    private val cuisineRepository: CuisineRepository
) {

    fun save(file: MultipartFile): String {
        try {
            val br = BufferedReader(InputStreamReader(file.inputStream))
            br.readLine() // ignore first line (header)

            var line: String? = br.readLine()

            while (line != null) {
                val data = line.split(",")
                val cuisine = Cuisine(
                    id = data[0].toInt(),
                    name = data[1]
                )
                cuisineRepository.save(cuisine)
                line = br.readLine()
            }
            return "Data processed with success!"
        } catch (e: Exception) {
            throw Exception("error on process csv: ${e.message}")
        }

    }
}
