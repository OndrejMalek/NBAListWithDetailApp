package eu.malek.nbaplayers.players

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.paging.compose.collectAsLazyPagingItems
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import eu.malek.android.compose.checkViewModelStoreOwner
import eu.malek.android.compose.viewModelWithAppModule
import eu.malek.nbaplayers.AppModuleMock
import eu.malek.nbaplayers.net.data.Player
import eu.malek.nbaplayers.ui.theme.NBAListWithDetailAppTheme

@Preview(showBackground = true)
@Composable
fun PlayersScreenPreview() {
    NBAListWithDetailAppTheme {
        PlayersScreen(
            PlayersViewModel(
                AppModuleMock()
            )
        )
    }
}


@Composable
fun PlayersScreen(
    viewModel: PlayersViewModel = playersViewModel(),
    navController: NavHostController = rememberNavController()
) {
    val lazyPagingItems = viewModel.pager.flow.collectAsLazyPagingItems()

    LazyColumn {
        if (lazyPagingItems.loadState.refresh == LoadState.Loading) {
            item {
                Text(
                    text = "Waiting for items to load",
                    modifier =
                    Modifier
                        .fillMaxWidth()
                        .wrapContentWidth(Alignment.CenterHorizontally)
                )
            }
        }

        items(count = lazyPagingItems.itemCount) { index ->
            val item = lazyPagingItems[index]
            if (item != null) {
                PlayerItem(player = item, onClick = {})
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
                    //TODO
//                    .data(player.) // Replace with actual image URL
                    .crossfade(true)
                    .build(),
                contentDescription = "Player Image",
                modifier = Modifier.size(64.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(text = "${player.firstName} ${player.lastName}")
                Text(text = "Position: ${player.position}")
                Text(text = "Team: ${player.team?.fullName}") // Assuming team is nullable
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

