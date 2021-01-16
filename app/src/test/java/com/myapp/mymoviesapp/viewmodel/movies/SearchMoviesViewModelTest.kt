package com.myapp.mymoviesapp.viewmodel.movies

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.myapp.mymoviesapp.datamodel.movie.MovieSearchResponse
import com.myapp.mymoviesapp.repository.Repository
import com.myapp.mymoviesapp.repository.ResultWrapper
import com.myapp.mymoviesapp.ui.movies.SearchMoviesViewModel
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
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import provideFakeCoroutinesDispatcherProvider

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class SearchMoviesViewModelTest {

    private val testDispatcher = TestCoroutineDispatcher()

    lateinit var viewModel : SearchMoviesViewModel

    @Mock
    lateinit var repository: Repository

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    @Before
    fun setUp(){
        MockitoAnnotations.initMocks(this)

        Dispatchers.setMain(testDispatcher)

        viewModel = SearchMoviesViewModel(repository,coroutinesDispatcherProvider = provideFakeCoroutinesDispatcherProvider(testDispatcher,testDispatcher,testDispatcher))


    }

    @Test
    fun `should emit loading and then search result on searchMovies success`() {

        testDispatcher.runBlockingTest {

            testDispatcher.pauseDispatcher()

            val searchResponse = MovieSearchResponse(1, emptyList(), 1, 11)

            Mockito.`when`(repository.searchMovies(anyString()))
                .thenReturn(ResultWrapper.Success(searchResponse))

            viewModel.searchMovies(anyString())

            val loadingActual = viewModel.liveData.value
            assertNotNull(loadingActual)
            assertEquals(ResultWrapper.Loading(true), loadingActual)

            testDispatcher.resumeDispatcher()

            Mockito.verify(repository).searchMovies(anyString())

            val actualSearchResult = viewModel.liveData.getOrAwaitValue()
            assertNotNull(actualSearchResult)
            assertEquals(ResultWrapper.Success(searchResponse), actualSearchResult)

        }
    }

        @Test
        fun `should first emit loading and then error, exception on searchMovie failure`(){

            testDispatcher.runBlockingTest {

                testDispatcher.pauseDispatcher()

                val errorMsg = "error"
                val exception = Exception(errorMsg)

                Mockito.`when`(repository.searchMovies(anyString()))
                    .thenReturn(ResultWrapper.Error(errorMsg, exception))

                viewModel.searchMovies(anyString())

                val loadingActual = viewModel.liveData.value
                assertNotNull(loadingActual)
                assertEquals(ResultWrapper.Loading(true),loadingActual)

                testDispatcher.resumeDispatcher()

                Mockito.verify(repository).searchMovies(anyString())

                val actualError = viewModel.liveData.getOrAwaitValue()
                assertNotNull(actualError)
                assertEquals(ResultWrapper.Error(errorMsg,exception),actualError)

            }
        }

    @Test
    fun `should first emit loading and then next page on loadMoviesNextPage success`(){

        testDispatcher.runBlockingTest {

            val searchResponse = MovieSearchResponse(1, emptyList(), 1, 11)

            Mockito.`when`(repository.searchMoviesWithPageNumber(anyString(), anyInt())).thenReturn(ResultWrapper.Success(searchResponse))

            val observer : Observer<ResultWrapper<MovieSearchResponse>> = mock()

            viewModel.loadMoviesNextPage("anyString()", 11).observeForever(observer)

            Mockito.verify(observer).onChanged(ResultWrapper.Loading(true))

            Mockito.verify(repository).searchMoviesWithPageNumber(anyString(), anyInt())

            Mockito.verify(observer).onChanged(ResultWrapper.Success(searchResponse))

            }
    }

    @Test
    fun `should first emit loading and then error, exception on loadMoviesNextPage Failure`(){

        testDispatcher.runBlockingTest {

            val errorMsg = "error"
            val exception = Exception(errorMsg)

            Mockito.`when`(repository.searchMoviesWithPageNumber(anyString(), anyInt())).thenReturn(ResultWrapper.Error(errorMsg,exception))

            val observer : Observer<ResultWrapper<MovieSearchResponse>> = mock()

            viewModel.loadMoviesNextPage("anyString", 11).observeForever(observer)

            Mockito.verify(observer).onChanged(ResultWrapper.Loading(true))

            Mockito.verify(repository).searchMoviesWithPageNumber(anyString(), anyInt())

            Mockito.verify(observer).onChanged(ResultWrapper.Error(errorMsg,exception))


        }
    }

    @After
    fun tearDown(){

        Dispatchers.resetMain()

        testDispatcher.cleanupTestCoroutines()

    }



}