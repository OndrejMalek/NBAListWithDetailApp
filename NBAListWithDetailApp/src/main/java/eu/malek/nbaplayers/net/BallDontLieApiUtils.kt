package eu.malek.nbaplayers.net

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.PagingState
import eu.malek.nbaplayers.net.NBAApi.HttpStatusCode
import eu.malek.nbaplayers.net.data.Envelop
import eu.malek.nbaplayers.ui.data.UiError
import eu.malek.nbaplayers.ui.data.UiErrorException
import retrofit2.Response

private const val PAGE_SIZE = 35


/**
 * [see docs](https://docs.balldontlie.io/#pagination)
 */
fun <T : Any> ballDontLieApiPager(
    apiCall: suspend (cursor: Int, perPage: Int) -> Response<Envelop<T>>,
    pageSize: Int = PAGE_SIZE
): Pager<Int, T> = Pager(
    PagingConfig(
        pageSize = pageSize,
        enablePlaceholders = true,
        maxSize = pageSize * 30
    )
) {
    object : PagingSource<Int, T>() {
        override fun getRefreshKey(state: PagingState<Int, T>): Int? {
            return state.anchorPosition?.let {
                state.closestPageToPosition(it)?.prevKey?.plus(1)
                    ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
            }
        }

        override suspend fun load(params: LoadParams<Int>): LoadResult<Int, T> {
            val pageNumber = params.key ?: 0
            val response = apiCall(pageNumber * pageSize, pageSize)
            val envelop = response.body()
            return when {
                response.isSuccessful && envelop != null -> {
                    // Since 0 is the lowest page number, return null to signify no more pages should
                    // be loaded before it.
                    val prevKey = if (pageNumber > 0) pageNumber - 1 else null

                    // This API defines that it's out of data when a page returns empty. When out of
                    // data, we return `null` to signify no more pages should be loaded
                    val nextKey = if (envelop.data.size == pageSize) pageNumber + 1 else null

                    LoadResult.Page(data = envelop.data, prevKey = prevKey, nextKey = nextKey)
                }

                else -> {
                    LoadResult.Error(UiErrorException(response.httpStatusCode().toUiError()))
                }
            }
        }
    }
}

fun HttpStatusCode.toUiError(): UiError {
    return when (this) {
        HttpStatusCode.NoConnection -> UiError.NoConnection
        HttpStatusCode.Unauthorized -> UiError.Unauthorized

        HttpStatusCode.Unknown,
        HttpStatusCode.BadRequest,
        HttpStatusCode.NotFound,
        HttpStatusCode.NotAcceptable -> UiError.ApiConfiguration

        HttpStatusCode.TooManyRequests -> UiError.TooManyRequests
        HttpStatusCode.InternalServerError -> UiError.InternalServerError
        HttpStatusCode.ServiceUnavailable -> UiError.ServiceUnavailable
    }
}