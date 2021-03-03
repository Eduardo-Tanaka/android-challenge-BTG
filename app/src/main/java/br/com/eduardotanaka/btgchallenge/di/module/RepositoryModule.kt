package br.com.eduardotanaka.btgchallenge.di.module

import br.com.eduardotanaka.btgchallenge.data.repository.RetrofitTesteRepository
import br.com.eduardotanaka.btgchallenge.data.repository.RetrofitTesteRepositoryImpl
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

/**
 * Classes repository colocar aqui
 */
@Module
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindRetrofitTesteRepository(retrofitTesteRepository: RetrofitTesteRepositoryImpl): RetrofitTesteRepository

}