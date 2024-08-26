package com.cashapp.stocks.stock

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.cashapp.stocks.stock.api.StockRepository
import com.cashapp.stocks.stock.api.StockState
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.verify
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.assertNull
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class StockViewModelTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()
    private lateinit var viewModel: StockViewModel
    private val repository: StockRepository = mockk(relaxed = true)
    private val mockedStocks = StockResponse(listOf(
        Stock("AAPL", "Apple", "USD", 100, 100, 100),
        Stock("GOOGL", "Google", "USD", 100, 100, 100)
    ))
    private val emptyStocks = StockResponse(emptyList())

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(Dispatchers.Unconfined)
        viewModel = StockViewModel(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `getState returns the correct initial state`() {
        val initialState = viewModel.state.value
        assertNotNull(initialState)
        assertEquals(StockState.Loading, initialState)
    }

    @Test
    fun `fetchStocks updates state with fetched stocks`() = runTest {
        // Given: Mocked repository returning a list of stocks
        coEvery { repository.getStockData() } returns flow { emit(mockedStocks) }

        // When: fetchStocks is called
        viewModel.fetchStocks()

        // Then: The state should be updated with the loaded stocks
        val updatedState = viewModel.state.value
        assertNotNull(updatedState)
        assertEquals(StockState.Success(mockedStocks.stocks), updatedState)

        // Verify that repository.getStocks() was called exactly once
        coVerify { repository.getStockData() }
    }

    @Test
    fun `fetchStocks handles errors correctly`() = runTest {
        // Given: Mocked repository returning an error
        val errorMessage = "No stocks available"
        coEvery { repository.getStockData() } returns flow { emit(emptyStocks) }

        // When: fetchStocks is called
        viewModel.fetchStocks()

        // Then: The state should be updated with an error
        val updatedState = viewModel.state.value
        assertNotNull(updatedState)
        assertEquals(StockState.Error(errorMessage), updatedState)

        // Verify that repository.getStocks() was called exactly once
        coVerify { repository.getStockData() }
    }

    @Test
    fun `updateSelectedStock updates the selected stock correctly`() {
        // Given: A stock to select
        val stock = mockedStocks.stocks.first()

        // When: updateSelectedStock is called
        viewModel.updateSelectedStock(stock)

        // Then: The selected stock should be updated correctly
        assertEquals(stock, viewModel.selectedStock)

        // Verify that no other interactions were made with the repository (if needed)
        verify(exactly = 0) { repository.getStockData() }
    }

    @Test
    fun `selected stock is Null when no stock has been assigned`() {
        assertNull(viewModel.selectedStock)
    }
}