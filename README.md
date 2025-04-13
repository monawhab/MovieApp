# Movie App 🎬

An Android app that displays movies using XML, Room, Retrofit, and Paging 3.

## Features
- View paginated movie lists from API
- Offline caching with Room
- Mark movies as favorites ❤️
- Single Source of Truth architecture
- MVI pattern

## Tech Stack
- XML
- Paging 3
- Retrofit & OkHttp
- Room Database
- Hilt for Dependency Injection
- Kotlin Coroutines & Flow

## 🏗️ Architecture Decision

The app follows **Clean Architecture** and **MVI (Model-View-Intent)** pattern, divided into three core layers:

- **Data Layer:** Handles local (Room) and remote (Retrofit) data sources. Uses `RemoteMediator` to fetch and store paged data.
- **Domain Layer:** Contains business logic and DTOs, serving as an abstraction between data and presentation layers.
- **Presentation Layer:** Built with **XML**, using unidirectional data flow. The `ViewModel` interacts with intents and emits view states.

This architecture promotes:
- **Testability**
- **Separation of concerns**
- **Scalability**
- **Reactivity with StateFlow and Paging**

## ⚖️ Trade-offs

    Single Source of Truth (SSOT): Remote API results are cached into the database using RemoteMediator. This may override favorite status on refresh. To preserve favorites, we perform a merge by checking local state before writing to DB.

    Favoriting logic: Favorites are stored in a separate Room table. When syncing new pages, we manually merge favorite flags based on local cache.

## ▶️ How to Run the Project

1. **Clone the Repository**
   ```bash
   git clone https://github.com/your-username/MovieApp.git
   cd MovieApp
2. **Open in Android Studio**

    Open the project in Android Studio Hedgehog or later.

3. **Run the App**

    Connect a device or start an emulator.