package com.beok.playground

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Test

class CoroutinesScopeTest {
    @Test
    fun `하나의 scope에서 exception이 발생하면 Crash가 발생합니다`() = runBlocking {
        data class Details(val name: String, val followers: Int)
        data class Tweet(val text: String)

        fun getFollowersNumber(): Int =
            throw Error("Service exception")

        suspend fun getUserName(): String {
            delay(500)
            return "marcinmoskala"
        }

        fun getTweets(): List<Tweet> {
            return listOf(Tweet("Hello, world"))
        }

        suspend fun CoroutineScope.getUserDetails(): Details {
            val userName = async { getUserName() }
            val followerNumber = async { getFollowersNumber() } // Exception!
            return Details(userName.await(), followerNumber.await())
        }

        val details = try {
            getUserDetails()
        } catch (e: Exception) {
            null
        }

        val tweets = async { getTweets() }
        println("User: $details")
        println("Tweets: ${tweets.await()}")
    }
}
