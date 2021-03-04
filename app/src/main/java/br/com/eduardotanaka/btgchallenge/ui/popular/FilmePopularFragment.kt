package br.com.eduardotanaka.btgchallenge.ui.popular

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
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
import dagger.android.support.DaggerFragment
import javax.inject.Inject


/**
 * View Binding example with a fragment that uses the alternate constructor for inflation and
 * [onViewCreated] for binding.
 */
class FilmePopularFragment : DaggerFragment() {

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
            }
        })

        return binding.root
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
}
