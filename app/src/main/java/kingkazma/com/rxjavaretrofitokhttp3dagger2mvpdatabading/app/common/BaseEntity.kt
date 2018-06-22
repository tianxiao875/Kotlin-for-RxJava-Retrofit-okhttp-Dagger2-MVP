package kingkazma.com.rxjavaretrofitokhttp3dagger2mvpdatabading.app.common

import java.io.Serializable

/**
 *描述:
 *------------------------------------------------------------------------
 * 工程:
 * #0000     tian xiao     创建日期: 2018-06-13 10:21
 *
 */
class BaseEntity<T> : Serializable {
    var data: T? = null
    var status: String? = null
    var error: String? = null
    var message: String? = null
}
