package kingkazma.com.rxjavaretrofitokhttp3dagger2mvpdatabading.mvp.retrofit

import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 *描述: 每个请求都写一段这个，显得特别麻烦，所以进行以下简单封装
 *------------------------------------------------------------------------
 * 工程:
 * #0000     tian xiao     创建日期: 2018-05-30 17:59
 *
 */
object NetworkScheduler {
    fun <T> compose(): ObservableTransformer<T, T> {
        return ObservableTransformer { observable ->
            observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
        }
    }

}