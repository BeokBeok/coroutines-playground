package com.beok.playground

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import com.beok.playground.ui.theme.CoroutinesplaygroundTheme
import kotlin.random.Random
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CoroutinesplaygroundTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting("Android")
                }
            }
        }

//        printLogOneLaunch()
//        printLogMultiLaunch()
    }

    private fun printLogOneLaunch(dispatcher: CoroutineDispatcher = Dispatchers.IO) {
        lifecycleScope.launch(dispatcher) {
            repeat(1_000) {
                List(1_000) {
                    Random.nextLong()
                }.maxOrNull()

                Log.d("kurt", "Running on thread: ${Thread.currentThread().name}")
            }
        }
    }

    /**
     * 하나의 launch에는 하나의 Dispatcher가 구동된다.
     * 즉, tid(6318)가 같으면 dispatcher(DefaultDispatcher-worker-15)도 같다
     *
     * 08:44:34.932 6276-6318 Running on thread: DefaultDispatcher-worker-15
     * 08:44:34.933 6276-6318 Running on thread: DefaultDispatcher-worker-15
     */
    private fun printLogMultiLaunch(dispatcher: CoroutineDispatcher = Dispatchers.IO) {
        lifecycleScope.launch {
            repeat(1_000) {
                launch(dispatcher) {
                    List(1_000) {
                        Random.nextLong()
                    }.maxOrNull()

                    Log.d("kurt", "Running on thread: ${Thread.currentThread().name}")
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CoroutinesplaygroundTheme {
        Greeting("Android")
    }
}
