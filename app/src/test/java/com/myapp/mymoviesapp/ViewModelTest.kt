package com.myapp.mymoviesapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.myapp.mymoviesapp.repository.Repository
import com.myapp.mymoviesapp.repository.ResultWrapper
import com.myapp.mymoviesapp.repository.model.SearchTVShowsResponse
import com.nhaarman.mockitokotlin2.atLeastOnce
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.mock
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.ArgumentMatchers
import org.mockito.Mockito
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class ViewModelTest{

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    val testDispatcher = TestCoroutineDispatcher()
    val testScope = TestCoroutineScope(testDispatcher)


    lateinit var viewModel: MainActivityViewModel

    private val repo: Repository = mock()

    @Before
    fun setUp() {

        Dispatchers.setMain(dispatcher = testDispatcher)

        MockitoAnnotations.initMocks(this)

        viewModel = MainActivityViewModel(repo)
    }

    @Before
    fun tearDown(){

        Dispatchers.resetMain()
        testScope.cleanupTestCoroutines()

    }


    @Test
    fun `should Show First Loading And Then Success Result on tvShowsSearchTest`() = testScope.runBlockingTest {

        // Mocked Observer
        val observer = Mockito.mock(Observer::class.java) as Observer<ResultWrapper<SearchTVShowsResponse>>
        viewModel.tvShowsLiveData.observeForever(observer)

        val searchTVShowsResponse = Mockito.mock(SearchTVShowsResponse::class.java)

        Mockito.`when`(repo.searchTvShows("", "")).thenReturn(searchTVShowsResponse)

        val searchTVShowsResponseResult = ResultWrapper.Success(searchTVShowsResponse)

        viewModel.searchTVShows("", "")

        Mockito.verify(observer).onChanged(ResultWrapper.Loading(true))

        Mockito.verify(observer, atLeastOnce()).onChanged(searchTVShowsResponseResult)

        Mockito.verify(observer, Mockito.never()).onChanged(ResultWrapper.Error(ArgumentMatchers.anyString(), Exception()))

    }


    @Test
    fun shouldShowErrorTvShowsSearchTest() = testScope.runBlockingTest {

        val observer = mock(Observer::class.java) as Observer<ResultWrapper<SearchTVShowsResponse>>

        viewModel.tvShowsLiveData.observeForever(observer)

        val exception = java.lang.Exception("My Exception")

        given(repo.searchTvShows("","")).willAnswer{
            throw exception
        }

        val resultError = ResultWrapper.Error(ex = exception,message = exception.localizedMessage)

        viewModel.searchTVShows("","")

        verify(observer, atLeastOnce()).onChanged(ResultWrapper.Loading(true))

        val searchTVShowsResponse = Mockito.mock(SearchTVShowsResponse::class.java)

        verify(observer, never()).onChanged(ResultWrapper.Success(searchTVShowsResponse))

        verify(observer, atLeastOnce()).onChanged(resultError)

    }

}