package com.example.sicedroid.ui.screens

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.Toast
import android.widget.Toast.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.sicedroid.Navigation.AppScreens
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import com.example.sicedroid.ui.screens.DialogResult as DialogResult

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun LoginScreen(
    navController: NavController,
    loginViewModel: LoginViewModel = viewModel(factory = LoginViewModel.Factory)
){
    var _matricula by remember { mutableStateOf("") }
    var _password by remember { mutableStateOf("") }
    val _padding = 15.dp

    Scaffold(
        topBar ={
            TopAppBar(
                title = { 
                    Text(
                        text = "El SICEDROID",
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor =MaterialTheme.colorScheme.primary
                )
            )
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(_padding),
            contentAlignment = Alignment.Center,
        ) {
            Column(
                modifier = Modifier . fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                var isError_User by rememberSaveable { mutableStateOf(false) }
                var isError_Pass by rememberSaveable { mutableStateOf(false) }
                TextField(
                    isError = isError_User,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(_padding)
                        .semantics {
                            if (isError_User) error("Usuario faltante")
                        },
                    value = _matricula,
                    onValueChange = { newText ->
                        _matricula = newText
                        isError_User=false
                    },
                    label = { Text("Numero de control") },
                    singleLine = true,
                    supportingText = {
                        if(isError_User){
                            Text(
                                modifier = Modifier.fillMaxWidth(),
                                text = "Usuario faltante *",
                                textAlign = TextAlign.End,
                                color = Color.Red
                            )
                        }
                    }
                )
                var passwordHidden by rememberSaveable { mutableStateOf(true) }
                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(_padding)
                        .semantics {
                            if (isError_Pass) error("Contraseña faltante")
                        },
                    value = _password,
                    singleLine = true,
                    onValueChange = { value ->
                        _password = value
                        isError_Pass = false
                    },
                    isError = isError_Pass,
                    label = {
                        Text("Contraseña")
                    },
                    visualTransformation =
                    if (passwordHidden) PasswordVisualTransformation() else VisualTransformation.None,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    trailingIcon = {
                        IconButton(onClick = { passwordHidden = !passwordHidden }) {
                            val visibilityIcon =
                                if (passwordHidden) Icons.Filled.Visibility else Icons.Filled.VisibilityOff

                            val description = if (passwordHidden) "Show password" else "Hide password"
                            Icon(imageVector = visibilityIcon, contentDescription = description)
                        }
                    },
                    supportingText = {
                        if(isError_Pass){
                            Text(
                                modifier = Modifier.fillMaxWidth(),
                                text = "Contraseña faltante *",
                                textAlign = TextAlign.End,
                                color = Color.Red
                            )
                        }
                    }
                )
                val scope = rememberCoroutineScope()
                var Denegado by rememberSaveable { mutableStateOf(false) }
                Button(
                    onClick = {
                        //Validar que haya algo en los campos de texto
                        if(_matricula.isNotEmpty() && _password.isNotEmpty()){
                            scope.launch {
                                if(loginViewModel.getAccess(_matricula,_password)) {

                                    val info = loginViewModel.getInfo()
                                    var encodedInfo = Uri.encode(info)
                                    navController.navigate(AppScreens.AccesoLoginApp.route + encodedInfo)
                                }else{
                                    //Mostrar mensaje de credenciales invalidas
                                    Denegado = true
                                }
                            }
                        }else{
                            //Mostrar mensaje de datos faltantes
                            if(_matricula.isEmpty())
                                isError_User=true
                            if(_password.isEmpty())
                                isError_Pass=true
                        }

                    },
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(_padding)
                ) {
                    Text("Ingresar")
                }
                if(Denegado){
                    DialogResult(
                        LocalContext.current,
                        "Inicio fallido",
                        "Credenciales incorrectas"
                    ).show()
                    Denegado=false
                }
            }
        }
    }
}


fun ErrorMessage(text: String){

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DialogResult(
    context: Context,
    dialogTitle: String,
    dialogText: String
): Dialog {
    val builder = AlertDialog.Builder(context)
    builder
        .setTitle(dialogTitle)
        .setMessage(dialogText)
        .setPositiveButton("Aceptar") { dialog, which ->
            // Aquí puedes realizar acciones antes de cerrar el diálogo si es necesario
            dialog.dismiss()  // Cierra el cuadro de diálogo
        }

    return builder.create()
}