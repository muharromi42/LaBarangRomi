package com.muharromi.labarangromi

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Body

data class Barang(
    val kode_barang: String,
    val nama_barang: String,
    val jumlah: Int
)

data class IncreaseQuantityRequest(
    val jumlah_tambahan: Int
)

data class ApiResponse<T>(
    val status: String,
    val message: String?,
    val data: T?,
    val errors: Map<String, List<String>>?
)

interface ApiService {
    @GET("api/v1/barang")
    fun getBarang(): Call<ApiResponse<List<Barang>>>

    @POST("api/v1/barang/increase/{kode_barang}")
    fun increaseQuantity(
        @Path("kode_barang") kode_barang: String,
        @Body request: IncreaseQuantityRequest
    ): Call<ApiResponse<Barang>>
}
