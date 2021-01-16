package com.myapp.mymoviesapp.viewmodel.tv

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.myapp.mymoviesapp.datamodel.tv.LastEpisodeToAir
import com.myapp.mymoviesapp.datamodel.tv.TVShowDetails
import com.myapp.mymoviesapp.repository.Repository
import com.myapp.mymoviesapp.repository.ResultWrapper
import com.myapp.mymoviesapp.ui.tvshow.TVShowDetailsViewModel
import com.myapp.mymoviesapp.utils.getOrAwaitValue
import com.nhaarman.mockitokotlin2.any
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.*
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import provideFakeCoroutinesDispatcherProvider

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class TVShowDetailsViewModelTest {

    @JvmField
    @Rule
    val rule = InstantTaskExecutorRule()

    private val testDispatcher = TestCoroutineDispatcher()

    @Mock
    lateinit var repository: Repository

    lateinit var viewModel : TVShowDetailsViewModel

    @Before
    fun setUp(){
        MockitoAnnotations.initMocks(this)

        Dispatchers.setMain(testDispatcher)

        viewModel = TVShowDetailsViewModel(repository,provideFakeCoroutinesDispatcherProvider(testDispatcher,testDispatcher,testDispatcher))

    }

    @Test
    fun `should first emit loading and then  tv shows details response on getTVShowsDetails success`(){

        testDispatcher.runBlockingTest {

            val tvId = 1

            testDispatcher.pauseDispatcher()

            val lastEpisodeToAir = LastEpisodeToAir("",11,1,"","","",11,1,"",1.0,1)

            val tvShowDetails = TVShowDetails("", emptyList(), emptyList(),"", emptyList(),"",1,true
           , emptyList(),"", lastEpisodeToAir,"", emptyList(), any(),1,1, emptyList(),
            "","","",1.9,"", emptyList(), emptyList(),"","",1.5,11)

            Mockito.`when`(repository.getTVShowDetails(tvId)).thenReturn(ResultWrapper.Success(tvShowDetails))

            viewModel.getTVShowsDetails(tvId)

            val loadingActual = viewModel.tvShowsLiveData.value
            Assert.assertNotNull(loadingActual)
            Assert.assertEquals(ResultWrapper.Loading(true), loadingActual)

            testDispatcher.resumeDispatcher()

            Mockito.verify(repository).getTVShowDetails(anyInt())

            val result = viewModel.tvShowsLiveData.getOrAwaitValue()
            Assert.assertNotNull(result)
            Assert.assertEquals(ResultWrapper.Success(tvShowDetails), result)


        }

    }

    @Test
    fun `should first emit loading and then error,exception on getTVShowsDetails Failure`(){

        testDispatcher.runBlockingTest {

            testDispatcher.pauseDispatcher()

            val tvId = 1
            val error = "error"
            val exception = Exception(error)

            Mockito.`when`(repository.getTVShowDetails(tvId)).thenReturn(ResultWrapper.Error(error,exception))

            viewModel.getTVShowsDetails(tvId)

            val loadingActual = viewModel.tvShowsLiveData.value
            Assert.assertNotNull(loadingActual)
            Assert.assertEquals(ResultWrapper.Loading(true), loadingActual)

            testDispatcher.resumeDispatcher()

            Mockito.verify(repository).getTVShowDetails(anyInt())

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