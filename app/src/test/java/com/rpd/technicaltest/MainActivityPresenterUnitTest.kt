package com.rpd.technicaltest


import com.rpd.technicaltest.domain.rates.GetRatesUseCase
import com.rpd.technicaltest.domain.rates.model.RateView
import com.rpd.technicaltest.presentation.ui.MainActivityPresenter
import com.rpd.technicaltest.presentation.ui.MainActivityView
import com.rpd.technicaltest.presentation.ui.RatesObserver
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.verify
import org.powermock.core.classloader.annotations.PrepareForTest
import org.powermock.modules.junit4.PowerMockRunner

@RunWith(PowerMockRunner::class)
@PrepareForTest(GetRatesUseCase::class)
class MainActivityPresenterUnitTest {
    private lateinit var presenter: MainActivityPresenter

    @Suppress("UNCHECKED_CAST")
    private fun <T> any(type: Class<T>): T {
        Mockito.any<T>(type)
        return null as T
    }

    @Mock
    lateinit var getRatesUseCase: GetRatesUseCase

    @Mock
    lateinit var view: MainActivityView

    @Before
    fun setUp() {
        presenter = MainActivityPresenter(getRatesUseCase)
        presenter.attachView(view)
    }

    @Test
    fun testInitView() {
        presenter.initialise()
        verify(view).initialiseView()
    }

    @Test
    fun testDisposeSubscritpion() {
        presenter.disposeSubscriptions()
        verify(getRatesUseCase).dispose()
    }

    @Test
    fun testExecuteUseCase() {
        presenter.initialise()
        verify(getRatesUseCase).execute(any(RatesObserver::class.java), any(GetRatesUseCase.Params::class.java))
    }

    @Test
    fun testSetRatesToView() {
        val list = emptyList<RateView>()
        presenter.showRates(list)
        verify(view).showRates(list)
    }
}