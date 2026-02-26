package com.example.app_kotlin

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.app_kotlin.ui.theme.AppkotlinTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppkotlinTheme {
                Scaffold { innerPadding ->
                    MainMenu(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun MainMenu(modifier: Modifier = Modifier) {

    val context = LocalContext.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        Text("Mini Proyectos")

        Spacer(modifier = Modifier.height(12.dp))

        // Botón Counter App
        Button(
            onClick = {
                val intent = Intent(context, CounterAppActivity::class.java)
                context.startActivity(intent)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Counter App")
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Botón Todo App
        Button(
            onClick = {
                val intent = Intent(context, TodoAppActivity::class.java)
                context.startActivity(intent)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Todo App")
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Botón Quiz App
        Button(
            onClick = {
                val intent = Intent(context, QuizActivity::class.java)
                context.startActivity(intent)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Quiz App")
        }
    }
}
