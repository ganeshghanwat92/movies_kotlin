package com.myapp.mymoviesapp.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.myapp.mymoviesapp.datamodel.people.Person
import com.myapp.mymoviesapp.repository.Repository
import com.myapp.mymoviesapp.repository.ResultWrapper
import com.myapp.mymoviesapp.ui.person.PersonDetailsViewModel
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
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import provideFakeCoroutinesDispatcherProvider

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class PersonDetailsViewModelTest {

    val testDispatcher = TestCoroutineDispatcher()

    @JvmField
    @Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    lateinit var repository: Repository

    lateinit var viewModel: PersonDetailsViewModel

    @Before
    fun setUp(){
        MockitoAnnotations.initMocks(this)

        Dispatchers.setMain(testDispatcher)

        viewModel = PersonDetailsViewModel(repository,provideFakeCoroutinesDispatcherProvider(testDispatcher,testDispatcher,testDispatcher))

    }

    @Test
    fun `should first emit loading and then person result on getPersonDetails success`(){

        testDispatcher.runBlockingTest {

            testDispatcher.pauseDispatcher()

            val person = Person(true, emptyList(),"","","",11,"",1,
            "","","","",1.0,"")

            Mockito.`when`(repository.getPeopleDetails(anyInt())).thenReturn(ResultWrapper.Success(person))

            viewModel.getPersonDetails(anyInt())

            val loadingActual = viewModel.personDetailsLiveData.value
            assertNotNull(loadingActual)
            assertEquals(ResultWrapper.Loading(true), loadingActual)

            testDispatcher.resumeDispatcher()

            Mockito.verify(repository).getPeopleDetails(anyInt())

            val actualResult = viewModel.personDetailsLiveData.getOrAwaitValue()
            assertNotNull(actualResult)
            assertEquals(ResultWrapper.Success(person), actualResult)

        }

    }

    @Test
    fun `should first emit loading and then return error on getPersonDetails failure`(){

        testDispatcher.runBlockingTest {

            testDispatcher.pauseDispatcher()

            val error = "error "
            val exception = Exception(error)

            Mockito.`when`(repository.getPeopleDetails(anyInt())).thenReturn(ResultWrapper.Error(error,exception))

            viewModel.getPersonDetails(anyInt())

            val loadingActual = viewModel.personDetailsLiveData.value
            assertNotNull(loadingActual)
            assertEquals(ResultWrapper.Loading(true), loadingActual)

            testDispatcher.resumeDispatcher()

            Mockito.verify(repository).getPeopleDetails(anyInt())

            val actualResult = viewModel.personDetailsLiveData.getOrAwaitValue()
            assertNotNull(actualResult)
            assertEquals(ResultWrapper.Error(error, exception), actualResult)

        }

    }



    @After
    fun tearDown(){

        Dispatchers.resetMain()

        testDispatcher.cleanupTestCoroutines()

    }


}