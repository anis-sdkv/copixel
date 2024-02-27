package com.example.core.network

import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl

object InMemoryCookieJar : CookieJar {
    private val cookieStore: HashMap<HttpUrl, List<Cookie>> = hashMapOf();
    override fun loadForRequest(url: HttpUrl): List<Cookie> = cookieStore[url] ?: listOf()
    override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
        cookieStore[url] = cookies
    }
}