/*
 * Copyright 2023 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.ivanvega.milocationymapascompose.ui.location

import android.Manifest
import android.annotation.SuppressLint
import android.text.method.Touch.onTouchEvent
import android.util.Log
import androidx.annotation.RequiresPermission
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.GoogleMap.OnMyLocationClickListener
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.Polyline
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import net.ivanvega.milocationymapascompose.ApiService
import net.ivanvega.milocationymapascompose.RouteResponse
import net.ivanvega.milocationymapascompose.permission.ui.PermissionBox
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@SuppressLint("MissingPermission")
/*@Sample(
    name = "Location - Getting Current Location",
    description = "This Sample demonstrate how to request of current location",
    documentation = "https://developer.android.com/training/location/retrieve-current",
)*/
@Composable
fun CurrentLocationScreen() {
    val permissions = listOf(
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION,
    )
    PermissionBox(
        permissions = permissions,
        requiredPermissions = listOf(permissions.first()),
        onGranted = {
            CurrentLocationContent(
                usePreciseLocation = it.contains(Manifest.permission.ACCESS_FINE_LOCATION),
            )
        },
    )
}

@RequiresPermission(
    anyOf = [Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION],
)
@Composable
fun CurrentLocationContent(usePreciseLocation: Boolean) {
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val locationClient = remember { LocationServices.getFusedLocationProviderClient(context) }
    var singapore = remember { LatLng(20.137104, -101.200620) }

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(singapore, 10f)
    }

    Column(Modifier.fillMaxWidth().animateContentSize().padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp), horizontalAlignment = Alignment.CenterHorizontally,) {
        val markerState = rememberMarkerState(position = singapore)

        Box(Modifier.fillMaxSize()){
            GoogleMap(
                onMapClick = {
                             val click = OnMyLocationClickListener {  }
                     val MyClick = it
                    singapore = it
                    Log.d("onMapClick ",MyClick.toString())
                },
                onMyLocationClick = { val MyClick = it
                Log.d("onMyLocationClick ","MyClick")
                //GeoPoint p = mapView.getProjection().fromPixels((int) event.getX(), (int) event.getY())
                }, modifier = Modifier.fillMaxSize(), cameraPositionState = cameraPositionState) {
                Marker(state = MarkerState(position = singapore), title = "Home", snippet = "Marker in home")
                if(!markerState.position.equals(singapore)){
                    Marker(state = MarkerState(position = markerState.position), title = "Ubicacion actual", snippet =  "Marker in current position")

                    val RouteList = remember { mutableStateOf<List<LatLng>>(emptyList()) }
                    createRoute(singapore,markerState.position){routePoints->
                        val pointsList = mutableListOf<LatLng>()
                        for (i in routePoints.indices step 2) {
                            val lat = routePoints[i]
                            val lng = routePoints[i + 1]
                            pointsList.add(LatLng(lat, lng))
                        }
                        RouteList.value=pointsList
                    }
                    Polyline(points = RouteList.value)
                }
            }
            Row (modifier = Modifier.align(Alignment.BottomStart)){
                Button(
                    onClick = {
                        //To get more accurate or fresher device location use this method
                        scope.launch(Dispatchers.IO) {
                            val priority = if (usePreciseLocation) {
                                Priority.PRIORITY_HIGH_ACCURACY
                            } else {
                                Priority.PRIORITY_BALANCED_POWER_ACCURACY
                            }
                            val result = locationClient.getCurrentLocation(
                                priority,
                                CancellationTokenSource().token,
                            ).await()
                            result?.let { fetchedLocation ->
                                markerState.position = LatLng(fetchedLocation.latitude,fetchedLocation.longitude)
                            }
                        }
                    },
                ) {
                    Text(text = "Home")
                }
            }
        }
    }
}


/*
public boolean onTouchEvent(MotionEvent event, MapView mapView)
{
    //---when user lifts his finger---
    if (event.getAction() == 1) {
        GeoPoint p = mapView.getProjection().fromPixels(
            (int) event.getX(),
            (int) event.getY());
        Toast.makeText(getBaseContext(),
            p.getLatitudeE6() / 1E6 + "," +
                    p.getLongitudeE6() /1E6 ,
            Toast.LENGTH_SHORT).show();
    }
    return false;
}
}
 */
private fun createRoute(startLocation: LatLng, endLocation: LatLng, callback: (List<Double>) -> Unit) {
    val routePoints = mutableListOf<LatLng>()
    CoroutineScope(Dispatchers.IO).launch {
        val call = getRetrofit().create(ApiService::class.java)
            .getRoute("5b3ce3597851110001cf62483965dad304464ee688452576529f19ab", "${startLocation.longitude},${startLocation.latitude}", "${endLocation.longitude},${endLocation.latitude}")
        if (call.isSuccessful) {
            drawRoute(call.body(), routePoints)
            val pointsList = routePoints.flatMap { listOf(it.latitude, it.longitude) }
            Log.d("route", "Route points as list: $pointsList")
            callback(pointsList)
        } else {
            Log.i("route", "KO")
        }
    }
}

private fun drawRoute(routeResponse: RouteResponse?, routePoints: MutableList<LatLng>) {
    routeResponse?.features?.firstOrNull()?.geometry?.coordinates?.forEach {
        val latLng = LatLng(it[1], it[0])
        routePoints.add(latLng)
    }
    Log.i("ghs", "Drawn route points: $routePoints")
}

private fun getRetrofit(): Retrofit {
    return Retrofit.Builder()
        .baseUrl("https://api.openrouteservice.org/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}