package br.com.eduardotanaka.btgchallenge.data.room.dao

import androidx.room.Dao
import androidx.room.Query
import br.com.eduardotanaka.btgchallenge.data.model.entity.FilmePopular
import br.com.eduardotanaka.btgchallenge.data.room.dao.base.BaseDao

@Dao
abstract class TMDBDao : BaseDao<FilmePopular>() {

    @Query("SELECT * FROM FilmePopular")
    abstract fun getAll(): List<FilmePopular>
}
