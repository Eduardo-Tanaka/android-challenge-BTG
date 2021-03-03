package br.com.eduardotanaka.btgchallenge.data.model.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.eduardotanaka.btgchallenge.data.model.api.FilmePopularResponse
import br.com.eduardotanaka.btgchallenge.data.model.entity.base.ReflectsApiResponse
import kotlinx.parcelize.Parcelize
import org.threeten.bp.LocalDate

@Entity
@Parcelize
data class FilmePopular(
    @PrimaryKey
    var movieId: Int = 0,
    var poster: String = "",
    var titulo: String = "",
    var data: LocalDate = LocalDate.now(),
    var sinopse: String = "",
    var nota: Double = 0.0,
    var generos: List<Int> = ArrayList()
) : ReflectsApiResponse<List<FilmePopular>, FilmePopularResponse>, Parcelable {
    override fun reflectFrom(apiResponse: FilmePopularResponse): List<FilmePopular> {
        val list = ArrayList<FilmePopular>()
        apiResponse.results.let { result ->
            result.map {
                list.add(
                    FilmePopular(
                        it.id,
                        it.posterPath,
                        it.title,
                        LocalDate.parse(it.releaseDate),
                        it.overview,
                        it.voteAverage,
                        it.genreIds
                    )
                )
            }
        }

        return list
    }
}
