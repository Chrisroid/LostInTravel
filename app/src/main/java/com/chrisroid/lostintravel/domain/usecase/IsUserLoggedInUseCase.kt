package com.chrisroid.lostintravel.domain.usecase

import com.chrisroid.lostintravel.data.repository.AuthRepository
import javax.inject.Inject

class IsUserLoggedInUseCase @Inject constructor(private val authRepository: AuthRepository) {
    suspend operator fun invoke(): Boolean {
        return authRepository.isUserLoggedIn()
    }
}