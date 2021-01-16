package com.myapp.mymoviesapp.viewmodel.tv

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.myapp.mymoviesapp.datamodel.tv.TVSearchResponse
import com.myapp.mymoviesapp.repository.Repository
import com.myapp.mymoviesapp.repository.ResultWrapper
import com.myapp.mymoviesapp.ui.tvshow.TvShowSearchViewModel
import com.myapp.mymoviesapp.utils.getOrAwaitValue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.*
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import provideFakeCoroutinesDispatcherProvider

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class TvShowSearchViewModelTest {

    @JvmField
    @Rule
    val rule = InstantTaskExecutorRule()

    private val testDispatcher = TestCoroutineDispatcher()

    @Mock
    lateinit var repository: Repository

    lateinit var viewModel : TvShowSearchViewModel

    @Before
    fun setUp(){
        MockitoAnnotations.initMocks(this)

        Dispatchers.setMain(testDispatcher)

        viewModel = TvShowSearchViewModel(repository,provideFakeCoroutinesDispatcherProvider(testDispatcher,testDispatcher,testDispatcher))

    }

    @Test
    fun `should first emit loading and then  tv shows response on searchTVShows success`(){

        testDispatcher.runBlockingTest {

            val query = "asd"

            testDispatcher.pauseDispatcher()

            val tvSearchResponse = TVSearchResponse(1, emptyList(),11,111)

            Mockito.`when`(repository.searchTVShows(query)).thenReturn(ResultWrapper.Success(tvSearchResponse))

            viewModel.searchTVShows(query)

            val loadingActual = viewModel.tvShowsLiveData.value
            Assert.assertNotNull(loadingActual)
            Assert.assertEquals(ResultWrapper.Loading(true), loadingActual)

            testDispatcher.resumeDispatcher()

            Mockito.verify(repository).searchTVShows(anyString())

            val result = viewModel.tvShowsLiveData.getOrAwaitValue()
            Assert.assertNotNull(result)
            Assert.assertEquals(ResultWrapper.Success(tvSearchResponse), result)


        }

    }

    @Test
    fun `should first emit loading and then error,exception on searchTVShows Failure`(){

        testDispatcher.runBlockingTest {

            testDispatcher.pauseDispatcher()

            val error = "error"
            val exception = Exception(error)

            Mockito.`when`(repository.searchTVShows(anyString())).thenReturn(ResultWrapper.Error(error,exception))

            viewModel.searchTVShows(anyString())

            val loadingActual = viewModel.tvShowsLiveData.value
            Assert.assertNotNull(loadingActual)
            Assert.assertEquals(ResultWrapper.Loading(true), loadingActual)

            testDispatcher.resumeDispatcher()

            Mockito.verify(repository).searchTVShows(anyString())

            val result = viewModel.tvShowsLiveData.getOrAwaitValue()
            Assert.assertNotNull(result)
            Assert.assertEquals(ResultWrapper.Error(error, exception), result)


        }

    }



    @After
    fun tearDown(){
        Dispatchers.resetMain()

        testDispatcher.cleanupTestCoroutines()

    }

}