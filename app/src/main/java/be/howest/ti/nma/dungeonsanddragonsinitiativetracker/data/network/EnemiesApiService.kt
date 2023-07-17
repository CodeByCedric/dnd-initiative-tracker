package be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.network

import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET


private const val BASE_URL =
    "https://www.dnd5eapi.co/api/"

private val retrofit = Retrofit.Builder()
    .addConverterFactory(ScalarsConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

interface EnemiesApiService {
    @GET("monsters")
    suspend fun getEnemies(): String
}

object EnemiesApi {
    val retrofitService: EnemiesApiService by lazy {
        retrofit.create(EnemiesApiService::class.java)
    }
}