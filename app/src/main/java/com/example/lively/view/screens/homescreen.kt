package com.example.lively.view.screens

import UpcomingMatchItem
import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.lively.R
import com.example.lively.data.remote.models.FixtureResponse
import com.example.lively.ui.theme.LivelyTheme
import com.example.lively.view.widgets.LiveMatchItem
import com.example.lively.viewmodels.HomeScreenViewmodel
import com.example.lively.viewmodels.MatchesViewModel
import com.example.lively.viewmodels.states.ResponseStates

@SuppressLint("UnrememberedMutableState")
@Composable
fun HomeScreen(matchesViewModel: MatchesViewModel = viewModel(), homeScreenViewmodel: HomeScreenViewmodel = viewModel()) {


    LivelyTheme(darkTheme = homeScreenViewmodel.isDarkThemeEnabled.value) {
        Scaffold(
            Modifier.background(MaterialTheme.colorScheme.background),
            topBar = {
                TopAppBar(onRefresh = {
                    matchesViewModel.getLiveMatches()
                    matchesViewModel.getUpcomingMatches()
                },
                    setTheme = {
                        homeScreenViewmodel.isDarkThemeEnabled.value = !homeScreenViewmodel.isDarkThemeEnabled.value
                    })
            },
            content = { paddingValues ->
                Column(
                    verticalArrangement = Arrangement.Top
                ) {
                    FetchLiveMatches(modifier = Modifier.padding(paddingValues))

                    FetchUpcomingMatches()
                }

            }
        )
    }
}

@Composable
fun TopAppBar(onRefresh:()->Unit, setTheme:()->Unit){
    Row (
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = CenterVertically
    ){
        IconButton(onClick = onRefresh) {
            Icon(imageVector = Icons.Default.Refresh, contentDescription = "Refresh Icon")
        }
        Text(text = "Lively", style = MaterialTheme.typography.headlineMedium)
        IconButton(onClick = setTheme) {
            Icon(imageVector = ImageVector.vectorResource(id = R.drawable.modeicon), contentDescription = "Mode Icon")
        }

    }
}

@Composable
fun FetchLiveMatches(
    modifier: Modifier = Modifier,
    matchesViewModel: MatchesViewModel = viewModel()
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = "Popular Live Matches",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(top = 12.dp)
        )
        when (val state = matchesViewModel.liveMatchesStates.collectAsState().value) {
            is ResponseStates.Empty -> Text(text = "No Data to show")
            is ResponseStates.Loading -> Text(text = "Loading")
            is ResponseStates.Success -> LiveMatches(liveMatches = state.fixtureResponse)
            is ResponseStates.Error -> Text(text = state.message)
        }
    }
}

@Composable
fun FetchUpcomingMatches(
    modifier: Modifier = Modifier,
    matchesViewModel: MatchesViewModel = viewModel()
) {
    Column(modifier = modifier) {
        Text(
            text = "Popular Upcoming Matches",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(top = 12.dp)
        )
        when (val state = matchesViewModel.upcomingMatchesStates.collectAsState().value) {
            is ResponseStates.Empty -> Text(text = "No Data to show")
            is ResponseStates.Loading -> Text(text = "Loading")
            is ResponseStates.Success -> UpcomingMatches(upcomingMatches = state.fixtureResponse)
            is ResponseStates.Error -> Text(text = state.message)
        }
    }
}



@Composable
fun LiveMatches(liveMatches: List<FixtureResponse>) {

    Column(modifier = Modifier
        .padding(horizontal = 15.dp, vertical = 10.dp)
        .fillMaxWidth()) {

        if (liveMatches.isEmpty()) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = "No Live Matches Currently"
                )
                Text(
                    text = "No Live Matches Currently",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        } else {

            LazyRow(
                modifier = Modifier.padding(top = 15.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(liveMatches.size) {
                    LiveMatchItem(fixture = liveMatches[it])
                }
            }
        }
    }
}

@Composable
fun UpcomingMatches(upcomingMatches: List<FixtureResponse>) {

    Column(modifier = Modifier
        .padding(horizontal = 15.dp, vertical = 10.dp)
        .fillMaxSize()) {


        if (upcomingMatches.isEmpty()) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = "No Upcoming Matches Currently"
                )
                Text(
                    text = "No Upcoming Matches Currently",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        } else {

            LazyColumn(
                modifier = Modifier.padding(top = 15.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(upcomingMatches.size) {
                    UpcomingMatchItem(fixture = upcomingMatches[it])
                }
            }
        }
    }
}