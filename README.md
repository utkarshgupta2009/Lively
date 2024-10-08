# Live Score App

A Jetpack Compose application that displays live scores and upcoming matches for top football leagues using the [API-Football](https://www.api-football.com/) service.

## Features

- Live scores and upcoming matches for top 5 popular leagues:
  1. English Premier League
  2. Spanish La Liga
  3. German Bundesliga
  4. Italian Serie A
  5. French Ligue 1
- Capability to show live and upcoming matches worldwide (currently limited to top 5 leagues)
- Dark and light theme switching for user preference
- Manual refresh button to update match data on demand
- MVVM architecture for clean separation of concerns
- Dependency injection with Dagger Hilt
- API requests handled by Retrofit and OkHttp
- Asynchronous programming with Kotlin Coroutines
- Reactive programming using StateFlows for seamless UI updates
- Auto-refresh functionality (currently disabled due to API rate limits)


## Getting Started

To run this project, you'll need to:

1. Clone the repository
2. Open the project in Android Studio
3. Obtain an API key from [API-Football](https://www.api-football.com/)
4. Add your API key to the `local.properties` file:
   ```
   API_KEY=your_api_key_here
   ```
5. Build and run the application

## Dependencies

- Jetpack Compose
- Dagger Hilt
- Retrofit
- OkHttp
- Kotlin Coroutines
- StateFlow
- ViewModel

## Asynchronous Programming

This app leverages Kotlin Coroutines for efficient asynchronous programming. Coroutines are used to handle network requests and other long-running operations without blocking the main thread, ensuring a smooth user experience.

## Reactive UI Updates

StateFlows are utilized to implement reactive programming patterns. This allows for seamless UI updates based on data changes, providing a responsive and dynamic user interface.

## Worldwide Matches Support

While the app is currently configured to display live scores and upcoming matches for the top 5 leagues, it's built with the capability to show matches from leagues worldwide. The codebase can be easily modified to expand coverage to additional leagues or to show all available matches globally.

## Theme Switching

The app supports both dark and light themes. Users can switch between these themes based on their preference, enhancing readability and user experience in different lighting conditions.

## Manual Refresh

A refresh button is provided in the user interface, allowing users to manually update the match data on demand. This feature ensures that users can access the most up-to-date information whenever they need it.

## Note on Auto-Refresh

The app includes an auto-refresh functionality to keep scores up-to-date. However, due to the API's rate limit of 100 requests per day, this feature is currently disabled. To enable it, you'll need to modify the refresh interval or implement a more sophisticated caching strategy to reduce API calls.

## Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details.
