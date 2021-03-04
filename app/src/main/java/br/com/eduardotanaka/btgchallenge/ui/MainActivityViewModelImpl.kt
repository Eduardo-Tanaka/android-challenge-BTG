package br.com.eduardotanaka.btgchallenge.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.eduardotanaka.btgchallenge.R
import br.com.eduardotanaka.btgchallenge.data.model.entity.FilmePopular
import br.com.eduardotanaka.btgchallenge.data.model.entity.Genero
import br.com.eduardotanaka.btgchallenge.data.repository.TMDBRepository
import br.com.eduardotanaka.btgchallenge.ui.base.BaseViewModel
import br.com.eduardotanaka.btgchallenge.ui.base.StatefulResource
import javax.inject.Inject

class MainActivityViewModelImpl @Inject constructor(
    private val tmdbRepository: TMDBRepository,
) : BaseViewModel(), MainActivityViewModel {

    private val mutableFilmePopularList: MutableLiveData<StatefulResource<List<FilmePopular>>> =
        MutableLiveData()
    override val filmePopularList: LiveData<StatefulResource<List<FilmePopular>>> =
        mutableFilmePopularList

    private val mutableFilmeGeneroList: MutableLiveData<StatefulResource<List<Genero>>> =
        MutableLiveData()
    override val filmeGeneroList: LiveData<StatefulResource<List<Genero>>> =
        mutableFilmeGeneroList

    private val mutableGenero: MutableLiveData<StatefulResource<Genero>> =
        MutableLiveData()
    override val genero: LiveData<StatefulResource<Genero>> =
        mutableGenero

    override fun getGeneroById(id: Int) {
        launch {
            mutableGenero.value = StatefulResource.with(StatefulResource.State.LOADING)
            val resource = tmdbRepository.getGeneroById(id)
            when {
                resource.hasData() -> {
                    //return the value
                    mutableGenero.value = StatefulResource.success(resource)
                }
                resource.isNetworkIssue() -> {
                    mutableGenero.value = StatefulResource<Genero>()
                        .apply {
                            setMessage(R.string.no_network_connection)
                            setState(StatefulResource.State.ERROR_NETWORK)
                        }
                }
                resource.isApiIssue() -> //TODO 4xx isn't necessarily a service error, expand this to sniff http code before saying service error
                    mutableGenero.value = StatefulResource<Genero>()
                        .apply {
                            setState(StatefulResource.State.ERROR_API)
                            setMessage(R.string.service_error)
                        }
                else -> mutableGenero.value = StatefulResource<Genero>()
                    .apply {
                        setState(StatefulResource.State.SUCCESS)
                        setMessage(R.string.not_found)
                    }
            }
        }
    }

    override fun getAllGeneros() {
        launch {
            mutableFilmeGeneroList.value = StatefulResource.with(StatefulResource.State.LOADING)
            val resource = tmdbRepository.getGeneros()
            when {
                resource.hasData() -> {
                    //return the value
                    mutableFilmeGeneroList.value = StatefulResource.success(resource)
                }
                resource.isNetworkIssue() -> {
                    mutableFilmeGeneroList.value = StatefulResource<List<Genero>>()
                        .apply {
                            setMessage(R.string.no_network_connection)
                            setState(StatefulResource.State.ERROR_NETWORK)
                        }
                }
                resource.isApiIssue() -> //TODO 4xx isn't necessarily a service error, expand this to sniff http code before saying service error
                    mutableFilmeGeneroList.value = StatefulResource<List<Genero>>()
                        .apply {
                            setState(StatefulResource.State.ERROR_API)
                            setMessage(R.string.service_error)
                        }
                else -> mutableFilmeGeneroList.value = StatefulResource<List<Genero>>()
                    .apply {
                        setState(StatefulResource.State.SUCCESS)
                        setMessage(R.string.not_found)
                    }
            }
        }
    }

    override fun getAll() {
        launch {
            mutableFilmePopularList.value = StatefulResource.with(StatefulResource.State.LOADING)
            val resource = tmdbRepository.getAll()
            when {
                resource.hasData() -> {
                    //return the value
                    mutableFilmePopularList.value = StatefulResource.success(resource)
                }
                resource.isNetworkIssue() -> {
                    mutableFilmePopularList.value = StatefulResource<List<FilmePopular>>()
                        .apply {
                            setMessage(R.string.no_network_connection)
                            setState(StatefulResource.State.ERROR_NETWORK)
                        }
                }
                resource.isApiIssue() -> //TODO 4xx isn't necessarily a service error, expand this to sniff http code before saying service error
                    mutableFilmePopularList.value = StatefulResource<List<FilmePopular>>()
                        .apply {
                            setState(StatefulResource.State.ERROR_API)
                            setMessage(R.string.service_error)
                        }
                else -> mutableFilmePopularList.value = StatefulResource<List<FilmePopular>>()
                    .apply {
                        setState(StatefulResource.State.SUCCESS)
                        setMessage(R.string.not_found)
                    }
            }
        }
    }

}
