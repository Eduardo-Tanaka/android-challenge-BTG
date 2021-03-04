package br.com.eduardotanaka.btgchallenge.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import br.com.eduardotanaka.btgchallenge.data.model.entity.FilmePopular
import br.com.eduardotanaka.btgchallenge.data.model.entity.Genero
import br.com.eduardotanaka.btgchallenge.data.room.converter.Converters
import br.com.eduardotanaka.btgchallenge.data.room.dao.GeneroDao
import br.com.eduardotanaka.btgchallenge.data.room.dao.TMDBDao

@Database(
    version = 2,
    entities = [
        FilmePopular::class,
        Genero::class
    ],
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun tmdbDao(): TMDBDao
    abstract fun generoDao(): GeneroDao
}
