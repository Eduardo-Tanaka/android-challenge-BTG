package br.com.eduardotanaka.btgchallenge.data.repository

import android.content.SharedPreferences
import br.com.eduardotanaka.btgchallenge.constants.CacheKey
import br.com.eduardotanaka.btgchallenge.data.model.api.FilmeGeneroResponse
import br.com.eduardotanaka.btgchallenge.data.model.api.FilmePopularResponse
import br.com.eduardotanaka.btgchallenge.data.model.entity.Favorito
import br.com.eduardotanaka.btgchallenge.data.model.entity.FilmePopular
import br.com.eduardotanaka.btgchallenge.data.model.entity.Genero
import br.com.eduardotanaka.btgchallenge.data.repository.base.BaseRepository
import br.com.eduardotanaka.btgchallenge.data.repository.base.Resource
import br.com.eduardotanaka.btgchallenge.data.repository.helpers.DataFetchHelper
import br.com.eduardotanaka.btgchallenge.data.room.AppDatabase
import br.com.eduardotanaka.btgchallenge.network.TMDBService
import retrofit2.Response
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class TMDBRepositoryImpl @Inject constructor(
    private val tmdbService: TMDBService,
    private val appDatabase: AppDatabase,
    private val sharedPreferences: SharedPreferences
) : BaseRepository(), TMDBRepository {

    override suspend fun getAll(): Resource<List<FilmePopular>> {
        val dataFetchHelper = object : DataFetchHelper.LocalFirstUntilStale<List<FilmePopular>>(
            TMDBRepositoryImpl::class.simpleName.toString(),
            sharedPreferences,
            CacheKey.FILME_POPULAR.toString(),
            "filmes_populares",
            TimeUnit.HOURS.toSeconds(24 * 1)
        ) {
            override suspend fun getDataFromLocal(): List<FilmePopular>? {
                return appDatabase.tmdbDao().getAll()
            }

            override suspend fun getDataFromNetwork(): Response<out Any?> {
                return tmdbService.getAll()
            }

            override suspend fun convertApiResponseToData(response: Response<out Any?>): List<FilmePopular> {
                return FilmePopular().reflectFrom(response.body() as FilmePopularResponse)
            }

            override suspend fun storeFreshDataToLocal(data: List<FilmePopular>): Boolean {
                data.let {
                    appDatabase.tmdbDao().insert(data)
                    return true
                }
            }

            override suspend fun operateOnDataPostFetch(data: List<FilmePopular>) {
                getGeneros()
            }
        }

        return dataFetchHelper.fetchDataIOAsync().await()
    }

    override suspend fun getGeneros(): Resource<List<Genero>> {
        val dataFetchHelper = object : DataFetchHelper.LocalFirstUntilStale<List<Genero>>(
            TMDBRepositoryImpl::class.simpleName.toString(),
            sharedPreferences,
            CacheKey.FILME_GENERO.toString(),
            "filmes_generos",
            TimeUnit.HOURS.toSeconds(24 * 1)
        ) {
            override suspend fun getDataFromLocal(): List<Genero>? {
                return appDatabase.generoDao().getAll()
            }

            override suspend fun getDataFromNetwork(): Response<out Any?> {
                return tmdbService.getGenres()
            }

            override suspend fun convertApiResponseToData(response: Response<out Any?>): List<Genero> {
                return Genero().reflectFrom(response.body() as FilmeGeneroResponse)
            }

            override suspend fun storeFreshDataToLocal(data: List<Genero>): Boolean {
                data.let {
                    appDatabase.generoDao().insert(data)
                    return true
                }
            }
        }

        return dataFetchHelper.fetchDataIOAsync().await()
    }

    override suspend fun getGeneroById(id: Int): Resource<Genero> {
        val dataFetchHelper = object : DataFetchHelper.LocalOnly<Genero>(
            TMDBRepositoryImpl::class.simpleName.toString(),
        ) {
            override suspend fun getDataFromLocal(): Genero {
                return appDatabase.generoDao().getById(id)
            }
        }

        return dataFetchHelper.fetchDataIOAsync().await()
    }

    override suspend fun getFavorito(id: Int): Resource<Favorito> {
        val dataFetchHelper = object : DataFetchHelper.LocalOnly<Favorito>(
            TMDBRepositoryImpl::class.simpleName.toString(),
        ) {
            override suspend fun getDataFromLocal(): Favorito {
                return appDatabase.favoritoDao().getById(id)
            }
        }

        return dataFetchHelper.fetchDataIOAsync().await()
    }

    override suspend fun favorita(favorito: Favorito): Resource<Favorito> {
        val dataFetchHelper = object : DataFetchHelper.LocalOnly<Favorito>(
            TMDBRepositoryImpl::class.simpleName.toString(),
        ) {
            override suspend fun getDataFromLocal(): Favorito {
                return appDatabase.favoritoDao()
                    .getById(appDatabase.favoritoDao().insert(favorito).toInt())
            }
        }

        return dataFetchHelper.fetchDataIOAsync().await()
    }

    override suspend fun getAllFavoritos(): Resource<List<FilmePopular>> {
        val dataFetchHelper = object : DataFetchHelper.LocalOnly<List<FilmePopular>>(
            TMDBRepositoryImpl::class.simpleName.toString(),
        ) {
            override suspend fun getDataFromLocal(): List<FilmePopular> {
                return getFimesFavoritos(appDatabase.favoritoDao().getAll())
            }

            fun getFimesFavoritos(favoritos: List<Favorito>): List<FilmePopular> {
                val ids = ArrayList<Int>()
                favoritos.forEach {
                    ids.add(it.movieId)
                }
                return appDatabase.tmdbDao().getAllByIds(ids)
            }
        }

        return dataFetchHelper.fetchDataIOAsync().await()
    }

}
