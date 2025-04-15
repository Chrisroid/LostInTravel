package com.chrisroid.lostintravel.domain.usecase

import com.chrisroid.lostintravel.data.repository.AuthRepository
import javax.inject.Inject

class SignOutUseCase @Inject constructor(private val authRepository: AuthRepository) {
    suspend operator fun invoke(): Result<Unit> {
        return authRepository.signOut()
    }
}