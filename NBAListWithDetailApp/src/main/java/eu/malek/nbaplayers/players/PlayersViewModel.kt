package eu.malek.nbaplayers.players


import androidx.lifecycle.ViewModel
import eu.malek.nbaplayers.AppModule


class PlayersViewModel(val appModule: AppModule) :
    ViewModel() {
    val playersPager = appModule.nbaApiRepo.playersPager
}



