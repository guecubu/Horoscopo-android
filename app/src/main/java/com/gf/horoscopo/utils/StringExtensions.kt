package com.gf.horoscopo.utils

import java.text.Normalizer

fun String.normalize(): String {
    val normalized = Normalizer.normalize(this, Normalizer.Form.NFD)
    val regex = "\\p{InCombiningDiacriticalMarks}+".toRegex()
    return regex.replace(normalized, "")
}

fun String.search(query: String, ignoreCase: Boolean = true): Boolean {
    return this.normalize().contains(query.normalize(), ignoreCase)
}