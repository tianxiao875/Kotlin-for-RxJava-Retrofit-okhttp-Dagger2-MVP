package kingkazma.com.rxjavaretrofitokhttp3dagger2mvpdatabading.mvp.retrofit

import android.content.Context
import android.support.annotation.StringRes
import kingkazma.com.rxjavaretrofitokhttp3dagger2mvpdatabading.R

/**
 *描述:
 *------------------------------------------------------------------------
 * 工程:
 * #0000     tian xiao     创建日期: 2018-05-30 17:33
 *
 */
enum class ErrorType(val code: Int, @param: StringRes private val messageId: Int) {

    INTERNAL_SERVER_ERROR(500, R.string.service_error),
    BAD_GATEWAY(502, R.string.service_error),
    NOT_FOUND(404, R.string.not_found),
    CONNECTION_TIMEOUT(408, R.string.timeout),
    NETWORK_NOT_CONNECT(499, R.string.network_wrong),
    UNEXPECTED_ERROR(700, R.string.unexpected_error);

    private val DEFAULT_CODE = 1

    fun getApiErrorModel(context: Context): ErrorModel {
        return ErrorModel(DEFAULT_CODE, context.getString(messageId))
    }
}

