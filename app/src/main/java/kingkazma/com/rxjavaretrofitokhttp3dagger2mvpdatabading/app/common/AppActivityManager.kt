package kingkazma.com.rxjavaretrofitokhttp3dagger2mvpdatabading.app.common

import android.annotation.SuppressLint
import android.app.Activity

import java.util.ArrayList

/**
 * 描述:
 * ------------------------------------------------------------------------
 * 工程:
 * #0000     tian xiao     创建日期: 2018-06-12 11:02
 */
class AppActivityManager//将构造方法私有化，所以不能通构造方法来初始化ActivityManager
private constructor() {
    private val mActivities = ArrayList<Activity>()
    var currentActivity: Activity? = null

    //添加activity
    fun addActivity(activity: Activity) {
        mActivities.add(activity)
    }

    //移除activity
    fun removeActivity(activity: Activity) {
        mActivities.remove(activity)
    }

    //将activity全部关闭掉
    fun clear() {
        for (activity in mActivities) {
            activity.finish()
        }
    }

    fun <T> closeActivity(clazz: Class<T>) {
        for (activity in mActivities) {
            if (clazz.isInstance(activity)) {
                activity.finish()
            }
        }
    }

    fun isContains(mainActivity: Activity): Boolean {
        return mActivities.contains(mainActivity)
    }

    fun isContains(cls: Class<*>): Boolean {
            for (activity in mActivities) {
                if (activity.javaClass == cls) {
                    return true
                }
            }
        return false
    }



    companion object {
        @SuppressLint("StaticFieldLeak")
        @Volatile
        private var instance: AppActivityManager? = null

        fun getInstance(): AppActivityManager {
            if (instance == null) {
                synchronized(AppActivityManager::class) {
                    if (instance == null) {
                        instance = AppActivityManager()
                    }
                }
            }
            return instance!!
        }

    }


}
