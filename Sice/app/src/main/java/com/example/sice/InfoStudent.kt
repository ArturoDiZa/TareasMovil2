package com.example.sice

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.sice.ui.theme.SiceTheme

class InfoStudent : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SiceTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                }
            }
        }
    }
}



@Composable
fun dataStudent(
    navController: NavController,
    text:String?
){
    val alumnoInfo=text?.split("(",")")?.get(1)?.split(",")
    Log.d("info",""+alumnoInfo)

    Column (horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center, modifier = Modifier.padding(7.dp)){
        Card (modifier = Modifier.fillMaxSize()){
            Column(horizontalAlignment = Alignment.CenterHorizontally,verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxHeight(.91f).fillMaxWidth().padding(3.dp)) {
                Text(
                    text = "------ ALUMNO ------",
                    fontSize = 19.sp,
                    fontFamily = FontFamily.SansSerif,
                    fontWeight = FontWeight.Bold)
                Text(
                    text = ""+alumnoInfo?.get(13)?.split("=")?.get(1),
                    fontSize = 21.sp,
                    fontFamily = FontFamily.SansSerif,
                    fontWeight = FontWeight.Bold)


                Spacer(modifier = Modifier.height(5.dp))
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "Fecha de reinscripción: "+alumnoInfo?.get(0)?.split("=")?.get(1))
                    Text(text = "Mod. Educativo: "+alumnoInfo?.get(1)?.split("=")?.get(1))
                    Text(text = "Adeudo: "+ validarCampos(alumnoInfo?.get(2)?.split("=")?.get(1)))
                    Text(text = "Adeudo descripción: "+ validarCampos(alumnoInfo?.get(4)?.split("=")?.get(1)))
                    Text(text = "Inscrito: "+ validarCampos(alumnoInfo?.get(5)?.split("=")?.get(1)))
                    Text(text = "Estatus: "+alumnoInfo?.get(6)?.split("=")?.get(1))
                    Text(text = "Semestre actual: "+alumnoInfo?.get(7)?.split("=")?.get(1))
                    Text(text = "Creditos acumulados: "+alumnoInfo?.get(8)?.split("=")?.get(1))
                    Text(text = "Creditos actuales: "+alumnoInfo?.get(9)?.split("=")?.get(1))
                    Text(text = "Carrera: "+alumnoInfo?.get(11)?.split("=")?.get(1))
                    Text(text = "Matricula: "+alumnoInfo?.get(14)?.split("=")?.get(1))
                }

            }
            Column (horizontalAlignment = Alignment.CenterHorizontally,modifier = Modifier.fillMaxWidth().padding(3.dp)){
                IconButton(
                    onClick = {
                        navController.popBackStack()
                    },
                    modifier = Modifier.size(50.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.cancel),
                        contentDescription = "",
                        modifier = Modifier.size(50.dp))
                }
            }
        }
    }
}

fun validarCampos(dato:String?):String?{
    if(dato.equals("")){
        return "Ninguno"
    }else if(dato.equals("true")){
        return "Si"
    }else if(dato.equals("false")){
        return "No"
    }else{
        return dato
    }
}


