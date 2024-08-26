import com.cashapp.stocks.App
import com.cashapp.stocks.stock.Stock
import com.cashapp.stocks.stock.StockResponse
import com.cashapp.stocks.stock.api.StockApi
import com.cashapp.stocks.stock.api.StockRepositoryImpl
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import java.io.IOException

class StockRepositoryImplTest {
    private lateinit var repository: StockRepositoryImpl
    private val app: App = mockk<App>()
    private val stockApi: StockApi = mockk()

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        every { app.stockApi } returns stockApi
        repository = StockRepositoryImpl(app)
    }

    @Test
    fun `getStockData returns valid stock data`() = runTest {
        // Given: The API returns a valid response
        val stockResponse = StockResponse(
            listOf(
                Stock("AAPL", "Apple Inc."),
                Stock("GOOGL", "Alphabet Inc.")
            )
        )
        coEvery { stockApi.getStockData() } returns stockResponse

        // When: The repository fetches the stock data
        val response = repository.getStockData()

        // Then: The result should be a successful response with the expected data
        response.collect {
            assertEquals(stockResponse.stocks, it?.stocks)
        }
    }

    @Test
    fun `getMalformedStockData returns failure for malformed data`() = runTest {
        // Given: The API returns a response that causes a parsing error
        coEvery { stockApi.getStockData() } throws IOException("Malformed data")

        // When: The repository fetches the stock data
        val response = repository.getMalformedStockData()

        // Then: The result should be a failure with the expected exception
        response.collect {
            assertTrue(it?.stocks == null)
        }
    }

    @Test
    fun `getEmptyStockData returns empty list when no stocks are available`() = runTest {
        // Given: The API returns an empty list
        val emptyStockResponse = StockResponse(emptyList())
        coEvery { stockApi.getStockData() } returns emptyStockResponse

        // When: The repository fetches the stock data
        val response = repository.getEmptyStockData()

        // Then: The result should be successful but with an empty list
        response.collect {
            assertTrue(it?.stocks == emptyList<Stock>())
        }
    }
}