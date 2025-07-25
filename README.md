# 🎬 Aflami App

Welcome to **Aflami**, an Android application built to explore and display movie and TV show content powered by **TMDB API**. This project is designed following **Clean Architecture** and **modularized by component**, ensuring scalability, maintainability, and high code quality.

---

## 🚀 Project Overview

Aflami allows users to:

- Search for movies and TV shows by title, actor name or by country 
- Browse trending, upcoming, and top-rated content
- View detailed information about selected media
- Experience smooth navigation and responsive UI in both light & dark themes

---

## 🧱 Modularization

The project is structured **by component**. Each feature is extracted into its own Gradle module

---

## 🧩 Tech Stack

| Area                 | Library             |
|----------------------|---------------------|
| Language             | Kotlin              |
| UI Framework         | Jetpack Compose     |
| Networking           | Retrofit + OkHttp   |
| Dependency Injection | Koin                |
| Async/Coroutines     | Kotlin Coroutines   |
| Local Caching        | Room                |
| Image Loading        | Coil                |
| Navigation           | Navigation-Compose  |
| Testing              | JUnit, MockK        |

---

## 🔍 Search Feature Highlights

- Smart debounced search  
- Actor-based search
- country-based search
- Search history with Room  
- Genre-based ranking (based on user preferences)  
---

## 🌐 Firebase Integration

We’ve integrated:

- Crashlytics  
- Analytics  
- Firebase App Distribution (via GitHub Actions)  

---

## ⚙️ CI/CD Pipeline

We use **GitHub Actions** for automated CI/CD:

- ✅ CI: Build and test on pull requests to `develop`  
- 🚀 CD: Auto-generate APK and upload to Firebase on merge to `develop`  

---

## 🖼️ Custom Image Viewer

We've built a reusable image viewer module:

- Applies on-device blurring for inappropriate content  
- Designed to respect cultural sensitivities  

---

## 📌 Notes

- Codebase is clean, modular, and scalable  
- Follows SOLID principles and modern Android best practices  
- Arabic & English support (RTL included)  
- Light/Dark themes supported  
- Tested and production-ready structure  

---

## 🏁 Next Goals

- Implement user authentication
- Implement Home screen
---

> Made with ❤️ by Berlin squad
