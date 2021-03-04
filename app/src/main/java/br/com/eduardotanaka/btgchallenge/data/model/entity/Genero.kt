package br.com.eduardotanaka.btgchallenge.data.model.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.eduardotanaka.btgchallenge.data.model.api.FilmeGeneroResponse
import br.com.eduardotanaka.btgchallenge.data.model.entity.base.ReflectsApiResponse
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class Genero(
    @PrimaryKey
    var id: Int = 0,
    var genero: String = "",
) : ReflectsApiResponse<List<Genero>, FilmeGeneroResponse>, Parcelable {
    override fun reflectFrom(apiResponse: FilmeGeneroResponse): List<Genero> {
        val list = ArrayList<Genero>()
        apiResponse.genres.let { result ->
            result.map {
                list.add(
                    Genero(
                        it.id,
                        it.name
                    )
                )
            }
        }

        return list
    }
}
