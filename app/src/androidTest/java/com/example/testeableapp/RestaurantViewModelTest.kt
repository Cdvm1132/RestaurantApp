package com.example.testeableapp

import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.assertNull
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class RestaurantViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
    @Test
    fun addItem_agregaProductoCorrectamente() = runTest {
        val viewModel = RestaurantViewModel()
        viewModel.addItem(1)
        advanceUntilIdle()
        assertEquals(1, viewModel.quantities.value[1])
    }

    @Test
    fun incrementItem_incrementaCantidad() = runTest {
        val viewModel = RestaurantViewModel()
        viewModel.addItem(1)
        viewModel.incrementItem(1)
        advanceUntilIdle()
        assertEquals(2, viewModel.quantities.value[1])
    }

    @Test
    fun decrementItem_eliminaProductoCuandoCantidadEsUno() = runTest {
        val viewModel = RestaurantViewModel()
        viewModel.addItem(1)
        viewModel.decrementItem(1)
        advanceUntilIdle()
        assertFalse(viewModel.quantities.value.containsKey(1))
    }

    @Test
    fun calcularTotalCorrectamente() = runTest {
        val viewModel = RestaurantViewModel()
        viewModel.addItem(1)
        advanceUntilIdle()
        assertEquals(5.50, viewModel.total.value, 0.01)
    }

    @Test
    fun placeOrder_generaConfirmacionConDatosCorrectos() = runTest {
        val viewModel = RestaurantViewModel()
        viewModel.addItem(1)
        viewModel.addItem(1)
        advanceUntilIdle()
        viewModel.placeOrder()
        advanceUntilIdle()
        assertNotNull(viewModel.confirmation.value)
        assertEquals(2, viewModel.confirmation.value?.itemCount)
        assertEquals(11.00, viewModel.confirmation.value?.total ?: 0.0, 0.01)
    }

    @Test
    fun dismissConfirmation_limpiaPedidoCompletamente() = runTest {
        val viewModel = RestaurantViewModel()
        viewModel.addItem(1)
        viewModel.addItem(2)
        advanceUntilIdle()
        viewModel.placeOrder()
        viewModel.dismissConfirmation()
        advanceUntilIdle()
        assertTrue(viewModel.quantities.value.isEmpty())
        assertNull(viewModel.confirmation.value)
    }
}