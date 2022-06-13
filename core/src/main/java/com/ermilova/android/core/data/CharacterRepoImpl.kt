package com.ermilova.android.core.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.ermilova.android.core.data.CharacterPagingSource.Companion.ITEMS_PER_PAGE
import com.ermilova.android.core.domain.model.CharacterDomainModel
import com.ermilova.android.core.domain.CharacterRepo
import com.ermilova.android.core.data.retrofit.CharacterRemoteDataSource
import com.ermilova.android.core.data.room.CharacterEntity
import com.ermilova.android.core.data.room.CharacterLocalDataSource
import com.ermilova.android.core.data.room.toDomainModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CharacterRepoImpl @Inject constructor(
    private val characterRemoteDataSource: CharacterRemoteDataSource,
    private val characterLocalDataSource: CharacterLocalDataSource,
) : CharacterRepo {

    override fun getAllCharacters(): Flow<PagingData<CharacterDomainModel>> {
        return Pager(
            config = PagingConfig(
                pageSize = ITEMS_PER_PAGE,
                maxSize = ITEMS_PER_PAGE * 3,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { CharacterPagingSource(characterRemoteDataSource, characterLocalDataSource) }
        ).flow
    }

    override suspend fun cacheCharacter(vararg character: CharacterEntity) {
        withContext(Dispatchers.IO) {
            characterLocalDataSource.saveCharacter(*character)
        }
    }

    override suspend fun getCharacter(characterId: Long): CharacterDomainModel {
        return withContext(Dispatchers.IO) {
            characterLocalDataSource.getCharacter(characterId).toDomainModel()
        }
    }
}