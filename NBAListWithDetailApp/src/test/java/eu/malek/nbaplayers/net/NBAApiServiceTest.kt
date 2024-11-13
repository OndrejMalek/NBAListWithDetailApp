package eu.malek.nbaplayers.net

import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertTrue


class NBAApiServiceTest {
    @Test
    fun getPlayersThrowsNoException() = runTest{
        assertTrue(NBAApiService().getPlayers(0,35).code() > 0)
    }
}