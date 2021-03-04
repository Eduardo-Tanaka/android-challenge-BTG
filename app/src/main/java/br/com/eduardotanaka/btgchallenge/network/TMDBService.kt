package br.com.eduardotanaka.btgchallenge.network

import br.com.eduardotanaka.btgchallenge.data.model.api.FilmeGeneroResponse
import br.com.eduardotanaka.btgchallenge.data.model.api.FilmePopularResponse
import retrofit2.Response
import retrofit2.http.GET

interface TMDBService {

    @GET("/3/movie/popular")
    suspend fun getAll(): Response<FilmePopularResponse>

    @GET("/3/genre/movie/list")
    suspend fun getGenres(): Response<FilmeGeneroResponse>
}
