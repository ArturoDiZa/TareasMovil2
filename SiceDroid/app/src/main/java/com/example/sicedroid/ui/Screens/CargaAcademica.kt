package com.example.sicedroid.ui.screens

import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.sicedroid.Models.carga_academica
import kotlinx.coroutines.async

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CargaAcademica(navController: NavController,
           loginViewModel: LoginViewModel = viewModel(factory = LoginViewModel.Factory),
                   modifier: Modifier = Modifier
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
                    Text(text = "Carga Academica")
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
        var materias by remember { mutableStateOf<List<carga_academica>>(emptyList()) }

        LaunchedEffect(key1 = Unit) {
            materias = scope.async {
                loginViewModel.getCargaAcademica().toMutableList()
            }.await()

            Log.d("Perro inside", materias.toString())
        }

        Box(
            modifier
                .padding(innerPadding)
                ) {
            if (materias.isNotEmpty()) {
                Log.d("Perro outside", materias.toString())
                Row (
                    Modifier
                        .border(2.dp, Color.DarkGray)
                        .fillMaxWidth()
                ){
                    Text(text = "Materia", Modifier.size(width = 130.dp, height = 35.dp))
                    Text(text = "Lunes", Modifier.size(width = 80.dp, height = 35.dp))
                    Text(text = "Martes", Modifier.size(width = 80.dp, height = 35.dp))
                    Text(text = "Miercoles", Modifier.size(width = 80.dp, height = 35.dp))
                    Text(text = "Jueves", Modifier.size(width = 80.dp, height = 35.dp))
                    Text(text = "Viernes", Modifier.size(width = 80.dp, height = 35.dp))
                }
                LazyColumn(
                    Modifier
                        .padding(top = 20.dp)
                        .fillMaxSize()
                        .align(Alignment.Center),
                    content = {
                        items(materias){
                                item ->
                            Renglon(item)
                        }
                    })
            }
        }
    }
}

@Composable
private fun Renglon(m: carga_academica){
    Row(
        Modifier
            .border(2.dp, Color.DarkGray)
            .fillMaxWidth()){
        Card(){
            Text(text = m.Materia+"\n"+m.Grupo+"\n"+m.Docente, Modifier.size(width = 130.dp, height = 80.dp))
        }
        Card(){
            Text(text = m.Lunes, Modifier.size(width = 80.dp, height = 80.dp))
        }
        Card(){
            Text(text = m.Martes, Modifier.size(width = 80.dp, height = 80.dp))
        }
        Card(){
            Text(text = m.Miercoles, Modifier.size(width = 80.dp, height = 80.dp))
        }
        Card(){
            Text(text = m.Jueves, Modifier.size(width = 80.dp, height = 80.dp))
        }
        Card(){
            Text(text = m.Viernes, Modifier.size(width = 80.dp, height = 80.dp))
        }


    }
}



