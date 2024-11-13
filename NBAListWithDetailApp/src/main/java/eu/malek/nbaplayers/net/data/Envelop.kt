package eu.malek.nbaplayers.net.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Envelop<T>(
    @SerialName("data") val data: ArrayList<T> = arrayListOf(),
    @SerialName("meta") val meta: Meta? = Meta()
)

@Serializable
data class Meta(
    @SerialName("next_cursor") val nextCursor: Int? = null,
    @SerialName("per_page") val perPage: Int? = null
)