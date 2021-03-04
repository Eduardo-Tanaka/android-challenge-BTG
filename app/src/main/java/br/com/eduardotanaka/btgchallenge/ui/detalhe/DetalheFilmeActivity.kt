package br.com.eduardotanaka.btgchallenge.ui.detalhe

import android.os.Bundle
import br.com.eduardotanaka.btgchallenge.constants.ExtraKey
import br.com.eduardotanaka.btgchallenge.data.model.entity.FilmePopular
import br.com.eduardotanaka.btgchallenge.databinding.ActivityDetalheFilmeBinding
import br.com.eduardotanaka.btgchallenge.ui.base.BaseActivity
import com.bumptech.glide.Glide
import com.google.android.material.chip.Chip

class DetalheFilmeActivity : BaseActivity() {

    private lateinit var binding: ActivityDetalheFilmeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetalheFilmeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        supportActionBar?.hide()

        val filme = intent.getParcelableExtra<FilmePopular>(ExtraKey.FILME.toString())

        Glide.with(this).load("https://image.tmdb.org/t/p/original/" + filme?.poster)
            .into(binding.posterFilme)

        binding.cardFilmePopular.transitionName = "filme_${filme?.movieId}"
        binding.filmeTitulo.text = filme?.titulo
        binding.filmeSinopse.text = filme?.sinopse
        binding.filmeNota.text = "nota: ${filme?.nota}"

        val chipGroup = binding.chipGroup
        filme?.generos?.map {
            val chip = Chip(this)
            chip.text = it.toString()
            chipGroup.addView(chip)
        }
    }
}
