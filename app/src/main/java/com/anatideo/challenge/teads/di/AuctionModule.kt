package com.anatideo.challenge.teads.di

import com.anatideo.challenge.teads.data.AuctionRepositoryImpl
import com.anatideo.challenge.teads.data.database.AuctionDatabaseProvider
import com.anatideo.challenge.teads.data.localsource.AuctionDataSource
import com.anatideo.challenge.teads.data.localsource.AuctionLocalDataSourceImpl
import com.anatideo.challenge.teads.data.mapper.BidderMapper
import com.anatideo.challenge.teads.data.mapper.DataBidMapper
import com.anatideo.challenge.teads.domain.AuctionRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@Module
@InstallIn(ApplicationComponent::class)
class AuctionModule {

    @Provides
    fun provideAuctionDataSource(
        auctionDatabaseProvider: AuctionDatabaseProvider,
        dataBidMapper: DataBidMapper
    ): AuctionDataSource = AuctionLocalDataSourceImpl(auctionDatabaseProvider, dataBidMapper)

    @Provides
    fun provideAuctionRepository(
        auctionDataSource: AuctionDataSource,
        bidderMapper: BidderMapper
    ): AuctionRepository = AuctionRepositoryImpl(auctionDataSource, bidderMapper)
}