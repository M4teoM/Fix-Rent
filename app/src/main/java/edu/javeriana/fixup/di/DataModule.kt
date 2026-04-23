package edu.javeriana.fixup.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import edu.javeriana.fixup.data.datasource.interfaces.*
import edu.javeriana.fixup.data.datasource.impl.*

@Module
@InstallIn(SingletonComponent::class)
@Suppress("unused")
interface DataModule {

    @Binds
    fun bindAuthDataSource(
        authDataSourceImpl: AuthDataSourceImpl
    ): AuthDataSource

    @Binds
    fun bindUserDataSource(
        userDataSourceImpl: UserDataSourceImpl
    ): UserDataSource

    @Binds
    fun bindReviewDataSource(
        reviewDataSourceImpl: ReviewDataSourceImpl
    ): ReviewDataSource

    @Binds
    fun bindArticleDataSource(
        articleDataSourceImpl: ArticleDataSourceImpl
    ): ArticleDataSource

    @Binds
    fun bindFeedDataSource(
        feedDataSourceImpl: FeedFirestoreDataSourceImpl
    ): FeedDataSource

    @Binds
    fun bindRentDataSource(
        rentDataSourceImpl: RentDataSourceImpl
    ): RentDataSource

    @Binds
    fun bindChatDataSource(
        chatDataSourceImpl: ChatDataSourceImpl
    ): ChatDataSource

    @Binds
    fun bindProfileDataSource(
        profileDataSourceImpl: ProfileDataSourceImpl
    ): ProfileDataSource

    @Binds
    fun bindCheckoutDataSource(
        checkoutDataSourceImpl: CheckoutDataSourceImpl
    ): CheckoutDataSource
}
