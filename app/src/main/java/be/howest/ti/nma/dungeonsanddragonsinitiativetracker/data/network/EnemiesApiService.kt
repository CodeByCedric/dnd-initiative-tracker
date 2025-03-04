package be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.network

import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

//TODO far from optimal, refactor (split between interface and repo)
private const val BASE_URL =
    "https://www.dnd5eapi.co/api/"

private val retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

interface EnemiesApiService {
    @GET("monsters")
    suspend fun getEnemies(): Response<EnemiesResponse>

    @GET("monsters/{index}")
    suspend fun getEnemy(@Path("index") index: String): Response<EnemyInfo>
}

object EnemiesApi {
    val retrofitService: EnemiesApiService by lazy {
        retrofit.create(EnemiesApiService::class.java)
    }
}