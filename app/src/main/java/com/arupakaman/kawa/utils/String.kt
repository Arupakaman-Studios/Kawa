package com.arupakaman.kawa.utils

fun String.toWildcardQuery() = String.format("*%s*", this)

fun String.sanitizeSearchQuery(): String {
    if (this.isEmpty()) {
        return "";
    }
    val queryWithEscapedQuotes = replace(Regex.fromLiteral("\""), "\"\"")
    return "*\"$queryWithEscapedQuotes\"*"
}