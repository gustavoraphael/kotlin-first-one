import com.gp.project.model.Cuisine
import com.gp.project.model.Restaurant
import com.gp.project.repository.CuisineRepository
import com.gp.project.repository.RestaurantRepository
import com.gp.project.service.RestaurantCreateService
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
class RestaurantCreateServiceTest {

    @Mock
    private lateinit var restaurantRepository: RestaurantRepository

    @Mock
    private lateinit var cuisineRepository: CuisineRepository

    private lateinit var service: RestaurantCreateService

    @BeforeEach
    fun setUp() {
        service = RestaurantCreateService(restaurantRepository, cuisineRepository)
    }

    @Test
    fun `when CSV is valid, save restaurants`() {
        val csvContent = "Name,Rating,Distance,Price,CuisineId\nItalian,5,2,25,1\nFrench,4,5,30,2"
        val file = MockMultipartFile("file", "restaurants.csv", "text/plain", csvContent.toByteArray())
        val italianCuisine = Cuisine(1, "Italian")
        val frenchCuisine = Cuisine(2, "French")
        `when`(cuisineRepository.findById(1)).thenReturn(Optional.of(italianCuisine))
        `when`(cuisineRepository.findById(2)).thenReturn(Optional.of(frenchCuisine))

        val message = service.save(file)

        verify(restaurantRepository, Mockito.times(2)).save(Mockito.any(Restaurant::class.java))
        assertEquals("Dados do CSV processados e salvos com sucesso!", message)
    }

    @Test
    fun `when CSV has invalid cuisine ID, throw exception`() {
        val csvContent = "Name,Rating,Distance,Price,CuisineId\nItalian,5,2,25,99"
        val file = MockMultipartFile("file", "restaurants.csv", "text/plain", csvContent.toByteArray())
        `when`(cuisineRepository.findById(99)).thenReturn(Optional.empty())

        val exception = assertThrows<Exception> {
            service.save(file)
        }

        assertTrue(exception.message!!.contains("Cuisine ID not found: 99"))
    }

    // Additional test to cover the case when the CSV is empty
    @Test
    fun `when CSV is empty, no restaurants are saved`() {
        val csvContent = ""
        val file = MockMultipartFile("file", "empty.csv", "text/plain", csvContent.toByteArray())

        val message = service.save(file)

        verify(restaurantRepository, Mockito.never()).save(Mockito.any(Restaurant::class.java))
        assertEquals("CSV data processed and saved successfully!", message)
    }
}
