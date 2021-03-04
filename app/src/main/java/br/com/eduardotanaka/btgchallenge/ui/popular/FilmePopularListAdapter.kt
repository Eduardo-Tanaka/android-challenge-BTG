package br.com.eduardotanaka.btgchallenge.ui.popular

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.recyclerview.widget.RecyclerView
import br.com.eduardotanaka.btgchallenge.R
import br.com.eduardotanaka.btgchallenge.data.model.entity.FilmePopular
import br.com.eduardotanaka.btgchallenge.databinding.LayoutFilmePopularBinding
import br.com.eduardotanaka.btgchallenge.util.DateTimeUtil.defaultFormatter
import com.bumptech.glide.Glide

class FilmePopularListAdapter(
    var filmes: List<FilmePopular>,
    val context: Context
) : RecyclerView.Adapter<FilmePopularListAdapter.MainViewHolder>() {

    interface OnItemSelectedListener {
        fun onFilmeClicked(filme: FilmePopular, options: ActivityOptionsCompat)
    }

    interface OnBottomReachedListener {
        fun onBottomReached(position: Int)
    }

    var onItemSelectedListener: OnItemSelectedListener? = null
    var onBottomReachedListener: OnBottomReachedListener? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FilmePopularListAdapter.MainViewHolder {
        val itemBinding =
            LayoutFilmePopularBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MainViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: FilmePopularListAdapter.MainViewHolder, position: Int) {
        if (position == filmes.size - 1) {
            onBottomReachedListener?.onBottomReached(position);
        }

        holder.bind(filmes[position])
    }

    override fun getItemCount(): Int {
        return filmes.count()
    }

    inner class MainViewHolder(private val rowView: LayoutFilmePopularBinding) :
        RecyclerView.ViewHolder(rowView.root) {
        private lateinit var filme: FilmePopular

        fun bind(filme: FilmePopular) {
            this.filme = filme

            Glide.with(context).load("https://image.tmdb.org/t/p/w500/" + this.filme.poster)
                .fitCenter() // this cropping technique scales the image so that it fills the requested bounds and then crops the extra.
                .into(rowView.posterFilme)

            rowView.cardFilmeTitulo.text = this.filme.titulo
            rowView.cardFilmeData.text = this.filme.data.format(defaultFormatter)
            rowView.cardFilmePopular.transitionName = "filme_${this.filme.movieId}"

            rowView.cardFilmePopular.setOnClickListener {
                val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                    context as Activity,
                    rowView.cardFilmePopular,
                    "filme_${filme.movieId}"
                )
                onItemSelectedListener?.onFilmeClicked(filme, options)
            }
        }
    }

    fun updateItems(newListFilmes: List<FilmePopular>) {
        filmes = newListFilmes
        notifyDataSetChanged()
    }
}
