package br.com.eduardotanaka.btgchallenge.ui

import androidx.lifecycle.LiveData
import br.com.eduardotanaka.btgchallenge.data.model.entity.RetrofitTeste
import br.com.eduardotanaka.btgchallenge.ui.base.StatefulResource

interface MainActivityViewModel {

    val retrofitTesteList: LiveData<StatefulResource<List<RetrofitTeste>>>
    fun getAll()
}