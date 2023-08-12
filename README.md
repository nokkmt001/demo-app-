AEON Booking App use for employee aeon.

## Flow UI
start MainApplication -> MainActivity(only one activity in application) add fragment SplashFragment
if is login -> pop and pushFragment MainFragment
  else      -> pop and pushFragemnt LoginFragment

## Tech stack & Open-source libraries
* AndroidX
* Min SDK 21+ (required by CameraX)
* [Kotlin] - [Coroutines] for asynchronous.
* Database 
  - [Hawk] Preference database
* JetPack
  - Lifecycle - dispose observing data when lifecycle state changes.
  - ViewModel - UI related data holder, lifecycle aware.
* Architecture
  - MVVM Architecture (View - DataBinding - ViewModel - Model)
  - [Bindables] - Android DataBinding kit for notifying data changes to UI layers.
  - Repository pattern
  - [Koin] dependency injection
* Material Design & Animations
  - [Sandwich] - construct lightweight network response interfaces and handling error responses.
  - [Retrofit2 & Gson] - constructing the REST API
  - [OkHttp3] - implementing interceptor, logging and mocking web server
  - [Glide] - loading images
  - [WhatIf] - checking nullable object and empty collections more fluently
  - [Bundler] - Android Intent & Bundle extensions that insert and retrieve values elegantly.
  - [Timber] - logging
* Ripple animation, Shared element container transform/transition