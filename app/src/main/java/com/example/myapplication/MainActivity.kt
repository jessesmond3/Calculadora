package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplication.ui.theme.MyApplicationTheme
import javax.sql.RowSetWriter

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme{
                Calculadora()
                }
            }
        }
    }


@Composable
fun Calculadora() {
    Column (modifier = Modifier.fillMaxSize(),
    verticalArrangement = Arrangement.Center) {
        Text(text = "Hello World", modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
            .background(Color.Blue)
            .height(50.dp),
            textAlign = TextAlign.Right)

        Column(modifier = Modifier.fillMaxSize().background(Color.DarkGray)){
            Row (horizontalArrangement = Arrangement.SpaceAround,
                modifier = Modifier.fillMaxWidth()) {
                Boton("9")
                Boton("6")
                Boton("3")
            }

            Row (horizontalArrangement = Arrangement.SpaceAround,
                modifier = Modifier.fillMaxWidth()) {
                Boton("8")
                Boton("5")
                Boton("2")
            }
            Row (horizontalArrangement = Arrangement.SpaceAround,
                modifier = Modifier.fillMaxWidth()) {
                Boton("7")
                Boton("4")
                Boton("1")
            }
            Row (horizontalArrangement = Arrangement.SpaceAround,
                modifier = Modifier.fillMaxWidth()) {
                Boton(".")
                Boton("0")
                Boton("op")
            }
        }
    }
}

@Composable
fun Boton(etiqueta: String){
    Text(
        etiqueta, modifier = Modifier.background(Color.Green)
            //.background(Color.Green)
            .padding(25.dp)
            .background(Color.Green)
            .height(50.dp)
            .width(50.dp),
        textAlign = TextAlign.Center,
        //fontSize = font
    )
}

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