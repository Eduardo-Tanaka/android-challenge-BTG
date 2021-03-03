package br.com.eduardotanaka.btgchallenge.network

import br.com.eduardotanaka.btgchallenge.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response
import timber.log.Timber

class HttpRequestInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val url = chain.request()
            .url
            .newBuilder()
            .addQueryParameter("api_key", BuildConfig.TMDBKKey)
            .addQueryParameter("language", "pt-br")
            .build()

        val request = chain.request()
            .newBuilder()
            .url(url)
            .build()

        Timber.d(request.toString())

        return chain.proceed(request)
    }
}
