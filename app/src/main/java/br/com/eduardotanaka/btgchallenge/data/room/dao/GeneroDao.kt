package br.com.eduardotanaka.btgchallenge.data.room.dao

import androidx.room.Dao
import androidx.room.Query
import br.com.eduardotanaka.btgchallenge.data.model.entity.Genero
import br.com.eduardotanaka.btgchallenge.data.room.dao.base.BaseDao

@Dao
abstract class GeneroDao : BaseDao<Genero>() {

    @Query("SELECT * FROM Genero")
    abstract fun getAll(): List<Genero>

    @Query("SELECT * FROM Genero WHERE id = :id")
    abstract fun getById(id: Int): Genero
}
