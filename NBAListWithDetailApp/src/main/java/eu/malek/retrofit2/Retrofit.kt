package eu.malek.retrofit2

import okhttp3.Interceptor
import okhttp3.Protocol
import okhttp3.ResponseBody.Companion.toResponseBody

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