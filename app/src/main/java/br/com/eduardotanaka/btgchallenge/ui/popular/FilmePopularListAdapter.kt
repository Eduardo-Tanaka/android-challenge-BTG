package br.com.eduardotanaka.btgchallenge.ui.popular

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.core.app.ActivityOptionsCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import br.com.eduardotanaka.btgchallenge.R
import br.com.eduardotanaka.btgchallenge.data.model.entity.FilmePopular
import br.com.eduardotanaka.btgchallenge.databinding.LayoutFilmePopularBinding
import br.com.eduardotanaka.btgchallenge.util.DateTimeUtil.defaultFormatter
import com.bumptech.glide.Glide
import java.util.*
import kotlin.collections.ArrayList

class FilmePopularListAdapter(
    var filmes: List<FilmePopular>,
    val context: Context
) : RecyclerView.Adapter<FilmePopularListAdapter.MainViewHolder>(), Filterable {

    var onItemSelectedListener: OnItemSelectedListener? = null
    var onBottomReachedListener: OnBottomReachedListener? = null

    var filmesFilterList = ArrayList<FilmePopular>()

    init {
        filmesFilterList = filmes as ArrayList<FilmePopular>
    }

    interface OnItemSelectedListener {
        fun onFilmeClicked(filme: FilmePopular, options: ActivityOptionsCompat)
    }

    interface OnBottomReachedListener {
        fun onBottomReached(position: Int)
    }

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

        holder.bind(filmesFilterList[position])
    }

    override fun getItemCount(): Int {
        return filmesFilterList.count()
    }

    inner class MainViewHolder(private val rowView: LayoutFilmePopularBinding) :
        RecyclerView.ViewHolder(rowView.root) {
        private lateinit var filme: FilmePopular

        fun bind(filme: FilmePopular) {
            this.filme = filme

            // create a ProgressDrawable object which we will show as placeholder
            val drawable = CircularProgressDrawable(context)
            drawable.setColorSchemeColors(
                R.color.purple_500,
                R.color.purple_700,
                R.color.purple_200
            )
            drawable.centerRadius = 30f
            drawable.strokeWidth = 5f
            // set all other properties as you would see fit and start it
            drawable.start()

            Glide.with(context).load("https://image.tmdb.org/t/p/original/" + this.filme.poster)
                .placeholder(drawable)
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
        filmesFilterList = newListFilmes as ArrayList<FilmePopular>
        notifyDataSetChanged()
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                filmesFilterList = if (charSearch.isEmpty()) {
                    filmes as ArrayList<FilmePopular>
                } else {
                    val resultList = ArrayList<FilmePopular>()
                    for (row in filmes) {
                        if (row.titulo.toLowerCase(Locale.ROOT)
                                .contains(charSearch.toLowerCase(Locale.ROOT)) || row.data.year == charSearch.toIntOrNull()
                        ) {
                            resultList.add(row)
                        }
                    }
                    resultList
                }

                val filterResults = FilterResults()
                filterResults.values = filmesFilterList
                return filterResults
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filmesFilterList = results?.values as ArrayList<FilmePopular>
                notifyDataSetChanged()
            }
        }
    }
}
