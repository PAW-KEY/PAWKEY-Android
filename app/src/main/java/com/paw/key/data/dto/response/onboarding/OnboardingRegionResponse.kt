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