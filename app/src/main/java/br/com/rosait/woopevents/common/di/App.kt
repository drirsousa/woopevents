package br.com.rosait.woopevents.common.di

import android.app.Application

class App : Application() {

    companion object {
        var firstInitViewModel = false
    }

    val component: AppComponent by lazy {
        DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()
    }

    override fun onCreate() {
        super.onCreate()
        component.inject(this)
    }
}