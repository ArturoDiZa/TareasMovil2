package com.example.sms_service

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.telephony.SmsManager
import android.telephony.TelephonyManager
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

class sms {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @Composable
    fun Screensms() {
        Scaffold(
            modifier = Modifier.fillMaxSize()
        ) {
            var num by remember { mutableStateOf("") }
            var messege by remember { mutableStateOf("") }
            OutlinedTextField(
                value = num,
                onValueChange = { num = it },
                maxLines = 1,
                placeholder = { Text("Numero De telefono") })
            Spacer(modifier = Modifier.height(25.dp))
            OutlinedTextField(
                value = messege,
                onValueChange = { messege = it },
                maxLines = 1,
                placeholder = { Text("Cuerpo del mensaje") })
            Spacer(modifier = Modifier.height(25.dp))
            OutlinedButton(onClick = { }) {
                Text(
                    text = "Enviar",
                    modifier = Modifier.width(120.dp),
                    textAlign = TextAlign.Center
                )
            }


        }

    }

}