package eu.malek.nbaplayers.players

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelStoreOwner
import eu.malek.android.compose.checkViewModelStoreOwner
import eu.malek.android.compose.viewModelWithAppModule
import eu.malek.nbaplayers.ui.theme.NBAListWithDetailAppTheme

@Composable
fun PlayersScreen(viewModel: PlayersViewModel = playersViewModel()) {
    Text("Android")
}

@Preview(showBackground = true)
@Composable
fun PlayersScreenPreview() {
    NBAListWithDetailAppTheme {
        PlayersScreen(playersViewModel())
    }
}

@Composable
fun playersViewModel(
    viewModelStoreOwner: ViewModelStoreOwner = checkViewModelStoreOwner(),
) =
    viewModelWithAppModule(viewModelStoreOwner = viewModelStoreOwner) { appModule, savedStateHandle ->
        PlayersViewModel(
            appModule,
            savedStateHandle
        )
    }

