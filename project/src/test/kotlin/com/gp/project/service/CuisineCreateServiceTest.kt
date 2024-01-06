package com.gp.project.service

import com.gp.project.model.Cuisine
import com.gp.project.repository.CuisineRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.mock.web.MockMultipartFile
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.assertThrows
import java.util.*

@ExtendWith(MockitoExtension::class)
class CuisineCreateServiceTest {

    @Mock
    private lateinit var cuisineRepository: CuisineRepository

    private lateinit var service: CuisineCreateService

    @BeforeEach
    fun setUp() {
        service = CuisineCreateService(cuisineRepository)
    }

    @Test
    fun `when CSV is valid, save cuisines`() {
        val csvContent = "Id,Name\n1,Italian\n2,French"
        val file = MockMultipartFile("file", "cuisines.csv", "text/plain", csvContent.toByteArray())

        service.save(file)

        verify(cuisineRepository, Mockito.times(2)).save(Mockito.any(Cuisine::class.java))
    }

    @Test
    fun `when CSV is empty, no cuisines are saved`() {
        val csvContent = "Id,Name\n"
        val file = MockMultipartFile("file", "empty.csv", "text/plain", csvContent.toByteArray())

        val message = service.save(file)

        verify(cuisineRepository, Mockito.never()).save(Mockito.any(Cuisine::class.java))
        assertEquals("Data processed with success!", message)
    }

}
