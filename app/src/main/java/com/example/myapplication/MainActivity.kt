package com.example.myapplication

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.example.myapplication.ui.theme.color_letra
import com.example.myapplication.ui.theme.fondo_teclado

data class BotonModelo(
    val id: String,
    var numero: String,
    var operacion_aritmetica: OperacionesAritmeticas = OperacionesAritmeticas.Ninguna,
    var operacion_a_mostrar: String = ""
)

enum class EstadosCalculadora {
    CuandoEstaEnCero,
    AgregandoNumeros,
    SeleccionadoOperacion,
    MostrandoResultado
}

enum class OperacionesAritmeticas {
    Ninguna, Suma, Resta, Multiplicacion, Division, Resultado
}

var hileras_de_botones_a_dibujar = listOf(
    listOf(
        BotonModelo("boton_9", "9"),
        BotonModelo("boton_8", "8"),
        BotonModelo("boton_7", "7", OperacionesAritmeticas.Division, "/")
    ),
    listOf(
        BotonModelo("boton_6", "6"),
        BotonModelo("boton_5", "5"),
        BotonModelo("boton_4", "4")
    ),
    listOf(
        BotonModelo("boton_3", "3"),
        BotonModelo("boton_2", "2"),
        BotonModelo("boton_1", "1")
    ),
    listOf(
        BotonModelo("boton_+", "+", OperacionesAritmeticas.Suma, "+"),
        BotonModelo("boton_-", "0", OperacionesAritmeticas.Resta, "*"),
        BotonModelo("boton_=", "-", OperacionesAritmeticas.Multiplicacion, "-")
    ),
    listOf(
        BotonModelo("boton_+", "/", OperacionesAritmeticas.Division, "+"),
        BotonModelo("boton_=", "*", OperacionesAritmeticas.Resultado, "=")
    ),
    listOf(
        BotonModelo("boton_+", "C", OperacionesAritmeticas.Division, "+"),
        BotonModelo("boton_=", "=", OperacionesAritmeticas.Resultado, "=")
    )
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                Calculadora()
            }
        }
    }
}

@Composable
fun Calculadora() {
    var pantalla_calculadora = remember { mutableStateOf("0") }
    var numero_anterior = remember { mutableStateOf("0") }
    var estado_de_la_calculadora = remember { mutableStateOf(EstadosCalculadora.CuandoEstaEnCero) }
    var operacion_seleccionada = remember { mutableStateOf(OperacionesAritmeticas.Ninguna) }
    val buttonSpacing = 8.dp

    fun realizarOperacion(): String {
        val num1 = numero_anterior.value.toDoubleOrNull() ?: 0.0
        val num2 = pantalla_calculadora.value.toDoubleOrNull() ?: 0.0
        return when (operacion_seleccionada.value) {
            OperacionesAritmeticas.Suma -> (num1 + num2).toString()
            OperacionesAritmeticas.Resta -> (num1 - num2).toString()
            OperacionesAritmeticas.Multiplicacion -> (num1 * num2).toString()
            OperacionesAritmeticas.Division -> if (num2 != 0.0) (num1 / num2).toString() else "Error"
            else -> pantalla_calculadora.value
        }
    }
    
    fun pulsar_boton(boton: BotonModelo) {
        Log.v("BOTONES_INTERFAZ", "Se ha pulsado el boton ${boton.id}")

        when (estado_de_la_calculadora.value) {
            EstadosCalculadora.CuandoEstaEnCero -> {
                pantalla_calculadora.value = if (boton.id == "boton_0") "0" else boton.numero
                estado_de_la_calculadora.value = EstadosCalculadora.AgregandoNumeros
            }

            EstadosCalculadora.AgregandoNumeros -> {
                pantalla_calculadora.value += boton.numero
            }

            EstadosCalculadora.SeleccionadoOperacion -> {
                if (boton.operacion_aritmetica != OperacionesAritmeticas.Ninguna &&
                    boton.operacion_aritmetica != OperacionesAritmeticas.Resultado
                ) {
                    operacion_seleccionada.value = boton.operacion_aritmetica
                    numero_anterior.value = pantalla_calculadora.value
                    pantalla_calculadora.value = "0"
                }
            }

            EstadosCalculadora.MostrandoResultado -> {
                pantalla_calculadora.value = "0"
                estado_de_la_calculadora.value = EstadosCalculadora.CuandoEstaEnCero
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(buttonSpacing), // Esto es correcto
        horizontalAlignment = Alignment.CenterHorizontally // Se reemplaza por una alineación válida
    ) {
        Text(
            text = pantalla_calculadora.value,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .height(200.dp),
            textAlign = TextAlign.Right,
            color = color_letra,
            fontSize = 86.sp
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(fondo_teclado),
            verticalArrangement = Arrangement.Top
            ) {
            hileras_de_botones_a_dibujar.forEach { fila_de_botones ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                    ) {
                    fila_de_botones.forEach { boton_a_dibujar ->
                        Boton(
                            etiqueta = boton_a_dibujar.numero,
                            modifier = Modifier
                                .padding(8.dp)
                                .weight(1f)
                            )
                        {
                            pulsar_boton(boton_a_dibujar)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Boton(etiqueta: String, modifier: Modifier = Modifier, alPulsar: () -> Unit = {}) {
    Button(onClick = { alPulsar() }, modifier = modifier.clip(CircleShape)) {
        Text(text = etiqueta, fontSize = 45.sp, textAlign = TextAlign.Center, color = Color.White)
    }
}


/* NOTAS
    px:Píxeles: corresponde a los píxeles reales en la pantalla
    dp:Píxeles independientes de la densidad: una unidad abstracta que se basa en la densidad física de la pantalla.
    sp : píxeles independientes de la escala: es como la unidad dp, pero la preferencia de tamaño de fuente del usuario también la escala.
    pt : Puntos – 1/72 de pulgada según el tamaño físico de la pantalla, asumiendo una pantalla con una densidad de 72 ppp.
    mm : Milímetros – Basado en el tamaño físico de la pantalla.
    en : Pulgadas – Basado en el tamaño físico de la pantalla.
*/

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyApplicationTheme {
        Calculadora()
    }
}

@Preview(showBackground = true)
@Composable
fun mostrar_boton(){
    MyApplicationTheme {
        Boton("4")
    }
}