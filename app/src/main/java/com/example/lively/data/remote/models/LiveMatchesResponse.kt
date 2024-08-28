package com.example.lively.data.remote.models

data class LiveMatchesResponse(

val get: String,
val parameters: Parameters,
val errors: List<Any>,
val results: Int,
val paging: Paging,
val response: List<FixtureResponse>

)
data class Parameters(
    val live: String
)

data class Paging(
    val current: Int,
    val total: Int
)

data class FixtureResponse(
    val fixture: Fixture,
    val league: League,
    val teams: Teams,
    val goals: Goals,
    val score: Score,
    val events: List<Event>
)

data class Fixture(
    val id: Int,
    val referee: String?,
    val timezone: String,
    val date: String,
    val timestamp: Long,
    val periods: Periods,
    val venue: Venue,
    val status: Status
)

data class Periods(
    val first: Long?,
    val second: Long?
)

data class Venue(
    val id: Int,
    val name: String,
    val city: String
)

data class Status(
    val long: String,
    val short: String,
    val elapsed: Int
)

data class League(
    val id: Int,
    val name: String,
    val country: String,
    val logo: String,
    val flag: String,
    val season: Int,
    val round: String
)

data class Teams(
    val home: Team,
    val away: Team
)

data class Team(
    val id: Int,
    val name: String,
    val logo: String,
    val winner: Boolean?
)

data class Goals(
    val home: Int,
    val away: Int
)

data class Score(
    val halftime: HalfScore,
    val fullTime: HalfScore,
    val extraTime: HalfScore,
    val penalty: HalfScore
)

data class HalfScore(
    val home: Int?,
    val away: Int?
)

data class Event(
    val time: Time,
    val team: Team,
    val player: Player,
    val assist: Assist,
    val type: String,
    val detail: String,
    val comments: String?
)

data class Time(
    val elapsed: Int,
    val extra: Int?
)

data class Player(
    val id: Int,
    val name: String
)

data class Assist(
    val id: Int?,
    val name: String?
)

