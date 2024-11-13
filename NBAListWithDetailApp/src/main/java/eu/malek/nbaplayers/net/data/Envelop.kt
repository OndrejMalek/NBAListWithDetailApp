package eu.malek.nbaplayers.net.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Envelop<T>(
    @SerialName("data" ) var dasadta : ArrayList<T> = arrayListOf(),
    @SerialName("meta" ) var meta : Meta?           = Meta()
)

@Serializable
data class Meta (
    @SerialName("next_cursor" ) var nextCursor : Int? = null,
    @SerialName("per_page"    ) var perPage    : Int? = null
)