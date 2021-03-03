package br.com.eduardotanaka.btgchallenge.di.module

import br.com.eduardotanaka.btgchallenge.data.repository.TMDBRepository
import br.com.eduardotanaka.btgchallenge.data.repository.TMDBRepositoryImpl
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
    abstract fun bindTmdbRepository(tmdbRepository: TMDBRepositoryImpl): TMDBRepository

}
