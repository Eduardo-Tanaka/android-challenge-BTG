package br.com.eduardotanaka.btgchallenge.ui.base

import androidx.lifecycle.ViewModelProvider
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

abstract class BaseActivity : DaggerAppCompatActivity(), HasAndroidInjector {

    @Inject
    lateinit var fragmentInjector: DispatchingAndroidInjector<Any>
    override fun androidInjector() = fragmentInjector

    @Inject
    lateinit var factory: ViewModelProvider.Factory
}