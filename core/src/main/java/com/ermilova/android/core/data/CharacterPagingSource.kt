package com.ermilova.android.core.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.ermilova.android.core.data.retrofit.CharacterRemoteDataSource
import com.ermilova.android.core.data.retrofit.toDatabaseEntity
import com.ermilova.android.core.data.retrofit.toDomainModel
import com.ermilova.android.core.data.room.CharacterLocalDataSource
import com.ermilova.android.core.domain.model.CharacterDomainModel


class CharacterPagingSource(
    private val characterRemoteDataSource: CharacterRemoteDataSource,
    private val characterLocalDataSource: CharacterLocalDataSource
) : PagingSource<Int, CharacterDomainModel>() {

    override fun getRefreshKey(state: PagingState<Int, CharacterDomainModel>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CharacterDomainModel> {
        val position = params.key ?: STARTING_PAGE_INDEX
        try {
            val characters =
                characterRemoteDataSource.getCharacters(position).also { dtoContainer ->
                    characterLocalDataSource.saveCharacter(*dtoContainer.toDatabaseEntity())
                }.toDomainModel()

            val nextKey = if (characters.isEmpty()) {
                null
            } else {
                position + 1
            }

            return LoadResult.Page(
                data = characters,
                prevKey = if (position == STARTING_PAGE_INDEX) null else position - 1,
                nextKey = nextKey
            )
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }

    companion object {
        const val STARTING_PAGE_INDEX = 1
        const val ITEMS_PER_PAGE = 20
    }
}