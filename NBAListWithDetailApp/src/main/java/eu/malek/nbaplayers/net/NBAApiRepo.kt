package eu.malek.nbaplayers.net


class NBAApiRepo(val service: NBAApi = NBAApi.createService()) : NBAApi by service {
    val playersPager = ballDontLieApiPager({ cursor, perPage ->
        this@NBAApiRepo.getPlayers(
            cursor = cursor,
            perPage = perPage
        )
    })
}


