package com.example.lively.viewmodels

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lively.data.remote.models.FixtureResponse
import com.example.lively.repository.MatchesRepository
import com.example.lively.viewmodels.states.ResponseStates
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okio.IOException
import retrofit2.HttpException
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class MatchesViewModel @Inject constructor(private val matchesRepository: MatchesRepository): ViewModel() {

    private val _liveMatchesState = MutableStateFlow<ResponseStates>(ResponseStates.Empty)
     val liveMatchesStates: StateFlow<ResponseStates> = _liveMatchesState

    private val _upcomingMatchesState = MutableStateFlow<ResponseStates>(ResponseStates.Empty)
    val upcomingMatchesStates: StateFlow<ResponseStates> = _upcomingMatchesState

    @SuppressLint("NewApi")
    private fun getCurrentDate():String{
        val currentDate = LocalDate.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val formattedDate = currentDate.format(formatter)
        return formattedDate
    }

    //Got the league ids from the api and made a seperate list
    private val popularLeagueIds = listOf(

        39,  // English Premier League
        140, // Spanish La Liga
        78,  // German Bundesliga
        135, // Italian Serie A
        61,  // French Ligue 1

    )
    private val season = 2024


    init {

        getLiveMatches()
        getUpcomingMatches()

    }
    //use this function to autoRefresh live scores(currently not using due to api rate limit)
   private fun autoRefresh(){
        viewModelScope.launch {
            while (true){
                getLiveMatches()
                delay(5000)
            }
        }
    }

     fun getLiveMatches(){
                _liveMatchesState.value = ResponseStates.Loading
                viewModelScope.launch(Dispatchers.IO) {
                    try {
                        val allMatches = mutableListOf<FixtureResponse>()
                        popularLeagueIds.chunked(3).forEach { leagueBatch ->
                            val batchResults = leagueBatch.map { leagueId ->
                                async {
                                    try {
                                        matchesRepository.getLiveMatches(leagueId = leagueId)
                                    } catch (e: Exception) {
                                        // Log the error and return an empty list
                                        println("Error fetching league $leagueId: ${e.message}")
                                        emptyList()
                                    }
                                }
                            }
                            allMatches.addAll(batchResults.awaitAll().flatten())
                            delay(1000)
                        }

                            _liveMatchesState.value = ResponseStates.Success(allMatches)

                    } catch (exception: HttpException) {
                        _liveMatchesState.value = ResponseStates.Error("NO Internet")
                    } catch (exception: IOException) {
                        _liveMatchesState.value = ResponseStates.Error("Something went wrong")
                    }
                }
     }


     fun getUpcomingMatches(){
        _upcomingMatchesState.value = ResponseStates.Loading
        viewModelScope.launch(Dispatchers.IO){
            try {
                val allMatches = mutableListOf<FixtureResponse>()
                popularLeagueIds.chunked(3).forEach { leagueBatch ->
                    val batchResults = leagueBatch.map { leagueId ->
                        async {
                            try {
                                matchesRepository.getUpcomingMatches(getCurrentDate(), leagueId, season)
                            } catch (e: Exception) {
                                // Log the error and return an empty list
                                println("Error fetching league $leagueId: ${e.message}")
                                emptyList()
                            }
                        }
                    }
                    allMatches.addAll(batchResults.awaitAll().flatten())
                    delay(1000)
                }
                    _upcomingMatchesState.value = ResponseStates.Success(allMatches)

            }
            catch (exception: HttpException){
                _upcomingMatchesState.value = ResponseStates.Error("NO Internet")
            }
            catch (exception: IOException){
                _upcomingMatchesState.value = ResponseStates.Error("Something went wrong")
            }
        }
    }
}