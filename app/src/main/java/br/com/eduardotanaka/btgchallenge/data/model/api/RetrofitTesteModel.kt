package br.com.eduardotanaka.btgchallenge.data.model.api

import br.com.eduardotanaka.btgchallenge.data.model.api.base.ApiResponseObject
import com.google.gson.annotations.SerializedName

// https://5e25087e43dea60014404938.mockapi.io/api/retrofit-teste
/*[{
    "id": "1",
    "createdAt": "2020-11-06T19:11:02.903Z",
    "name": "Jonas Doyle",
    "avatar": "https://s3.amazonaws.com/uifaces/faces/twitter/damenleeturks/128.jpg"
}]*/
data class RetrofitTesteModel(
    @SerializedName("id")
    val id: Int,
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("avatar")
    val avatar: String
) : ApiResponseObject

