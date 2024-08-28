package com.example.lively.viewmodels.states

import com.example.lively.data.remote.models.FixtureResponse

sealed class ResponseStates {

    data object Empty: ResponseStates();
    data object Loading: ResponseStates();
    class  Success(val fixtureResponse: List<FixtureResponse>):ResponseStates()
    class Error(val message: String):ResponseStates()
}