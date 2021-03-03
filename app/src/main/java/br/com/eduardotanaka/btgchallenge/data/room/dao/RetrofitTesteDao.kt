package br.com.eduardotanaka.btgchallenge.data.room.dao

import androidx.room.Dao
import androidx.room.Query
import br.com.eduardotanaka.btgchallenge.data.model.entity.RetrofitTeste
import br.com.eduardotanaka.btgchallenge.data.room.dao.base.BaseDao

@Dao
abstract class RetrofitTesteDao : BaseDao<RetrofitTeste>() {

    @Query("SELECT * FROM RetrofitTeste")
    abstract fun getAll(): List<RetrofitTeste>
}