package com.ermilova.android.core.retrofit

import com.squareup.moshi.Json
import com.ermilova.android.core.Character
import com.ermilova.android.core.utils.parseCharacterCreated

data class CharacterNetworkDto(
    @Json(name = "id")
    val id: Long,

    @Json(name = "name")
    val name: String?,

    @Json(name = "status")
    val status: String?,

    @Json(name = "species")
    val species: String?,

    @Json(name = "gender")
    val gender: String?,

    @Json(name = "created")
    val created: String?,

    @Json(name = "image")
    val image: String?,
) {
    companion object {
        fun mapToModel(characterDto: CharacterNetworkDto): Character {
            return Character(
                id = characterDto.id,
                name = characterDto.name ?: "No info",
                status = characterDto.status ?: "No info",
                species = characterDto.species ?: "No info",
                gender = characterDto.gender ?: "No info",
                created = parseCharacterCreated(characterDto.created) ?: "No info",
                image = characterDto.image
            )
        }
    }
}