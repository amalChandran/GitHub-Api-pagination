package com.amal.closedpr.github.repository.model

import com.google.gson.annotations.SerializedName

data class PRModelItem(
    @SerializedName("closed_at")
    var closedAt: String,
    @SerializedName("created_at")
    var createdAt: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("user")
    val user: User
)

data class User(
    @SerializedName("avatar_url")
    val avatar: String,
    @SerializedName("login")
    val name: String,
)