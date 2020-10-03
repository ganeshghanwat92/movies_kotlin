package com.myapp.mymoviesapp.di.module

import com.myapp.mymoviesapp.MainActivity
import com.myapp.mymoviesapp.di.scope.ActivityScope
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainActivityModule {

    @ActivityScope
    @ContributesAndroidInjector(modules = [FragmentBuilderModule::class])
    abstract fun mainActivity() : MainActivity

}