package br.com.eduardotanaka.btgchallenge.data.model.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class Favorito(
    @PrimaryKey
    val movieId: Int,
    val isFavorito: Boolean
) : Parcelable
