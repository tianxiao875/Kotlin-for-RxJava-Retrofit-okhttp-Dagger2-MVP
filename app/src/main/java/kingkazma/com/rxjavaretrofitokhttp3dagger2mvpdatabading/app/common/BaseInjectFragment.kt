package kingkazma.com.rxjavaretrofitokhttp3dagger2mvpdatabading.app.common

import android.content.Context
import android.support.v4.app.Fragment
import dagger.android.support.AndroidSupportInjection

/**
 *描述:
 *------------------------------------------------------------------------
 * 工程:
 * #0000     tian xiao     创建日期: 2018-06-12 10:24
 *
 */
abstract class BaseInjectFragment : Fragment() {
    protected fun inject() {
        AndroidSupportInjection.inject(this)
    }

    protected fun injectRouter(): Boolean {
        return false
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        inject()
    }
}