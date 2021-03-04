package br.com.eduardotanaka.btgchallenge.data.model.api

import br.com.eduardotanaka.btgchallenge.data.model.api.base.ApiResponseObject

data class GeneroResponse(
    val id: Int,
    val name: String
) : ApiResponseObject
