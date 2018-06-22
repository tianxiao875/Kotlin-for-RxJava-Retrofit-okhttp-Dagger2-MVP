package kingkazma.com.rxjavaretrofitokhttp3dagger2mvpdatabading.app.common

import android.content.ComponentName
import android.content.Intent
import com.google.gson.JsonSyntaxException
import io.reactivex.annotations.NonNull
import io.reactivex.observers.DisposableObserver
import kingkazma.com.rxjavaretrofitokhttp3dagger2mvpdatabading.app.service.GlobalReceiver
import org.json.JSONException
import java.net.SocketException
import java.net.UnknownHostException

/**
 *描述:
 *------------------------------------------------------------------------
 * 工程:
 * #0000     tian xiao     创建日期: 2018-06-13 10:22
 *
 */
public abstract class BaseObserver<T> : DisposableObserver<BaseEntity<T>>() {
    private val SUCCESS_CODE = "200"
    private val ERROR_CODE = "400"
    private val TOKEN_INVALID_CODE = "001"
    private val APP_NEED_UPDATE_CODE = "100"


    override fun onNext(@NonNull tBaseEntity: BaseEntity<T>) {
        if (SUCCESS_CODE == tBaseEntity.status) {
            val t = tBaseEntity.data
            onHandleSuccess(t)
        } else if (ERROR_CODE == tBaseEntity.status) {
            if (TOKEN_INVALID_CODE == tBaseEntity.error) {
                //TOKE 失效 需要重新登录
                val intent = Intent(GlobalReceiver.ACTION_APP_LOGIN_OUT)
                intent.setComponent(ComponentName("com.youjuke.merchantbizmanage",
                        "com.youjuke.merchantbizmanage.receiver.GlobalReceiver"))
                MyApplication.getInstance().sendOrderedBroadcast(intent, null)
            } else if (APP_NEED_UPDATE_CODE == tBaseEntity.getError()) {
                //app需要更新
                val intent = Intent(GlobalReceiver.ACTION_APP_NEED_UPDATE)
                intent.setComponent(ComponentName("com.youjuke.merchantbizmanage",
                        "com.youjuke.merchantbizmanage.receiver.GlobalReceiver"))
                MyApplication.getInstance().sendOrderedBroadcast(intent, null)
            } else {
                onHandleFailed(tBaseEntity.getError(), tBaseEntity.getMessage())
            }
        } else {
            L.e("BaseObserver", tBaseEntity.getStatus() + "--" + tBaseEntity.getMessage())
            ToastUtil.show(MyApplication.getInstance(),
                    MyApplication.getInstance().getString(R.string.service_error_tips))
        }
    }

    override fun onError(@NonNull e: Throwable) {
        e.printStackTrace()
        if (e is JsonSyntaxException
                || e is JSONException
                || e is IllegalStateException) {
            ToastUtil.show(MyApplication.getInstance(), "数据异常，请稍后再试")
        } else if (e is UnknownHostException
                || e is SocketException
                || e is HttpException
                || e is retrofit2.adapter.rxjava2.HttpException) {
            ToastUtil.show(MyApplication.getInstance(), "服务器异常，请稍后再试")
        } else if (e is NullPointerException) {
            ToastUtil.show(MyApplication.getInstance(), "数据异常，请稍后再试")
        } else {
            ToastUtil.show(MyApplication.getInstance(), "数据异常，请稍后再试")
        }
        if (!NetworkUtils.isConnected(MyApplication.getInstance())) {
            ToastUtil.show(MyApplication.getInstance(), "网络连接不可用，检查你的网络设置")
        }
        onFinally()
    }

    override fun onComplete() {
        onFinally()
    }

    protected abstract fun onHandleSuccess(t: T)

    protected fun onFinally() {}

    protected fun onHandleFailed(error_code: String, message: String) {
        ToastUtil.show(MyApplication.getInstance(), message)
    }
