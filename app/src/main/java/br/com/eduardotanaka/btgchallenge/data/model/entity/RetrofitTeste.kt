package br.com.eduardotanaka.btgchallenge.data.model.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.eduardotanaka.btgchallenge.data.model.api.RetrofitTesteResponse
import br.com.eduardotanaka.btgchallenge.data.model.entity.base.ReflectsApiResponse
import br.com.eduardotanaka.btgchallenge.util.DateTimeUtil.localDateTimeFormatter
import kotlinx.parcelize.Parcelize
import org.threeten.bp.LocalDateTime

@Entity
@Parcelize
data class RetrofitTeste(
    @PrimaryKey
    var id: Int = 0,
    var createdAt: LocalDateTime = LocalDateTime.now(),
    var name: String = "",
    var avatar: String = ""
) : ReflectsApiResponse<List<RetrofitTeste>, RetrofitTesteResponse>, Parcelable {
    override fun reflectFrom(apiResponse: RetrofitTesteResponse): List<RetrofitTeste> {
        val retrofitTesteList = ArrayList<RetrofitTeste>()
        apiResponse.retrofitTesteModelList.let { result ->
            result.map {
                retrofitTesteList.add(
                    RetrofitTeste(
                        it.id,
                        LocalDateTime.parse(it.createdAt, localDateTimeFormatter),
                        it.name,
                        it.avatar
                    )
                )
            }
        }

        return retrofitTesteList
    }
}