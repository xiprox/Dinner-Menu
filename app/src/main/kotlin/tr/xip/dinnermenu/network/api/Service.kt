package tr.xip.dinnermenu.network.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import tr.xip.dinnermenu.model.Menu

interface Service {

    @GET("get")
    fun getMenu(@Query("month") month: Int, @Query("year") year: Int): Call<Menu>
}