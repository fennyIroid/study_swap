# Project Architecture & Development Reference

This document describes the **90s Wildflower** Android app’s structure, conventions, and data flow so you can reuse the same patterns in another project.

---

## 1. Project Overview

| Aspect | Choice |
|--------|--------|
| **Language** | Kotlin |
| **UI** | Jetpack Compose |
| **DI** | Hilt (Dagger) |
| **Networking** | Retrofit + OkHttp + Gson |
| **Async** | Kotlin Coroutines + Flow |
| **State** | StateFlow, MutableStateFlow |
| **Min SDK** | 26, Target 35, Compile 36 |
| **Build** | Gradle Kotlin DSL, build flavors (Development / Production) |

**Base package:** `com.wildflower.mobile.app`

---

## 2. Folder & File Structure

All paths below are relative to `app/src/main/java/com/wildflower/mobile/app/`.

```
com.wildflower.mobile.app/
├── data/
│   ├── source/
│   │   ├── Constants.kt                    # App screen keys, prefs, etc.
│   │   ├── local/
│   │   │   └── datastore/
│   │   │       └── AppPreferenceDataStore.kt
│   │   └── remote/
│   │       ├── EndPoints.kt                 # API path constants
│   │       ├── helper/
│   │       │   └── NetworkResult.kt        # Sealed class: Success | Error | Loading | UnAuthenticated
│   │       ├── interceptor/
│   │       │   ├── AuthInterceptor.kt
│   │       │   └── RequestInterceptor.kt
│   │       └── repository/
│   │           ├── ApiServices.kt           # Retrofit interface
│   │           ├── ApiRepository.kt         # Repository interface
│   │           └── ApiRepositoryImpl.kt    # Repository implementation
│   └── ...
├── di/
│   ├── AppModule.kt                        # ApiRepository, DataStore, etc.
│   ├── NetworkModule.kt                    # Retrofit, OkHttp, ApiServices
│   └── CoroutinesModule.kt                 # Dispatchers if needed
├── domain/                                 # Optional shared domain logic
│   └── validation/
├── model/
│   └── domain/
│       ├── request/                        # One file per API request body
│       │   ├── LoginRequest.kt
│       │   ├── SendEmailChangeOtpRequest.kt
│       │   └── ...
│       └── response/                       # One file per API response type
│           ├── ApiResponse.kt              # Generic wrapper (data, message, status, apiErrors)
│           ├── AuthUserResponse.kt
│           ├── GetUserProfileResponse.kt
│           └── ...
├── navigation/
│   ├── NavComposeRoute.kt                  # Base for Compose routes
│   ├── NaveRoute.kt / NavRoute.kt          # Route types & definitions
│   ├── ViewModelNav.kt                     # Navigation from ViewModel
│   ├── HandleNavBarNavigation.kt          # Bottom bar nav handling
│   └── graph/
│       ├── AppStartupGraph.kt              # Splash, onboarding, auth
│       ├── AppMainGraph.kt                 # Main tabs (Home, Create, Settings)
│       └── AppContainerGraph.kt            # Container activity (search, diary, detail, edit profile, etc.)
├── ui/
│   ├── theme/                              # Colors, typography, theme
│   └── compose/
│       └── common/                         # Reusable composables
│           ├── GlassTextField.kt
│           ├── WildflowerButton.kt
│           ├── OtpInputField.kt
│           └── ...
├── ux/
│   ├── main/                               # Main app (after login)
│   │   ├── MainScreen.kt
│   │   ├── MainViewModel.kt
│   │   ├── bottombar/
│   │   ├── home/                           # Feature: Home tab
│   │   │   ├── HomeScreen.kt
│   │   │   ├── HomeViewModel.kt
│   │   │   ├── HomeUiState.kt
│   │   │   ├── HomeRoute.kt
│   │   │   └── GetHomeUiStateUseCase.kt
│   │   ├── settings/
│   │   │   ├── SettingsScreen.kt
│   │   │   ├── SettingsViewModel.kt
│   │   │   ├── SettingsUiState.kt
│   │   │   ├── SettingsRoute.kt
│   │   │   ├── GetSettingsUiStateUseCase.kt
│   │   │   └── SettingsConfirmSheets.kt
│   │   └── create/
│   ├── container/                         # Screens opened from main (activities or nav)
│   │   ├── ContainerActivity.kt
│   │   ├── ContainerViewModel.kt          # Shared args (e.g. journal detail, edit journal)
│   │   ├── ContainerScreen.kt
│   │   ├── editprofile/
│   │   │   ├── EditProfileScreen.kt
│   │   │   ├── EditProfileViewModel.kt
│   │   │   ├── EditProfileUiState.kt
│   │   │   ├── EditProfileRoute.kt
│   │   │   └── GetEditProfileUiStateUseCase.kt
│   │   ├── journaldetail/
│   │   │   ├── JournalDetailScreen.kt
│   │   │   ├── JournalDetailViewModel.kt
│   │   │   ├── JournalDetailUiState.kt
│   │   │   ├── JournalDetailRoute.kt
│   │   │   ├── JournalDetailArgs.kt
│   │   │   └── DeleteJournalConfirmSheet.kt
│   │   ├── addjournal/
│   │   ├── changepassword/
│   │   └── ...
│   └── startup/                           # Before main app (splash, onboarding, auth)
│       ├── splash/
│       ├── onboarding/
│       ├── auth/
│       │   ├── login/
│       │   └── signup/
│       └── vibequestionnaire/
└── utils/
    ├── ext/
    │   ├── Extensions.kt                  # e.g. ResponseBody.extractError()
    │   ├── ContextExt.kt
    │   └── ...
    └── AppUtils.kt                        # showSuccessMessage, showErrorMessage (Toasty)
```

