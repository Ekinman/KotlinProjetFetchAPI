package cnns.com.example.kotlintestapp

import cnns.com.example.kotlintestapp.models.*
import retrofit2.Response
import retrofit2.http.*


interface ApiService {

    @GET("pokemon/{num}")
    suspend fun getPokemontById(@Path("num") num : Int): Response<Pokemon>

}