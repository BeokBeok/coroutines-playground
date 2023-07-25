package com.beok.playground

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

fun <T> List<T>.buffer(size: Int, timeMillis: Long = 100): Flow<T> {
    return flow {
        chunked(size).forEachIndexed { chunkedSize, chunkedList ->
            repeat(chunkedList.size) { index ->
                emit(chunkedList[index])
            }
            println("buffer count is ${chunkedSize.inc()}")
            delay(timeMillis)
        }
    }
}
