package com.anatideo.challenge.teads.auctionresult

import com.anatideo.challenge.teads.di.AuctionModule
import com.anatideo.challenge.teads.domain.AuctionRepository
import com.anatideo.challenge.teads.domain.model.Bidder
import com.anatideo.challenge.teads.fake.FakeAuctionRepositoryTestImpl
import com.anatideo.challenge.teads.fake.FakeAuctionResultData
import com.anatideo.challenge.teads.presentation.auctionresult.AuctionResultFragment
import com.anatideo.challenge.teads.utils.launchFragmentInHiltContainer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import dagger.hilt.components.SingletonComponent
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.math.BigDecimal

@HiltAndroidTest
@UninstallModules(AuctionModule::class)
class AuctionResultFragmentTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Module
    @InstallIn(SingletonComponent::class)
    object TestAppModule {
        @Provides
        fun provideAuctionRepositoryTest(): AuctionRepository {
            return FakeAuctionRepositoryTestImpl(fakeAuctionResultData)
        }
    }

    @Test
    fun whenDisplayed_thereIsAWinner() {
        fakeAuctionResultData.apply {
            reservePrice = BigDecimal.valueOf(1000.0)
            bidders = listOf(Bidder(1L, "Lou", listOf(BigDecimal.valueOf(2000.0))))
        }
        val expectedResultText = "Sold to Lou!!\nBy the price of $1000.0"

        launchFragmentInHiltContainer<AuctionResultFragment> {}

        auctionResult {
            matchResultText(expectedResultText)
        }
    }

    @Test
    fun whenDisplayed_thereIsNoWinner() {
        fakeAuctionResultData.apply {
            reservePrice = BigDecimal.valueOf(1000.0)
            bidders = listOf(Bidder(1L, "Lou", listOf(BigDecimal.valueOf(10.0))))
        }
        val expectedResultText = "Not sold!!\nBids did not reach reserve price"

        launchFragmentInHiltContainer<AuctionResultFragment> {}

        auctionResult {
            matchResultText(expectedResultText)
        }
    }

    @Test
    fun whenDisplayed_thereAreMoreThanOneWinner() {
        fakeAuctionResultData.apply {
            reservePrice = BigDecimal.valueOf(1000.0)
            bidders = listOf(
                Bidder(1L, "Lou", listOf(BigDecimal.valueOf(2000.0))),
                Bidder(2L, "Billy", listOf(BigDecimal.valueOf(2000.0)))
            )
        }
        val expectedResultText = "Not sold!!\nTwo or more potentially winners"

        launchFragmentInHiltContainer<AuctionResultFragment> {}

        auctionResult {
            matchResultText(expectedResultText)
        }
    }

    companion object {
        private val fakeAuctionResultData = FakeAuctionResultData()
    }
}