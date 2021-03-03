package br.com.eduardotanaka.btgchallenge.data.model.api

import br.com.eduardotanaka.btgchallenge.data.model.api.base.ApiResponseObject

data class RetrofitTesteResponse(
    val retrofitTesteModelList: List<RetrofitTesteModel>
) : ApiResponseObject