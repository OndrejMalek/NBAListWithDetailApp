package eu.malek.nbaplayers.net.data

import kotlinx.serialization.json.Json

fun Player.Companion.mock() = Player.envelopeMock().data[0]

fun Player.Companion.mocks(count: Int) =
    List(count) { index -> Player.envelopeMock().data[0].copy(id = index) }

fun Player.Companion.envelopeMock() = Json.decodeFromString<Envelop<Player>>(
    """
{
    "data": [
        {
            "id": 1,
            "first_name": "Alex",
            "last_name": "Abrines",
            "position": "G",
            "height": "6-6",
            "weight": "190",
            "jersey_number": "8",
            "college": "FC Barcelona",
            "country": "Spain",
            "draft_year": 2013,
            "draft_round": 2,
            "draft_number": 32,
            "team": {
                "id": 21,
                "conference": "West",
                "division": "Northwest",
                "city": "Oklahoma City",
                "name": "Thunder",
                "full_name": "Oklahoma City Thunder",
                "abbreviation": "OKC"
            }
        },
        {
            "id": 2,
            "first_name": "Jaylen",
            "last_name": "Adams",
            "position": "G",
            "height": "6-0",
            "weight": "225",
            "jersey_number": "10",
            "college": "St. Bonaventure",
            "country": "USA",
            "draft_year": null,
            "draft_round": null,
            "draft_number": null,
            "team": {
                "id": 1,
                "conference": "East",
                "division": "Southeast",
                "city": "Atlanta",
                "name": "Hawks",
                "full_name": "Atlanta Hawks",
                "abbreviation": "ATL"
            }
        },
        {
            "id": 3,
            "first_name": "Steven",
            "last_name": "Adams",
            "position": "C",
            "height": "6-11",
            "weight": "265",
            "jersey_number": "12",
            "college": "Pittsburgh",
            "country": "New Zealand",
            "draft_year": 2013,
            "draft_round": 1,
            "draft_number": 12,
            "team": {
                "id": 11,
                "conference": "West",
                "division": "Southwest",
                "city": "Houston",
                "name": "Rockets",
                "full_name": "Houston Rockets",
                "abbreviation": "HOU"
            }
        }
    ],
    "meta": {
        "next_cursor": 3,
        "per_page": 3
    }
}        
"""
)