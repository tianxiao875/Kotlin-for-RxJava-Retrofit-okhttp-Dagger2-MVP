package utils

import android.arch.lifecycle.LifecycleOwner
import com.uber.autodispose.AutoDispose
import com.uber.autodispose.AutoDisposeConverter
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider

/**
 *描述: Rx 生命周期工具
 * https://blog.csdn.net/mq2553299/article/details/79418068
 *------------------------------------------------------------------------
 * 工程:
 * #0000     tian xiao     创建日期: 2018-06-12 13:40
 *
 */
class RxLifecycleUtils {
    companion object {
        fun <T> bindLifecycle(lifecycleOwner: LifecycleOwner): AutoDisposeConverter<T> {
            return AutoDispose.autoDisposable(
                    AndroidLifecycleScopeProvider.from(lifecycleOwner)
            )
        }
    }

    private fun RxLifecycleUtils() {
        throw IllegalStateException("Can't instance the RxLifecycleUtils")
    }


}