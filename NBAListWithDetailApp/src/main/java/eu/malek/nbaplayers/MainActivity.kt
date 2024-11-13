package eu.malek.nbaplayers

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import eu.malek.nbaplayers.players.PlayersScreen
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
    NavHost(navController, startDestination = Route.Players) {
        composable<Route.Players> {
            PlayersScreen(navController = navController)
        }
    }
}

object Route {
    @Serializable
    data class PlayerDetail(val id: Int)
    @Serializable
    object Players
}
