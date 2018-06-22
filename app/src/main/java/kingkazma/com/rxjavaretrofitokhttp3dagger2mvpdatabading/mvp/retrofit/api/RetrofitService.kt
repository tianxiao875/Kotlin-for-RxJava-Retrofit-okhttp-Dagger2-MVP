package kingkazma.com.rxjavaretrofitokhttp3dagger2mvpdatabading.mvp.retrofit.api

import io.reactivex.Observable
import kingkazma.com.rxjavaretrofitokhttp3dagger2mvpdatabading.mvp.retrofit.ResponseBean
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

/**
 *描述:
 *------------------------------------------------------------------------
 * 工程:
 * #0000     tian xiao     创建日期: 2018-05-30 11:06
 *
 */
interface RetrofitService {

    @FormUrlEncoded
    @POST("materialMall/management_interface")
    fun getData(@Field("json_msg")json_msg:String):Observable<ResponseBean>
}