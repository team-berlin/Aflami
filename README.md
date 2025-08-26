 [![StandWithPalestine](https://raw.githubusercontent.com/TheBSD/StandWithPalestine/main/badges/StandWithPalestine.svg)](https://thebsd.github.io/StandWithPalestine/)

<div align="center">
  <img src="https://img.shields.io/badge/UI-Jetpack%20Compose-orange.svg" alt="UI">
  <img src="https://img.shields.io/badge/Kotlin-0095D5?&style=for-the-badge&logo=kotlin&logoColor=white" alt="Language">
  <img src="https://img.shields.io/badge/Android-3DDC84?style=for-the-badge&logo=android&logoColor=white" alt="Platform">
  <img src="https://img.shields.io/badge/Architecture-MVVM-red.svg" alt="Architecture">
</div>

---

# 🎬 Aflami App

Welcome to **Aflami**, an Android application built to explore and display movie and TV show content powered by **TMDB API**. <BR>This project is designed following **Clean Architecture** and **modularized by component**, ensuring scalability, maintainability, and high code quality.



# Features
## 🔻OnBoarding
> **_WELCOME ABOARD!.. LEARN ABOUT THE APP'S KEY FEATURES
AND GET READY TO EXPLORE MOVIES EFFORTLESSLY_**
<img width="1400" height="1120" alt="onboardingScreens" src="https://github.com/user-attachments/assets/ab89985e-500c-47a1-b1a7-8e68fb2f4449" />

- Introduces the app’s key features  
- Simple visuals to guide new users  
- Clear buttons for next or skip

  ---

  
## 🔻Login
> **_CHOOSE YOUR WAY TO START..
SIGN IN FOR A PERSONALIZED JOURNEY OR EXPLORE AS A GUEST_**
<img width="1400" height="1120" alt="loginScreen" src="https://github.com/user-attachments/assets/fc5bb34c-1ef6-4029-b33b-fc03f2a49d49" />

- Sign in with your account  
- Option to create a new account
- Option if you forgot password 
- Guest mode available for quick access
  
  ---

  

## 🔻Home
> **_DISCOVER TRENDING..
MOVIES AND TV SHOWS WITH A PERSONALIZED BROWSING EXPERIENCE_**
<img width="1400" height="1120" alt="homeScreen" src="https://github.com/user-attachments/assets/804572e0-e168-4fcc-8ba6-84424aefeb53" />

- Displays the latest featured content  
- Quick access to movies and TV shows  
- Includes a search bar and navigation tabs

  ---

  
## 🔻Lists
> **_SAVE YOUR FAVORITE MOVIES
TO CUSTOM LISTS FOR QUICK ACCESS ANYTIME_**
<img width="1400" height="1120" alt="listScreens" src="https://github.com/user-attachments/assets/421d4fd7-7f2a-49bb-ba94-66bfff0b8f55" />

- Save movies
- Organize your lists your way  
- Easy access to saved content later  

  ---
## 🔻Game
> **_CHALLENGE YOURSELF
WITH MULTIPLE FUN MODES TO GUESS MOVIES, CHARACTERS, AND GENRES AGAINST TIME._**
<img width="1400" height="1120" alt="gameScreen" src="https://github.com/user-attachments/assets/0c104e90-b93b-4181-9397-55b2073d041a" />

- Pick your favorite games easily and increase your points  
- Interactive and visually engaging experience  
- Quick details preview for each game  

  ---
## 🔻Localizations
> **_PICK YOUR LANGUAGE..
SPEAK THE APP IN YOUR OWN LANGUAGE WITH ONE SIMPLE TAP (RTL included)_**
<img width="1400" height="1120" alt="LANGUAGE" src="https://github.com/user-attachments/assets/750b36fd-247b-4aab-81b4-5e38d1e081e6" />

- Select your preferred app language  
- Supports multiple languages (e.g., English/Arabic)  
- Changes applied instantly across the app  

  ---
  ## 🔻Theme
> **_Switch seamlessly..
between light and dark mode for a personalized experience_**
<img width="1400" height="1120" alt="theming" src="https://github.com/user-attachments/assets/815496de-8550-4fb7-8520-643456fd6dcf" />

- Toggle between **Light** and **Dark** modes  
- Provides a personalized look and feel  
- Remembers your last selected theme automatically
  
#  Installation

````md
Prerequisites
Before you begin, make sure you have the following installed:
- Android Studio (latest version) 
- Java Development Kit (JDK 17+)
- Gradle (comes with Android Studio)
- Android SDK with NDK installed
````
Follow these steps to set up and run the project locally:

### 1- Clone the repository

```bash
git clone https://github.com/team-berlin/Aflami.git
````

### 2- Open the project

Open the cloned project in [Android Studio](https://developer.android.com/studio).

### 3- Configure local properties

In the project root, create or edit a file named **`local.properties`**, then add the following keys:

```properties
apiKey="YOUR_API_KEY"
baseUrl="https://api.themoviedb.org/3/"
baseImageUrl="https://image.tmdb.org/t/p/w500"
ndkDir="~/Library/Android/sdk/ndk"
movieSignUp="https://www.themoviedb.org/signup"
movieResetPassword="https://www.themoviedb.org/reset-password"
```

> 💡 **How to get your `apiKey`:**
>
> 1. Sign up at [The Movie Database (TMDB)](https://www.themoviedb.org/signup)
> 2. Navigate to **Settings → API** in your TMDB account
> 3. Generate an **API Key** and paste it in place of `YOUR_API_KEY`

### 4. Build and Run
1. Open the project in Android Studio  
2. Sync the project with Gradle files  
3. Build the project (**Build > Make Project**)  
4. Run on device or emulator (**Run > Run 'app'**)
### 5. Running the App
1. Enable USB debugging and connect your physical device 
2. Create an Android Virtual Device (AVD) with API level 24 or higher
---

# Modularization

<img width="1483" height="580" alt="by_component" src="https://github.com/user-attachments/assets/586ad636-9042-4b82-bf21-ad1a33319ed2" />

---

# Tech Stack

| Area                 | Library             |
|----------------------|---------------------|
| Language             | Kotlin              |
| UI Framework         | Jetpack Compose     |
| Networking           | Retrofit + OkHttp   |
| Dependency Injection | Dagger Hilt         |
| Async/Coroutines     | Kotlin Coroutines   |
| pagination           | Paging3             |
| Local Caching        | Room                |
| Image Loading        | Coil                |
| Navigation           | Navigation-Compose  |
| Testing              | JUnit, MockK        |



---


# Firebase Integration

We’ve integrated:

- Crashlytics  
- Analytics  
- Firebase App Distribution (via GitHub Actions)  

---

# CI/CD Pipeline

We use **GitHub Actions** for automated CI/CD:

-  CI: Build and test on pull requests to `develop`  
-  CD: Auto-generate APK and upload to Firebase on merge to `develop`  

---

# Custom Image Viewer

We've built a reusable image viewer module:

- Applies on-device blurring for inappropriate content  
- Designed to respect cultural sensitivities  

---

# 📌 Notes

- Codebase is clean, modular, and scalable  
- Follows SOLID principles and modern Android best practices  
- Tested and production-ready structure  

---

# Contributors
<a href="https://github.com/team-berlin/Aflami/graphs/contributors">
  <img src="https://contrib.rocks/image?repo=team-berlin/Aflami" />
</a>

> Made with ❤️ by Berlin squad
