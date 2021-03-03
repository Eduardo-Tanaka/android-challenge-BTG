package br.com.eduardotanaka.btgchallenge.data.repository

import android.content.SharedPreferences
import br.com.eduardotanaka.btgchallenge.constants.CacheKey
import br.com.eduardotanaka.btgchallenge.data.model.api.RetrofitTesteModel
import br.com.eduardotanaka.btgchallenge.data.model.api.RetrofitTesteResponse
import br.com.eduardotanaka.btgchallenge.data.model.entity.RetrofitTeste
import br.com.eduardotanaka.btgchallenge.data.repository.base.BaseRepository
import br.com.eduardotanaka.btgchallenge.data.repository.base.Resource
import br.com.eduardotanaka.btgchallenge.data.repository.helpers.DataFetchHelper
import br.com.eduardotanaka.btgchallenge.data.room.AppDatabase
import br.com.eduardotanaka.btgchallenge.network.RetrofitTesteService
import retrofit2.Response
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class RetrofitTesteRepositoryImpl @Inject constructor(
    private val retrofitTesteService: RetrofitTesteService,
    private val appDatabase: AppDatabase,
    private val sharedPreferences: SharedPreferences
) : BaseRepository(), RetrofitTesteRepository {
    override suspend fun getAll(): Resource<List<RetrofitTeste>> {
        val dataFetchHelper = object : DataFetchHelper.LocalFirstUntilStale<List<RetrofitTeste>>(
            RetrofitTesteRepositoryImpl::class.simpleName.toString(),
            sharedPreferences,
            CacheKey.CACHE_NAME.toString(),
            "descricao opcional",
            TimeUnit.HOURS.toSeconds(24 * 30)
        ) {
            override suspend fun getDataFromLocal(): List<RetrofitTeste>? {
                return appDatabase.retrofitTesteDao().getAll()
            }

            override suspend fun getDataFromNetwork(): Response<out Any?> {
                return retrofitTesteService.getAll()
            }

            override suspend fun convertApiResponseToData(response: Response<out Any?>): List<RetrofitTeste> {
                val retrofitTesteResponse = RetrofitTesteResponse(response.body() as List<RetrofitTesteModel>)
                return RetrofitTeste().reflectFrom(retrofitTesteResponse)
            }

            override suspend fun storeFreshDataToLocal(data: List<RetrofitTeste>): Boolean {
                data.let {
                    appDatabase.retrofitTesteDao().insert(data)
                    return true
                }
            }
        }

        return dataFetchHelper.fetchDataIOAsync().await()
    }
}