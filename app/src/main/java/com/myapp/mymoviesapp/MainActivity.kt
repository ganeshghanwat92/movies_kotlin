package com.myapp.mymoviesapp

import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabsIntent
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.myapp.mymoviesapp.repository.Repository
import com.myapp.mymoviesapp.repository.remote.ApiClient
import com.myapp.mymoviesapp.repository.remote.RemoteDataSource
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var viewModel : MainActivityViewModel

    val appBarConfiguration = AppBarConfiguration(setOf(R.id.movieHomeFragment,R.id.tvHomeFragment,R.id.multiSearchFragment))


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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

        viewModel = ViewModelProvider(this,MainActivityViewModelFactory(Repository(RemoteDataSource(ApiClient.apiService)))).get(MainActivityViewModel::class.java)

    }

    override fun onSupportNavigateUp(): Boolean {
        findNavController(R.id.fragment).navigateUp(appBarConfiguration)
        return super.onSupportNavigateUp()
    }
}
