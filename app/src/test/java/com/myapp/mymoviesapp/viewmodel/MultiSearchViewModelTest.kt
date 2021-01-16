package com.myapp.mymoviesapp.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.myapp.mymoviesapp.datamodel.multisearch.MultiSearchResponse
import com.myapp.mymoviesapp.repository.Repository
import com.myapp.mymoviesapp.repository.ResultWrapper
import com.myapp.mymoviesapp.ui.multisearch.MultiSearchViewModel
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
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import provideFakeCoroutinesDispatcherProvider

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class MultiSearchViewModelTest{

    @JvmField
    @Rule
    val rule = InstantTaskExecutorRule()

    lateinit var viewModel: MultiSearchViewModel

    @Mock
    lateinit var repository: Repository

    private val testDispatcher = TestCoroutineDispatcher()

    @Before
    fun setUp(){
        MockitoAnnotations.initMocks(this)

        Dispatchers.setMain(testDispatcher)

        viewModel = MultiSearchViewModel(repository,provideFakeCoroutinesDispatcherProvider(testDispatcher,testDispatcher,testDispatcher))

    }

    @Test
    fun `should first emit loading and then multisearch result on multiSearch success`(){

        testDispatcher.runBlockingTest {

            testDispatcher.pauseDispatcher()

            val multiSearchResponse = MultiSearchResponse(1, emptyList(),11,111)

            Mockito.`when`(repository.multiSearch(anyString())).thenReturn(ResultWrapper.Success(multiSearchResponse))

            viewModel.multiSearch(anyString())

            val loadingActual = viewModel.multiSearchLiveData.value
            assertNotNull(loadingActual)
            assertEquals(ResultWrapper.Loading(true),loadingActual)


            testDispatcher.resumeDispatcher()

            Mockito.verify(repository).multiSearch(anyString())

            val actualResult = viewModel.multiSearchLiveData.getOrAwaitValue()
            assertNotNull(actualResult)
            assertEquals(ResultWrapper.Success(multiSearchResponse),actualResult)

        }

    }

    @Test
    fun `should first emit loading and then return error on multiSearch failure`(){

        testDispatcher.runBlockingTest {

            testDispatcher.pauseDispatcher()

            val error = "error "
            val exception = Exception(error)

            Mockito.`when`(repository.multiSearch(anyString())).thenReturn(ResultWrapper.Error(error,exception))

            viewModel.multiSearch(anyString())

            val loadingActual = viewModel.multiSearchLiveData.value
            assertNotNull(loadingActual)
            assertEquals(ResultWrapper.Loading(true),loadingActual)


            testDispatcher.resumeDispatcher()

            Mockito.verify(repository).multiSearch(anyString())

            val actualResult = viewModel.multiSearchLiveData.getOrAwaitValue()
            assertNotNull(actualResult)
            assertEquals(ResultWrapper.Error(error,exception),actualResult)

        }

    }

    @After
    fun tearDown(){

        Dispatchers.resetMain()

        testDispatcher.cleanupTestCoroutines()

    }


}