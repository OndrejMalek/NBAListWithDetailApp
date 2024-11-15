package eu.malek.retrofit2

import eu.malek.nbaplayers.net.NBAApi
import eu.malek.nbaplayers.net.NBAApiRepo
import eu.malek.nbaplayers.net.httpStatusCode
import kotlinx.coroutines.test.runTest
import kotlin.test.Ignore
import kotlin.test.Test
import kotlin.test.assertEquals


class RetrofitKtTest {


    /**
     * Requires disabled internet connection
     */
    @Test
    @Ignore
    fun catchAllExceptionsInterceptor_whenNoInternet_NoExceptionsThrown() = runTest {
        val players = NBAApiRepo().getPlayers(0, 35)

        assertEquals(NBAApi.HttpStatusCode.NoConnection, players.httpStatusCode())
    }
}