package com.alikazi.codetest.weatherzone.utils

object Constants {

    const val LOG_TAG = "WEATHERZONE"

    const val HEADER_KEY_AUTHORIZATION = "Authorization"
    const val PEXELS_API_KEY = "563492ad6f91700001000001e0aa154e93164874bb37306f18397920"

    private const val URL_BASE = "https://api.pexels.com/v1/"
    const val URL_SEARCH = URL_BASE + "search/"
    const val PARAM_QUERY = "query"
    const val PARAM_PER_PAGE = "per_page"
    const val PARAM_PAGE = "page"
    const val PARAM_VALUE_PER_PAGE = "21"
    const val PARAM_VALUE_PAGE = "1"
}