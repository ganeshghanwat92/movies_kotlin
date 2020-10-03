package com.myapp.mymoviesapp

import android.net.Uri
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabsIntent
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity(), HasAndroidInjector{

    val appBarConfiguration = AppBarConfiguration(setOf(R.id.movieHomeFragment,R.id.tvHomeFragment,R.id.multiSearchFragment))

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

     val viewModel : MainActivityViewModel by viewModels { viewModelFactory  }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

      //  viewModel = ViewModelProvider(this,viewModelFactory).get(MainActivityViewModel::class.java)

        viewModel.getNowPlayingMovies()
        //  startActivity(Intent(this, MainModuleActivity::class.java))

        buttonAAA.setOnClickListener {

            val url = "https://google.com"
            val builder : CustomTabsIntent.Builder = CustomTabsIntent.Builder()
            val customTabsIntent : CustomTabsIntent = builder.build()
            customTabsIntent.launchUrl(this, Uri.parse(url))


        }

        var navController = findNavController(R.id.fragment)

        // Setting Navigation Controller with the BottomNavigationView
        bottom_navigation.setupWithNavController(navController)

        setupActionBarWithNavController(navController, appBarConfiguration)


    }

    override fun onSupportNavigateUp(): Boolean {
        findNavController(R.id.fragment).navigateUp(appBarConfiguration)
        return super.onSupportNavigateUp()
    }

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>

    override fun androidInjector(): AndroidInjector<Any>? {
        return dispatchingAndroidInjector
    }
}
