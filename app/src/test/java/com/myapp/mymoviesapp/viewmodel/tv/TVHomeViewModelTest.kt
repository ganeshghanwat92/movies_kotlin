package com.myapp.mymoviesapp.viewmodel.tv

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.myapp.mymoviesapp.datamodel.tv.TVSearchResponse
import com.myapp.mymoviesapp.repository.Repository
import com.myapp.mymoviesapp.repository.ResultWrapper
import com.myapp.mymoviesapp.ui.tvshow.TvHomeViewModel
import com.myapp.mymoviesapp.utils.getOrAwaitValue
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
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import provideFakeCoroutinesDispatcherProvider

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class TVHomeViewModelTest {

    @JvmField
    @Rule
    val rule  = InstantTaskExecutorRule()

    @Mock
    lateinit var repository: Repository

    lateinit var viewModel : TvHomeViewModel

    private val testDispatcher = TestCoroutineDispatcher()

    @Before
    fun setUp(){

        MockitoAnnotations.initMocks(this)

        Dispatchers.setMain(testDispatcher)

        viewModel = TvHomeViewModel(repository,provideFakeCoroutinesDispatcherProvider(testDispatcher,testDispatcher,testDispatcher))

    }

    @Test
    fun `should first emit loading and then on air tv shows response on getOnAirTVShows success`(){

        testDispatcher.runBlockingTest {

            testDispatcher.pauseDispatcher()

            val tvSearchResponse = TVSearchResponse(1, emptyList(),11,111)

            Mockito.`when`(repository.getOnAirTVShows()).thenReturn(ResultWrapper.Success(tvSearchResponse))

            viewModel.getOnAirTVShows()

            val loadingActual = viewModel.onAirLiveData.value
            assertNotNull(loadingActual)
            assertEquals(ResultWrapper.Loading(true),loadingActual)

            testDispatcher.resumeDispatcher()

            Mockito.verify(repository).getOnAirTVShows()

            val result = viewModel.onAirLiveData.getOrAwaitValue()
            assertNotNull(result)
            assertEquals(ResultWrapper.Success(tvSearchResponse),result)


        }

    }

    @Test
    fun `should first emit loading and then error,exception on getOnAirTVShows Failure`(){

        testDispatcher.runBlockingTest {

            testDispatcher.pauseDispatcher()

            val error = "error"
            val exception = Exception(error)

            Mockito.`when`(repository.getOnAirTVShows()).thenReturn(ResultWrapper.Error(error,exception))

            viewModel.getOnAirTVShows()

            val loadingActual = viewModel.onAirLiveData.value
            assertNotNull(loadingActual)
            assertEquals(ResultWrapper.Loading(true),loadingActual)

            testDispatcher.resumeDispatcher()

            Mockito.verify(repository).getOnAirTVShows()

            val result = viewModel.onAirLiveData.getOrAwaitValue()
            assertNotNull(result)
            assertEquals(ResultWrapper.Error(error,exception),result)


        }

    }

    @Test
    fun `should first emit loading and then top Rated tv shows response on getTopRated success`(){

        testDispatcher.runBlockingTest {

            testDispatcher.pauseDispatcher()

            val tvSearchResponse = TVSearchResponse(1, emptyList(),11,111)

            Mockito.`when`(repository.getTopRatedTVShows()).thenReturn(ResultWrapper.Success(tvSearchResponse))

            viewModel.getTopRated()

            val loadingActual = viewModel.topRatedLiveData.value
            assertNotNull(loadingActual)
            assertEquals(ResultWrapper.Loading(true),loadingActual)

            testDispatcher.resumeDispatcher()

            Mockito.verify(repository).getTopRatedTVShows()

            val result = viewModel.topRatedLiveData.getOrAwaitValue()
            assertNotNull(result)
            assertEquals(ResultWrapper.Success(tvSearchResponse),result)


        }

    }

    @Test
    fun `should first emit loading and then error,exception on getTopRated Failure`(){

        testDispatcher.runBlockingTest {

            testDispatcher.pauseDispatcher()

            val error = "error"
            val exception = Exception(error)

            Mockito.`when`(repository.getTopRatedTVShows()).thenReturn(ResultWrapper.Error(error,exception))

            viewModel.getTopRated()

            val loadingActual = viewModel.topRatedLiveData.value
            assertNotNull(loadingActual)
            assertEquals(ResultWrapper.Loading(true),loadingActual)

            testDispatcher.resumeDispatcher()

            Mockito.verify(repository).getTopRatedTVShows()

            val result = viewModel.topRatedLiveData.getOrAwaitValue()
            assertNotNull(result)
            assertEquals(ResultWrapper.Error(error,exception),result)


        }

    }

    @Test
    fun `should first emit loading and then popular tv shows response on getPopular success`(){

        testDispatcher.runBlockingTest {

            testDispatcher.pauseDispatcher()

            val tvSearchResponse = TVSearchResponse(1, emptyList(),11,111)

            Mockito.`when`(repository.getPopularTVShows()).thenReturn(ResultWrapper.Success(tvSearchResponse))

            viewModel.getPopular()

            val loadingActual = viewModel.popularLiveData.value
            assertNotNull(loadingActual)
            assertEquals(ResultWrapper.Loading(true),loadingActual)

            testDispatcher.resumeDispatcher()

            Mockito.verify(repository).getPopularTVShows()

            val result = viewModel.popularLiveData.getOrAwaitValue()
            assertNotNull(result)
            assertEquals(ResultWrapper.Success(tvSearchResponse),result)


        }

    }

    @Test
    fun `should first emit loading and then error,exception on getPopular Failure`(){

        testDispatcher.runBlockingTest {

            testDispatcher.pauseDispatcher()

            val error = "error"
            val exception = Exception(error)

            Mockito.`when`(repository.getPopularTVShows()).thenReturn(ResultWrapper.Error(error,exception))

            viewModel.getPopular()

            val loadingActual = viewModel.popularLiveData.value
            assertNotNull(loadingActual)
            assertEquals(ResultWrapper.Loading(true),loadingActual)

            testDispatcher.resumeDispatcher()

            Mockito.verify(repository).getPopularTVShows()

            val result = viewModel.popularLiveData.getOrAwaitValue()
            assertNotNull(result)
            assertEquals(ResultWrapper.Error(error,exception),result)


        }

    }


    @After
    fun tearDown(){

        Dispatchers.resetMain()

        testDispatcher.cleanupTestCoroutines()

    }

}