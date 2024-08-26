# Stocks App

## Overview

This is a simple stocks app demonstrating modern Android development best practices. It features a state-based architecture (MVVM), clean Kotlin code with coroutines, and an intuitive UI. The app fetches stock data from a remote API, handles caching, and presents the data to the user in a clean and organized format.

## Architecture and Design Decisions

### State-Based Architecture (MVVM)
- **Why:** The MVVM pattern was chosen for its ability to decouple UI logic from business logic. The `ViewModel` serves as the single source of truth for UI state, which helps manage complex data flows and ensures the UI remains reactive to data changes.
- **Trade-offs:**
    - While it makes managing complex UI states easier, it requires more upfront boilerplate, especially for small projects.
    - Additional complexity might be unnecessary for simple apps, but it provides a scalable and testable architecture for this project.

### Coroutines for Asynchronous Operations
- **Why:** Kotlin coroutines provide a clean and efficient way to handle asynchronous operations like network calls. They integrate smoothly with Retrofit and offer structured concurrency, which reduces the risk of memory leaks.
- **Trade-offs:**
    - Coroutines are lightweight and easy to manage, but they might not be as familiar to developers who are used to callbacks or RxJava.
    - They provide less fine-grained control over threading compared to lower-level options like ExecutorService.

### Retrofit for Networking
- **Why:** Retrofit is a mature and highly reliable networking library. It allows seamless integration with coroutines and simplifies API interaction with its built-in serialization capabilities.
- **Trade-offs:**
    - Its simplicity can become limiting for more complex API interactions like WebSockets or advanced request customization.

### Repository Pattern
- **Why:** The repository pattern abstracts data sources, enabling easy testing and swapping of data sources (e.g., from remote API to local cache). It keeps the `ViewModel` clean and focused on UI logic.
- **Trade-offs:**
    - Introduces extra layers that might seem overkill for small applications. However, it’s beneficial for maintaining scalability and separation of concerns.

### Error Handling
- **Why:** Centralized error handling ensures consistent behavior across the app and reduces duplicated code. By using a sealed class for states, we account for all possible outcomes (success, error, loading).
- **Trade-offs:**
    - Managing complex error scenarios like partial failures can add additional overhead to state management.

## How to Run the Project

### Prerequisites
- **Android Studio**: Ensure you have the latest stable version.
- **Java SDK**: The project requires JDK 11 (or higher).

### Steps to Run
1. **Clone the Repository:**
   ```bash
   git clone https://github.com/izzazaga/StocksApp
   cd StocksApp
   ```
2. **Open in Android Studio:**
- Launch Android Studio and select Open an Existing Project.
- Navigate to the project’s root directory and wait for Gradle sync.

3. **Build the Project:**
- Build the project using Build > Make Project or Ctrl + F9.

4. **Run the App:**
- Connect an Android device or launch an emulator.
- Click the green play button or press Shift + F10 to run the app.

5. **Running Tests:**
- Go to the test directory under src.
- Right-click on a test class (e.g., StockViewModelTest) and select 'Run test name'.
- You can also run all tests by right-clicking the tests folder in the file viewer and select 'Run Tests in 'folder name'.

## Next Steps

To enhance the functionality and maintainability of the app, the following improvements and features could be added:

1. **Add Database for Caching:**
  - **Why:** Implementing a local database using Room or another database solution would allow offline access to previously fetched stock data. This would improve the user experience by reducing load times and ensuring data availability even when the device is offline.
  - **How:**
    - Create a Room database with entities representing stock data.
    - Implement data synchronization between the local cache and remote API.
    - Update the repository to first query cached data and then refresh from the network when needed.

2. **Introduce Dependency Injection (DI):**
  - **Why:** Integrating a DI framework like Hilt or Koin would help reduce boilerplate code and improve testability by making it easier to inject dependencies, such as the repository, API, and database, into different parts of the app.
  - **How:**
    - Integrate Hilt or Koin into the project.
    - Refactor the `ViewModel`, repository, and other components to use constructor injection.
    - Define and manage the lifecycle of singletons, such as Retrofit and the database, through the DI container.

3. **Add Navigation to Detailed Stock Data:**
  - **Why:** To provide a richer user experience, the app could be extended to include a detailed view for each stock. This would offer more in-depth information, historical trends, and other relevant metrics.
  - **How:**
    - Create a new screen with detailed stock information, fetching additional data as needed.
    - Update the `ViewModel` to manage the state of the detailed screen and handle any relevant business logic.

These improvements would not only extend the app’s functionality but also ensure it remains scalable and maintainable as more features are added.