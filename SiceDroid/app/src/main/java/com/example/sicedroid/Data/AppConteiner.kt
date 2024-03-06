package com.example.sicedroid.Data

import com.example.sicedroid.Network.SiceApiService
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.simplexml.SimpleXmlConverterFactory

interface AppContainer{
    val alumnosRepository: Repository
}
class DefaultAppContainer : AppContainer {
    private val BASE_URL = "https://sicenet.surguanajuato.tecnm.mx/"
    private val interceptor= CookiesInterceptor()
    private val cliente = OkHttpClient.Builder().addInterceptor(interceptor).build()
    private val retrofit = Retrofit.Builder()
        .addConverterFactory(SimpleXmlConverterFactory.create())
        .baseUrl(BASE_URL).client(cliente)
        .build()

    private val retrofitService : SiceApiService by lazy {
        retrofit.create(SiceApiService::class.java)
    }


    override val alumnosRepository: Repository by lazy {
        NetworkAlumnosRepository(retrofitService)
    }
}

class CookiesInterceptor : Interceptor {
    // Variable para almacenar las cookies de manera persistente
    private val cookieStore = mutableMapOf<String, String>()

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()

        // Agregar las cookies al encabezado de la solicitud
        val cookiesHeader = StringBuilder()
        for ((name, value) in cookieStore) {
            if (cookiesHeader.isNotEmpty()) {
                cookiesHeader.append("; ")
            }
            cookiesHeader.append("$name=$value")
        }

        if (cookiesHeader.isNotEmpty()) {
            request = request.newBuilder()
                .header("Cookie", cookiesHeader.toString())
                .build()
        }

        val response = chain.proceed(request)

        // Almacenar las cookies de la respuesta para futuras solicitudes
        val receivedCookies = response.headers("Set-Cookie")
        for (cookie in receivedCookies) {
            val parts = cookie.split(";")[0].split("=")
            if (parts.size == 2) {
                val name = parts[0]
                val value = parts[1]
                cookieStore[name] = value
            }
        }

        return response
    }
}