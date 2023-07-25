package com.beok.playground

import app.cash.turbine.test
import kotlin.test.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test

class CoroutinesFlowTest {

    @get:Rule
    val mainCoroutinesRule = MainCoroutinesRule()

    @Test
    fun `size만큼 emit하는 flow를 구현합니다`() = runBlocking {
        listOf(1,2,3,4,5)
            .buffer(size = 3)
            .test {
                assertEquals(expected = 1, actual = awaitItem())
                assertEquals(expected = 2, actual = awaitItem())
                assertEquals(expected = 3, actual = awaitItem())
                cancelAndIgnoreRemainingEvents()
            }
    }
}
