package br.com.eduardotanaka.btgchallenge.data.repository

import br.com.eduardotanaka.btgchallenge.data.model.entity.Favorito
import br.com.eduardotanaka.btgchallenge.data.model.entity.FilmePopular
import br.com.eduardotanaka.btgchallenge.data.model.entity.Genero
import br.com.eduardotanaka.btgchallenge.data.repository.base.Resource

interface TMDBRepository {

    suspend fun getAll(): Resource<List<FilmePopular>>

    suspend fun getGeneros(): Resource<List<Genero>>

    suspend fun getGeneroById(id: Int): Resource<Genero>

    suspend fun getFavorito(id: Int): Resource<Favorito>

    suspend fun getAllFavoritos(): Resource<List<FilmePopular>>
}