### 2.1 Naming Conventions

| Layer | File / type | Naming |
|-------|-------------|--------|
| API path | `EndPoints.kt` | `object Feature { const val ACTION = "api/..." }` |
| Request body | `model/domain/request/` | `<Name>Request.kt` |
| Response body | `model/domain/response/` | `<Name>Response.kt` |
| Retrofit | `ApiServices.kt` | `suspend fun action(...): Response<XResponse>` |
| Repository | `ApiRepository.kt` / `ApiRepositoryImpl.kt` | `suspend fun action(...): Flow<NetworkResult<X>>` |
| Use case | `Get<Feature>UiStateUseCase.kt` | Holds state, handles events, calls API |
| UI state | `<Feature>UiState.kt` | `FeatureUiState`, `FeatureDataState`, `FeatureUiEvent` |
| ViewModel | `<Feature>ViewModel.kt` | `@HiltViewModel`, exposes `uiState` |
| Screen | `<Feature>Screen.kt` | Composable, collects state, calls `event()` |
| Route | `<Feature>Route.kt` | Object extending `NavComposeRoute` or `SimpleNavComposeRoute` |

---

## 3. Data Flow (High Level)

```
User action (UI)
    → Screen: uiState.event(FeatureUiEvent.OnXxx)
    → ViewModel (or UseCase invoked by ViewModel)
    → UseCase: handleEvent() → apiRepository.xxx().collect { result -> ... }
    → ApiRepositoryImpl: flow { emit(Loading); ... emit(Success/Error/UnAuthenticated) }
    → ApiServices (Retrofit): HTTP call
    → Back: NetworkResult → UseCase updates MutableStateFlow → ViewModel exposes StateFlow
    → Screen: state by uiState.featureStateFlow.collectAsStateWithLifecycle() → recompose
```

- **One-way data flow:** Events from UI → ViewModel/UseCase → Repository → API; state flows back via StateFlow.
- **No business logic in ViewModel:** ViewModel either delegates to a UseCase (invoking it with `context`, `coroutineScope`, `navigate`) or holds minimal UI-only logic (e.g. some container screens).
- **Repository returns Flow\<NetworkResult\<T>>:** So the UI layer can react to Loading, Success, Error, UnAuthenticated in one place.

