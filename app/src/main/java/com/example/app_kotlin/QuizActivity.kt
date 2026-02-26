package com.example.app_kotlin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.app_kotlin.ui.theme.AppkotlinTheme

class QuizActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppkotlinTheme {
                QuizApp(onBack = { finish() })
            }
        }
    }
}

data class QuizQuestion(
    val question: String,
    val options: List<String>,
    val correctIndex: Int
)

val quizQuestions = listOf(
    QuizQuestion("¿Cuál es el lenguaje principal para Android?", listOf("Java", "Kotlin", "Swift", "Python"), 1),
    QuizQuestion("¿Qué es Jetpack Compose?", listOf("Una base de datos", "Un framework UI declarativo", "Un emulador", "Un IDE"), 1),
    QuizQuestion("¿Qué función inicia un Composable?", listOf("@Component", "@Composable", "@View", "@Widget"), 1),
    QuizQuestion("¿Qué es un State en Compose?", listOf("Un archivo XML", "Un valor que recompone la UI al cambiar", "Una clase abstracta", "Un tipo de Activity"), 1),
    QuizQuestion("¿Cómo se declara una variable inmutable en Kotlin?", listOf("var", "let", "val", "const"), 2),
    QuizQuestion("¿Qué componente muestra listas en Compose?", listOf("RecyclerView", "ListView", "LazyColumn", "ScrollView"), 2),
    QuizQuestion("¿Qué hace rememberSaveable?", listOf("Guarda en base de datos", "Persiste el estado ante rotación", "Crea animaciones", "Navega entre pantallas"), 1),
    QuizQuestion("¿Qué es un Intent en Android?", listOf("Un diseño de pantalla", "Un mensaje para iniciar actividades", "Una clase de red", "Un tipo de variable"), 1),
    QuizQuestion("¿Cuál es el archivo principal de configuración de Android?", listOf("build.gradle", "settings.xml", "AndroidManifest.xml", "MainActivity.kt"), 2),
    QuizQuestion("¿Qué operador se usa para null safety en Kotlin?", listOf("!", "?", "#", "@"), 1),
    QuizQuestion("¿Qué es un Scaffold en Compose?", listOf("Una animación", "Estructura base con TopBar, FAB y contenido", "Un tipo de lista", "Un componente de navegación"), 1),
    QuizQuestion("¿Cómo se llama el ciclo de vida al crear una Activity?", listOf("onStart()", "onResume()", "onCreate()", "onInit()"), 2),
    QuizQuestion("¿Qué es mutableStateOf?", listOf("Una lista inmutable", "Un estado observable que recompone la UI", "Un archivo de recursos", "Una función de red"), 1),
    QuizQuestion("¿Para qué sirve el modificador fillMaxWidth?", listOf("Centra el elemento", "Hace que ocupe todo el ancho", "Agrega padding", "Cambia el color"), 1),
    QuizQuestion("¿Qué es una data class en Kotlin?", listOf("Una clase abstracta", "Una clase para almacenar datos con equals y copy", "Una clase de red", "Una Activity especial"), 1)
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuizApp(onBack: () -> Unit) {

    var currentIndex by remember { mutableIntStateOf(0) }
    var selectedIndex by remember { mutableStateOf<Int?>(null) }
    var feedback by remember { mutableStateOf<String?>(null) }
    var lives by remember { mutableIntStateOf(3) }
    var score by remember { mutableIntStateOf(0) }
    var quizFinished by remember { mutableStateOf(false) }
    var confirmed by remember { mutableStateOf(false) }

    val totalQuestions = quizQuestions.size
    val currentQuestion = quizQuestions[currentIndex]
    val isLastQuestion = currentIndex == totalQuestions - 1
    val progress = ((currentIndex).toFloat() / totalQuestions * 100).toInt()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Quiz App", color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF6A1B9A)
                )
            )
        }
    ) { innerPadding ->

        if (quizFinished) {
            // ── Pantalla de resultados ──────────────────────────────────
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text("🏁 Resultados", fontSize = 28.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(24.dp))
                Text("Respuestas correctas: $score / $totalQuestions", fontSize = 20.sp)
                Spacer(modifier = Modifier.height(8.dp))
                Text("Vidas restantes: ${"❤️".repeat(lives)}", fontSize = 20.sp)
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    "Puntaje: ${(score.toFloat() / totalQuestions * 100).toInt()}%",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF6A1B9A)
                )
                Spacer(modifier = Modifier.height(32.dp))
                Button(
                    onClick = {
                        currentIndex = 0
                        selectedIndex = null
                        feedback = null
                        lives = 3
                        score = 0
                        quizFinished = false
                        confirmed = false
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6A1B9A))
                ) {
                    Text("Volver a intentar")
                }
            }

        } else {
            // ── Pantalla de pregunta ────────────────────────────────────
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                // Vidas
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("Vidas: ", fontWeight = FontWeight.Bold)
                    Text("❤️".repeat(lives) + "🖤".repeat(3 - lives), fontSize = 18.sp)
                }

                // Pregunta
                Text(
                    text = "Pregunta ${currentIndex + 1} de $totalQuestions",
                    color = Color.Gray,
                    fontSize = 14.sp
                )

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFF3E5F5))
                ) {
                    Text(
                        text = currentQuestion.question,
                        modifier = Modifier.padding(16.dp),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }

                // Opciones
                currentQuestion.options.forEachIndexed { index, option ->
                    val isSelected = selectedIndex == index
                    val bgColor = when {
                        confirmed && index == currentQuestion.correctIndex -> Color(0xFFC8E6C9)
                        confirmed && isSelected && index != currentQuestion.correctIndex -> Color(0xFFFFCDD2)
                        isSelected -> Color(0xFFE1BEE7)
                        else -> Color.White
                    }

                    OutlinedButton(
                        onClick = { if (!confirmed) selectedIndex = index },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.outlinedButtonColors(containerColor = bgColor)
                    ) {
                        Text(
                            text = option,
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Start,
                            color = Color.Black
                        )
                    }
                }

                // Feedback
                feedback?.let {
                    Text(
                        text = it,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (it.startsWith("✅")) Color(0xFF2E7D32) else Color(0xFFC62828)
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                // Botón Confirmar / Siguiente / Ver resultados
                if (!confirmed) {
                    Button(
                        onClick = {
                            confirmed = true
                            val correct = selectedIndex == currentQuestion.correctIndex
                            if (correct) {
                                feedback = "✅ Correcto"
                                score++
                            } else {
                                feedback = "❌ Incorrecto"
                                lives--
                                if (lives <= 0) {
                                    quizFinished = true
                                }
                            }
                        },
                        enabled = selectedIndex != null,
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6A1B9A))
                    ) {
                        Text(if (isLastQuestion) "Ver resultados" else "Confirmar")
                    }
                } else {
                    Button(
                        onClick = {
                            if (isLastQuestion) {
                                quizFinished = true
                            } else {
                                currentIndex++
                                selectedIndex = null
                                feedback = null
                                confirmed = false
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6A1B9A))
                    ) {
                        Text(if (isLastQuestion) "Ver resultados" else "Siguiente →")
                    }
                }

                // Porcentaje de avance
                Text(
                    text = "Porcentaje de avance: $progress%",
                    color = Color.Gray,
                    fontSize = 13.sp,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )

                LinearProgressIndicator(
                    progress = { currentIndex.toFloat() / totalQuestions },
                    modifier = Modifier.fillMaxWidth(),
                    color = Color(0xFF6A1B9A)
                )
            }
        }
    }
}
