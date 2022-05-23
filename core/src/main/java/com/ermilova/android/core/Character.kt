package com.ermilova.android.core

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "character_table")
data class Character (
    @PrimaryKey
    @ColumnInfo(name = "character_id")
    val id: Long,
    @ColumnInfo(name = "character_name")
    val name: String,
    @ColumnInfo(name = "character_status")
    val status: String,
    @ColumnInfo(name = "character_species")
    val species: String,
    @ColumnInfo(name = "character_gender")
    val gender: String,
    @ColumnInfo(name = "character_created")
    val created: String,
    @ColumnInfo(name = "character_image")
    val image: String?,
)