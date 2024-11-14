package eu.malek.nbaplayers.playerdetail

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.tooling.preview.Preview
import eu.malek.nbaplayers.players.PlayerItem
import eu.malek.nbaplayers.players.PlayersViewModel
import eu.malek.nbaplayers.players.playersViewModel
import eu.malek.nbaplayers.ui.preview.NBAAppPreview

@Preview(showBackground = true)
@Composable
fun PlayerDetailScreenPreview() {
    NBAAppPreview {
        PlayerDetailScreen(playersViewModel())
    }
}

@Composable
fun PlayerDetailScreen(
    viewModel: PlayersViewModel
) {
    val player = viewModel.selectedPlayer.collectAsState().value
    if (player != null) {
        PlayerItem(player) { }
    }
}