---

## 4. API Integration (Step-by-Step)

Use this order for **every** new API.

### 4.1 Endpoint

**File:** `data/source/remote/EndPoints.kt`

- Add a constant under the right object (e.g. `User`, `Journal`).
- Path only (no base URL): `"api/user/send-email-change-otp"`.

```kotlin
object User {
    const val SEND_EMAIL_CHANGE_OTP = "api/user/send-email-change-otp"
    const val VERIFY_EMAIL_CHANGE = "api/user/verify-email-change"
}
```

### 4.2 Request and Response Models

**Location:** `model/domain/request/`, `model/domain/response/`

- **Request:** Data class with fields matching API; use `@SerializedName` if names differ.
- **Response:** Data class for the JSON body. For “wrapper” responses use either:
  - `ApiResponse<T>` when the API returns `data`, `message`, `status`, `apiErrors`, or
  - A simple type with `success`, `message`, `data` when the API uses that shape.

Example request:

```kotlin
// SendEmailChangeOtpRequest.kt
data class SendEmailChangeOtpRequest(
    @SerializedName("new_email") val newEmail: String
)
```

Example response:

```kotlin
// SendEmailChangeOtpResponse.kt
data class SendEmailChangeOtpResponse(
    @SerializedName("success") val success: Boolean = false,
    @SerializedName("message") val message: String? = null
)
```

### 4.3 ApiServices (Retrofit)

**File:** `data/source/remote/repository/ApiServices.kt`

- Declare a `suspend` function returning `Response<YourResponseType>`.
- Use `@Body`, `@Query`, `@Path`, `@Part` as needed.

```kotlin
@POST(EndPoints.User.SEND_EMAIL_CHANGE_OTP)
suspend fun sendEmailChangeOtp(@Body req: SendEmailChangeOtpRequest): Response<SendEmailChangeOtpResponse>
```

### 4.4 ApiRepository and ApiRepositoryImpl

**Interface:** `ApiRepository.kt`  
- Method returns `Flow<NetworkResult<YourResponseType>>`.

**Implementation:** `ApiRepositoryImpl.kt`  
- Use `flow { }`:
  - `try { response = apiServices.xxx(...) }`
  - If `response.isSuccessful && response.body() != null` → `emit(NetworkResult.Success(response.body()!!))`
  - Else → `emit(NetworkResult.Error(response.errorBody().extractError()))`
  - `catch (e: IOException)` → `emit(NetworkResult.Error(e.message))`
  - `catch (e: HttpException)` → if `e.code() == 401` then `emit(NetworkResult.UnAuthenticated(e.message))` else `emit(NetworkResult.Error(e.message))`
- Chain: `.onStart { emit(NetworkResult.Loading()) }.flowOn(Dispatchers.IO).catch { emit(NetworkResult.Error(it.message)) }`

**Error body parsing:** `utils/ext/Extensions.kt` defines `ResponseBody?.extractError(): String` (e.g. read JSON `message` or fallback string).

### 4.5 NetworkResult

**File:** `data/source/remote/helper/NetworkResult.kt`

```kotlin
sealed class NetworkResult<T>(val data: T? = null, val message: String? = null) {
    class Success<T>(data: T?) : NetworkResult<T>(data)
    class Error<T>(message: String?, data: T? = null) : NetworkResult<T>(data, message)
    class Loading<T> : NetworkResult<T>()
    class UnAuthenticated<T>(message: String?) : NetworkResult<T>(message = message)
}
```

Use these four cases in the UI/UseCase when collecting the repository Flow.

### 4.6 UseCase (UI state + API call)

**File:** `ux/.../Get<Feature>UiStateUseCase.kt`

