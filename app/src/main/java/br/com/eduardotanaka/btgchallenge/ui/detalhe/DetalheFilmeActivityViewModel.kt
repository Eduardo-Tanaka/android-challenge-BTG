package br.com.eduardotanaka.btgchallenge.ui.detalhe

import androidx.lifecycle.LiveData
import br.com.eduardotanaka.btgchallenge.data.model.entity.Favorito
import br.com.eduardotanaka.btgchallenge.ui.base.StatefulResource

interface DetalheFilmeActivityViewModel {

    val favorito: LiveData<StatefulResource<Favorito>>
    fun isFavorito(id: Int)

    fun favoritaFilme(favorito: Favorito)
}
