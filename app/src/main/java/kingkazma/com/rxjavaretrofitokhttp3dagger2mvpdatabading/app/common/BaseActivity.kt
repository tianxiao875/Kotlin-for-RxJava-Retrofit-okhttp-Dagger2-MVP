package kingkazma.com.rxjavaretrofitokhttp3dagger2mvpdatabading.app.common

import android.arch.lifecycle.Lifecycle
import android.os.Bundle
import android.os.PersistableBundle
import android.support.annotation.CallSuper
import android.support.annotation.MainThread
import com.uber.autodispose.AutoDisposeConverter
import org.jetbrains.annotations.NotNull
import utils.RxLifecycleUtils
import javax.inject.Inject


/**
 *描述:Activity基类
 *------------------------------------------------------------------------
 * 工程:
 * #0000     tian xiao     创建日期: 2018-05-30 17:56
 *
 */
abstract class BaseActivity<P :IPresenter> :IActivity, BaseInjectActivity() {
    @Inject
    lateinit var presenter:P

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        AppActivityManager.getInstance().addActivity(this)
    }

    @MainThread
    override fun showLoding() {
    }

    @MainThread
    override fun hideLoding() {
    }

    @Override
    @CallSuper
    override fun onDestroy() {
        super.onDestroy()
        AppActivityManager.getInstance().removeActivity(this)
    }


    @MainThread
    protected abstract fun initViews(savedInstanceState: Bundle?)
    @MainThread
    abstract fun getLayoutId(): Int

    @MainThread
    abstract fun initToolBar()

    @MainThread
    protected abstract fun initData()

    @MainThread
    protected fun initStatusBar() {
    }

    fun initWebView() {

    }

    protected fun <T> bindLifecycle(): AutoDisposeConverter<T> {
        return RxLifecycleUtils.bindLifecycle(this)
    }


    protected  fun initLifecycleObserver(@NotNull  lifecycle:Lifecycle){
        lifecycle.addObserver(presenter)
    }

}