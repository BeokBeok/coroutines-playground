package com.beok.playground

import app.cash.turbine.test
import kotlin.test.assertEquals
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test

class CoroutinesFlowTest {

    @get:Rule
    val mainCoroutinesRule = MainCoroutinesRule()

    @Test
    fun `size만큼 emit하는 flow를 구현합니다`() = runBlocking {
        listOf(1, 2, 3, 4, 5)
            .buffer(size = 3)
            .test {
                assertEquals(expected = 1, actual = awaitItem())
                assertEquals(expected = 2, actual = awaitItem())
                assertEquals(expected = 3, actual = awaitItem())
                cancelAndIgnoreRemainingEvents()
            }
    }

    @Test
    fun `flow 내 CancellationException은 try-catch문을 활용한다`() = runBlocking {
        flow {
            try {
                emit(1)
                emit(2)
                println("실행되지 않을거임")
                emit(3)
            } catch (e: CancellationException) {
                println("CancellationException 발생!")
                // 이미 CancellationException이 발생하였기 때문에, emit을 해도 동작하지 않음
            }
        }
            .take(2)
            .test {
                assertEquals(expected = 1, actual = awaitItem())
                assertEquals(expected = 2, actual = awaitItem())
                cancelAndIgnoreRemainingEvents()
            }
    }
}
