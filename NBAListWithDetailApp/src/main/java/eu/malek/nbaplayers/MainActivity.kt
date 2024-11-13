package eu.malek.nbaplayers

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import eu.malek.nbaplayers.players.PlayersScreen
import eu.malek.nbaplayers.ui.theme.NBAListWithDetailAppTheme

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
    NavHost(rememberNavController(), startDestination = Route.Players) {
        composable<Route.Players> {
            PlayersScreen()
        }
    }
}

object Route {
    object Players
}
