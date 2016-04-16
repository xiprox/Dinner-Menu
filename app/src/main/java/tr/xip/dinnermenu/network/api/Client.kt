package tr.xip.dinnermenu.network.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import tr.xip.dinnermenu.BuildConfig.DEBUG
import tr.xip.dinnermenu.model.Menu

object Client {
    val BASE_URL = "https://dinnermenu.xiprox.me/api/"

    private var service: Service? = null

    fun init() {
        val clientBuilder = OkHttpClient.Builder()
        if (DEBUG) {
            val httpLoggingInterceptor = HttpLoggingInterceptor()
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            clientBuilder.addInterceptor(httpLoggingInterceptor)
        }

        var retrofit = Retrofit.Builder()
                .client(clientBuilder.build())
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        service = retrofit.create(Service::class.java)
    }

    fun getMenu(month: Int, year: Int): Call<Menu> {
        crashIfServiceNull()
        return service!!.getMenu(month, year)
    }

    fun crashIfServiceNull() {
        if (service == null) {
            throw NullPointerException("Service is null. Are you sure you are calling ${javaClass.simpleName}.init()?")
        }
    }
}