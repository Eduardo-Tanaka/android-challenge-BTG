package br.com.eduardotanaka.btgchallenge.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import br.com.eduardotanaka.btgchallenge.data.model.entity.RetrofitTeste
import br.com.eduardotanaka.btgchallenge.data.room.converter.Converters
import br.com.eduardotanaka.btgchallenge.data.room.dao.RetrofitTesteDao

@Database(
    version = 1,
    entities = [
        RetrofitTeste::class
    ],
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun retrofitTesteDao(): RetrofitTesteDao
}