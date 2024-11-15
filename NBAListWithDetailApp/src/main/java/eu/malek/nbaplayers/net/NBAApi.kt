package eu.malek.nbaplayers.net

import eu.malek.nbaplayers.BuildConfig
import eu.malek.nbaplayers.net.NBAApi.HttpStatusCode
import eu.malek.nbaplayers.net.data.Envelop
import eu.malek.nbaplayers.net.data.Player
import eu.malek.retrofit2.catchAllExceptionsInterceptor
import eu.malek.retrofit2.forceCacheAllResponsesInterceptor
import kotlinx.serialization.json.Json
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.io.File
import java.math.BigDecimal
import kotlin.time.Duration.Companion.hours

fun <T> Response<T>.httpStatusCode(): HttpStatusCode {
    return HttpStatusCode.fromCode(this.code())
}


/**
 * See [docs](https://docs.balldontlie.io/)
 */
interface NBAApi {

    /**
     * based on [docs - errors](https://docs.balldontlie.io/#errors)
     */
    enum class HttpStatusCode(val code: Int, val message: String) {
        NoConnection(1000, "No connection"),

        Unauthorized(
            401,
            "You either need an API key or your account tier does not have access to the endpoint."
        ),

        Unknown(0, "Unknown response error code"),
        BadRequest(400, "The request is invalid. The request parameters are probably incorrect."),
        NotFound(404, "The specified resource could not be found."),
        NotAcceptable(406, "You requested a format that isn't json."),

        TooManyRequests(429, "You're rate limited."),
        InternalServerError(500, "We had a problem with our server. Try again later."),
        ServiceUnavailable(
            503,
            "We're temporarily offline for maintenance. Please try again later."
        ), ;

        companion object {
            fun fromCode(code: Int): HttpStatusCode {
                return entries.find { it.code == code } ?: Unknown
            }
        }
    }

    enum class AccountTier(val requestsPerMin: Int, val priceUSDPerMonth: BigDecimal) {
        GOAT(6000, BigDecimal(39.99)),
        AllStar(600, BigDecimal(9.99)),
        Free(30, BigDecimal(0)),
    }

    companion object {
        const val BASE_URL = "https://api.balldontlie.io/v1/"
        const val API_KEY = "84769aec-bde3-42e2-890c-7e11b35d83c3" //TODO move out of the repo

        val json = Json {
            prettyPrint = true
            isLenient = true
            ignoreUnknownKeys = true
        }

        fun createService(cacheDir: File?): NBAApi {
            val retrofit = Retrofit.Builder()
                .client(createHttpClient(cacheDir))
                .baseUrl(BASE_URL)
                .addConverterFactory(
                    json.asConverterFactory(
                        "application/json; charset=UTF8".toMediaType()
                    )
                )
                .build()
            return retrofit.create(NBAApi::class.java)
        }

        fun createHttpClient(cacheDir: File? = null): OkHttpClient {
            return OkHttpClient.Builder()
                .addInterceptor(catchAllExceptionsInterceptor(HttpStatusCode.NoConnection.code))
                .addInterceptor(addAuthorizationHeader())
                .apply {
                    if (BuildConfig.DEBUG) {
                        addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                    }



                    cacheDir?.let {
                        cache(Cache(it, 10 * 1024 * 1024L))
                        forceCacheAllResponsesInterceptor(4.hours)
                    }
                }
                .build()
        }


        private fun addAuthorizationHeader() = { chain: Interceptor.Chain ->
            chain.proceed(
                chain.request().newBuilder().header("Authorization", API_KEY).build()
            )
        }

    }

    @GET("players")
    suspend fun getPlayers(
        @Query("cursor") cursor: Int? = null,
        @Query("per_page") perPage: Int? = null,
        @Query("search") search: String? = null,
        @Query("first_name") firstName: String? = null,
        @Query("last_name") lastName: String? = null,
        @Query("team_ids[]") teamIds: List<Int>? = null,
        @Query("player_ids[]") playerIds: List<Int>? = null
    ): Response<Envelop<Player>>

}



