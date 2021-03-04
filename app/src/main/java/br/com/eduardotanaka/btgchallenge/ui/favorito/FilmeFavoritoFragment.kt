package br.com.eduardotanaka.btgchallenge.ui.favorito

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import br.com.eduardotanaka.btgchallenge.R
import br.com.eduardotanaka.btgchallenge.ui.MainActivityViewModelImpl
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class FilmeFavoritoFragment : DaggerFragment() {

    @Inject
    lateinit var factory: ViewModelProvider.Factory
    private val viewModel by viewModels<MainActivityViewModelImpl> { factory }

    companion object {
        fun newInstance() = FilmeFavoritoFragment()
    }

    override
    fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_filme_favorito, container, false)
    }
}
