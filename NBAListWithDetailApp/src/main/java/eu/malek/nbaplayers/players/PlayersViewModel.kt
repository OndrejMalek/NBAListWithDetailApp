package eu.malek.nbaplayers.players


import androidx.lifecycle.ViewModel
import eu.malek.nbaplayers.AppModule
import eu.malek.nbaplayers.net.data.Player
import kotlinx.coroutines.flow.MutableStateFlow


class PlayersViewModel(val appModule: AppModule) :
    ViewModel() {
    val playersPager = appModule.nbaApiRepo.playersPager
    val selectedPlayer: MutableStateFlow<Player?> = MutableStateFlow(null)
}



