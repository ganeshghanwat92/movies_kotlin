# movies_kotlin

This is single activity application which uses Jetpack navigation component for showing and navigating to the different fragments. This app is written in Kotlin language by following MVVM architecture pattern.

So basically it fetches the movies and TV shows details from TMDB database using REST api's and shows lists using recycler view.  Repository pattern is used for fetching data from remote data source using Retrofit networking library. BottomNavigationView is used to show movies and TV shows on separate fragments.

Coroutines are used for executing network calls on background thread. Glide library is used for loading images.  Dagger Android is used for dependency injection. You can find it's implementation in 'dagger' branch.  Unit tests are also written for all View Models using JUnit and Mockito library.

<img src="https://user-images.githubusercontent.com/7188891/224299031-e26fa071-71a4-4398-b9e8-208517035d65.png" width="200"/>

<img src="https://user-images.githubusercontent.com/7188891/224299052-96fb3a60-2c8a-43cc-a9be-15df0bab7b30.png" width="200"/>

<img src="https://user-images.githubusercontent.com/7188891/224299060-114e190d-98f2-47a6-8792-eae04274f2b6.png" width="200"/>
