package com.myapp.mymoviesapp.viewmodel.movies

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.myapp.mymoviesapp.datamodel.movie.MovieDetails
import com.myapp.mymoviesapp.repository.Repository
import com.myapp.mymoviesapp.repository.ResultWrapper
import com.myapp.mymoviesapp.ui.movies.MovieDetailViewModel
import com.myapp.mymoviesapp.utils.getOrAwaitValue
import com.nhaarman.mockitokotlin2.mock
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
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import provideFakeCoroutinesDispatcherProvider

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class MovieDetailsViewModelTest {

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    val testDispatcher = TestCoroutineDispatcher()
  //  val testScope = TestCoroutineScope(testDispatcher)


    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        Dispatchers.setMain(dispatcher = testDispatcher)


    }

    @After
    fun tearDown(){

        Dispatchers.resetMain()

        testDispatcher.cleanupTestCoroutines()

    }

    @Test
    fun `should emit loading and then result on getMovieDetails success call`(){

        testDispatcher.runBlockingTest {

            testDispatcher.pauseDispatcher()


            val id = 1

            val repo: Repository = mock()

           val viewModel = MovieDetailViewModel(repo = repo,coroutinesDispatcherProvider = provideFakeCoroutinesDispatcherProvider(testDispatcher,testDispatcher,testDispatcher))
          //  viewModel.liveDataMovie.observeForever(observer)

            val movieDetails = MovieDetails(true,"",null,1, emptyList(),"null",1,
            "","","","",1.1,"", emptyList(), emptyList(),
            "null",1,1, emptyList(),"","","title",true,1.1,11)

            val res = ResultWrapper.Success(movieDetails)

            Mockito.`when`(repo.getMovieDetails(id)).thenReturn(res)


            viewModel.getMovieDetails(id)

            val loadingActual = viewModel.liveDataMovie.value

            assertNotNull(loadingActual)

            assertEquals(ResultWrapper.Loading(true), loadingActual)

          //  Mockito.verify(observer).onChanged(ResultWrapper.Loading(true))

      //      Mockito.verify(repo).getMovieDetails(id)

            testDispatcher.resumeDispatcher()

           val resultActual =   viewModel.liveDataMovie.getOrAwaitValue()

                assertNotNull(viewModel.liveDataMovie.value)

                assertEquals(res, resultActual)



          //  Mockito.verify(observer).onChanged(res)

         //   Mockito.verify(observer, Mockito.never()).onChanged(ResultWrapper.Error(ArgumentMatchers.anyString(), Exception()))

         //   viewModel.liveDataMovie.removeObserver(observer)

        }

    }


    @Test
    fun `should emit loading and then exception on getMovieDetails failure`(){

        testDispatcher.runBlockingTest {

            testDispatcher.pauseDispatcher()

            val id = 0

            val repo: Repository = mock()

            val viewModel = MovieDetailViewModel(repo = repo,coroutinesDispatcherProvider = provideFakeCoroutinesDispatcherProvider(testDispatcher,testDispatcher,testDispatcher))

            val exception = Exception()

            val errorMsg = ""

            val res = ResultWrapper.Error(errorMsg,exception)

            Mockito.`when`(repo.getMovieDetails(id)).thenReturn(res)

            viewModel.getMovieDetails(id)

            val loadingActual = viewModel.liveDataMovie.value
            assertNotNull(loadingActual)

            assertEquals(ResultWrapper.Loading(true),loadingActual)

         //   Mockito.verify(observer).onChanged(ResultWrapper.Loading(true))

//            Mockito.verify(repo).getMovieDetails(id)

            testDispatcher.resumeDispatcher()


            val resultActual = viewModel.liveDataMovie.getOrAwaitValue()
            assertNotNull(resultActual)

            assertEquals(res,resultActual)


        //    Mockito.verify(observer, Mockito.never()).onChanged(ResultWrapper.Success(movieDetails))

        //    Mockito.verify(observer).onChanged(res)

         //  viewModel.liveDataMovie.removeObserver(observer)

        }

    }
}