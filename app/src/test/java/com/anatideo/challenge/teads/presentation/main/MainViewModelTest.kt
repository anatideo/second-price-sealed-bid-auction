package com.anatideo.challenge.teads.presentation.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.anatideo.challenge.teads.domain.AddBidUseCase
import com.anatideo.challenge.teads.domain.AddReservePriceUseCase
import com.anatideo.challenge.teads.domain.GetAuctionResultUseCase
import com.anatideo.challenge.teads.domain.errors.InsufficientHighestBidError
import com.anatideo.challenge.teads.domain.model.AuctionResult
import com.anatideo.challenge.teads.domain.model.Bid
import com.anatideo.challenge.teads.domain.model.Bidder
import com.anatideo.challenge.teads.presentation.model.AuctionViewState
import io.mockk.*
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.*
import java.math.BigDecimal

@ExperimentalCoroutinesApi
class MainViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()
    private val dispatcher = TestCoroutineDispatcher()
    private val viewStateObserver: Observer<AuctionViewState> = mockk(relaxed = true)

    private val getAuctionResultUseCase = mockk<GetAuctionResultUseCase>()
    private val addReservePriceUseCase = mockk<AddReservePriceUseCase>()
    private val addBidUseCase = mockk<AddBidUseCase>()

    private lateinit var mainViewModel: MainViewModel

    @Before
    fun before() {
        Dispatchers.setMain(dispatcher)

        mainViewModel = MainViewModel(
            getAuctionResultUseCase,
            addReservePriceUseCase,
            addBidUseCase
        ).also {
            it.viewState.observeForever(viewStateObserver)
        }
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        dispatcher.cleanupTestCoroutines()
    }

    @Test
    fun `when onStartingAuction then view state is AuctionStarted`() {
        // Given
        val strReservePrice = "100.0"
        val reservePrice = BigDecimal.valueOf(100.0)

        coEvery { addReservePriceUseCase(any()) } just Runs

        // When
        mainViewModel.onStartingAuction(strReservePrice)

        // Then
        coVerifySequence {
            addReservePriceUseCase.invoke(reservePrice)
            viewStateObserver.onChanged(AuctionViewState.AuctionStarted)
        }
    }

    @Test
    fun `when onStartingAuction then view state is ShowUnknownError`() {
        // Given
        val strReservePrice = "100.0"
        val reservePrice = BigDecimal.valueOf(100.0)

        coEvery { addReservePriceUseCase(any()) } throws Exception()

        // When
        mainViewModel.onStartingAuction(strReservePrice)

        // Then
        coVerifySequence {
            addReservePriceUseCase.invoke(reservePrice)
            viewStateObserver.onChanged(AuctionViewState.ShowUnknownError)
        }
    }

    @Test
    fun `when onStartingAuction then view state is MissingReservePrice because reserve price is empty`() {
        // Given
        val strReservePrice = ""

        // When
        mainViewModel.onStartingAuction(strReservePrice)

        // Then
        verify {
            viewStateObserver.onChanged(AuctionViewState.MissingReservePrice)
        }
    }

    @Test
    fun `when onStartingAuction then view state is MissingReservePrice because reserve price is not numeric`() {
        // Given
        val strReservePrice = "x"

        // When
        mainViewModel.onStartingAuction(strReservePrice)

        // Then
        verify {
            viewStateObserver.onChanged(AuctionViewState.MissingReservePrice)
        }
    }

    @Test
    fun `when onAddingBid then view state is BidAdded`() {
        // Given
        val id = "1"
        val name = "L"
        val value = "1000"
        val bidSlot = slot<Bid>()

        coEvery { addBidUseCase(any()) } just Runs

        // When
        mainViewModel.onAddingBid(id, name, value)

        // Then
        coVerifySequence {
            addBidUseCase.invoke(capture(bidSlot))
            viewStateObserver.onChanged(AuctionViewState.BidAdded)
        }

        assertEquals(id.toLong(), bidSlot.captured.bidderId)
        assertEquals(name, bidSlot.captured.name)
        assertEquals(value.toBigDecimal(), bidSlot.captured.value)
    }

    @Test
    fun `when onAddingBid then view state is ShowUnknownError`() {
        // Given
        val id = "1"
        val name = "L"
        val value = "1000"

        coEvery { addBidUseCase(any()) } throws Exception()

        // When
        mainViewModel.onAddingBid(id, name, value)

        // Then
        coVerifySequence {
            addBidUseCase.invoke(any())
            viewStateObserver.onChanged(AuctionViewState.ShowUnknownError)
        }
    }

    @Test
    fun `when onAddingBid then view state is MissingBidValue because id is empty`() {
        // Given
        val id = ""
        val name = "L"
        val value = "1000"

        // When
        mainViewModel.onAddingBid(id, name, value)

        // Then
        verify {
            viewStateObserver.onChanged(AuctionViewState.MissingId)
        }
    }

    @Test
    fun `when onAddingBid then view state is MissingId because id is not numeric`() {
        // Given
        val id = "x"
        val name = "L"
        val value = "1000"

        // When
        mainViewModel.onAddingBid(id, name, value)

        // Then
        verify {
            viewStateObserver.onChanged(AuctionViewState.MissingId)
        }
    }

    @Test
    fun `when onAddingBid then view state is MissingBidValue because value is empty`() {
        // Given
        val id = "1"
        val name = "L"
        val value = ""

        // When
        mainViewModel.onAddingBid(id, name, value)

        // Then
        verify {
            viewStateObserver.onChanged(AuctionViewState.MissingBidValue)
        }
    }

    @Test
    fun `when onAddingBid then view state is MissingBidValue because value is not numeric`() {
        // Given
        val id = "1"
        val name = "L"
        val value = "x"

        // When
        mainViewModel.onAddingBid(id, name, value)

        // Then
        verify {
            viewStateObserver.onChanged(AuctionViewState.MissingBidValue)
        }
    }

    @Test
    fun `when onAddNewBid then view state is AddNewBid`() {
        // When
        mainViewModel.onAddNewBid()

        // Then
        verify {
            viewStateObserver.onChanged(AuctionViewState.AddNewBid)
        }
    }

    @Test
    fun `when onTerminate then view state is Terminate`() {
        // When
        mainViewModel.onTerminate()

        // Then
        verify {
            viewStateObserver.onChanged(AuctionViewState.Terminate)
        }
    }

    @Test
    fun `when onAuctionResult then view state is ShowWinner`() {
        // Given
        val bidder = Bidder(1L, "L", listOf(BigDecimal.valueOf(100.0)))
        val winningPrice = BigDecimal.valueOf(100.0)
        val result = AuctionResult(bidder, winningPrice)

        coEvery { getAuctionResultUseCase() } returns result

        // When
        mainViewModel.onAuctionResult()

        // Then
        coVerifySequence {
            getAuctionResultUseCase.invoke()
            viewStateObserver.onChanged(AuctionViewState.ShowWinner(result))
        }
    }

    @Test
    fun `when onAuctionResult then view state is ShowUnknownError`() {
        // Given
        coEvery { getAuctionResultUseCase() } throws Exception()

        // When
        mainViewModel.onAuctionResult()

        // Then
        coVerifySequence {
            getAuctionResultUseCase.invoke()
            viewStateObserver.onChanged(AuctionViewState.ShowUnknownError)
        }
    }

    @Test
    fun `when onAuctionResult then view state is ShowInsufficientHighestBid`() {
        // Given
        coEvery { getAuctionResultUseCase() } throws InsufficientHighestBidError()

        // When
        mainViewModel.onAuctionResult()

        // Then
        coVerifySequence {
            getAuctionResultUseCase.invoke()
            viewStateObserver.onChanged(AuctionViewState.ShowInsufficientHighestBid)
        }
    }

    @Test
    fun `when toBid from a instance of a Triple it returns as expected`() {
        // Given
        val id = "1"
        val name = "L"
        val value = "100"
        val triple = Triple(id, name, value)

        with(mainViewModel) {
            // When
            val bid = triple.toBid()

            // Then
            assertEquals(id.toLong(), bid.bidderId)
            assertEquals(name, bid.name)
            assertEquals(value.toBigDecimal(), bid.value)
        }
    }
}