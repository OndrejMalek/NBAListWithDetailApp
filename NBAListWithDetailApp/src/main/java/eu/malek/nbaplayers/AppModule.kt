package eu.malek.nbaplayers

import eu.malek.nbaplayers.net.NBAApiRepo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import java.io.File

/**
 * Injection module with Application lifecycle scope
 */
open class AppModule(
    cacheDir: File? = null,
    val nbaApiRepo: NBAApiRepo = NBAApiRepo(cacheDir),
    /**
     * Scope with Global / Application lifecycle
     */
    val appScope: CoroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)
)


class AppModuleMock : AppModule() {

}