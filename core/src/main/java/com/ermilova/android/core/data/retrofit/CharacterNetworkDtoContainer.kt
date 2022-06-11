package com.ermilova.android.core.data.retrofit

import android.content.res.Resources
import com.ermilova.android.core.domain.model.CharacterDomainModel
import com.ermilova.android.core.R
import com.ermilova.android.core.data.room.CharacterEntity
import com.ermilova.android.core.utils.parseCharacterCreated
import com.squareup.moshi.Json

data class CharacterNetworkDtoContainer(
    @Json(name = "results")
    val results: List<CharacterNetworkDto>
)

fun CharacterNetworkDtoContainer.toDomainModel(): List<CharacterDomainModel> {
    return results.map { characterNetworkDto ->
        CharacterDomainModel(
            id = characterNetworkDto.id,
            name = characterNetworkDto.name ?: Resources.getSystem().getString(R.string.no_info),
            status = characterNetworkDto.status ?: Resources.getSystem().getString(R.string.no_info),
            species = characterNetworkDto.species ?: Resources.getSystem().getString(R.string.no_info),
            gender = characterNetworkDto.gender ?: Resources.getSystem().getString(R.string.no_info),
            created = parseCharacterCreated(characterNetworkDto.created) ?: Resources.getSystem().getString(
                R.string.no_info),
            image = characterNetworkDto.image
        )
    }
}

fun CharacterNetworkDtoContainer.toDatabaseEntity(): Array<CharacterEntity> {
    return results.map { characterNetworkDto ->
        CharacterEntity(
            id = characterNetworkDto.id,
            name = characterNetworkDto.name ?: Resources.getSystem().getString(R.string.no_info),
            status = characterNetworkDto.status ?: Resources.getSystem().getString(R.string.no_info),
            species = characterNetworkDto.species ?: Resources.getSystem().getString(R.string.no_info),
            gender = characterNetworkDto.gender ?: Resources.getSystem().getString(R.string.no_info),
            created = parseCharacterCreated(characterNetworkDto.created) ?: Resources.getSystem().getString(
                R.string.no_info),
            image = characterNetworkDto.image
        )
    }.toTypedArray()
}