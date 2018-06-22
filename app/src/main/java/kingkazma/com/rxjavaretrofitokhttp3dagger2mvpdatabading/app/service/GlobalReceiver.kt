package kingkazma.com.rxjavaretrofitokhttp3dagger2mvpdatabading.app.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

/**
 *描述: 全局广播监听
 *------------------------------------------------------------------------
 * 工程:
 * #0000     tian xiao     创建日期: 2018-06-13 10:23
 *
 */
class GlobalReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        when (intent.action) {
            ACTION_APP_LOGIN_OUT -> {
//                UserProxy.getInstance().deleteJpushAlias(context)
//                val loginIntent = Intent(context, LoginActivity::class.java)
//                loginIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//                UserProxy.getInstance().setOnline(context, false)
//                context.startActivity(loginIntent)
//                ToastUtil.showLong(context, context.getString(R.string.token_invalid_tips))
//                abortBroadcast()
            }
            ACTION_APP_NEED_UPDATE -> {

            }
            else -> {
            }
        }
    }

    companion object {
        val ACTION_APP_LOGIN_OUT = "ACTION_APP_LOGIN_OUT"
        val ACTION_APP_NEED_UPDATE = "ACTION_APP_NEED_UPDATE"
    }
}