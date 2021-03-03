package br.com.eduardotanaka.btgchallenge.ui

import androidx.lifecycle.LiveData
import br.com.eduardotanaka.btgchallenge.data.model.entity.FilmePopular
import br.com.eduardotanaka.btgchallenge.ui.base.StatefulResource

interface MainActivityViewModel {

    val filmePopularList: LiveData<StatefulResource<List<FilmePopular>>>
    fun getAll()

}
