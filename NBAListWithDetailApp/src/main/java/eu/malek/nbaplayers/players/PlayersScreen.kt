package eu.malek.nbaplayers.players

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelStoreOwner
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.paging.LoadState
import androidx.paging.LoadStates
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import eu.malek.android.compose.checkViewModelStoreOwner
import eu.malek.android.compose.viewModelWithAppModule
import eu.malek.nbaplayers.AppModuleMock
import eu.malek.nbaplayers.Route
import eu.malek.nbaplayers.net.data.Player
import eu.malek.nbaplayers.net.data.mocks
import eu.malek.nbaplayers.ui.data.UiError
import eu.malek.nbaplayers.ui.data.UiErrorException
import eu.malek.nbaplayers.ui.preview.NBAAppPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

@Preview(showBackground = true)
@Composable
fun PlayersScreenPreview_Loading() {
    PlayersScreenPreview(LoadState.Loading)
}

@Preview(showBackground = true)
@Composable
fun PlayersScreenPreview_NotLoading() {
    PlayersScreenPreview(LoadState.NotLoading(false))
}

@Preview(showBackground = true)
@Composable
fun PlayersScreenPreview_Error() {
    PlayersScreenPreview(LoadState.Error(UiErrorException(UiError.ApiConfiguration)))
}

@Preview(showBackground = true)
@Composable
fun PlayersScreenPreview_ErrorNoInternet() {
    PlayersScreenPreview(LoadState.Error(UiErrorException(UiError.NoConnection)))
}


@Composable
private fun PlayersScreenPreview(loadState: LoadState) {
    NBAAppPreview {
        PagingPlayersList(
            viewModel = PlayersViewModel(AppModuleMock()),
            pagingPlayerFlow = MutableStateFlow(
                PagingData.from(
                    data = Player.mocks(20),
                    sourceLoadStates = notLoadingLoadStates().copy(
                        refresh = loadState
                    )
                )
            )
        )
    }
}

private fun notLoadingLoadStates() = LoadStates(
    refresh = LoadState.NotLoading(false),
    prepend = LoadState.NotLoading(false),
    append = LoadState.NotLoading(false)
)


@Composable
fun PlayersScreen(
    viewModel: PlayersViewModel = playersViewModel(),
    navController: NavHostController = rememberNavController()
) {
    PagingPlayersList(viewModel, viewModel.playersPagerFlow, navController)
}

@Composable
private fun PagingPlayersList(
    viewModel: PlayersViewModel,
    pagingPlayerFlow: Flow<PagingData<Player>>,
    navController: NavHostController = rememberNavController()
) {
    val lazyPagingItems = pagingPlayerFlow.collectAsLazyPagingItems()
    val refresh = lazyPagingItems.loadState.refresh
    if (refresh is LoadState.Error) {
        val errorException = refresh.error
        if (errorException is UiErrorException) {
            CenteredText(errorException.error.message)
        } else {
            throw IllegalStateException("Unknown error type", errorException)
        }
    } else if (refresh == LoadState.Loading) {
        CenteredText("Waiting for items to load")
    } else {
        LazyColumn(Modifier.fillMaxSize()) {
            items(
                count = lazyPagingItems.itemCount
            ) { index ->
                val item = lazyPagingItems[index]
                if (item != null) {
                    PlayerItem(
                        player = item,
                        onClick = {
                            viewModel.selectedPlayer.value = item
                            navController.navigate(Route.PlayerDetail)
                        })
                }
            }

            if (lazyPagingItems.loadState.append == LoadState.Loading) {
                item {
                    CircularProgressIndicator(
                        modifier =
                        Modifier
                            .fillMaxWidth()
                            .wrapContentWidth(Alignment.CenterHorizontally)
                    )
                }
            }
        }
    }
}


@Composable
private fun CenteredText(text: String) {
    Text(
        text = text,
        modifier =
        Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center)
    )
}

@Composable
fun PlayerItem(player: Player, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick() },
    ) {
        Row(modifier = Modifier.padding(16.dp)) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    //TODO API does not provide images
                    //.data(player.) // Replace with actual image URL
                    .crossfade(true)
                    .build(),
                contentDescription = "Player Image",
                modifier = Modifier.size(64.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(text = "${player.firstName} ${player.lastName}")
                Text(text = "Position: ${player.position}")
                Text(text = "Team: ${player.team?.fullName}")
            }
        }
    }
}

@Composable
fun playersViewModel(
    viewModelStoreOwner: ViewModelStoreOwner = checkViewModelStoreOwner(),
) =
    viewModelWithAppModule(viewModelStoreOwner = viewModelStoreOwner) { appModule, _ ->
        PlayersViewModel(
            appModule,
        )
    }

