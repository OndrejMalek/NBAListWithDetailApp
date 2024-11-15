package eu.malek.nbaplayers.net

import java.io.File


class NBAApiRepo(
    private val cacheDir: File? = null,
    private val service: NBAApi = NBAApi.createService(cacheDir)
) :
    NBAApi by service {
    val playersPager = ballDontLieApiPager({ cursor, perPage ->
        this@NBAApiRepo.getPlayers(
            cursor = cursor,
            perPage = perPage
        )
    }).flow
}


