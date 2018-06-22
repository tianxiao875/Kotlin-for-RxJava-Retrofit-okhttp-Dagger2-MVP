package kingkazma.com.rxjavaretrofitokhttp3dagger2mvpdatabading.mvp.retrofit

import kingkazma.com.rxjavaretrofitokhttp3dagger2mvpdatabading.BuildConfig
import kingkazma.com.rxjavaretrofitokhttp3dagger2mvpdatabading.mvp.retrofit.api.RetrofitService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 *描述: 链式调用风格单例
 *------------------------------------------------------------------------
 * 工程:
 * #0000     tian xiao     创建日期: 2018-05-30 14:04
 *
 */
class RetrofitClient private constructor() {

    private val DEFAULT_TIMEOUT: Long = 15
    private val BASE_URL: String = ""
    lateinit var service: RetrofitService

    private object Holder {
        val INSTANCE = RetrofitClient()
    }

    companion object {
        val instance by lazy { Holder.INSTANCE }
    }

     fun init(){

        val interceptor = HttpLoggingInterceptor()

        if (BuildConfig.DEBUG)
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        else
            interceptor.setLevel(HttpLoggingInterceptor.Level.NONE)

        val client = OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .retryOnConnectionFailure(true)
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .build()

         service= Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build().create(RetrofitService::class.java)

    }

}