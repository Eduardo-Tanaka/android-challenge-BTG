package br.com.eduardotanaka.btgchallenge.ui.detalhe

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.eduardotanaka.btgchallenge.R
import br.com.eduardotanaka.btgchallenge.data.model.entity.Favorito
import br.com.eduardotanaka.btgchallenge.data.repository.TMDBRepository
import br.com.eduardotanaka.btgchallenge.data.repository.base.Resource
import br.com.eduardotanaka.btgchallenge.data.room.AppDatabase
import br.com.eduardotanaka.btgchallenge.ui.base.BaseViewModel
import br.com.eduardotanaka.btgchallenge.ui.base.StatefulResource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DetalheFilmeActivityViewModelImpl @Inject constructor(
    private val tmdbRepository: TMDBRepository,
    private val appDatabase: AppDatabase,
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
            withContext(Dispatchers.IO) {
                appDatabase.favoritoDao().insert(favorito).toInt()
            }
            val resource = Resource<Favorito>().copy(favorito)
            mutableFavorito.value = StatefulResource.success(resource)
        }
    }
}
