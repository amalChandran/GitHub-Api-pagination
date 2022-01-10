package com.amal.closedpr.github.api

import com.amal.closedpr.github.repository.model.PRModelItem
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query


interface GithubAPI {
    // A personal token valid for a few days with minimum access(just public profile)
    // This is need to overcome the ratelimiting on Github's side.
//    @Headers("Authorization: token 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")
    @GET("/repos/{user}/{repoName}/pulls")
    suspend fun getClosedPRs(
        @Path("user") userId: String,
        @Path("repoName") repoName: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int = 10,
        @Query("state") state: String = "closed",
    ): ArrayList<PRModelItem>
}