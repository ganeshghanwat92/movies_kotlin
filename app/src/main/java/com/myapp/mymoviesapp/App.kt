package com.myapp.mymoviesapp

import android.app.Application
import com.myapp.mymoviesapp.di.component.AppComponent
import com.myapp.mymoviesapp.di.component.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class App : Application() , HasAndroidInjector{

    lateinit var appComponent: AppComponent

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent
            .builder()
            .build()

        appComponent.inject(this@App)

    }

    override fun androidInjector(): AndroidInjector<Any> = dispatchingAndroidInjector

}