package br.com.eduardotanaka.btgchallenge.data.repository

import br.com.eduardotanaka.btgchallenge.data.model.entity.FilmePopular
import br.com.eduardotanaka.btgchallenge.data.repository.base.Resource

interface TMDBRepository {

    suspend fun getAll(): Resource<List<FilmePopular>>

}
