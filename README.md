# Dog Breeds Project

## How to run
- Have all dependencies synced in the `libs.versions.toml` and `build.gradle.kts` file.
- Click RUN
- For tests: navigate to the test folder, right-click files and click "Run `test_filename`"

## Tech used
- Kotlin/Jetpack Compose
- Retrofit/OKHttp for API
- Room Database
- MVVM
- Coroutines
- Hilt
- Glide
- JUnit
- Mockito

## My Approach
- Took initial time to understand how to target the dog breed API responses, and modelled the data layer to reflect the type of data that would come into the application.
- Separated the application into various layers to adhere to Clean Architecture Principles and MVVM, with a data layer, a di layer, a domain layer and a presentation layer.
- **Data**: Contains the API service, database, models and repository implementations. The repository allows the app to work offline by determining how to pull data from the API service and when to update the local database.
- **DI**: Contains the dependency injection module for the application to help provide dependencies systematically.
- **Domain**: Contains the usecases, domain models and repository interfaces.
- **Presentation**: Contains the viewModels for both the DogBreedListScreen and DogBreedRandomImageScreen, along with the data mappers to transform the domain layer data for the UI, and the MainNavGraph that contains the navigation logic to go from one screen to another.
- Also note that not all dog breeds have ten images available to display.
- **Tests**: Made unit tests for both viewModels to confirm that the state handling logic is correct.

## Future implementations
- If I had more time, I would use Coil for image loading as it handles caching by default.
- Would add more tests for the other layers, like for the repository.

## Screenshots
<img width="261" height="565" alt="Screenshot 2026-02-19 at 12 39 37" src="https://github.com/user-attachments/assets/576391c2-e19b-4c45-ac43-3c0a2dd03784" />
<img width="261" height="565" alt="Screenshot 2026-02-19 at 13 02 15" src="https://github.com/user-attachments/assets/a4695483-5937-4c45-a66b-d95104ebd799" />


