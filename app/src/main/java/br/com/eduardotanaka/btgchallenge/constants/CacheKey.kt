package br.com.eduardotanaka.btgchallenge.constants

enum class CacheKey {

    FILME_POPULAR,
    FILME_GENERO;

    override fun toString(): String {
        return name
    }
}
