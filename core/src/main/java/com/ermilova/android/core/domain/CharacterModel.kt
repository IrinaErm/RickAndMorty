package com.ermilova.android.core.domain

data class CharacterModel(
    val id: Long,
    val name: String,
    val status: String,
    val species: String,
    val gender: String,
    val created: String,
    val image: String?
)