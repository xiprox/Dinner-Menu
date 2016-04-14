package tr.xip.dinnermenu.network.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import tr.xip.dinnermenu.model.Menu

object Client {
    val BASE_URL = "http://pastebin.com"

    private var service: Service? = null

    fun init() {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor).build()

        var retrofit = Retrofit.Builder()
                .client(client)
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        service = retrofit.create(Service::class.java)
    }

    fun getMenu(month: Int, year: Int): Call<Menu> {
        crashIfServiceNull()
        return service!!.getMenu(/*month, year*/)
    }

    fun crashIfServiceNull() {
        if (service == null) {
            throw NullPointerException("Service is null. Are you sure you are calling ${javaClass.simpleName}.init()?")
        }
    }
}