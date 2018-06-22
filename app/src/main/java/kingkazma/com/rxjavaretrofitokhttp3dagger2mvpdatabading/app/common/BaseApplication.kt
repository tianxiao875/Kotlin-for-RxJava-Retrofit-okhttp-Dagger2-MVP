package kingkazma.com.rxjavaretrofitokhttp3dagger2mvpdatabading.app.common

import android.app.Activity
import android.app.ActivityManager
import android.app.Application
import android.app.Fragment
import android.app.Service
import android.content.BroadcastReceiver
import android.content.ContentProvider
import android.content.Context
import android.os.Bundle
import android.os.Process
import android.text.TextUtils
import android.util.Log
import com.google.gson.Gson
import dagger.android.*
import dagger.android.support.HasSupportFragmentInjector
import utils.ApplicationUtils
import utils.L
import utils.SDCardUtil
import java.io.BufferedReader
import java.io.FileReader
import java.io.IOException
import javax.inject.Inject

/**
 *描述:
 *------------------------------------------------------------------------
 * 工程:
 * #0000     tian xiao     创建日期: 2018-06-13 10:26
 *
 */
abstract class BaseApplication : Application(), HasActivityInjector,
        HasBroadcastReceiverInjector,
        HasFragmentInjector,
        HasServiceInjector,
        HasContentProviderInjector,
        HasSupportFragmentInjector {

    @Inject
    internal var activityInjector: DispatchingAndroidInjector<Activity>? = null

    @Inject
    internal var fragmentInjector: DispatchingAndroidInjector<Fragment>? = null
    @Inject
    internal var supportFragmentInjector: DispatchingAndroidInjector<android.support.v4.app.Fragment>? = null
    @Inject
    internal var broadcastReceiverInjector: DispatchingAndroidInjector<BroadcastReceiver>? = null
    @Inject
    internal var serviceInjector: DispatchingAndroidInjector<Service>? = null
    @Inject
    internal var contentProviderInjector: DispatchingAndroidInjector<ContentProvider>? = null

    @Inject
    var gson: Gson? = null

    //activity 在前台的数量
    internal var activityInForegroundCount = 0
    private var activityLifecycleCallbacks: Application.ActivityLifecycleCallbacks? = null
    protected var isDebug: Boolean = false

    companion object {
        var mInstance: BaseApplication? = null
    }

    override fun onCreate() {
        super.onCreate()

        val currentProcessName = getProcessName(Process.myPid())
        //防止app 多进程 初始化多个application
        if (currentProcessName == null || packageName != currentProcessName) {
            return
        }
        mInstance = this
        isDebug = ApplicationUtils.isApkDebugable(this)
        //配置时候显示toast
        ToastUtil.isShow = true
        //设置SD卡 项目目录
        createAPPDir()
        //Dagger inject
        initDI()
        //注册Activity生命周期监听
        registerLifecycleCallbacks()

    }


    private fun initDI() {
        injectApp()
    }

    /**
     * 这是类库底层的injectApp代码示例，你应该在你的Module中重写该方法:
     *
     *
     * DaggerMyAppComponent.builder()
     * .cacheModule(getCacheModule())
     * .appModule(getAppModule())
     * .globalConfigModule(getGlobalConfigModule())
     * .httpClientModule(getHttpClientModule())
     * .serviceModule(getServiceModule())
     * .build()
     * .inject(this);
     */
    protected abstract fun injectApp()

    /**
     * 创建SD卡 项目目录
     */

    public fun createAPPDir(){
        SDCardUtil.initAppSDCardPath(this)
    }


    /**
     * 注册activity生命周期监听
     */
    private fun registerLifecycleCallbacks() {

        activityLifecycleCallbacks = object : Application.ActivityLifecycleCallbacks {
            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle) {
                L.i("Application", activity.javaClass.simpleName + " Created")
            }

            override fun onActivityStarted(activity: Activity) {
                if (activityInForegroundCount == 0) {
                    Log.i("Application", "app to foreground")
                }
                activityInForegroundCount++
            }

            override fun onActivityResumed(activity: Activity) {
                AppActivityManager.getInstance().currentActivity=activity
                L.i("Application", activity.javaClass.simpleName + " Resumed")
            }

            override fun onActivityPaused(activity: Activity) {
                L.i("Application", activity.javaClass.simpleName + " Paused")
            }

            override fun onActivityStopped(activity: Activity) {
                activityInForegroundCount--
                AppActivityManager.getInstance().currentActivity=null
                //无activity在前台 即应用回到后台
                if (activityInForegroundCount == 0) {
                    L.i("Application", "app to background")
                }
            }

            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {

            }

            override fun onActivityDestroyed(activity: Activity) {
                L.i("Application", activity.javaClass.simpleName + " Destroyed")
            }
        }
        registerActivityLifecycleCallbacks(activityLifecycleCallbacks)
    }


    /**
     * 获取进程名
     * @param cxt
     * @param pid
     * @return
     */
    fun getProcessName(cxt: Context, pid: Int): String? {
        val am = cxt.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val runningApps = am.runningAppProcesses ?: return null
        for (procInfo in runningApps) {
            if (procInfo.pid == pid) {
                return procInfo.processName
            }
        }
        return null
    }

    /**
     * 获取进程号对应的进程名
     *
     * @param pid 进程号
     * @return 进程名
     */
    protected fun getProcessName(pid: Int): String? {
        var reader: BufferedReader? = null
        try {
            reader = BufferedReader(FileReader("/proc/$pid/cmdline"))
            var processName = reader.readLine()
            if (!TextUtils.isEmpty(processName)) {
                processName = processName.trim { it <= ' ' }
            }
            return processName
        } catch (throwable: Throwable) {
            throwable.printStackTrace()
        } finally {
            try {
                if (reader != null) {
                    reader.close()
                }
            } catch (exception: IOException) {
                exception.printStackTrace()
            }

        }
        return null
    }


    override fun activityInjector(): AndroidInjector<Activity> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun broadcastReceiverInjector(): AndroidInjector<BroadcastReceiver> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun fragmentInjector(): AndroidInjector<Fragment> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun serviceInjector(): AndroidInjector<Service> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun contentProviderInjector(): AndroidInjector<ContentProvider> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun supportFragmentInjector(): AndroidInjector<android.support.v4.app.Fragment> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}