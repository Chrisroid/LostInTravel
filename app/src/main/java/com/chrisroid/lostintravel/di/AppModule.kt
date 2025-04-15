package com.chrisroid.lostintravel.di

import android.app.Application
import android.content.Context
import com.chrisroid.lostintravel.data.local.UserPreferences
import com.chrisroid.lostintravel.data.remote.FirebaseAuthProvider
import com.chrisroid.lostintravel.data.repository.AuthRepository
import com.chrisroid.lostintravel.data.repository.FirebaseAuthRepositoryImpl
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideUserPreferences(@ApplicationContext context: Context): UserPreferences {
        return UserPreferences(context)
    }

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    @Provides
    @Singleton
    fun provideAuthRepository(
        firebaseAuthProvider: FirebaseAuthProvider,
        userPreferences: UserPreferences
    ): AuthRepository {
        return FirebaseAuthRepositoryImpl(firebaseAuthProvider, userPreferences)
    }
}