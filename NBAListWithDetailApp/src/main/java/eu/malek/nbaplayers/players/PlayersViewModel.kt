package eu.malek.nbaplayers.players


import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.PagingState
import eu.malek.nbaplayers.AppModule
import eu.malek.nbaplayers.net.data.Envelop
import eu.malek.nbaplayers.net.data.Player
import kotlinx.coroutines.launch
import retrofit2.Response

private const val PAGE_SIZE = 35

private const val TOO_MANY_REQUESTS = 429

class PlayersViewModel(val appModule: AppModule) :
    ViewModel() {

    val pager = Pager(
        PagingConfig(
            pageSize = PAGE_SIZE,
            enablePlaceholders = true,
            maxSize = PAGE_SIZE * 30
        )
    ) {
        object : PagingSource<Int, Player>() {
            override fun getRefreshKey(state: PagingState<Int, Player>): Int? {
                return state.anchorPosition?.let {
                    state.closestPageToPosition(it)?.prevKey?.plus(1)
                        ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
                }
            }

            override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Player> {
                val pageNumber = params.key ?: 0
                val response = appModule.nbaApi.getPlayers(cursor = pageNumber * PAGE_SIZE, perPage = PAGE_SIZE)

                val envelop = response.body()
                return when {
                    response.isSuccessful && envelop != null -> {
                        // Since 0 is the lowest page number, return null to signify no more pages should
                        // be loaded before it.
                        val prevKey = if (pageNumber > 0) pageNumber - 1 else null

                        // This API defines that it's out of data when a page returns empty. When out of
                        // data, we return `null` to signify no more pages should be loaded
                        val nextKey = if (envelop.data.size == PAGE_SIZE ) pageNumber + 1 else null

                        LoadResult.Page(data = envelop.data, prevKey = prevKey, nextKey = nextKey)
                    }
                    response.code() == TOO_MANY_REQUESTS -> LoadResult.Error(TooManyRequestsException(response))
                    else -> {
                        LoadResult.Error(ResponseException(response))
                    }
                }
            }
        }
    }

}

open class ResponseException(val response: Response<Envelop<Player>>) : Throwable()
class TooManyRequestsException(response: Response<Envelop<Player>>) : ResponseException(response)

