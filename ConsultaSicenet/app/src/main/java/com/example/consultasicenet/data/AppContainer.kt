package com.example.consultasicenet.data

import com.example.consultasicenet.network.SiceApiService
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.simplexml.SimpleXmlConverterFactory

val url = "xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
        "  <soap:Body>\n" +
        "    <accesoLogin xmlns=\"http://tempuri.org/\">\n" +
        "      <strMatricula>string</strMatricula>\n" +
        "      <strContrasenia>string</strContrasenia>\n" +
        "      <tipoUsuario>ALUMNO or DOCENTE</tipoUsuario>\n" +
        "    </accesoLogin>\n" +
        "  </soap:Body>\n" +
        "</soap:Envelope>\n"

interface AppContainer {
    val SiceRepository: SiceRepository
}

class DefaultAppContainer : AppContainer {
    private val baseUrl = "sicenet.surguanajuato.tecnm.mx"
    private val interceptor= CookiesInterceptor()
    private val cliente = OkHttpClient.Builder().addInterceptor(interceptor).build()


    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(SimpleXmlConverterFactory.create())
        .baseUrl(baseUrl).client(cliente)
        .build()

    private val retrofitService: SiceApiService by lazy {
        retrofit.create(SiceApiService::class.java)
    }
    override val SiceRepository: SiceRepository by lazy {
        NetworkSiceRepository(retrofitService)
    }
}

class CookiesInterceptor : Interceptor {

    // Variable que almacena las cookies
    private var cookies: List<String> = emptyList()

    // Método para establecer las cookies
    fun setCookies(cookies: List<String>) {
        this.cookies = cookies
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()

        // Agregar las cookies al encabezado de la solicitud
        if (cookies.isNotEmpty()) {
            val cookiesHeader = StringBuilder()
            for (cookie in cookies) {
                if (cookiesHeader.isNotEmpty()) {
                    cookiesHeader.append("; ")
                }
                cookiesHeader.append(cookie)
            }

            request = request.newBuilder()
                .header("Cookie", cookiesHeader.toString())
                .build()
        }

        val response = chain.proceed(request)

        // Almacenar las cookies de la respuesta para futuras solicitudes
        val receivedCookies = response.headers("Set-Cookie")
        if (receivedCookies.isNotEmpty()) {
            setCookies(receivedCookies)
        }

        return response
    }
}