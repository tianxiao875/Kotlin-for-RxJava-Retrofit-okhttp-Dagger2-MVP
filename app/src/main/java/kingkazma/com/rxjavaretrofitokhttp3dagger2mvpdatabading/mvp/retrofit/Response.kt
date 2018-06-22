package kingkazma.com.rxjavaretrofitokhttp3dagger2mvpdatabading.mvp.retrofit

import android.content.Context
import com.google.gson.Gson
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

/**
 *描述: 请求响应的处理
 *------------------------------------------------------------------------
 * 工程:
 * #0000     tian xiao     创建日期: 2018-05-30 17:29
 *
 */
abstract class ApiResponse<T>(private val context: Context) : Observer<T> {
    abstract fun success(data: T)
    abstract fun failure(statusCode: Int, apiErrorModel: ErrorModel)

    override fun onSubscribe(d: Disposable) {
        //LoadingDialog.show(context)
    }

    override fun onNext(t: T) {
        success(t)
    }

    override fun onComplete() {
        // LoadingDialog.cancel()
    }

    override fun onError(e: Throwable) {
        // LoadingDialog.cancel()
        if (e is HttpException) { //连接服务器成功但服务器返回错误状态码
            val apiErrorModel: ErrorModel = when (e.code()) {
                ErrorType.INTERNAL_SERVER_ERROR.code ->
                    ErrorType.INTERNAL_SERVER_ERROR.getApiErrorModel(context)
                ErrorType.BAD_GATEWAY.code ->
                    ErrorType.BAD_GATEWAY.getApiErrorModel(context)
                ErrorType.NOT_FOUND.code ->
                    ErrorType.NOT_FOUND.getApiErrorModel(context)
                else -> otherError(e)

            }
            failure(e.code(), apiErrorModel)
            return
        }

        val apiErrorType: ErrorType = when (e) {  //发送网络问题或其它未知问题，请根据实际情况进行修改
            is UnknownHostException -> ErrorType.NETWORK_NOT_CONNECT
            is ConnectException -> ErrorType.NETWORK_NOT_CONNECT
            is SocketTimeoutException -> ErrorType.CONNECTION_TIMEOUT
            else -> ErrorType.UNEXPECTED_ERROR
        }
        failure(apiErrorType.code, apiErrorType.getApiErrorModel(context))
    }

    private fun otherError(e: HttpException) =
            Gson().fromJson(e.response().errorBody()?.charStream(), ErrorModel::class.java)
}

