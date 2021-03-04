package br.com.eduardotanaka.btgchallenge.data.room.dao

import androidx.room.Dao
import androidx.room.Query
import br.com.eduardotanaka.btgchallenge.data.model.entity.Favorito
import br.com.eduardotanaka.btgchallenge.data.room.dao.base.BaseDao

@Dao
abstract class FavoritoDao : BaseDao<Favorito>() {

    @Query("SELECT * FROM Favorito WHERE movieId = :id")
    abstract fun getById(id: Int): Favorito

    @Query("SELECT * FROM Favorito WHERE movieId in (:ids)")
    abstract fun getByIds(ids: List<Int>): List<Favorito>

    @Query("SELECT * FROM Favorito WHERE isFavorito = 1")
    abstract fun getAll(): List<Favorito>
}
