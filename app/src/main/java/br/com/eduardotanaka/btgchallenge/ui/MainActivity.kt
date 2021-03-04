package br.com.eduardotanaka.btgchallenge.ui

import android.os.Bundle
import br.com.eduardotanaka.btgchallenge.R
import br.com.eduardotanaka.btgchallenge.constants.TabLayoutFilmeKey
import br.com.eduardotanaka.btgchallenge.databinding.ActivityMainBinding
import br.com.eduardotanaka.btgchallenge.ui.base.BaseActivity
import br.com.eduardotanaka.btgchallenge.ui.favorito.FilmeFavoritoFragment
import br.com.eduardotanaka.btgchallenge.ui.popular.FilmePopularFragment
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : BaseActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val adapter = FilmeViewPagerAdapter(supportFragmentManager, lifecycle)
        adapter.addFragment(FilmePopularFragment.newInstance())
        adapter.addFragment(FilmeFavoritoFragment.newInstance())
        binding.viewPagerFilme.adapter = adapter

        TabLayoutMediator(binding.tabLayoutFilme, binding.viewPagerFilme) { tab, position ->
            when (position) {
                0 -> {
                    tab.text = TabLayoutFilmeKey.POPULAR.toString()
                    tab.icon = getDrawable(R.drawable.ic_local_movies_24px)
                }
                1 -> {
                    tab.text = TabLayoutFilmeKey.FAVORITO.toString()
                    tab.icon = getDrawable(R.drawable.ic_favorite_24px)
                }
            }
        }.attach()
    }
}