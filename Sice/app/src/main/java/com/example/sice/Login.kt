package com.example.sice


import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.sice.ViewModel.LoginView
import com.example.sice.navigation.AppNavigation
import com.example.sice.navigation.AppScreens
import com.example.sice.ui.theme.SiceTheme
import kotlinx.coroutines.launch


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SiceTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNavigation()
                }
            }
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun loginApp(
    navController: NavController,
    viewmodel: LoginView = viewModel(factory = LoginView.Factory)
){
    val scope= rememberCoroutineScope()
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.padding(40.dp)
    ) {
        OutlinedTextField(
            value = viewmodel.noControl,
            label = { Text(text = "Enter your registration") },
            onValueChange = {
                viewmodel.updateMatricula(it)
                viewmodel.updateErrorLogin(false)
            },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(25.dp))
        OutlinedTextField(
            value = viewmodel.password,
            label = { Text(text = "Enter your password") },
            onValueChange = {
                viewmodel.updatePassword(it)
                viewmodel.updateErrorLogin(false)
            },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation(),
        )
        Spacer(modifier = Modifier.height(35.dp))
        Row(
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier.fillMaxWidth()
        ) {
            ExposedDropdownMenuBox(
                expanded = viewmodel.expand,
                onExpandedChange = {viewmodel.updateExpandido(it)}
            ) {
                OutlinedTextField(
                    value = viewmodel.userType,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text(stringResource(id = R.string.tipoUsuario)) },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = false) },
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth(),
                )
                ExposedDropdownMenu(
                    expanded = viewmodel.expand,
                    onDismissRequest = { viewmodel.expand = false })
                {
                    var alumno = stringResource(id = R.string.alumno)
                    var docente = stringResource(id = R.string.docente)
                    DropdownMenuItem(
                        text = { Text(alumno) },
                        onClick = {
                            viewmodel.updateTipoUsuario(alumno)
                            viewmodel.expand=false
                            viewmodel.updateErrorLogin(false)
                        }
                    )
                    DropdownMenuItem(
                        text = { Text(docente) },
                        onClick = {
                            viewmodel.updateTipoUsuario(docente)
                            viewmodel.expand=false
                            viewmodel.updateErrorLogin(false)
                        }
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(25.dp))
        Row(
            modifier=Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            OutlinedButton(
                onClick = {
                    if (!viewmodel.noControl.equals("") && !viewmodel.password.equals("")){
                       scope.launch {
                           if(viewmodel.getAccess(viewmodel.noControl,viewmodel.password,viewmodel.userType)){
                               viewmodel.updateErrorLogin(false)
                               var info=viewmodel.getInfo()
                               var encodedInfo = Uri.encode(info)
                               //navController.navigate(route = AppScreens.Info.route+encodedInfo)
                               val carg=viewmodel.getCarga()
                               var encodedCarg = Uri.encode(info)
                               navController.navigate(route = AppScreens.CargaA.route+encodedCarg)
                           }else{
                               viewmodel.updateErrorLogin(true)
                           }
                       }
                    }else{
                        viewmodel.updateErrorLogin(true)
                    }
                },

            ) {
                Text(text = "Login",
                    modifier=Modifier.width(120.dp),
                    textAlign = TextAlign.Center)
            }
        }
        if(viewmodel.loginError){
            Spacer(modifier = Modifier.height(45.dp))
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ){
                /*Icon(
                    painter = painterResource(id = R.drawable.warning),
                    contentDescription = "",
                    modifier=Modifier.size(35.dp),
                    tint = Color(255,124,112))
                 */
                Text(
                    text = "matricula o contraseña",
                    fontSize = 14.sp,
                    color=Color(255,158,142))
                Text(text = "son incorrectos",
                    fontSize = 14.sp,
                    color=Color(255,158,142))
            }
        }

    }
}

