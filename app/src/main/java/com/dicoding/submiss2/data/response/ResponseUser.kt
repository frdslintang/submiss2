package com.dicoding.submiss2.data.response

import com.google.gson.annotations.SerializedName

data class ResponseUser(
    @SerializedName("total_count")
    val totalCount: Int = 0,

    @SerializedName("incomplete_results")
    val incompleteResults: Boolean = false,

    @SerializedName("items")
    val items: MutableList<Item>? = null
) {
    data class Item(
        @SerializedName("gists_url")
        val gistsUrl: String = "",

        @SerializedName("repos_url")
        val reposUrl: String = "",

        @SerializedName("following_url")
        val followingUrl: String = "",

        @SerializedName("starred_url")
        val starredUrl: String = "",

        @field:SerializedName("login")
        val login: String,

        @SerializedName("followers_url")
        val followersUrl: String = "",

        @SerializedName("type")
        val type: String = "",

        @SerializedName("url")
        val url: String = "",

        @SerializedName("subscriptions_url")
        val subscriptionsUrl: String = "",

        @SerializedName("score")
        val score: Int = 0,

        @SerializedName("received_events_url")
        val receivedEventsUrl: String = "",

        @field:SerializedName("avatar_url")
        val avatarUrl: String,

        @SerializedName("events_url")
        val eventsUrl: String = "",

        @field:SerializedName("html_url")
        val  htmlUrl: String,

        @SerializedName("site_admin")
        val siteAdmin: Boolean = false,

        @field:SerializedName("id")
        val id: Int,

        @SerializedName("gravatar_id")
        val gravatarId: String = "",

        @SerializedName("node_id")
        val nodeId: String = "",

        @SerializedName("organizations_url")
        val organizationsUrl: String = ""
    )
}
