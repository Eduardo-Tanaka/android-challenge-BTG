package br.com.eduardotanaka.btgchallenge.di.module

import br.com.eduardotanaka.btgchallenge.ui.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Colocar as activities aqui e caso necessário importar os modules
 */
@Module
abstract class ActivityBuilderModule {

    @ContributesAndroidInjector(modules = [FragmentModule::class])
    abstract fun contributesMainActivity(): MainActivity

}