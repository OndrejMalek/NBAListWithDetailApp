package eu.malek.nbaplayers.players


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import eu.malek.nbaplayers.AppModule
import eu.malek.nbaplayers.net.data.Player
import kotlinx.coroutines.flow.MutableStateFlow


class PlayersViewModel(val appModule: AppModule) :
    ViewModel() {
    val playersPagerFlow = appModule.nbaApiRepo.playersPager.flow.cachedIn(viewModelScope)
    val selectedPlayer: MutableStateFlow<Player?> = MutableStateFlow(null)
}



