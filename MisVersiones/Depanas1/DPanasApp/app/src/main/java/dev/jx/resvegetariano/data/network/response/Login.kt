package dev.jx.resvegetariano.data.network.response

class Login<T>(val accessToken: String, val refreshToken: String, val data: T)