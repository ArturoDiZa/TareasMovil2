package com.example.sice

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.sice.data.AlumnosRepository
import com.example.sice.ui.theme.SiceTheme


@Composable
fun CargaAcademica(navController: NavController,
                   text:String?) {
    val alumnoInfo=text?.split("(",")")?.get(1)?.split(",")
    Log.d("info",""+alumnoInfo)
    /*
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(
            top = 115.dp,
            bottom =  115.dp)
    ) {
        items(){
            Column(modifier = Modifier.padding( start = 15.dp, end = 15.dp), verticalArrangement = Arrangement.Center){
                Text(text = it.TitleNote)
                Text(text = it.NoteDate)
                Divider()
            }
        }
    }
     */
}