package com.ermilova.android.core.domain.model

data class CharacterDomainModel(
    val id: Long,
    val name: String,
    val status: String,
    val species: String,
    val gender: String,
    val created: String,
    val image: String?
)