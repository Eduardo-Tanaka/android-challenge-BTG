package br.com.eduardotanaka.btgchallenge.constants

enum class CacheKey {

    CACHE_NAME;

    override fun toString(): String {
        return name
    }
}