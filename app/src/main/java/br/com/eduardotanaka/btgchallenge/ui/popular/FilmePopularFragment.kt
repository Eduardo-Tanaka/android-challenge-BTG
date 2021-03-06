package br.com.eduardotanaka.btgchallenge.ui.popular

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
import br.com.eduardotanaka.btgchallenge.databinding.FragmentFilmePopularBinding
import br.com.eduardotanaka.btgchallenge.ui.MainActivityViewModelImpl
import br.com.eduardotanaka.btgchallenge.ui.base.StatefulResource
import br.com.eduardotanaka.btgchallenge.ui.detalhe.DetalheFilmeActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.android.support.DaggerFragment
import javax.inject.Inject


/**
 * View Binding example with a fragment that uses the alternate constructor for inflation and
 * [onViewCreated] for binding.
 */
class FilmePopularFragment : DaggerFragment(), SearchView.OnQueryTextListener {

    @Inject
    lateinit var factory: ViewModelProvider.Factory
    private val viewModel by viewModels<MainActivityViewModelImpl> { factory }
    private var adapter: FilmePopularListAdapter? = null

    // Scoped to the lifecycle of the fragment's view (between onCreateView and onDestroyView)
    private var fragmentFilmePopularBinding: FragmentFilmePopularBinding? = null
    private var lista: List<FilmePopular> = ArrayList()

    companion object {
        fun newInstance() = FilmePopularFragment()
    }

    override
    fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setHasOptionsMenu(true)

        val binding = FragmentFilmePopularBinding.inflate(inflater, container, false)
        fragmentFilmePopularBinding = binding

        fragmentFilmePopularBinding?.swipeRefresh?.setOnRefreshListener {
            viewModel.getAll()
        }

        viewModel.getAll()
        viewModel.filmePopularList.observe(viewLifecycleOwner, {
            if (it.state == StatefulResource.State.SUCCESS && it.hasData()) {
                fragmentFilmePopularBinding?.loadingListPopular?.hide()
                fragmentFilmePopularBinding?.swipeRefresh?.isRefreshing = false

                lista = it.resource?.data!!

                adapter = FilmePopularListAdapter(lista, requireContext())

                fragmentFilmePopularBinding?.rvFilmePopular?.layoutManager =
                    GridLayoutManager(requireContext(), 2)
                fragmentFilmePopularBinding?.rvFilmePopular?.adapter = adapter

                adapter?.onItemSelectedListener = object :
                    FilmePopularListAdapter.OnItemSelectedListener {
                    override fun onFilmeClicked(
                        filme: FilmePopular,
                        options: ActivityOptionsCompat
                    ) {
                        val intent = Intent(requireContext(), DetalheFilmeActivity::class.java)
                        intent.putExtra(ExtraKey.FILME.toString(), filme)

                        startActivity(intent, options.toBundle())
                    }
                }

                adapter?.onBottomReachedListener = object :
                    FilmePopularListAdapter.OnBottomReachedListener {
                    override fun onBottomReached(position: Int) {
                        // TODO pegar novos filmes da api
                        fragmentFilmePopularBinding?.loadingListPopular?.show()
                        viewModel.getAll()
                    }
                }
            } else if (it.state == StatefulResource.State.ERROR_NETWORK) {
                fragmentFilmePopularBinding?.loadingListPopular?.hide()
                fragmentFilmePopularBinding?.swipeRefresh?.isRefreshing = false
                MaterialAlertDialogBuilder(requireContext())
                    .setTitle("ERRO INTERNET")
                    .setMessage("Ocorreu um erro ao requisitar informações da internet, por favor verifique sua conexão.")
                    .setPositiveButton("OK") { dialog, _ ->
                        // Respond to positive button press
                        dialog.dismiss()
                    }
                    .show()
            } else if (it.state == StatefulResource.State.ERROR_API) {
                fragmentFilmePopularBinding?.loadingListPopular?.hide()
                fragmentFilmePopularBinding?.swipeRefresh?.isRefreshing = false
                MaterialAlertDialogBuilder(requireContext())
                    .setTitle("SERVIÇO INDISPONÍVEL")
                    .setMessage(it.message!!)
                    .setPositiveButton("OK") { dialog, _ ->
                        // Respond to positive button press
                        dialog.dismiss()
                    }
                    .show()
            }
        })

        return binding.root
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
        fragmentFilmePopularBinding = null
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
