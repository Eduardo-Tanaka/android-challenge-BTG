package br.com.eduardotanaka.btgchallenge.ui.detalhe

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.eduardotanaka.btgchallenge.R
import br.com.eduardotanaka.btgchallenge.data.model.entity.Favorito
import br.com.eduardotanaka.btgchallenge.data.repository.TMDBRepository
import br.com.eduardotanaka.btgchallenge.ui.base.BaseViewModel
import br.com.eduardotanaka.btgchallenge.ui.base.StatefulResource
import javax.inject.Inject

class DetalheFilmeActivityViewModelImpl @Inject constructor(
    private val tmdbRepository: TMDBRepository,
) : BaseViewModel(), DetalheFilmeActivityViewModel {

    private val mutableFavorito: MutableLiveData<StatefulResource<Favorito>> =
        MutableLiveData()
    override val favorito: LiveData<StatefulResource<Favorito>> =
        mutableFavorito

    override fun isFavorito(id: Int) {
        launch {
            mutableFavorito.value = StatefulResource.with(StatefulResource.State.LOADING)
            val resource = tmdbRepository.getFavorito(id)
            when {
                resource.hasData() -> {
                    //return the value
                    mutableFavorito.value = StatefulResource.success(resource)
                }
                else -> mutableFavorito.value = StatefulResource<Favorito>()
                    .apply {
                        setState(StatefulResource.State.SUCCESS)
                        setMessage(R.string.not_found)
                    }
            }
        }
    }

    override fun favoritaFilme(favorito: Favorito) {
        launch {
            mutableFavorito.value = StatefulResource.with(StatefulResource.State.LOADING)
            val resource = tmdbRepository.favorita(favorito)
            when {
                resource.hasData() -> {
                    //return the value
                    mutableFavorito.value = StatefulResource.success(resource)
                }
                else -> mutableFavorito.value = StatefulResource<Favorito>()
                    .apply {
                        setState(StatefulResource.State.SUCCESS)
                        setMessage(R.string.not_found)
                    }
            }
        }
    }
}