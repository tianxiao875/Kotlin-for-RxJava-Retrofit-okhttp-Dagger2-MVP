package kingkazma.com.rxjavaretrofitokhttp3dagger2mvpdatabading.app.common

import android.arch.lifecycle.Lifecycle
import android.os.Bundle
import android.support.annotation.CallSuper
import android.support.annotation.LayoutRes
import android.support.annotation.MainThread
import android.support.v4.app.FragmentTransaction
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.uber.autodispose.AutoDisposeConverter
import utils.RxLifecycleUtils
import javax.inject.Inject

/**
 *描述:Fragment基类
 *------------------------------------------------------------------------
 * 工程:
 * #0000     tian xiao     创建日期: 2018-06-12 15:57
 * #0001                   解决重叠问题
 */
abstract class BaseFragment<p :IPresenter>:BaseInjectFragment(),IFragment {

    companion object {
        var STATE_SAVE_IS_HIDDEN:String="STATE_SAVE_IS_HIDDEN"
    }

    @Inject
    lateinit var presenter :p

    lateinit var rootView:View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //过场动画
        // “内存重启”时调用  解决重叠问题  #0001
        if (savedInstanceState!=null){
           val isSupportHidden:Boolean = savedInstanceState.getBoolean(STATE_SAVE_IS_HIDDEN)
           val ft: FragmentTransaction= fragmentManager!!.beginTransaction()
            if (isSupportHidden){
                ft.hide(this)
            }else{
                ft.show(this)
            }
            ft.commit()
        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootView=LayoutInflater.from(context).inflate(getLayoutRes(),container,false)
        return rootView
    }

    @LayoutRes
    protected abstract fun  getLayoutRes():Int


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initLifecycleObserver(lifecycle)
        initView(view)
        initData()
    }

    override fun onDestroy() {
        super.onDestroy()

    }

    protected fun <T> bindLifecycle(): AutoDisposeConverter<T> {
        return RxLifecycleUtils.bindLifecycle(this)
    }

    @CallSuper
    @MainThread
    protected fun initLifecycleObserver(lifecycle: Lifecycle) {
        lifecycle.addObserver(presenter)
    }

    protected abstract fun initView(view: View)

    protected abstract fun initData()

    @MainThread
    fun showLoading() {

    }

    @MainThread
    fun hideLoading() {

    }


}