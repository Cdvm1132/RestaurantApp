package com.example.testeableapp

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import org.junit.Rule
import org.junit.Test

class RestaurantOrderTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()
    @Test
    fun mensajePedidoVacioVisibleAlInicio() {
        composeTestRule.waitForIdle()
        composeTestRule
            .onNodeWithTag("emptyOrderMessage")
            .performScrollTo()
        composeTestRule.waitForIdle()
        composeTestRule
            .onNodeWithTag("emptyOrderMessage")
            .assertIsDisplayed()
    }
    @Test
    fun todosLosItemsDelMenuVisibles() {
        composeTestRule.waitForIdle()
        for (id in 1..10) {
            composeTestRule
                .onNodeWithTag("menuItemName_$id")
                .performScrollTo()
            composeTestRule
                .onNodeWithTag("menuItemName_$id")
                .assertIsDisplayed()
        }
    }
    @Test
    fun totalSeActualizaAlAgregarItem() {
        composeTestRule.waitForIdle()

        composeTestRule
            .onNodeWithTag("addButton_1")
            .performScrollTo()
        composeTestRule
            .onNodeWithTag("addButton_1")
            .performClick()

        composeTestRule.waitForIdle()

        composeTestRule
            .onNodeWithTag("totalValue")
            .performScrollTo()
        composeTestRule
            .onNodeWithTag("totalValue")
            .assertTextEquals("5,50 €")
    }
 @Test
    fun tituloDeLaAppVisible() {
        composeTestRule.waitForIdle()
        composeTestRule
            .onNodeWithTag("appTitle")
            .performScrollTo()
        composeTestRule
            .onNodeWithTag("appTitle")
            .assertIsDisplayed()
    }
    @Test
    fun mensajeVacioDesaparaceAlAgregarItem() {
        composeTestRule.waitForIdle()

        composeTestRule
            .onNodeWithTag("addButton_1")
            .performScrollTo()
        composeTestRule
            .onNodeWithTag("addButton_1")
            .performClick()

        composeTestRule.waitForIdle()

        composeTestRule
            .onNodeWithTag("emptyOrderMessage")
            .assertDoesNotExist()
    }
}