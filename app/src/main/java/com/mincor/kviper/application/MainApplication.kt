package com.mincor.kviper.application

import android.app.Application
import com.mincor.kviper.di.modules.netModule
import com.mincor.kviper.di.presenters.mainModule
import com.mincor.kviper.di.presenters.searchModule
import com.raizlabs.android.dbflow.config.FlowConfig
import com.raizlabs.android.dbflow.config.FlowManager
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule


/**
 * Created by a.minkin on 25.09.2017.
 */
class MainApplication : Application(), KodeinAware {

    ///----- DEPENDENCY INJECTION by Kodein
    override val kodein = Kodein.lazy {
        import(androidXModule(this@MainApplication))
        import(netModule)
        import(mainModule)
        import(searchModule)
    }

    override fun onCreate() {
        super.onCreate()
        FlowManager.init(FlowConfig.Builder(this).openDatabasesOnInit(true).build())
    }

    override fun onTerminate() {
        super.onTerminate()
        FlowManager.destroy()
        //Analytics.clearAnalytics()
    }
}