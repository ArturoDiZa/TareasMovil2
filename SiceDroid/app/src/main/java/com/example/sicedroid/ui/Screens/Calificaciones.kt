package com.example.sicedroid.ui.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.sicedroid.Models.Cal_Unidad
import com.example.sicedroid.Models.Materia
import com.example.sicedroid.Models.final
import kotlinx.coroutines.async

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Calificaciones(navController: NavController,
           loginViewModel: LoginViewModel = viewModel(factory = LoginViewModel.Factory)
){
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection)
        ,
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = "Calificaciones")
                },
                navigationIcon = {
                    IconButton(
                        onClick = { /*TODO*/ }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = null)
                    }
                }
            )
        }
    ){innerPadding ->
        val scope = rememberCoroutineScope()
        var materias by remember { mutableStateOf<List<final>>(emptyList()) }
        var califs by remember { mutableStateOf<List<Cal_Unidad>>(emptyList()) }

        LaunchedEffect(key1 = Unit) {
            materias = scope.async {
                loginViewModel.getCalificacionFinal().toMutableList()
            }.await()
            //Log.d("Perro inside", materias.toString())
        }

        LaunchedEffect(key1 = Unit) {
            califs = scope.async {
                loginViewModel.getCalificacionUnidad().toMutableList()
            }.await()
            //Log.d("Perro inside", califs.toString())
        }
        Box(
            Modifier
                .padding(innerPadding)) {

            // ExpandCard(m = final())
            
         
            if (materias.isNotEmpty()) {
                Log.d("Perro outside", califs.toString())

                LazyColumn(
                    Modifier
                        .padding(top = 20.dp)
                        .fillMaxSize()
                        .align(Alignment.Center),
                    content = {
                        items(materias){
                                item ->
                                ExpandCard(m = item, finder(item.grupo,califs))
                        }
                    })
            }
        }
    }
}

private fun finder(materia: String, lista: List<Cal_Unidad>): Cal_Unidad {
    val calis =  lista.find { it.Grupo.equals(materia)}
    //Log.d("INFO",calis.toString())
    if (calis != null){
        return calis
    }
    return Cal_Unidad()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ExpandCard(m: final, c: Cal_Unidad){
    val openAlertDialog = remember { mutableStateOf(false) }
    var content = remember { mutableStateOf("") }

    when {
        openAlertDialog.value -> {
            if (c.Grupo.equals(m.grupo)){
                Log.d("Entro",c.Materia)
                if(c.C1!=null && c.C1.isNotBlank())
                    content.value = content.value +"-Unidad 1: " + c.C1 +"\n"
                if(c.C2!= null && c.C2.isNotBlank())
                    content.value = content.value + "Unidad 2: "+ c.C2 +"\n"
                if(c.C3!=null && c.C3.isNotBlank())
                    content.value = content.value + "Unidad 3: "+c.C3 +"\n"
                if(c.C4 != null && c.C4.isNotBlank())
                    content.value = content.value + "Unidad 4: "+c.C4 +"\n"
                if(c.C5!=null && c.C5.isNotBlank())
                    content.value = content.value + "Unidad 5: "+c.C5 +"\n"
                if(c.C6!=null && c.C6.isNotBlank())
                    content.value = content.value + "Unidad 6: "+c.C6 +"\n"
                if(c.C7!=null && c.C7.isNotBlank())
                    content.value = content.value + "Unidad 7: "+c.C7 +"\n"
                if(c.C8!=null&&c.C8.isNotBlank())
                    content.value = content.value + "Unidad 8: "+c.C8 +"\n"
                if(c.C9!=null&&c.C9.isNotBlank())
                    content.value = content.value + "Unidad 9: "+c.C9 +"\n"
                if(c.C10!=null && c.C10.isNotBlank())
                    content.value = content.value + "Unidad 10: "+c.C10 +"\n"
                if(c.C11 != null && c.C11.isNotBlank())
                    content.value = content.value + "Unidad 11: "+c.C11 +"\n"
                if(c.C12!= null && c.C12.isNotBlank())
                    content.value = content.value + "Unidad 12: "+c.C12 +"\n"
                if(c.C13!=null && c.C13.isNotBlank())
                    content.value = content.value + "Unidad 13: "+c.C13 +"\n"
            }else{
                Log.d("No entro", ":C")
            }
            DataDialog(
                onDismissRequest = { openAlertDialog.value = false },
                onConfirmation = {
                    openAlertDialog.value = false
                },
                dialogTitle = m.materia,
                content = content.value.split("-")[1]
            )
        }
    }

    Card(
        onClick = {
            openAlertDialog.value=true
        },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.onTertiary,
        ),
        modifier = Modifier
            .fillMaxWidth()
            .height(90.dp)
            .padding(bottom = 5.dp)
    ) {
       Row(
           verticalAlignment = Alignment.CenterVertically
       ) {
            Text(
                text = m.materia,
                modifier = Modifier
                    .weight(0.5f)
                    .align(Alignment.CenterVertically),
                textAlign = TextAlign.Center,
            )
           Text(text = "Calf. Final: ${m.calif}",
               modifier = Modifier
                   .padding(16.dp),
               textAlign = TextAlign.End,
               )
           
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DataDialog(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    dialogTitle: String,
    content: String
) {
    AlertDialog(
        title = {
            Column {
                Text(
                    text = dialogTitle,
                    fontSize = 10.sp,
                    lineHeight = 10.sp
                )
                Text(text = "Calif. parciales")
            }
        },
        text = {
            //TODO(Cambiar esta parte por calif parciales)
            Text(text = content)
        },
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirmation()
                }
            ) {
                Text("OK")
            }
        }
    )
}
