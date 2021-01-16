package com.myapp.mymoviesapp.viewmodel.movies

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.myapp.mymoviesapp.datamodel.movie.MovieSearchResponse
import com.myapp.mymoviesapp.repository.Repository
import com.myapp.mymoviesapp.repository.ResultWrapper
import com.myapp.mymoviesapp.ui.movies.MovieHomeViewModel
import com.myapp.mymoviesapp.utils.getOrAwaitValue
import com.nhaarman.mockitokotlin2.inOrder
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.never
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.ArgumentMatchers
import org.mockito.InOrder
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import provideFakeCoroutinesDispatcherProvider

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class MovieHomeViewModelTest {


    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    val repository: Repository = Mockito.mock(Repository::class.java)

    val dispatcher = TestCoroutineDispatcher()


    @Before
    fun setUp() {

        MockitoAnnotations.initMocks(this)

        Dispatchers.setMain(dispatcher)

    }

    /**
     * Without Pausing and Resuming dispatcher,
     * Use mock Observer object to verify onChange method calls
     * [InOrder] is used for verifying consecutive live data updates
     * used [FakeAppInjection.provideFakeCoroutinesDispatcherProvider] to inject dispatcher
     */

    @Test
    fun `should return loading and then response on getNowPlayingMovies Success call`() {

        dispatcher.runBlockingTest {

            val observer =
                Mockito.mock(Observer::class.java) as Observer<ResultWrapper<MovieSearchResponse>>

            val viewModel = MovieHomeViewModel(repository,provideFakeCoroutinesDispatcherProvider(dispatcher,dispatcher,dispatcher))

            viewModel.nowPlayingMovieLiveData.observeForever(observer)

            val movieSearchResponse = MovieSearchResponse(1, emptyList(), 2, 22)

            Mockito.`when`(repository.getNowPlayingMovies())
                .thenReturn(ResultWrapper.Success(movieSearchResponse))

            val inOrder = inOrder(observer)

            viewModel.getNowPlayingMovies()

            inOrder.verify(observer).onChanged(ResultWrapper.Loading(true))

            Mockito.verify(repository).getNowPlayingMovies()

            assertEquals(ResultWrapper.Success(movieSearchResponse), viewModel.nowPlayingMovieLiveData.value)

            inOrder.verify(observer).onChanged(ResultWrapper.Success(movieSearchResponse))

            inOrder.verify(observer, never())
                .onChanged(ResultWrapper.Error(ArgumentMatchers.anyString(), Exception()))

            viewModel.nowPlayingMovieLiveData.removeObserver(observer)

        }
    }

    /**
     * Without Pausing and Resuming dispatcher,
     * Use mock [Observer] object to verify onChange method calls
     * [InOrder] is used for verifying consecutive live data updates
     *
     */

        @Test
        fun `should return loading and then Error on getNowPlaying API failure`(){

            dispatcher.runBlockingTest {

                val viewModel = MovieHomeViewModel(repository,provideFakeCoroutinesDispatcherProvider(dispatcher,dispatcher,dispatcher))

                val observer: Observer<ResultWrapper<MovieSearchResponse>> = mock()

                viewModel.nowPlayingMovieLiveData.observeForever(observer)

                val errorMsg = "error"
                val exception = Exception()

                val movieSearchResponse = MovieSearchResponse(1, emptyList(), 2, 22)

                Mockito.`when`(repository.getNowPlayingMovies()).thenReturn(ResultWrapper.Error(errorMsg,exception))

                val inOrder = inOrder(observer)

                viewModel.getNowPlayingMovies()

                inOrder.verify(observer).onChanged(ResultWrapper.Loading(true))

                Mockito.verify(repository).getNowPlayingMovies()

                inOrder.verify(observer, never()).onChanged(ResultWrapper.Success(movieSearchResponse))

                inOrder.verify(observer).onChanged(ResultWrapper.Error(errorMsg,exception))

                viewModel.nowPlayingMovieLiveData.removeObserver(observer)

            }

        }

    /**
     * Pause the dispatcher to check initial loading value,
     * then resume dispatcher to check slow updated livedata value
     * [LiveDataTestUtils.getOrWaitValue] function is used to get the updated live data value
     *
     */
    @Test
    fun `should emit loading and then result on getUpcomingMovies Success`(){

        dispatcher.runBlockingTest {


            val viewModel = MovieHomeViewModel(repository,provideFakeCoroutinesDispatcherProvider(dispatcher,dispatcher,dispatcher))

            val movieSearchResponse = MovieSearchResponse(1, emptyList(), 2, 22)

            Mockito.`when`(repository.getUpcomingMovies()).thenReturn(ResultWrapper.Success(movieSearchResponse))

            dispatcher.pauseDispatcher()

            viewModel.getUpcomingMovies()

            val isLoadingActual = viewModel.upcomingMoviesLiveData.value
            assertNotNull(isLoadingActual)
            assertEquals(ResultWrapper.Loading(true), isLoadingActual)

            dispatcher.resumeDispatcher()

            Mockito.verify(repository).getUpcomingMovies()

            val resultActual = viewModel.upcomingMoviesLiveData.getOrAwaitValue()
            assertNotNull(resultActual)

            assertEquals(ResultWrapper.Success(movieSearchResponse), resultActual)
        }

    }

    /**
     * Pause the dispatcher to check initial loading value,
     * then resume dispatcher to check slow updated livedata value
     * [LiveDataTestUtils.getOrWaitValue] function is used to get the updated live data value
     *
     */
    @Test
    fun `should emit error on getUpcomingMovies api failure`(){

        dispatcher.runBlockingTest {

            dispatcher.pauseDispatcher()

            val viewModel = MovieHomeViewModel(repository,provideFakeCoroutinesDispatcherProvider(dispatcher,dispatcher,dispatcher))

            val errorMsg = "error"
            val exception = Exception(errorMsg)

            val res = ResultWrapper.Error(errorMsg,exception)

            Mockito.`when`(repository.getUpcomingMovies()).thenReturn(res)

            viewModel.getUpcomingMovies()

           val loadingActual =   viewModel.upcomingMoviesLiveData.value

            assertNotNull(loadingActual)

            assertEquals(ResultWrapper.Loading(true), loadingActual)

            dispatcher.resumeDispatcher()

            Mockito.verify(repository).getUpcomingMovies()

            val resultActual =   viewModel.upcomingMoviesLiveData.getOrAwaitValue()

            assertNotNull(resultActual)

            assertEquals(res, resultActual)

        }

    }

    @Test
    fun `should return loading and then top rated movie data on getTopRatedMovies call`() {

        dispatcher.runBlockingTest {

            dispatcher.pauseDispatcher()

            val movieSearchResponse = MovieSearchResponse(1, emptyList(), 2, 22)

            Mockito.`when`(repository.getTopRatedMovies())
                .thenReturn(ResultWrapper.Success(movieSearchResponse))

            val viewModel = MovieHomeViewModel(
                repository,
                provideFakeCoroutinesDispatcherProvider(dispatcher, dispatcher, dispatcher)
            )

            viewModel.getTopRatedMovies()

            val loadingActual = viewModel.topRatedMoviesLiveData.value

            assertNotNull(loadingActual)
            assertEquals(ResultWrapper.Loading(true), loadingActual)

            dispatcher.resumeDispatcher()

            Mockito.verify(repository).getTopRatedMovies()

            val resultActual = viewModel.topRatedMoviesLiveData.getOrAwaitValue()

            assertNotNull(resultActual)
            assertEquals(ResultWrapper.Success(movieSearchResponse), resultActual)

        }
    }

    @Test
    fun `should return loading and then Error on getTopRatedMovies API fail`(){

            dispatcher.runBlockingTest {

                dispatcher.pauseDispatcher()

                val errorMsg = "error"
                val exception = Exception(errorMsg)

                Mockito.`when`(repository.getTopRatedMovies()).thenReturn(ResultWrapper.Error(errorMsg,exception))

                val viewModel = MovieHomeViewModel(repository,provideFakeCoroutinesDispatcherProvider(dispatcher,dispatcher,dispatcher))

                viewModel.getTopRatedMovies()

                val loadingActual = viewModel.topRatedMoviesLiveData.value
                assertNotNull(loadingActual)
                assertEquals(ResultWrapper.Loading(true),loadingActual)

                dispatcher.resumeDispatcher()

                val exceptionResultActual = viewModel.topRatedMoviesLiveData.getOrAwaitValue()

                assertNotNull(exceptionResultActual)
                assertEquals(ResultWrapper.Error(errorMsg,exception),exceptionResultActual)

            }
        }

    @Test
    fun `should return loading and then popular movie data on getPopularMovies call`() {

        dispatcher.runBlockingTest {

            dispatcher.pauseDispatcher()

            val movieSearchResponse = MovieSearchResponse(1, emptyList(), 2, 22)

            Mockito.`when`(repository.getPopularMovies())
                .thenReturn(ResultWrapper.Success(movieSearchResponse))

            val viewModel = MovieHomeViewModel(
                repository,
                provideFakeCoroutinesDispatcherProvider(dispatcher, dispatcher, dispatcher)
            )

            viewModel.getPopularMovies()

            val loadingActual = viewModel.popularMoviesLiveData.value

            assertNotNull(loadingActual)
            assertEquals(ResultWrapper.Loading(true), loadingActual)

            dispatcher.resumeDispatcher()

            Mockito.verify(repository).getPopularMovies()

            val resultActual = viewModel.popularMoviesLiveData.getOrAwaitValue()

            assertNotNull(resultActual)
            assertEquals(ResultWrapper.Success(movieSearchResponse), resultActual)

        }
    }

    @Test
    fun `should return loading and then Error on getPopularMovies API fail`(){

        dispatcher.runBlockingTest {

            dispatcher.pauseDispatcher()

            val errorMsg = "error"
            val exception = Exception(errorMsg)

            Mockito.`when`(repository.getPopularMovies()).thenReturn(ResultWrapper.Error(errorMsg,exception))

            val viewModel = MovieHomeViewModel(repository,provideFakeCoroutinesDispatcherProvider(dispatcher,dispatcher,dispatcher))

            viewModel.getPopularMovies()

            val loadingActual = viewModel.popularMoviesLiveData.value
            assertNotNull(loadingActual)
            assertEquals(ResultWrapper.Loading(true),loadingActual)

            dispatcher.resumeDispatcher()

            val exceptionResultActual = viewModel.popularMoviesLiveData.getOrAwaitValue()

            assertNotNull(exceptionResultActual)
            assertEquals(ResultWrapper.Error(errorMsg,exception),exceptionResultActual)

        }
    }


    @After
    fun tearDown() {

        Dispatchers.resetMain()

        dispatcher.cleanupTestCoroutines()


    }

}