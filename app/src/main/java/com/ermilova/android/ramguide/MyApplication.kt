package com.ermilova.android.ramguide

import android.app.Application
import com.ermilova.android.ramguide.di.AppComponent
import com.ermilova.android.ramguide.di.DaggerAppComponent

class MyApplication : Application() {
    val appComponent: AppComponent = DaggerAppComponent.builder().application(this).build()
}