- `@Inject constructor(private val apiRepository: ApiRepository, ...)`
- Holds `MutableStateFlow<FeatureDataState>`.
- `operator fun invoke(context, coroutineScope, onBack): FeatureUiState`:
  - Returns `FeatureUiState(featureStateFlow = _flow.asStateFlow(), event = { handleEvent(it, context, coroutineScope, onBack) })`.
- In `handleEvent`, for the action that triggers the API:
  - Validate input.
  - `coroutineScope.launch { apiRepository.xxx(...).collect { result -> ... } }`.
  - On `Loading`: `update { it.copy(isLoading = true) }`.
  - On `Success`: `update { it.copy(isLoading = false, ...) }`, optionally save to DataStore, `showSuccessMessage(context, ...)`, navigate if needed.
  - On `Error` / `UnAuthenticated`: `update { it.copy(isLoading = false) }`, `showErrorMessage(context, result.message ?: fallback)`.

Access payload: `result.data` (and for wrapped APIs often `result.data?.data`).

### 4.7 UiState / DataState / Events

**File:** `ux/.../<Feature>UiState.kt` (or same package as UseCase)

- **FeatureUiState:** `data class FeatureUiState(val featureStateFlow: StateFlow<FeatureDataState>, val event: (FeatureUiEvent) -> Unit)`.
- **FeatureDataState:** Data class with all screen state: form fields, `isLoading`, errors, etc.
- **FeatureUiEvent:** Sealed interface with one subtype per user action, e.g. `OnEmailChange(val value: String)`, `OnSubmitClick`, `OnDismissSheet`.

### 4.8 ViewModel

**File:** `ux/.../<Feature>ViewModel.kt`

- `@HiltViewModel`
- Constructor inject UseCase (and optionally `@ApplicationContext context`).
- `val uiState: FeatureUiState = getFeatureUiStateUseCase(context = context, coroutineScope = viewModelScope) { navigate(it) }`  
  (or equivalent: invoke UseCase and expose its returned FeatureUiState.)
- If the screen needs navigation: implement `ViewModelNav by ViewModelNavImpl()` and handle navigation in the UseCase via the passed lambda.

### 4.9 Screen (Composable)

- `viewModel: FeatureViewModel = hiltViewModel()`
- `val state by viewModel.uiState.featureStateFlow.collectAsStateWithLifecycle()`
- For user actions: `viewModel.uiState.event(FeatureUiEvent.OnXxx(...))`
- If ViewModel is ViewModelNav: use `HandleNavigation(viewModelNav = viewModel, navController = navController)` (or equivalent) so navigation events from the UseCase are applied.

### 4.10 Dependency Injection

- **AppModule:** Provide `ApiRepository` as `ApiRepositoryImpl(apiServices)`.
- **NetworkModule:** Provide OkHttp (with interceptors), Retrofit, and `ApiServices`.
- ViewModels and UseCases are constructor-injected; no extra binding needed for concrete UseCase classes.

---

## 5. Summary: API Flow Checklist

