package br.com.eduardotanaka.btgchallenge.ui

import android.os.Bundle
import androidx.activity.viewModels
import br.com.eduardotanaka.btgchallenge.databinding.ActivityMainBinding
import br.com.eduardotanaka.btgchallenge.ui.base.BaseActivity
import br.com.eduardotanaka.btgchallenge.ui.base.StatefulResource
import timber.log.Timber

class MainActivity : BaseActivity() {

    private val viewModel by viewModels<MainActivityViewModelImpl> { factory }
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.textView.text = "view binding"

        viewModel.getAll()
        viewModel.retrofitTesteList.observe(this, {
            if (it.state == StatefulResource.State.SUCCESS && it.hasData()) {
                Timber.d(it.resource?.data?.toString())
            }
        })
    }
}