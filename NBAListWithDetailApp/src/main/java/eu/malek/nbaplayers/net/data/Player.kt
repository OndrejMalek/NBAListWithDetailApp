package eu.malek.nbaplayers.net.data


import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName


@Serializable
data class Player(
    @SerialName("id") var id: Int? = null,
    @SerialName("first_name") var firstName: String? = null,
    @SerialName("last_name") var lastName: String? = null,
    @SerialName("position") var position: String? = null,
    @SerialName("height") var height: String? = null,
    @SerialName("weight") var weight: String? = null,
    @SerialName("jersey_number") var jerseyNumber: String? = null,
    @SerialName("college") var college: String? = null,
    @SerialName("country") var country: String? = null,
    @SerialName("draft_year") var draftYear: Int? = null,
    @SerialName("draft_round") var draftRound: Int? = null,
    @SerialName("draft_number") var draftNumber: Int? = null,
    @SerialName("team") var team: Team? = Team()

)

@Serializable
data class Team(
    @SerialName("id") var id: Int? = null,
    @SerialName("conference") var conference: String? = null,
    @SerialName("division") var division: String? = null,
    @SerialName("city") var city: String? = null,
    @SerialName("name") var name: String? = null,
    @SerialName("full_name") var fullName: String? = null,
    @SerialName("abbreviation") var abbreviation: String? = null
)