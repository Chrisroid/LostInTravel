```markdown
# 🌍 LostInTravel

LostInTravel is a curated travel inspiration app that demonstrates clean architecture, authentication, state handling, and modern Android development using **Jetpack Compose**.

---

## ✨ Features

- ✅ **Email/Password Authentication**
- ✅ **bearer Token Persistence using DataStore**
- ✅ **Apollo GraphQL Integration**
- ✅ **Recommended Destinations UI (Figma Matching)**
- ✅ **Pull-to-Refresh (SwipeRefresh)**
- ✅ **Glide for Image Loading**
- ✅ **MVVM Architecture + Hilt DI**
- ✅ **Error, Loading & Empty State Handling**

---

## 🚀 Getting Started

### 🧰 Prerequisites

- Android Studio Hedgehog or newer
- Kotlin 1.9+
- Firebase project with Email/Password Auth enabled
- Apollo GraphQL plugin installed

### ⚙️ Setup Instructions

1. **Clone this repo**  
   ```bash
   git clone https://github.com/yourusername/LostInTravel.git
   ```

2. **Add your Firebase config** Place your `google-services.json` in the `app/` directory.

3. **Set your Web Client ID** Add to `res/values/strings.xml`:
   ```xml
   <string name="default_web_client_id">YOUR_CLIENT_ID</string>
   ```

4. **Run the app** Open in Android Studio and run on an Android 8.0+ emulator or device.

## 📦 Tech Stack

| Layer | Technology | 
| --- | --- |
| Language | Kotlin |
| UI | Jetpack Compose |
| Architecture | MVVM + Hilt |
| Dependency Injection | Hilt |
| Networking | Apollo GraphQL + OkHttp |
| Auth | Firebase (Email/Password) |
| Persistence | DataStore |
| Images | Glide |


## 🧱 Architecture

```
com.chrisroid.lostintravel
├── ui/                 // Compose UI Screens
├── data/
│   ├── repository/     // Auth + Apollo interaction
│   └── local/          // DataStore Preferences
├── domain/             // Domain Models
├── di/                 // Hilt Modules
├── graphql/            // Apollo Generated Queries
├── viewmodel/          // StateFlow + ViewModel
```

## ✅ Requirements Completed
* Email/password login flow using Firebase
* GraphQL login + token persistence
* Apollo integration with Authorization header
* Recommended destinations from GraphQL API
* Swipe-to-refresh support
* UI design matched to Figma
* Error, empty, and loading states handled

## ❗ Limitations
* Google Sign-In is present but not active
* No destination detail screen
* No offline caching yet

## 🌱 Future Improvements
* Add Destination Details screen
* Implement offline caching (Room)
* Google Sign-In toggle
* Light/Dark theme support
* Input field validation and toast feedback

## 🧪 Testing
* Manual tests on Android 10 and 12
* Confirmed GraphQL headers contain valid token
* Verified DataStore retains login state and Bearer
* Pull-to-refresh re-fetches recommended places

## 📜 License
MIT License

**Built with ❤️ by Chris Asarhasa**
```
