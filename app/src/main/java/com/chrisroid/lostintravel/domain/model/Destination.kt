package com.chrisroid.lostintravel.domain.model

data class Destination(
    val id: String,
    val name: String,
    val country: String,
    val imageUrl: String,
    val description: String? = null
)
