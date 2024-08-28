package com.example.lively.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class HomeScreenViewmodel : ViewModel() {
    val isDarkThemeEnabled = mutableStateOf(value = false);
}