1. **EndPoints.kt** – Add path constant.
2. **model/domain/request/** – Add `<Name>Request.kt`.
3. **model/domain/response/** – Add `<Name>Response.kt`.
4. **ApiServices.kt** – Add suspend function returning `Response<...>`.
5. **ApiRepository.kt** – Add method returning `Flow<NetworkResult<...>>`.
6. **ApiRepositoryImpl.kt** – Implement with `flow { }`, Loading/Success/Error/UnAuthenticated, `extractError()`, `flowOn(Dispatchers.IO)`.
7. **GetXxxUiStateUseCase.kt** – In handleEvent, call `apiRepository.xxx().collect { ... }`, update state and toasts.
8. **XxxUiState.kt** – Add any new state fields and events.
9. **XxxViewModel.kt** – Wire UseCase (no change if event already exists).
10. **XxxScreen.kt** – Use new state/events and optional navigation.

---

## 6. Navigation

### 6.1 Route definition

- **Base:** `NavComposeRoute` (abstract) or `SimpleNavComposeRoute(routeDefinitionValue)` for no-args routes.
- Each screen has an object, e.g. `EditProfileRoute`, with:
  - `routeDefinition: NavRouteDefinition`
  - `createRoute(): NavRoute` for building the route string to navigate to
  - `getArguments(): List<NamedNavArgument>` (empty if no args)
  - `addNavigationRoute(navGraphBuilder, ..., content)` to add the composable to a `NavHost`.

### 6.2 Passing data between screens

- **ContainerViewModel** (or similar) holds `MutableStateFlow<Args?>`.
- Caller: `containerViewModel.setJournalDetailArgs(args)` then `navController.navigate(JournalDetailRoute.createRoute().value)`.
- Destination: `LaunchedEffect(Unit) { containerViewModel.consumeJournalDetailArgs()?.let { viewModel.setInitialArgs(it) } }`.
- Consume once so the same args are not reapplied after process death or recomposition.

### 6.3 App entry points

- **Constants.AppScreen** in `Constants.kt` lists screen keys (e.g. `EDIT_PROFILE`, `JOURNAL_DETAIL`).
- **ContainerActivity** gets `Intent` extra `IS_COME_FOR` and sets NavHost `startDestination` from `AppContainerGraph` based on that key.

---

## 7. Local Data & Configuration

- **DataStore:** `AppPreferenceDataStore` (provided in AppModule) for user/session data.
- **BuildConfig:** `BASE_URL` from build flavors (e.g. Development vs Production); `EndPoints.URLs.BASE_URL` uses it.
- **Interceptors:** Auth token and request metadata are added in `AuthInterceptor` and `RequestInterceptor` (NetworkModule).

---

## 8. Reusable UI & Utils

- **Composables:** Under `ui/compose/common/` (e.g. `GlassTextField`, `WildflowerButton`, `OtpInputField`). Use for consistency.
- **Toasts:** `AppUtils.showSuccessMessage(context, message)` and `showErrorMessage(context, message)`.
- **Extensions:** `ResponseBody?.extractError()` in `utils/ext/Extensions.kt`; use for parsing API error bodies.

---

## 9. Screens Without UseCase (Alternative Pattern)

Some screens (e.g. journal detail) use a **ViewModel that holds state and calls ApiRepository directly** (no separate GetXxxUiStateUseCase). Same ideas still apply:

- State in ViewModel: `MutableStateFlow<DataState>`
- Events: `fun onEvent(event: XxxUiEvent)` with `when (event) { ... }`
- API: `viewModelScope.launch { apiRepository.xxx().collect { ... } }`
- UI: collect state, call `viewModel.onEvent(...)`

Use the UseCase-based pattern for forms and flows that match the “invoke UseCase with context/coroutineScope/navigate” model; use the ViewModel-only pattern when it keeps the feature simpler.

---

## 10. Quick Reference: Adding a New Feature (With API)

| Step | Where | What |
|------|--------|------|
| 1 | EndPoints.kt | Add path |
| 2 | model/domain/request | Add XxxRequest.kt |
| 3 | model/domain/response | Add XxxResponse.kt |
| 4 | ApiServices.kt | Add suspend fun |
| 5 | ApiRepository.kt | Add method |
| 6 | ApiRepositoryImpl.kt | Implement flow + NetworkResult |
| 7 | GetXxxUiStateUseCase.kt | handleEvent → apiRepository.xxx().collect |
| 8 | XxxUiState.kt | DataState + UiEvent |
| 9 | XxxViewModel.kt | Invoke UseCase, expose uiState |
| 10 | XxxScreen.kt | collectAsStateWithLifecycle, event() |
| 11 | XxxRoute.kt | NavComposeRoute / SimpleNavComposeRoute |
| 12 | AppContainerGraph or AppMainGraph | addNavigationRoute + screen |

Use this reference when starting a new project to keep the same structure, data flow, and API integration pattern.
