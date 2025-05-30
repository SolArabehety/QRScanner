# QRScanner

This project was built as part of the Superformula Mobile Developer Coding Challenge.

It includes:

- A backend service in Node.js to generate and validate QR seeds
- A Kotlin Android app to generate, display, and scan QR codes

## 📱 Android App

### Architecture

- **MVVM** pattern with clean separation between UI, business logic, and data layers.
- **Hilt** for dependency injection
- **Jetpack Compose** for UI
- **CameraX** for barcode scanning
- **Coroutines + StateFlow** for reactive state handling
- **Custom Result wrapper** for error handling

### Features

- Generate a new QR code from a seed fetched from the backend
- Automatically expires after 5 minutes
- Scan QR codes and validate them against the backend
- Clean UI with navigation across three screens

### How to run

1. Clone this repo
2. Open the project in Android Studio Hedgehog or later
3. Run the app on an emulator or physical device (Android 8+)
4. Ensure your backend is deployed and reachable from the app

### Requirements

- Android Studio Hedgehog
- Android SDK 34
- Gradle Plugin 8.3.1
- Kotlin 1.9+

### 🧪 Testing
Unit tests are included for key components

ViewModel, UseCase and Repository layers are tested with MockK and Turbine

To run tests:

./gradlew test

## 🌐 Backend

- Built with **Node.js 20+** and **Express**
- Two endpoints: 
  - `POST /seed` to generate a seed
  - `GET /validate?seed=xyz` to validate if a seed is valid or expired
- Seeds expire after 5 minutes
- Data is stored in memory

### Deployment

The backend is hosted on Render and available publicly.

### How to run locally

```bash
git clone https://github.com/SolArabehety/QRScanner
cd backend
npm install
npm start
```

Then the server will be running at http://localhost:3000.

API Endpoints
curl -X POST http://localhost:3000/seed     

curl -X POST http://localhost:3000/validate 
  -H "Content-Type: application/json" \
  -d '{"seed": "exampleseed"}'

