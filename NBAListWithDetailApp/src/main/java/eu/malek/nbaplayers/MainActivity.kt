package eu.malek.nbaplayers

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import eu.malek.nbaplayers.playerdetail.PlayerDetailScreen
import eu.malek.nbaplayers.players.PlayersScreen
import eu.malek.nbaplayers.players.playersViewModel
import eu.malek.nbaplayers.ui.theme.NBAListWithDetailAppTheme
import kotlinx.serialization.Serializable

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NBAListWithDetailAppTheme {
                MainNavHost()
            }
        }
    }
}

@Composable
fun MainNavHost() {
    val navController = rememberNavController()
    NavHost(navController, startDestination = Route.PlayersRoot) {
        navigation<Route.PlayersRoot>(startDestination = Route.Players) {
            composable<Route.Players> { backStackEntry ->
                val parentEntry = remember(backStackEntry) {
                    navController.getBackStackEntry(Route.PlayersRoot)
                }

                PlayersScreen(
                    navController = navController,
                    viewModel = playersViewModel(viewModelStoreOwner = parentEntry)
                )
            }
            composable<Route.PlayerDetail> { backStackEntry ->
                val parentEntry = remember(backStackEntry) {
                    navController.getBackStackEntry(Route.PlayersRoot)
                }

                PlayerDetailScreen(
                    viewModel = playersViewModel(viewModelStoreOwner = parentEntry)
                )
            }
        }
    }
}

object Route {
    @Serializable
    object PlayersRoot

    @Serializable
    object Players

    @Serializable
    object PlayerDetail
}
