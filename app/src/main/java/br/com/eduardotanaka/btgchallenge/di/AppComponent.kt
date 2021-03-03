package br.com.eduardotanaka.btgchallenge.di

import android.app.Application
import br.com.eduardotanaka.btgchallenge.AppApplication
import br.com.eduardotanaka.btgchallenge.di.module.*
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        ActivityBuilderModule::class,
        AndroidInjectionModule::class,
        NetworkModule::class,
        PersistenceModule::class,
        RepositoryModule::class,
        AppModule::class
    ]
)
interface AppComponent : AndroidInjector<AppApplication> {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance application: Application): AppComponent
    }

}