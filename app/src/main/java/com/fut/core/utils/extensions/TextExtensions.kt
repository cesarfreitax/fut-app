package com.fut.utils.extensions

import java.text.Normalizer

fun CharSequence.unaccent(): String {
    var string = this
    val out = CharArray(string.length)
    string = Normalizer.normalize(string, Normalizer.Form.NFD)
    var j = 0
    for (i in string.indices) {
        val c = string[i]
        if (c <= '\u007F') out[j++] = c
    }
    return String(out)
}