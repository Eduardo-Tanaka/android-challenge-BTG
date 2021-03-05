package br.com.eduardotanaka.btgchallenge.ui.favorito

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.core.app.ActivityOptionsCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import br.com.eduardotanaka.btgchallenge.R
import br.com.eduardotanaka.btgchallenge.constants.ExtraKey
import br.com.eduardotanaka.btgchallenge.data.model.entity.FilmePopular
import br.com.eduardotanaka.btgchallenge.databinding.FragmentFilmeFavoritoBinding
import br.com.eduardotanaka.btgchallenge.ui.MainActivityViewModelImpl
import br.com.eduardotanaka.btgchallenge.ui.base.StatefulResource
import br.com.eduardotanaka.btgchallenge.ui.detalhe.DetalheFilmeActivity
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class FilmeFavoritoFragment : DaggerFragment(), SearchView.OnQueryTextListener {

    @Inject
    lateinit var factory: ViewModelProvider.Factory
    private val viewModel by viewModels<MainActivityViewModelImpl> { factory }
    private var adapter: FilmeFavoritoListAdapter? = null

    // Scoped to the lifecycle of the fragment's view (between onCreateView and onDestroyView)
    private var fragmentFilmeFavorioBinding: FragmentFilmeFavoritoBinding? = null
    private var lista: List<FilmePopular> = ArrayList()

    companion object {
        fun newInstance() = FilmeFavoritoFragment()
    }

    override
    fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)

        val binding = FragmentFilmeFavoritoBinding.inflate(inflater, container, false)
        fragmentFilmeFavorioBinding = binding

        viewModel.filmeFavoritoList.observe(viewLifecycleOwner, {
            if (it.state == StatefulResource.State.SUCCESS && it.hasData()) {
                fragmentFilmeFavorioBinding?.loadingListFavorito?.hide()

                lista = it.resource?.data!!
                adapter = FilmeFavoritoListAdapter(lista, requireContext())

                fragmentFilmeFavorioBinding?.rvFilmeFavorito?.layoutManager =
                    GridLayoutManager(requireContext(), 2)
                fragmentFilmeFavorioBinding?.rvFilmeFavorito?.adapter = adapter

                adapter?.onItemSelectedListener = object :
                    FilmeFavoritoListAdapter.OnItemSelectedListener {
                    override fun onFilmeClicked(
                        filme: FilmePopular,
                        options: ActivityOptionsCompat
                    ) {
                        val intent = Intent(requireContext(), DetalheFilmeActivity::class.java)
                        intent.putExtra(ExtraKey.FILME.toString(), filme)

                        startActivity(intent, options.toBundle())
                    }
                }
            }
        })

        return binding.root
    }

    override fun onResume() {
        super.onResume()

        viewModel.getFavoritos()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        //inflater.inflate(R.menu.menu_cow_list, menu)
        val searchItem = menu.findItem(R.id.search)
        val searchView = searchItem.actionView as SearchView
        searchView.setOnQueryTextListener(this)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.search -> {
                true
            }
            R.id.ordenar_titulo -> {
                lista = lista.sortedBy { it.titulo }
                adapter?.updateItems(lista)
                true
            }
            R.id.ordenar_data -> {
                lista = lista.sortedBy { it.data }
                adapter?.updateItems(lista)
                true
            }
            else -> false
        }
    }

    override fun onDestroyView() {
        // Consider not storing the binding instance in a field, if not needed.
        fragmentFilmeFavorioBinding = null
        super.onDestroyView()
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        adapter?.filter?.filter(newText)
        return false
    }
}
