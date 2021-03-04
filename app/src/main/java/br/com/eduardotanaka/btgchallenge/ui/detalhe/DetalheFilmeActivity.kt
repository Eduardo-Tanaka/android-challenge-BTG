package br.com.eduardotanaka.btgchallenge.ui.detalhe

import android.os.Bundle
import androidx.activity.viewModels
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import br.com.eduardotanaka.btgchallenge.R
import br.com.eduardotanaka.btgchallenge.constants.ExtraKey
import br.com.eduardotanaka.btgchallenge.data.model.entity.FilmePopular
import br.com.eduardotanaka.btgchallenge.databinding.ActivityDetalheFilmeBinding
import br.com.eduardotanaka.btgchallenge.ui.MainActivityViewModelImpl
import br.com.eduardotanaka.btgchallenge.ui.base.BaseActivity
import br.com.eduardotanaka.btgchallenge.ui.base.StatefulResource
import com.bumptech.glide.Glide
import com.google.android.material.chip.Chip


class DetalheFilmeActivity : BaseActivity() {

    private val viewModel by viewModels<MainActivityViewModelImpl> { factory }

    private lateinit var binding: ActivityDetalheFilmeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetalheFilmeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        supportActionBar?.hide()

        val filme = intent.getParcelableExtra<FilmePopular>(ExtraKey.FILME.toString())

        // create a ProgressDrawable object which we will show as placeholder
        val drawable = CircularProgressDrawable(this)
        drawable.setColorSchemeColors(
            R.color.purple_500,
            R.color.purple_700,
            R.color.purple_200
        )
        drawable.centerRadius = 60f
        drawable.strokeWidth = 10f
        // set all other properties as you would see fit and start it
        drawable.start()

        Glide.with(this).load("https://image.tmdb.org/t/p/original/" + filme?.poster)
            .placeholder(drawable)
            //.diskCacheStrategy(DiskCacheStrategy.NONE)
            //.skipMemoryCache(true)
            .fitCenter() // this cropping technique scales the image so that it fills the requested bounds and then crops the extra.
            .into(binding.posterFilme)

        binding.cardFilmePopular.transitionName = "filme_${filme?.movieId}"
        binding.filmeTitulo.text = filme?.titulo
        binding.filmeSinopse.text = filme?.sinopse
        binding.filmeNota.text = "nota: ${filme?.nota}"

        val chipGroup = binding.chipGroup
        viewModel.genero.observe(this, { result ->
            if (result.state == StatefulResource.State.SUCCESS && result.hasData()) {
                val chip = Chip(this)
                chip.text = result.resource?.data?.genero
                chipGroup.addView(chip)
            }
        })

        filme?.generos?.map {
            viewModel.getGeneroById(it)
        }
    }
}
