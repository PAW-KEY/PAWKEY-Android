import com.paw.key.domain.model.entity.onboarding.District
import com.paw.key.domain.model.entity.onboarding.Dong
import com.paw.key.domain.model.entity.onboarding.Gu
import com.paw.key.domain.model.entity.onboarding.OnboardingRegion
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DistrictResponse(
    @SerialName("code")
    val code: String,

    @SerialName("message")
    val message: String,

    @SerialName("data")
    val data: DistrictDataDto
)

@Serializable
data class DistrictDataDto(
    @SerialName("districtDtos")
    val districtDtos: List<DistrictDto>
)

@Serializable
data class DistrictDto(
    @SerialName("gu")
    val gu: GuDto,

    @SerialName("dongs")
    val dongs: List<DongDto>
)

@Serializable
data class GuDto(
    @SerialName("id")
    val id: Int,

    @SerialName("name")
    val name: String
)

@Serializable
data class DongDto(
    @SerialName("id")
    val id: Int,

    @SerialName("name")
    val name: String
)

fun DistrictResponse.toDomain(): OnboardingRegion {
    return OnboardingRegion(
        districtList = this.data.districtDtos.map { it.toDomain() }
    )
}

fun DistrictDto.toDomain(): District {
    return District(
        gu = this.gu.toDomain(),
        dongs = this.dongs.map { it.toDomain() }
    )
}

fun GuDto.toDomain(): Gu {
    return Gu(
        id = this.id,
        name = this.name
    )
}

fun DongDto.toDomain(): Dong {
    return Dong(
        id = this.id,
        name = this.name
    )
}