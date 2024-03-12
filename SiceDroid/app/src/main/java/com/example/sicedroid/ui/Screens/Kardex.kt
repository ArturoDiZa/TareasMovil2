package com.example.sicedroid.ui.screens

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedCard
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
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.sicedroid.Models.Materia
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext


@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Kardex(navController: NavController,
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
                    Text(text = "Kardex")
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
        var materias by remember { mutableStateOf<List<Materia>>(emptyList()) }

        LaunchedEffect(key1 = Unit) {
            materias = scope.async {
                loginViewModel.getKardex().toMutableList()
            }.await()

            Log.d("Perro inside", materias.toString())
        }

        Box(
            modifier
                .padding(innerPadding)) {
            if (materias.isNotEmpty()) {
                Log.d("Perro outside", materias.toString())
                Row(
                    Modifier
                        .border(2.dp, Color.DarkGray)
                        .fillMaxWidth()
                    )
                    {
                    Text(text = "Clave", Modifier.weight(0.1f))
                    Text(text = "Materia",
                        Modifier
                            .weight(0.5f)
                            .padding(start = 100.dp))
                    Text(text = "Calif  ")
                }
                LazyColumn(
                    Modifier
                        .padding(top = 20.dp)
                        .fillMaxSize()
                        .align(Alignment.Center),
                    content = {
                    items(materias){
                        item ->
                        Row(item)
                    }
                })
            }
        }
    }

}


@Composable
private fun Row(m: Materia){
    Row(
        Modifier
            .border(2.dp, Color.DarkGray)
            .fillMaxWidth()){

            Text(text = m.ClvOfiMat, Modifier.weight(0.3f))


            Text(text = m.Materia, Modifier.weight(0.5f))


           Text(text = m.Calif+"  ")


    }
}