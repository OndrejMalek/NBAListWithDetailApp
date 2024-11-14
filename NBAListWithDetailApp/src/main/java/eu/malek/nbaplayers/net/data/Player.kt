package eu.malek.nbaplayers.net.data


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class Player(
    @SerialName("id") val id: Int,
    @SerialName("first_name") val firstName: String? = null,
    @SerialName("last_name") val lastName: String? = null,
    @SerialName("position") val position: String? = null,
    @SerialName("height") val height: String? = null,
    @SerialName("weight") val weight: String? = null,
    @SerialName("jersey_number") val jerseyNumber: String? = null,
    @SerialName("college") val college: String? = null,
    @SerialName("country") val country: String? = null,
    @SerialName("draft_year") val draftYear: Int? = null,
    @SerialName("draft_round") val draftRound: Int? = null,
    @SerialName("draft_number") val draftNumber: Int? = null,
    @SerialName("team") val team: Team? = null
) {
    companion object
}

@Serializable
data class Team(
    @SerialName("id") val id: Int,
    @SerialName("conference") val conference: String? = null,
    @SerialName("division") val division: String? = null,
    @SerialName("city") val city: String? = null,
    @SerialName("name") val name: String? = null,
    @SerialName("full_name") val fullName: String? = null,
    @SerialName("abbreviation") val abbreviation: String? = null
)

