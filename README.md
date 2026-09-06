# OpsAI Mobile - AI-Powered IT Incident Management

OpsAI Mobile is the Android client for the OpsAI IT Incident Management Platform. It allows SREs and IT operations teams to manage incidents, perform AI-powered root cause analysis, and monitor system health on the go.

## Features

- **Real-time Incident Dashboard**: View critical incidents, MTTR, and SLA compliance.
- **AI Log Analyzer**: Paste or upload logs for instant AI analysis.
- **Incident Lifecycle Management**: Create, investigate, and resolve incidents.
- **AI Recommendations**: Get suggested root causes and actions for every incident.
- **Knowledge Base**: Access historical resolutions and prevention strategies.
- **Real-time Updates**: WebSocket integration for instant incident notifications.
- **Offline Support**: Local caching of incidents using Room.

## Tech Stack

- **Language**: Kotlin
- **UI**: Jetpack Compose (Material 3)
- **Architecture**: MVVM / Clean Architecture
- **DI**: Hilt
- **Networking**: Retrofit / OkHttp / WebSocket
- **Persistence**: Room / DataStore
- **Concurrency**: Kotlin Coroutines & Flow

## Setup Instructions

1. **Backend Configuration**:
   The application is configured to connect to the OpsAI backend. You can change the base URL in `app/build.gradle.kts`:
   ```kotlin
   buildConfigField("String", "BASE_URL", "\"https://api.opsai.com/\"")
   ```

2. **Dependencies**:
   Ensure you have the latest version of Android Studio (Jellyfish or newer).
   The project uses Gradle Version Catalog for dependency management.

3. **Building the App**:
   - Open the project in Android Studio.
   - Wait for Gradle sync to complete.
   - Click 'Run' to deploy to an emulator or physical device.

4. **Authentication**:
   The app uses JWT authentication. The tokens are stored securely using DataStore.

## Project Structure

- `data`: Implementation of repositories, local database, and network DTOs.
- `domain`: Core business logic, models, repository interfaces, and use cases.
- `presentation`: UI components and ViewModels organized by feature.
- `di`: Hilt modules for dependency injection.
- `websocket`: Real-time communication logic.
- `utils`: Common utility classes like the `Resource` wrapper.

## AI Features

The AI features (Root Cause Analysis, Severity Suggestion) are integrated into the "Create Incident" and "Log Analyzer" flows. The app communicates with the backend's AI analysis endpoints to provide insights based on incident data and logs.
