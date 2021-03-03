package br.com.eduardotanaka.btgchallenge.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.eduardotanaka.btgchallenge.R
import br.com.eduardotanaka.btgchallenge.data.model.entity.RetrofitTeste
import br.com.eduardotanaka.btgchallenge.data.repository.RetrofitTesteRepository
import br.com.eduardotanaka.btgchallenge.ui.base.BaseViewModel
import br.com.eduardotanaka.btgchallenge.ui.base.StatefulResource
import javax.inject.Inject

class MainActivityViewModelImpl @Inject constructor(
    private val retrofitTesteRepository: RetrofitTesteRepository,
) : BaseViewModel(), MainActivityViewModel {

    private val mutableRetrofitTesteList: MutableLiveData<StatefulResource<List<RetrofitTeste>>> =
        MutableLiveData()
    override val retrofitTesteList: LiveData<StatefulResource<List<RetrofitTeste>>> =
        mutableRetrofitTesteList

    override fun getAll() {
        launch {
            mutableRetrofitTesteList.value = StatefulResource.with(StatefulResource.State.LOADING)
            val resource = retrofitTesteRepository.getAll()
            when {
                resource.hasData() -> {
                    //return the value
                    mutableRetrofitTesteList.value = StatefulResource.success(resource)
                }
                resource.isNetworkIssue() -> {
                    mutableRetrofitTesteList.value = StatefulResource<List<RetrofitTeste>>()
                        .apply {
                            setMessage(R.string.no_network_connection)
                            setState(StatefulResource.State.ERROR_NETWORK)
                        }
                }
                resource.isApiIssue() -> //TODO 4xx isn't necessarily a service error, expand this to sniff http code before saying service error
                    mutableRetrofitTesteList.value = StatefulResource<List<RetrofitTeste>>()
                        .apply {
                            setState(StatefulResource.State.ERROR_API)
                            setMessage(R.string.service_error)
                        }
                else -> mutableRetrofitTesteList.value = StatefulResource<List<RetrofitTeste>>()
                    .apply {
                        setState(StatefulResource.State.SUCCESS)
                        setMessage(R.string.not_found)
                    }
            }
        }
    }

}