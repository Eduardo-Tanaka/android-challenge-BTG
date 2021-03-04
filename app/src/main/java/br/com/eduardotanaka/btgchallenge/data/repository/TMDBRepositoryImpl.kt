package br.com.eduardotanaka.btgchallenge.data.repository

import android.content.SharedPreferences
import br.com.eduardotanaka.btgchallenge.constants.CacheKey
import br.com.eduardotanaka.btgchallenge.data.model.api.FilmePopularResponse
import br.com.eduardotanaka.btgchallenge.data.model.entity.FilmePopular
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
            "filmes populares",
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
        }

        return dataFetchHelper.fetchDataIOAsync().await()
    }

}
