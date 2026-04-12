# Mutual Fund Exploration and Tracking Platform

A modern Android app built with **Kotlin and Jetpack Compose** for discovering mutual funds, viewing NAV performance, and managing custom watchlists.  
The app follows **MVVM architecture** and uses **Room** for offline caching and local persistence, so key data remains available even when the network is unavailable.

## Features

- Explore mutual funds across curated categories such as:
  - Index Funds
  - Bluechip Funds
  - Tax Saver (ELSS)
  - Large Cap Funds
- View fund details, including:
  - Fund name
  - Scheme type
  - Latest NAV
- Visualize NAV history with a Compose-native chart using **Vico**
- Search funds with debounced input
- Create and manage multiple watchlists
- Add or remove funds from watchlists
- Efficient pagination
- Offline caching with **Room**

## Tech Stack

- **Kotlin**
- **Jetpack Compose**
- **MVVM**
- **Room Database**
- **KSP**
- **Retrofit**
- **Paging**
- **Vico Chart Library**
- **Material 3**

### Requirements

- Android Studio latest stable
- JDK 11+
- Android Gradle Plugin 9
- Kotlin 2.3.20
- KSP 2.3.6
- Minimum SDK 26

### Build and Run

1. Clone the repository
2. Open the project in Android Studio
3. Sync Gradle
4. Run the app on an emulator or physical device

## API Used

This app uses the free **MFAPI** endpoints for mutual fund data:
- Search: `https://api.mfapi.in/mf/search?q={query}`
- Fund details and NAV history: `https://api.mfapi.in/mf/{scheme_code}`
- Get all funds with pagination support: `https://api.mfapi.in/mf`
- Fund details with latest NAV value and other details: `https://api.mfapi.in/mf/{scheme_code}/latest`

## Architecture

The app follows a clean MVVM structure:

- **UI layer**: Jetpack Compose screens and reusable components
- **ViewModel layer**: UI state handling and business logic
- **Data layer**: Repository pattern, remote API service, local Room storage
- **Persistence layer**: Room database for watchlists and cached fund data

## File Structure

```text
Mutual-Fund-Exploration-and-Tracking-Platform/
|── mutualfundexplorationandtrackingplatform/
│       ├── MainActivity.kt
│       ├── MutualFundApp.kt
│       ├── MutualFundApplication.kt
│       ├── data/
│       │   ├── local/
│       │   |   ├── MutualFundDatabase.kt
│       │   │   ├── dao/
│       │   │   |   ├── MutualFundDAO.kt
│       │   │   |   └── WatchListDao.kt
│       │   │   └── entity/
│       │   │        ├── MutualFundDetail.kt
│       │   │        ├── WatchList.kt
│       │   │        └── watchListDataEntity/
│       │   │             └── WatchListFundCrossRef.kt
│       │   ├── paging/
│       │   |   └── MutualFundPagingSource.kt
│       │   ├── remote/
│       │   |   ├── api/
│       │   │   |   └── MutualFundApiService.kt
│       │   │   ├── dto/
│       │   │   |   ├── MutualFundDTO.kt
│       │   │   |   ├── MutualFundResponseDTO.kt
│       │   │   |   └── NavDataResponse.kt
│       │   │    └── mapper/
│       │   │       └── Mappers.kt
│       │   └── repository/
│       │   |   ├── MutualFundRepository.kt
│       │   │   └── MutualFundRepositoryImpl.kt
│       ├── di/
│       │   └── AppContainer.kt
│       │── ui/
│       │   ├── components/
│       │   |   ├── AnalysisStatItem.kt
│       │   │   ├── ExploreScreenFunds.kt
│       │   │   ├── FundCard.kt
│       │   │   ├── ListScreenItem.kt
│       │   │   ├── NavLineChart.kt
│       │   │   ├── ShimmerBrush.kt
│       │   │   └── ViewAllButton.kt
│       │   ├── screens/
│       │   │   |── AnalysisScreen.kt
│       │   │   ├── CategoryListScreen.kt
│       │   │   ├── ExploreScreen.kt
│       │   │   ├── ListScreen.kt
│       │   │   ├── PortfolioScreen.kt
│       │   │   ├── SearchScreen.kt
│       │   │   └── WatchListScreen.kt
│       │   ├── utils/
│       │   │   |── CategoryUiState.kt
│       │   │   └── UiState.kt
│       │   │── viewmodels/
│       │   │   |── ExploreViewModel.kt
│       │   │   ├── SearchViewModel.kt
│       │   │   ├── ViewModelFactory.kt
│       │   │   └── WatchlistViewModel.kt
