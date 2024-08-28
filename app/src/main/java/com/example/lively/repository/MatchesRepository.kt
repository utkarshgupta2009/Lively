package com.example.lively.repository
import com.example.lively.data.remote.RapidApiServices
import com.example.lively.data.remote.models.FixtureResponse
import javax.inject.Inject

class MatchesRepository @Inject constructor(private val rapidApiServices: RapidApiServices){

   suspend fun getLiveMatches(leagueId: Int):
           List<FixtureResponse> = rapidApiServices.getLiveMatches(leagueId).response

   suspend fun getUpcomingMatches(date: String, leagueId:Int, season:Int):
           List<FixtureResponse> = rapidApiServices.getUpcomingMatches(date,leagueId,season).response
}