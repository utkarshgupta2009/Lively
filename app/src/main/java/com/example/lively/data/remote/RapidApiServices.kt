package com.example.lively.data.remote

import com.example.lively.data.remote.models.LiveMatchesResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface RapidApiServices {

    @GET(ApiEndpoints.LIVE_MATCHES)
    suspend fun getLiveMatches(
        @Query("league") leagueId:Int
    ): LiveMatchesResponse

    @GET(ApiEndpoints.UPCOMING_MATCHES)
    suspend fun getUpcomingMatches(
        @Query("date") date: String,
        @Query("league") leagueId: Int,
        @Query("season") season: Int
    ): LiveMatchesResponse
}