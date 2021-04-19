@file:Suppress("unused")

package com.arupakaman.kawa.utils

import androidx.core.text.HtmlCompat

fun String.toWildcardQuery() = String.format("*%s*", this)

fun String.sanitizeSearchQuery(): String {
    if (this.isEmpty()) {
        return "";
    }
    val queryWithEscapedQuotes = replace(Regex.fromLiteral("\""), "\"\"")
    return "*\"$queryWithEscapedQuotes\"*"
}

fun String.toPlainText() = HtmlCompat.fromHtml(this, HtmlCompat.FROM_HTML_MODE_LEGACY).toString()
