# movies_kotlin

This is single activity application which uses Jetpack navigation component for showing and navigating to the different fragments. This app is written in Kotlin language by following MVVM architecture pattern.

So basically it fetches the movies and TV shows details from TMDB database using REST api's and shows lists using recycler view.  Repository pattern is used for fetching data from remote data source using Retrofit networking library. BottomNavigationView is used to show movies and TV shows on separate fragments.

This is just demo movie application build while learning Kotlin and Dagger. It is single activity application which uses Jetpack navigation component for showing and navigating to the different fragments. This app is written in Kotlin language by following MVVM architecture pattern. So basically it fetches the movies and TV shows details from TMDB database using REST api's and shows lists using recycler view.  Repository pattern is used for fetching data from remote data source using Retrofit networking library. BottomNavigationView is used to show movies and TV shows on separate fragments. Coroutines are used for executing network calls on background thread. Glide library is used for loading images.  Dagger Android is used for dependency injection. You can find it's implementation in 'dagger' branch.  Unit tests are also written for all View Models using JUnit and Mockito library.

