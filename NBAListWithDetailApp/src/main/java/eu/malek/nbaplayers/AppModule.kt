package eu.malek.nbaplayers

import eu.malek.nbaplayers.net.NBAApi
import eu.malek.nbaplayers.net.NBAApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

/**
 * Injection module with Application lifecycle scope
 */
open class AppModule(
    val nbaApi: NBAApi = NBAApiService(),
    /**
     * Scope with Global / Application lifecycle
     */
    val appScope: CoroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)
)


class AppModuleMock : AppModule() {

}