package br.com.eduardotanaka.btgchallenge.data.repository

import br.com.eduardotanaka.btgchallenge.data.model.entity.RetrofitTeste
import br.com.eduardotanaka.btgchallenge.data.repository.base.Resource

interface RetrofitTesteRepository {

    suspend fun getAll(): Resource<List<RetrofitTeste>>
}