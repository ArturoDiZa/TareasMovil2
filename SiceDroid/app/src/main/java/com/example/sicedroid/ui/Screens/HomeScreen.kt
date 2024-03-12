package com.example.sicedroid.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.wear.compose.material.dialog.Dialog
import com.example.sicedroid.Navigation.AppScreens
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    loginViewModel: LoginViewModel = viewModel(factory = LoginViewModel.Factory),
    text: String?,
    modifier: Modifier = Modifier
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val estudiante = text?.split("(", ")")?.get(1)?.split(",")
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    var ShowProfile by rememberSaveable { mutableStateOf(false) }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(modifier.width(200.dp)) {
                Text("Operaciones de consulta",
                    modifier = Modifier.padding(10.dp),
                    fontWeight = FontWeight.Bold,
                    )
                NavigationDrawerItem(
                    label = { Text(text = "Kardex") },
                    selected = false,
                    onClick = {
                        scope.launch {
                         //   val Kardex = loginViewModel.getKardex()
                            navController.navigate(AppScreens.Kardex.route) }
                        }
                )
                Divider(thickness = 2.dp)
                NavigationDrawerItem(
                    label = { Text(text = "Calificaciones") },
                    selected = false,
                    onClick = { navController.navigate(AppScreens.Calificaciones.route ) }
                )
                Divider(thickness = 2.dp)
                NavigationDrawerItem(
                    label = { Text(text = "Carga academica") },
                    selected = false,
                    onClick = { navController.navigate(AppScreens.CargaAcademica.route ) }
                )
                Divider(thickness = 4.dp)
                IconButton(
                    onClick = { navController.navigate(AppScreens.LoginScreen.route) },
                    modifier.fillMaxWidth()

                ) {
                    Row(modifier.align(Alignment.CenterHorizontally)) {
                        Text(text = "Cerrar sesion")
                        Icon(Icons.Filled.Done , contentDescription = "logout")
                    }
                }
            }
        },
    ) {
        Scaffold(
            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
            topBar = {
                CenterAlignedTopAppBar(
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        titleContentColor = MaterialTheme.colorScheme.primary,
                    ),
                    title = {
                        Text(
                            "Bienvenido",
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            textAlign = TextAlign.Center
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = {
                            scope.launch {
                            drawerState.apply {
                                if (isClosed) open() else close()
                            }
                        }}) {
                            Icon(
                                imageVector = Icons.Filled.Menu,
                                contentDescription = "Localized description"
                            )
                        }
                    },
                    actions = {
                        IconButton(onClick = { ShowProfile=true}) {
                            Icon(
                                imageVector = Icons.Filled.Person,
                                contentDescription = "Localized description"
                            )
                        }
                    },
                    scrollBehavior = scrollBehavior
                )
            },
        ) { innerPadding ->
           Box(modifier.padding(innerPadding)) {

           }


            //Bloque para desplegar el dialog de perfil
            when {
                ShowProfile -> {
                    Dialog(
                        true,
                        properties = DialogProperties(usePlatformDefaultWidth = false),
                        onDismissRequest = { ShowProfile = false },
                        content = {
                            Scaffold(
                                modifier= modifier.fillMaxSize(),
                                bottomBar = {
                                    Box(modifier = modifier.padding(
                                        start = 150.dp,
                                        bottom = 25.dp
                                    )){
                                        IconButton(
                                            onClick = { ShowProfile = false },
                                            ) {
                                            Icon(imageVector = Icons.Filled.Close,
                                                contentDescription = null,
                                                modifier
                                                    .fillMaxSize(.9f)
                                                    .background(Color.DarkGray, shape = CircleShape)
                                            )

                                        }
                                    }

                                }
                            ) {innerPadding ->
                                Box(modifier = modifier.padding(innerPadding)){
                                    Box(
                                        contentAlignment = Alignment.Center,
                                        modifier = modifier.padding(innerPadding)
                                    ) {
                                        Column(
                                            modifier = Modifier
                                                .padding(7.dp)
                                                .fillMaxSize(),
                                            verticalArrangement = Arrangement.Top,
                                            horizontalAlignment = Alignment.CenterHorizontally
                                        ) {
                                            Spacer(modifier = Modifier.height(80.dp))

                                            Icon(imageVector = Icons.Filled.Person, contentDescription = null)
                                            Text(text = estudiante?.get(0)?.split("=")?.get(1).toString(), fontWeight = FontWeight.Bold)
                                            Text(text = "No. Control: " + estudiante?.get(6)?.split("=")?.get(1))

                                            Text(text = estudiante?.get(5)?.split("=")?.get(1).toString())
                                            Text(text = estudiante?.get(7)?.split("=")?.get(1).toString())

                                            Spacer(modifier= Modifier.height(5.dp))
                                            Text(text = "Semestre actual: " + estudiante?.get(2)?.split("=")?.get(1))
                                            Text(text = "Creditos acumulados: " + estudiante?.get(3)?.split("=")?.get(1))
                                            Text(text = "Creditos actuales: " + estudiante?.get(4)?.split("=")?.get(1))

                                        }
                                    }
                                }
                            }
                        }
                    )
                }

            }
        }
    }
}



