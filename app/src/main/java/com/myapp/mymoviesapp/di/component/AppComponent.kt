package com.myapp.mymoviesapp.di.component

import com.myapp.mymoviesapp.App
import com.myapp.mymoviesapp.di.module.AppModule
import com.myapp.mymoviesapp.di.module.MainActivityModule
import com.myapp.mymoviesapp.di.module.ViewModelModule
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component( modules = [AndroidInjectionModule::class, AndroidSupportInjectionModule::class, MainActivityModule::class,AppModule::class, ViewModelModule::class])
interface AppComponent {

   fun inject(app: App)

}