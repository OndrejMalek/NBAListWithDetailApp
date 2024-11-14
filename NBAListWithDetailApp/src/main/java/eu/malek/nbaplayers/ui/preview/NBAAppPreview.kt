package eu.malek.nbaplayers.ui.preview

import androidx.compose.runtime.Composable
import eu.malek.nbaplayers.ui.theme.NBAListWithDetailAppTheme

@Composable
fun NBAAppPreview(content: @Composable () -> Unit) {
    NBAListWithDetailAppTheme(content = content)
}