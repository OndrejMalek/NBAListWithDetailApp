package eu.malek.android.compose

import androidx.compose.runtime.Composable
import androidx.lifecycle.HasDefaultViewModelProviderFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import eu.malek.nbaplayers.App
import eu.malek.nbaplayers.AppModule

/**
 * Viewmodel factory that injects [AppModule], adapter for [viewModel]
 */
@Composable
inline fun <reified VM : ViewModel> viewModelWithAppModule(
    viewModelStoreOwner: ViewModelStoreOwner = checkNotNull(LocalViewModelStoreOwner.current) {
        "No ViewModelStoreOwner was provided via LocalViewModelStoreOwner"
    },
    key: String? = null,
    extras: CreationExtras = if (viewModelStoreOwner is HasDefaultViewModelProviderFactory) {
        viewModelStoreOwner.defaultViewModelCreationExtras
    } else {
        CreationExtras.Empty
    },
    crossinline vmFactory: (AppModule, SavedStateHandle) -> VM
) =
    viewModel<VM>(
        viewModelStoreOwner = viewModelStoreOwner,
        key = key,
        extras = extras,
        factory = viewModelFactory {
            initializer {
                val app =
                    checkNotNull(this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]) as App
                val savedStateHandle = this.createSavedStateHandle()
                vmFactory(app.appModule, savedStateHandle)
            }
        }
    )


@Composable
fun checkViewModelStoreOwner() = checkNotNull(
    LocalViewModelStoreOwner.current
) {
    "No ViewModelStoreOwner was provided via LocalViewModelStoreOwner"
}