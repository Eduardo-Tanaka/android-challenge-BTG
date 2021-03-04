package br.com.eduardotanaka.btgchallenge.di.module

import br.com.eduardotanaka.btgchallenge.ui.FilmeFavoritoFragment
import br.com.eduardotanaka.btgchallenge.ui.FilmePopularFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentModule {

    @ContributesAndroidInjector
    internal abstract fun contributeFilmeFavoritoFragment(): FilmeFavoritoFragment

    @ContributesAndroidInjector
    internal abstract fun contributeFilmePopularFragment(): FilmePopularFragment
}
