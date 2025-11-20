# Architecture Overview

## Project Structure

```
Otter/
├── app/
│   └── src/
│       └── main/
│           ├── java/com/appharbor/otter/
│           │   ├── MainActivity.kt
│           │   ├── ui/
│           │   │   ├── components/          # Reusable glassmorphic components
│           │   │   │   ├── GlassCard.kt
│           │   │   │   ├── GlassButton.kt
│           │   │   │   ├── GlassInput.kt
│           │   │   │   ├── GlassCheckbox.kt
│           │   │   │   ├── GlassToggle.kt
│           │   │   │   ├── GlassBackground.kt
│           │   │   │   └── ComponentsPreview.kt
│           │   │   ├── screens/            # App screens
│           │   │   │   ├── HomeScreen.kt
│           │   │   │   ├── DownloadsScreen.kt
│           │   │   │   ├── SettingsScreen.kt
│           │   │   │   └── FormatSelectionScreen.kt
│           │   │   ├── theme/              # Theme configuration
│           │   │   │   ├── Color.kt
│           │   │   │   ├── Theme.kt
│           │   │   │   └── Type.kt
│           │   │   └── viewmodels/         # ViewModels
│           │   │       ├── DownloadViewModel.kt
│           │   │       └── SettingsViewModel.kt
│           │   ├── data/                   # Data layer
│           │   │   ├── repository/
│           │   │   │   └── DownloadRepository.kt
│           │   │   ├── models/
│           │   │   │   ├── DownloadTask.kt
│           │   │   │   ├── VideoInfo.kt
│           │   │   │   └── FormatInfo.kt
│           │   │   └── local/
│           │   │       └── DownloadDatabase.kt
│           │   ├── domain/                 # Business logic
│           │   │   └── usecases/
│           │   │       ├── GetVideoInfoUseCase.kt
│           │   │       ├── DownloadVideoUseCase.kt
│           │   │       └── ExtractAudioUseCase.kt
│           │   └── python/                 # Python bridge
│           │       ├── YtDlpBridge.kt
│           │       └── PythonManager.kt
│           └── python/                     # Python scripts
│               ├── ytdlp_wrapper.py
│               └── requirements.txt
└── docs/                                   # Documentation
```

## Layer Responsibilities

### UI Layer
- **Composables**: Declarative UI components built with Jetpack Compose
- **ViewModels**: State management and UI logic
- **Components**: Reusable glassmorphic UI components
- **Theme**: Color schemes, typography, and styling

### Domain Layer
- **Use Cases**: Business logic encapsulation
- **Models**: Domain entities and data classes

### Data Layer
- **Repository**: Data source abstraction
- **Local Storage**: Room database for downloads history
- **Python Bridge**: Interface to yt-dlp functionality

## Design Patterns

### MVVM (Model-View-ViewModel)
- **Model**: Data classes and repository
- **View**: Composable functions
- **ViewModel**: State holders and business logic coordinators

### Repository Pattern
- Abstracts data sources
- Provides clean API for ViewModels
- Handles data caching and synchronization

### Use Case Pattern
- Single responsibility per use case
- Testable business logic
- Clean separation from UI concerns

## State Management

### Unidirectional Data Flow
```
User Action → ViewModel → Use Case → Repository → Python Bridge
                ↓
            UI State ← ViewModel
```

### State Holders
- `StateFlow` for reactive state updates
- `MutableState` for Compose recomposition
- `LiveData` for lifecycle-aware observations (if needed)

## Dependency Injection

Future implementation will use:
- **Hilt/Dagger** for dependency injection
- **Module organization** by feature
- **Scoping** for lifecycle management

## Threading

- **Main Thread**: UI rendering and user interactions
- **IO Dispatcher**: File operations and network calls
- **Default Dispatcher**: CPU-intensive operations
- **Python Thread Pool**: yt-dlp operations

## Navigation

Using Jetpack Compose Navigation:
- Type-safe navigation arguments
- Bottom navigation for main screens
- Modal sheets for format selection
- Deep links for sharing support

## Error Handling

- Result/Either pattern for operations that can fail
- User-friendly error messages
- Logging and crash reporting (Firebase Crashlytics)
- Graceful degradation

## Testing Strategy

### Unit Tests
- ViewModels logic
- Use Cases
- Repository operations
- Data transformations

### Integration Tests
- Python bridge functionality
- Database operations
- End-to-end download flows

### UI Tests
- Critical user journeys
- Component interactions
- Navigation flows

## Performance Considerations

- **Lazy Loading**: Compose LazyColumn for lists
- **Image Caching**: Coil for thumbnail loading
- **Background Processing**: WorkManager for downloads
- **Memory Management**: Proper lifecycle handling

## Security

- **Storage**: Scoped storage for Android 10+
- **Permissions**: Runtime permission handling
- **Network**: HTTPS-only communication
- **Data Privacy**: No tracking or analytics by default
