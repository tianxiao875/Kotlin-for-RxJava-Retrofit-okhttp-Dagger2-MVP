package kingkazma.com.rxjavaretrofitokhttp3dagger2mvpdatabading.app.common

import android.app.Fragment
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasFragmentInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

/**
 *描述:
 *------------------------------------------------------------------------
 * 工程:
 * #0000     tian xiao     创建日期: 2018-06-11 17:33
 *
 */
open class BaseInjectActivity : AppCompatActivity(),HasFragmentInjector, HasSupportFragmentInjector {
    @Inject
    lateinit var supportFragmentInjector: DispatchingAndroidInjector<android.support.v4.app.Fragment>
    @Inject
    lateinit var frameworkFragmentInjector:DispatchingAndroidInjector<android.app.Fragment>

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        inject()
    }

    fun inject(){
        AndroidInjection.inject(this)
    }

    override fun supportFragmentInjector(): AndroidInjector<android.support.v4.app.Fragment> {
       return supportFragmentInjector
    }

    override fun fragmentInjector(): AndroidInjector<Fragment> {
       return frameworkFragmentInjector
    }

}