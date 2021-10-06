package com.anatideo.challenge.teads.di

import com.anatideo.challenge.teads.data.AuctionRepositoryImpl
import com.anatideo.challenge.teads.data.database.AuctionDatabaseProvider
import com.anatideo.challenge.teads.data.localsource.AuctionDataSource
import com.anatideo.challenge.teads.data.localsource.AuctionLocalDataSourceImpl
import com.anatideo.challenge.teads.data.mapper.BidderMapper
import com.anatideo.challenge.teads.data.mapper.DataBidMapper
import com.anatideo.challenge.teads.data.mapper.DataReservePriceMapper
import com.anatideo.challenge.teads.domain.AuctionRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class AuctionModule {

    @Provides
    fun provideAuctionDataSource(
        auctionDatabaseProvider: AuctionDatabaseProvider,
        dataBidMapper: DataBidMapper,
        dataReservePriceMapper: DataReservePriceMapper
    ): AuctionDataSource = AuctionLocalDataSourceImpl(auctionDatabaseProvider, dataBidMapper, dataReservePriceMapper)

    @Provides
    fun provideAuctionRepository(
        auctionDataSource: AuctionDataSource,
        bidderMapper: BidderMapper
    ): AuctionRepository = AuctionRepositoryImpl(auctionDataSource, bidderMapper)
}