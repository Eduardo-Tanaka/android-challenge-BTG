package br.com.eduardotanaka.btgchallenge.data.model.api

import br.com.eduardotanaka.btgchallenge.data.model.api.base.ApiResponseObject
import com.google.gson.annotations.SerializedName

data class FilmeGeneroResponse(
    @SerializedName("genres")
    val genres: List<GeneroResponse>
) : ApiResponseObject
