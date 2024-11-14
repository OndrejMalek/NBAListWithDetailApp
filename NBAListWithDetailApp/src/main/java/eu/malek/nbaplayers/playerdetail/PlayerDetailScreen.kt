package eu.malek.nbaplayers.playerdetail

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import eu.malek.nbaplayers.players.PlayerItem
import eu.malek.nbaplayers.players.PlayersViewModel
import eu.malek.nbaplayers.players.playersViewModel
import eu.malek.nbaplayers.ui.preview.NBAAppPreview

@Preview(showBackground = true)
@Composable
fun PlayerDetailScreenPreview() {
    NBAAppPreview {
        PlayerDetailScreen(viewModel = playersViewModel(viewModelStoreOwner = parentEntry))
    }
}

@Composable
fun PlayerDetailScreen(
    navController: NavHostController = rememberNavController(),
    viewModel: PlayersViewModel
) {
    PlayerItem() { }
}