package com.berlin.aflami

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.aflami.designsystem.theme.AflamiTheme
import screens.SearchByActorScreen
import com.berlin.aflami.ui.theme.AflamiTheme
import androidx.compose.ui.unit.dp
import com.berlin.aflami.screens.search.screen.WorldTourScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AflamiTheme {
                Scaffold { padding->
                    SearchByActorScreen(padding)
            }
            }
        }

    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    BasicText(
        modifier = modifier,
        text = name,
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Greeting("Aflami")
}