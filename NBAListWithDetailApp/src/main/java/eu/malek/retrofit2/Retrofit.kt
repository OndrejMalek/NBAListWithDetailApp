package eu.malek.retrofit2

import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Protocol
import okhttp3.ResponseBody.Companion.toResponseBody
import java.util.concurrent.TimeUnit
import kotlin.time.Duration


fun catchAllExceptionsInterceptor(caughtResponseCode: Int) = { chain: Interceptor.Chain ->
    try {
        chain.proceed(chain.request())
    } catch (e: Throwable) {
        val message = e.toString()
        okhttp3.Response
            .Builder()
            .protocol(Protocol.HTTP_1_1)
            .request(chain.request())
            .code(caughtResponseCode)
            .body(message.toResponseBody(null))
            .message(message)
            .build()
    }
}

fun OkHttpClient.Builder.forceCacheAllResponsesInterceptor(cacheDuration: Duration) {
    this.addInterceptor { chain ->
        chain.proceed(
            chain.request().newBuilder().header(
                "Cache-Control",
                CacheControl.Builder()
                    .maxAge(cacheDuration.inWholeSeconds.toInt(), TimeUnit.SECONDS)
                    .build().toString()
            ).build()
        )
    }
}