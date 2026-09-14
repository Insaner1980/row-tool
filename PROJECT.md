# RowTool implementation reference

This document describes the implementation currently present in the repository. It is not a feature roadmap.

Source inspection date: **2026-09-14** (Europe/Helsinki). Inspected branch: **`main`**. Pre-commit baseline HEAD: **`866f53bda73e0940d41520fc1b24cf1d6258ede8`**. The working tree already contained relevant uncommitted production, resource, build, lock/verification, test, stability-snapshot, and `PROJECT.md` changes, plus untracked production, font, debug-manifest, host-test, and Android-test files. **These live working-tree files are included in this reference; The baseline commit alone does not reproduce this inspected state.** The inspected application source contains **48 production Kotlin files**. This reference covers those files, Android resources, persistence, all host/device test sources, build configuration, lock and verification metadata, local check wrappers, CI, and release-support documents. Version values below are repository declarations, not claims about the newest externally available release or a freshly resolved dependency graph. Final local verification and its execution limits are recorded below; source descriptions do not imply device or hosted-CI verification.

Use the implementation paths and test map below when drafting review questions. A documented review boundary is not a confirmed defect. Historical material in `RowTool-koodintarkistus/` contains five prompt files and five result files spanning prompts 001–301; it is supporting history, not authority over current source. This document does not certify that every historical finding remains fixed.

Freshness was established from live files, including untracked files, rather than inferred from commit dates. This task changes only `PROJECT.md` and preserves the pre-existing work. Generated reports, private local configuration, and external shared-engine internals are not implementation authorities. The root `.gitignore` ignores the repository-local `/.kotlin/` tree.

Production Kotlin paths abbreviated as `data/`, `domain/`, `ui/`, `MainActivity.kt`, or `RowToolApplication.kt` are relative to `app/src/main/java/com/finnvek/rowtool/`. Test paths are relative to their stated source set. Build/report paths describe configured outputs unless explicitly called inspected source files.

## Contents

- [Identity and platform](#identity-and-platform)
- [Architecture](#architecture)
- [Technology versions](#technology-versions)
- [Database schema](#database-schema)
- [Domain rules and transaction contracts](#domain-rules-and-transaction-contracts)
- [Screens and behavior](#screens-and-behavior)
- [State ownership, startup, navigation, and failures](#state-ownership-startup-navigation-and-failures)
- [Persistent settings](#persistent-settings)
- [Backup format and replacement semantics](#backup-format-and-replacement-semantics)
- [UI implementation, assets, accessibility, and localization](#ui-implementation-assets-accessibility-and-localization)
- [Detailed UI reference](#detailed-ui-reference)
- [Manifest and privacy boundary](#manifest-and-privacy-boundary)
- [Build and verification commands](#build-and-verification-commands)
- [Test inventory and evidence limits](#test-inventory-and-evidence-limits)
- [Code-review invariants and quality tooling](#code-review-invariants-and-quality-tooling)
- [Build tooling and local check details](#build-tooling-and-local-check-details)
- [Source-of-truth locations](#source-of-truth-locations)
- [Change-impact and review map](#change-impact-and-review-map)
- [External release steps](#external-release-steps)
- [Documentation conflicts and inspection limits](#documentation-conflicts-and-inspection-limits)

## Identity and platform

| Item | Value |
|---|---|
| Display name | RowTool |
| Publisher identity | Finnvek |
| Gradle module | `:app` |
| Namespace / application ID | `com.finnvek.rowtool` |
| Version | `versionCode 1`, `versionName 1.0.0` |
| Android SDK | `minSdk 29`, `compileSdk 37`, `targetSdk 37` |
| Java source/target and Kotlin JVM target | 17 |
| Business model | Store documentation describes a paid Play download; implementation has no billing, subscription, trial, ads, or feature gates. Actual store configuration/publication is external. |
| Supported platform | Android 10/API 29 and later; no non-Android application target |

The product counts rows and rounds for knitting and crochet. There is no account, backend, remote API, synchronization service, onboarding flow, pattern editor, timer, statistics screen, notification flow, or in-app language selector in the inspected application. A target is informational and does not stop counting. Project names need not be unique. The intended store title is `RowTool: Row Counter`; the numeric price is a Play Console decision, not an application constant.

## Architecture

RowTool is a single-activity Compose application with three Navigation Compose destinations. Screen-specific ViewModels expose immutable Flow-backed state and call a small repository layer. `AppContainer` in `RowToolApplication.kt` constructs Room and repositories and supplies the Context-owned DataStore. MainActivity and navigation destinations use the ViewModels' explicit companion factories; no dependency-injection framework is involved.

Room is the persistent source of truth for projects and undo history. Preferences DataStore is the persistent source of truth for app settings and the last-active project. Counter mutations are serialized by a repository `Mutex` and performed in Room transactions; accepted count changes are persisted immediately and retain the newest 100 undo records per project.

Startup remains behind the splash screen until `RowToolAppViewModel` resolves the last-active project. An `IOException` during that resolution falls back to the Projects screen, but Room/database and other non-recoverable failures are not converted into a successful resolved state.

The UI follows a Route/Content split. `ProjectsRoute`, `CounterRoute`, and `SettingsRoute` collect lifecycle-aware `StateFlow` values, consume transient effects, own dialogs and Android platform launchers/side effects, and translate ViewModel operations into action bundles. `ProjectsScreenContent`, `CounterScreenContent`, and `SettingsScreenContent` render state and invoke those actions without resolving repositories or navigation themselves. Projects and Settings use buffered channels for one-shot navigation/messages. Counter messages remain buffered, mandatory return navigation is derived from `CounterUiState`, and haptics use a separate non-replaying `SharedFlow` collected only while the destination is resumed. `RowToolApp` owns a lifecycle-aware FIFO queue for root snackbar presentation.

`ProjectsRoute` stores edit/delete dialog selection as saveable project-ID strings and resolves the current `CounterProject` from the latest Room-backed UI state. Domain model instances are not placed directly in saved-instance state.

## Technology versions

| Component | Declared version |
|---|---|
| Gradle wrapper | 9.7.1 |
| Android Gradle Plugin | 9.4.0 |
| Kotlin / Compose plugin | 2.4.20 |
| KSP | 2.3.11 |
| Compose BOM | 2026.09.00 |
| Activity Compose | 1.13.0 |
| AndroidX Core | 1.19.0 |
| Lifecycle | 2.11.0 |
| Navigation Compose | 2.10.1 |
| Room | 2.8.5 |
| Preferences DataStore | 1.2.1 |
| SplashScreen | 1.2.0 |
| Kotlin serialization | 1.11.0 |
| Coroutines | 1.11.0 |

The version catalog at `gradle/libs.versions.toml` is authoritative for dependency and plugin versions. `app/build.gradle.kts` is authoritative for Android SDK levels, application identity, Java compatibility, lint, Room schema export, and release shrinking.

## Database schema

Database: `rowtool.db`, Room schema version `1`.

### `projects`

| Column | Storage | Meaning |
|---|---|---|
| `id` | non-null text primary key | Locally generated UUID; validated imports may supply another nonblank string |
| `name` | non-null text | Trimmed project name, maximum 60 Unicode code points |
| `counterUnit` | non-null text | `ROWS` or `ROUNDS`; unknown persisted values map safely to `ROWS` |
| `count` | non-null integer | Current count, `0..999999` |
| `startValue` | non-null integer | Reset value, `0` or `1` |
| `targetCount` | nullable integer | Optional target, `1..999999` |
| `repeatLength` | nullable integer | Optional repeat length, `2..999` |
| `isArchived` | non-null integer/boolean | Active/archive state |
| `createdAt` | non-null integer | Epoch milliseconds |
| `updatedAt` | non-null integer | Epoch milliseconds |

The table has a nonunique index `index_projects_isArchived_updatedAt` on `isArchived, updatedAt`. There are no SQL column defaults. All project fields are mandatory constructor arguments; nullable target/repeat must still be supplied by callers.

### `counter_history`

| Column | Storage | Meaning |
|---|---|---|
| `id` | auto-generated integer primary key | History ordering identity |
| `projectId` | non-null text foreign key | Owning project |
| `previousCount` | non-null integer | Count before the mutation |
| `newCount` | non-null integer | Count after the mutation |
| `changeReason` | non-null text | `INCREMENT`, `DECREMENT`, `MANUAL_SET`, or `RESET` |
| `createdAt` | non-null integer | Epoch milliseconds |

`projectId` references `projects.id` with `ON DELETE CASCADE` and `ON UPDATE NO ACTION`. The nonunique `index_counter_history_projectId_id` index on `projectId, id` supports newest-history lookup. History ID is Kotlin `Long` with constructor default `0` to request auto-generation; other history fields have no constructor defaults. Both history counts and timestamp are `Long`, and project ID/reason are `String`; all history columns are non-null. There are no SQL column defaults. Undo consumes the newest history row and does not add another row.

The exported schema is committed at `app/schemas/com.finnvek.rowtool.data.local.RowToolDatabase/1.json`.

Numeric domain ranges are enforced in Kotlin, not by SQL `CHECK` constraints. Counts and timestamps use Kotlin `Long`; `startValue` and `repeatLength` use `Int`. The database is built with `Room.databaseBuilder(...).build()`, with no explicit migration, destructive fallback, encryption library, or custom journal-mode override. There is no project-name uniqueness constraint. Project inserts use `OnConflictStrategy.ABORT`, including bulk replacement inserts; IDs alone enforce project uniqueness. `@Update` returns an affected-row count, while the repository first loads the row inside its transaction. Exported schema identity is `929fe220bd5bf1864da2e8bda3cdeb39`, with two application tables and Room's identity setup queries.

## Domain rules and transaction contracts

Sources: `domain/model/CounterModels.kt`, `domain/model/ProjectValidation.kt`, `domain/counter/CounterCalculators.kt`, `data/repository/CounterRepository.kt`, and `data/local/*Dao.kt` under the production Kotlin root.

| Operation | Exact behavior |
|---|---|
| Create | Counts all active and archived records; rejects creation at 1,000 projects with `ProjectLimitReachedException`. Validates values, generates a UUID, initializes count from start value, and assigns the same clock value to both timestamps. |
| Edit metadata | Revalidates using the existing count; changes name, unit, start value, target, and repeat. Preserves current count, creation timestamp, archive state, and history. A byte-for-byte equivalent candidate returns without advancing `updatedAt` or calling the clock. Missing project returns `null`. Archived metadata can also be edited. |
| Archive/restore | Returns `false` for missing ID. Changes `updatedAt` only when archive state changes; preserves count and history. Repeating the same archive state returns `true` without writing. |
| Delete | Deletes by ID inside a transaction; the foreign key cascades history deletion. Missing ID is effectively a no-op. There is no undo for project deletion. |
| Increment | `min(count + 1, 999999)`. Reaching a target does not disable increment. |
| Decrement | `max(count - 1, 0)`. A start value of 1 does not change this lower bound. |
| Manual set | Accepts `0..999999`; otherwise returns `Invalid` containing `INVALID_COUNT`. |
| Reset | Uses the project's current `startValue`, even if it was edited after creation. |
| Accepted count change | Inserts history, updates count and `updatedAt`, then trims history to the newest 100 IDs in one transaction. |
| Unchanged count | Returns `NoOp`; does not add history or update the timestamp. Applies to boundary taps, setting the same value, and resetting an already-reset count. |
| Undo | Loads highest history ID, restores `previousCount`, updates timestamp, deletes that history row, and returns `Changed` with the original reason. Unknown stored reason falls back to `MANUAL_SET`. Empty history returns `NoOp`. There is no redo stack. |

All modifying methods on the shared `CounterRepository` use its instance-local `mutationMutex` and Room transactions. `mutate` and `undo` first reject missing/archived projects with `ProjectMissing`/`ProjectArchived`. This guard does not prohibit metadata editing, restoring, or deleting archived projects. `BackupRepository` has its own Room transaction and does **not** acquire this mutex; do not describe the mutex as a global lock across both repositories.

`CounterConstants` is the shared public authority for `MIN_COUNT = 0`, `MAX_COUNT = 999_999`, the 100-entry history cap, and the 1,000-project backup/library cap. `ProjectValidation` is the semantic project-field authority. Its public `validate(...)` returns either normalized `ValidatedProjectValues` or the complete set of independent errors: `NAME_BLANK`, `NAME_TOO_LONG`, `INVALID_COUNT`, `INVALID_START_VALUE`, `INVALID_TARGET`, `INVALID_REPEAT_LENGTH`. Its module-internal constants are `MAX_NAME_CODE_POINTS = 60`, `MIN_TARGET_COUNT = 1`, `MIN_REPEAT_LENGTH = 2`, and `MAX_REPEAT_LENGTH = 999`; module-internal `normalizeName`, `nameErrors`, `isTargetValid`, and `isRepeatValid` expose the exact reusable field rules. Name length counts Unicode code points, not UTF-16 code units or grapheme clusters. Target may be below the current count. Optional target/repeat are represented by `null`, not zero.

`ProjectEditorInputValidation` deliberately reuses those domain name/target/repeat rules while retaining UI responsibilities: trimming and returning the save value, parsing target text as `Long` and repeat text as `Int`, ignoring disabled optional fields, and exposing field-specific `nameValid`, `targetValid`, `repeatValid`, plus aggregate `canSave`. The editor does not call full domain validation because counter unit, current count, and start-value validation remain later repository/import responsibilities. Repository validation failures throw `IllegalArgumentException` for create/edit; import validates independently through the enforced backup factory.

Project queries order by `isArchived ASC, updatedAt DESC, id ASC`. The newest-active fallback uses `updatedAt DESC, id ASC`. Consequently count changes, undo, actual metadata changes, and archive transitions affect ordering; merely opening a project does not update its timestamp. Equal timestamps have deterministic lexical ID ordering.

### Concurrency, dispatchers, and cancellation

The single application container shares one counter repository, but its mutex covers only that instance's create/edit/archive/delete/mutate/undo calls. Reads and Flow observation, all preference writes, and backup export/import do not acquire it. Room transactions prevent partial project/history updates; there is no cross-store transaction joining Room and DataStore and no app-wide import lock. Import may order before or after a counter transaction, and subsequent operations see the resulting database; there is no generation token rejecting an operation because it was requested before replacement.

ViewModels launch in `viewModelScope` on Main. Room suspend DAO/transaction APIs handle database execution; `CounterRepository` adds no explicit dispatcher switch. Backup snapshot/encoding and stream decoding/replacement switch to the injected dispatcher (default IO). Settings external-stream I/O also runs on its injected IO dispatcher. The byte-array `prepareImport` overload decodes on the caller's thread.

Counter's atomics are per ViewModel: `routeResolved` distinguishes the first observed project from later emissions, and `unavailableFeedbackHandled` limits unavailable-project feedback to one optional message. Neither atomic owns or acknowledges return navigation; mandatory return is derived by the route from observable state. They are not mutation locks or durable event acknowledgements. Haptic milestone evaluation uses the project snapshot captured from `uiState` before the mutation, together with returned previous/new counts; metadata is not returned in the transaction result.

Import's sequence suppresses stale prepare results/errors; it does not cancel earlier stream reads. `dismissImport` clears only the preview, without incrementing the sequence or cancelling work. The confirm atomic covers replacement attempts within one Settings ViewModel and is released in `finally`; it does not disable every UI action, serialize exports, block new selections, or coordinate other ViewModels. A successful confirm clears the preview unconditionally.

No broad cancellation catch or `NonCancellable` completion section is present. Cancellation propagates through coroutine boundaries; a cancelled coroutine can miss feedback/preference work after a database commit. Stream `use` closes opened resources, but the bounded synchronous read loop has no explicit cancellation poll or special handling for repeated zero-byte reads. Room rollback concerns the replacement transaction, not later selection lookup/write or external export-file contents.

### Repeat and target calculations

- Without repeat length, repeat progress is absent. At count 0 the step is 0; otherwise it is `((count - 1) % repeatLength) + 1`. Completed repeats are integer division `count / repeatLength`.
- Example with repeat length 6: counts `0, 1, 5, 6, 7, 12` produce steps `0, 1, 5, 6, 1, 6` and completed totals `0, 0, 0, 1, 1, 2`. A boundary displays the final step, not the first step of the next repeat.
- Without a target, target progress is absent. Otherwise fraction is `count / targetCount` using floating-point arithmetic clamped to `0..1`, and reached means `count >= targetCount`.
- Both calculators use the absolute count, without subtracting `startValue`. They rely on validated positive optional values rather than validating them themselves.

## Screens and behavior

### Projects

- Lists active projects separately from an expandable archived section.
- Creates rows or rounds projects with a start value and optional target/repeat.
- Opens, renames/edits, archives, restores, and deletes projects; deletion requires confirmation.
- Automatically opens a newly created project.

### Counter

- Displays the project name, row/round label, and a responsive large count.
- Uses the supplied plus, minus, and undo WebP resources in distinct accessible touch targets.
- Prevents decrement below zero and increment above `999,999`.
- Supports persistent multi-step undo, direct count editing, and confirmed undoable reset.
- Shows optional target progress and repeat position/completed-repeat information.
- Provides edit, set-count, reset, archive, and delete actions; archived projects cannot be counted or undone and are redirected to Projects.
- Performs optional light haptics for accepted taps and stronger feedback at a repeat boundary or target.

### Settings

- Theme: system, light, or dark.
- Haptic feedback toggle.
- Keep-screen-awake toggle, applied while an active counter is open.
- Manual JSON export and validated replacement import through the system document picker.
- App version, Finnvek identity, local-data privacy summary, and business-model summary.

The navigation routes are `projects`, `counter/{projectId}`, and `settings`. `Screen` owns `PROJECTS`, `SETTINGS`, the private `COUNTER_BASE`, internal `COUNTER_PROJECT_ID_ARG = "projectId"`, public `COUNTER_PATTERN`, and `counter(projectId)`. Route construction applies `Uri.encode(projectId)`. `RowToolNavHost` registers the same pattern/argument constant as a `NavType.StringType` and reads it through that constant; it does not repeat a literal argument name or manually decode the result. Startup validates the stored last-active ID against Room; if it is missing or archived, the most recently updated active project is selected, or the Projects screen opens when none exists. Archiving or deleting the selected project clears the matching preference immediately when possible, while startup remains the repair path for a stale or failed preference write.

## State ownership, startup, navigation, and failures

### Construction and observation

`RowToolApplication.onCreate()` creates one `AppContainer`. Construction order is Room database, counter repository, preferences repository using the project DAO, then backup repository. Production uses the Context `preferencesDataStore` delegate with `ReplaceFileCorruptionHandler { emptyPreferences() }`. Corrupted preference files therefore reset settings through that handler; Room is not given an equivalent destructive recovery policy. There is no Hilt/Koin service locator or external dependency-injection container.

| Owner | State and lifetime |
|---|---|
| `RowToolAppViewModel` | Activity-owned; preferences use `SharingStarted.Eagerly`. `StartupState` begins unresolved and is assigned once startup lookup completes. |
| `ProjectsViewModel` | Destination-owned; maps Room's ordered list into active and archived lists. Initial state has empty lists and `isLoading = true`. |
| `CounterViewModel` | Destination-owned with key `counter:$projectId`; combines project and history-count observations. Initial state is loading; preferences are a separate state flow. |
| `SettingsViewModel` | Destination-owned; observes preferences and holds a nullable in-memory import preview plus import coordination atomics. |
| Screen observation | Projects `uiState`, Counter `uiState`/`preferences`, and Settings `preferences` use `WhileSubscribed(5000)`; preference initial values are `AppPreferences()`. Counter combines project and undo availability, sets loading false on combined emission. Settings `importPreview` is a `MutableStateFlow` initially null, exposed read-only. Routes call `collectAsStateWithLifecycle()`. |
| Buffered one-shot effects | `ProjectsViewModel` sends `OpenProject` and `ShowMessage`; `SettingsViewModel` sends `ShowMessage` and `ImportComplete`; `CounterViewModel`'s buffered channel currently sends `ShowMessage`. Each uses `Channel.BUFFERED` plus `receiveAsFlow()`. Routes collect with `LaunchedEffect(viewModel)` and keep navigation/message callbacks current with `rememberUpdatedState`. These are one-consumer transient events, not replayed or persisted acknowledgements. |
| Counter return navigation | `CounterRoute` derives `mustReturnToProjects` from non-loading state whose project is missing or archived. A keyed `LaunchedEffect` invokes the current Projects callback. Because the cause remains in `CounterUiState`, an uncompleted navigation is attempted again when the route composition is recreated. There is no navigation effect subtype. |
| Counter haptics | `CounterViewModel` emits `CounterEffect.Haptic` through a separate default `MutableSharedFlow`: `replay = 0`, `extraBufferCapacity = 0`, suspending overflow. `CounterRoute` collects it only inside `repeatOnLifecycle(RESUMED)` and checks exact resumed state plus the current haptic preference before calling the current `View`. Feedback emitted with no resumed collector is dropped and never replayed. |
| Root snackbar presentation | `RowToolApp` remembers a `SnackbarHostState` and an insertion-ordered `mutableStateListOf<String>`. Routes enqueue resolved text; a root presenter consumes only while the activity lifecycle is `RESUMED`. The queue is independent of destination composition but is remembered, not saved across activity recreation/process death. |
| Projects saved UI | Archive expansion, create-dialog visibility, edit ID, and delete ID use `rememberSaveable`. Selected IDs resolve against the latest active/archive lists. |
| Counter saved UI | The nullable `CounterDialog` enum records set/edit/reset/archive/delete dialog selection with `rememberSaveable`. The dialog is rendered only when a project exists. |
| Editor saved UI | Name, unit name, start value, toggles, and numeric text use `rememberSaveable(project?.id)`. Count editor saves `TextFieldValue` using its Saver, keyed by current count. |
| Temporary UI | Dropdown expansion uses `remember`; import preview is ViewModel state, not a `SavedStateHandle` or backup field. Activity recreation and OS process death are different lifetimes. |

Projects/Settings buffered effects and Counter messages are collected by composition-scoped `LaunchedEffect`s, not lifecycle-gated collectors; lifecycle-aware StateFlow collection does not itself gate those channels. Counter haptics are the deliberate exception: their separate lossy flow is destination-lifecycle-gated at `RESUMED`. Root snackbar display is independently activity-lifecycle-gated at `RESUMED`, so receiving a message and visibly presenting it are separate steps. `rememberUpdatedState` refreshes navigation/message/haptic preference callbacks; the haptic collector is also keyed to the current View and lifecycle. No ViewModel uses `SavedStateHandle`. Room/history/settings survive process recreation as disk data; ViewModels, atomics, snackbar queue, import preview, pending work and effects do not. Saveable editor fields and route IDs can participate in Android saved-instance-state restoration; this is not a guarantee for force-stop, abrupt kill, or an unsaved OS state.

### Startup and back-stack contract

1. `MainActivity` installs SplashScreen before `super.onCreate`, then enables obscured-touch filtering and edge-to-edge layout. The splash remains while startup is unresolved; the app NavHost is composed only after resolution.
2. Preferences return a stored ID only if Room says the project exists and is active. Otherwise the newest active ID (or `null`) is returned. Repair uses compare-and-set-style DataStore editing: a newer concurrently stored selection is not overwritten by a stale repair/clear.
3. `RowToolNavHost` owns `rememberNavController()`. Start destination is the concrete encoded counter route when an ID is available, otherwise Projects. The counter destination uses `Screen.COUNTER_PATTERN` and declares/reads `Screen.COUNTER_PROJECT_ID_ARG` as `NavType.StringType`; application code does not repeat the argument literal or manually decode it again. There are no declared external deep links; an internal direct counter start is not an Android deep-link entry point.
4. Projects opens a counter or Settings with `launchSingleTop`. Counter handles both toolbar and system Back through `navigateToProjects()`: pop to an existing Projects entry, or navigate there after inclusive `popUpTo(graph.id)` if no Projects entry exists.
5. Settings toolbar Back calls `popBackStack()`; system Back uses the navigation/activity default, with no Settings-specific BackHandler. Projects has no custom BackHandler and leaving its root follows default activity Back behavior. Successful import navigates to the newest active imported counter or Projects, using inclusive `popUpTo(navController.graph.id)` and `launchSingleTop` to remove the complete previous application flow, regardless of which destination was the graph's dynamic start destination. `DirectCounterNavigationTest.emptyImportAfterDirectCounterFallbackDoesNotLeaveSettingsOnBackStack` is the regression source for this graph-anchor contract.
6. Counter's first observed project is classified once using `routeResolved.compareAndSet`. A valid first project writes the last-active preference; an initially missing project requests the missing message, and an initially archived project requests the archived message. Later missing/archived emissions use the same feedback path. `unavailableFeedbackHandled` permits at most one non-null unavailable message, but explicit archive/delete calls may mark feedback handled without a message so their observer emission cannot add a duplicate. Mandatory navigation is not an effect: after loading, `CounterRoute` derives it from missing/archived state and calls Projects from a keyed `LaunchedEffect`. The persistent cause makes navigation recoverable after route composition or activity recreation. `CounterRouteNavigationTest` covers deletion/archive retry after composition recreation and the valid-project non-return case; `DirectCounterNavigationTest` covers deletion while stopped followed by activity recreation.

### Failure and feedback boundaries

- Preference reads recover from `IOException` with empty defaults; other exceptions propagate. Unknown stored theme names map to `SYSTEM`. Selection writes tolerate `IOException` in the specific startup/create/open/archive/delete repair paths.
- Startup catches only `IOException`. Other failures do not set `isResolved = true`; there is no implemented fatal-startup recovery screen.
- Project/counter write operations catch Android `SQLException` and emit `error_database_write`. Project creation maps the project limit to `error_project_limit`. Validation/programming exceptions are not broadly converted to generic success or swallowed.
- `CounterMutationResult.Invalid` maps to the set-count error. Missing/archived results navigate with the relevant message. A no-op increment can emit the maximum message, although the normal plus control is disabled at maximum. Other no-op operations are silent.
- Changed increment, decrement, and undo may emit haptic feedback on the separate lossy flow. Manual set and reset do not. Only increment uses strong feedback: a positive repeat multiple or crossing from below to exactly the target. Decrement/undo are light. Delivery additionally requires the Counter destination to be `RESUMED` and the current haptic setting to be enabled; feedback completed while no resumed collector exists is discarded. `CounterHapticLifecycleTest` covers navigation-away loss, non-replay on return, fresh resumed delivery, and preference disabling.
- Haptic constants are `CLOCK_TICK` for light, `CONFIRM` for strong on API 30+, and `LONG_PRESS` for strong on API 29. No vibration permission is requested because this uses `View.performHapticFeedback`.
- Missing/archive messages remain one-shot buffered feedback, while the required return to Projects is state-derived and independent of message delivery. Snackbars belong to the app root and have no undo action; persistent counter undo is a separate operation.
- Project and counter dialogs close before dispatching save/confirm operations. They do not remain as retry forms after a database error. The import confirmation has different failure retention, described below.

## Persistent settings

Preferences DataStore name: `rowtool_preferences`.

| Key | Default | Purpose |
|---|---|---|
| `theme_mode` | `SYSTEM` | String enum name: `SYSTEM`, `LIGHT`, `DARK`; unknown string falls back to `SYSTEM` |
| `haptic_feedback_enabled` | `true` | Boolean; counter haptic feedback |
| `keep_screen_awake` | `true` | Boolean; keep the display awake on the Counter screen |
| `last_active_project_id` | absent | String project ID; nullable API removes the key for `null`; validated against Room at startup |

`PreferencesRepository` owns one `defaults = AppPreferences()` value and derives every absent theme/haptic/keep-awake value from it rather than repeating literals. Unknown stored theme strings also fall back to `defaults.themeMode` (`SYSTEM`) without rewriting the stored value. All setters use DataStore `edit`; ordinary setters propagate failures. Conditional selection clear/repair catches only `IOException`. Reads emit empty preferences on `IOException`, which then map through the same defaults; corruption is separately handled by the application's replacement handler. There is no extra per-key repair layer. Theme/haptics/keep-awake are unchanged by import; last-active selection is updated after database replacement. These settings are not part of the JSON project backup.

## Backup format and replacement semantics

`BackupFormat` is the shared internal format-identity authority: `CURRENT_SCHEMA_VERSION = 1` and `APPLICATION_ID = "RowTool"`. Export writes both values and import compares both values exactly. The current decoder therefore accepts schema version 1 only; the name `CURRENT_SCHEMA_VERSION` does not mean that every future or historical version is automatically importable.

The UTF-8 JSON root contains `schemaVersion`, `application`, `exportedAt`, and `projects`. Each project contains the persisted project fields listed in the Room schema. Undo history and DataStore settings are intentionally excluded.

Import accepts unknown JSON keys for forward-compatible optional additions, but rejects malformed JSON, files over 5 MiB, unsupported schema versions, another application identity, more than 1,000 projects, duplicate/blank IDs, unknown counter units, and invalid project values. The UI presents active/archive counts and requires confirmation before replacement. Confirmed replacement clears projects and undo history and inserts the validated projects in one Room transaction, then resolves the last-active imported project.

The Storage Access Framework supplies file access, so export/import requires no broad storage permission.

### Exact JSON and I/O contract

Example of a valid payload (illustrative data, not bundled project content):

```json
{
  "schemaVersion": 1,
  "application": "RowTool",
  "exportedAt": 1788825600000,
  "projects": [
    {
      "id": "example-project",
      "name": "Garden scarf",
      "counterUnit": "ROWS",
      "count": 42,
      "startValue": 0,
      "targetCount": 120,
      "repeatLength": 6,
      "isArchived": false,
      "createdAt": 1788820000000,
      "updatedAt": 1788825000000
    }
  ]
}
```

`BackupFile` and `BackupProject` declare no default property values. All shown fields must be present, including optional-value keys whose values may be `null`; `explicitNulls = true`. Production `encode` emits compact JSON; formatting above is for readability. `ignoreUnknownKeys = true` allows extra keys, not absent required fields or arbitrary schema changes.

Imported IDs must be nonblank and distinct but are not required to be UUIDs, trimmed, or checked against a separate length limit. Imported names are normalized by `ProjectValidation`; units must match an enum name exactly. Timestamps are accepted as `Long` without positivity or chronology checks. A nonpositive export timestamp is shown as an unknown date in preview. An empty project list is valid and confirmed import clears the library.

`BackupCodec.MAX_BACKUP_MIB` is the internal displayed-size authority and currently equals `5`; private `MAX_BACKUP_BYTES` derives `MAX_BACKUP_MIB * 1024 * 1024`, exactly **5,242,880 bytes**. The byte-array decoder rejects anything larger before parsing. The stream decoder allocates that byte threshold plus one and reads only until EOF or the extra byte proves oversize. UTF-8 decoding is strict (`throwOnInvalidSequence = true`); malformed UTF-8 maps to `MALFORMED_JSON`. Validation order after parsing is schema version, application identity, project-count/duplicate-ID precedence, then per-project ID and domain values. Individual failures collapse to `INVALID_PROJECT`; structural/type/missing-field parsing failures map to `MALFORMED_JSON`. No existing rows are changed during preparation.

| Flow | Implementation detail |
|---|---|
| Export picker | `CreateDocument("application/json")`; date-based filename from `backup_file_name` and ISO local date. A cancelled picker does nothing. |
| Export snapshot | `BackupRepository.exportJson()` reads all projects in DAO order inside a Room transaction on its injected IO dispatcher and encodes afterward. No history/preferences are exported. |
| Export write | `SettingsViewModel` opens the selected URI with mode `"w"`, writes UTF-8 with a buffered writer, and closes it with `use`. File-not-found, IO, security, or SQL exceptions emit export failure. No provider-independent rollback or cleanup of a partially written external file is implemented. |
| Import picker | `OpenDocument()` with `application/json` and `text/json`. Opens the selected stream on IO and closes it with `use`; no persisted URI grant is requested. |
| Competing selections | Each prepare request increments `importRequestSequence` and clears old preview. Completion/error from an older request is ignored. This orders selections; it is not a durable import queue. |
| Preview | Contains the validated backup and active/archive counts. The route formats export date with localized medium-date/short-time formatting and shows total/active/archive plurals plus replacement warning. |
| Confirm | `importInProgress.compareAndSet(false, true)` suppresses duplicate concurrent confirms; `finally` resets it. `replaceWith` consumes the already validated object; it does not decode or revalidate the object again. |
| Database failure | Android `SQLException` inside replacement returns `DATABASE_WRITE_FAILED`; Room rolls back deletes/inserts. ViewModel retains preview and shows the write error, permitting another confirmation. |
| Database success | Clears preview, sends success message, then `ImportComplete`. Last-active selection uses newest active with the DAO tie-break. Failure of the following preference write with `IOException` does not undo committed projects. |

`ValidatedBackup` is a constructor-enforced validation wrapper. It is a public `data class` with a private constructor and `@ConsistentCopyVisibility`, so generated `copy` retains the constructor's private visibility. Its only construction path is `ValidatedBackup.create(exportedAt, projects)`, which returns `BackupDecodeResult`: it checks the 1,000-project cap and duplicate IDs, rejects blank IDs, reuses `ProjectValidation.validate` for every project, normalizes accepted names into copied immutable `CounterProject` values, and builds a new mapped list so later mutation/clearing of the caller's collection cannot change `backup.projects`. IDs remain otherwise untrimmed and need not be UUIDs; timestamps remain unrestricted `Long` values. `BackupCodec` converts DTOs and delegates to this same factory. `BackupRepository.replaceWith` can trust the enforced `ValidatedBackup` type and maps it directly to entities without a second validation pass. Export does not apply an explicit byte-size limit. No app-level encryption, checksum, signature, compression, merge import, automatic backup schedule, or history export is implemented. A user-selected document provider controls the destination, potentially including its own cloud storage; the app itself has no network upload path.

## UI implementation, assets, accessibility, and localization

Routes own state collection, effects, dialogs, navigation callbacks and platform integration. Screen content receives explicit state/action objects. The project editor is shared by Projects and Counter; dropdown rendering is shared by both menus. Three small shared UI authorities avoid duplicating specific presentation contracts without constituting a generic design-system framework:

- `RowToolDialogActionColors.kt` owns ordinary secondary text-button colors and destructive error-container/on-error-container text-button colors. Project and count editor actions use the ordinary helper; the shared confirmation dialog uses ordinary or destructive confirmation plus ordinary dismissal; Settings' richer replacement-import dialog calls both helpers directly.
- `RowToolSectionHeadingText.kt` owns locale-aware uppercase, `labelSmall`, secondary-colored section-label text. Projects uses it for active and archived headings; Settings uses it for each settings section. It does not add heading semantics and is not used for the Counter toolbar title.
- `RowToolConfirmationDialog.kt` owns the common title/message/confirm/cancel `AlertDialog` shape and optional destructive confirm styling. Projects uses it for deletion; Counter uses it for reset, archive, and deletion. Settings retains its custom import-summary dialog.

Source constraints below are not measured-device acceptance evidence.

## Detailed UI reference

### Screen composition and responsive behavior

| Surface | Current layout and interaction |
|---|---|
| Projects toolbar | App name and Settings icon; background-colored TopAppBar. No bottom navigation or project-detail destination. |
| Projects loading | Centered progress indicator, no empty-library content while loading. |
| Empty library | Scrollable centered title, explanatory body, New project button and privacy text. No floating creation control when both lists are empty. |
| Nonempty library | Centered, maximum 600 dp LazyColumn; 20 dp horizontal, 12 dp top, 104 dp bottom content padding. Floating untinted plus image is 64 dp artwork in a 72 dp touch box, with New project description. No extended text FAB. |
| Active items | Uppercase localized header, labelSmall/secondary with 20 dp top and 8 dp bottom padding; individually keyed lazy items (`project.id`). Active row opens the project; separate overflow offers edit/archive/delete. |
| Archived items | At least 48 dp expansion row, localized total and show/hide description, expand icon rotated 180 degrees when open. AnimatedVisibility contains a Column in one lazy item, using `key(project.id)`; expanded rows are not individually virtualized. Restore/edit/delete remain available; row body does not open the counter. |
| Project rows | Cardless rows with 14 dp vertical padding and top-aligned content. Name uses titleLarge, at most two lines with ellipsis; plural count uses titleMedium/secondary; optional target/repeat use bodySmall/onSurfaceVariant. Archived status is explicit text. Between rows: 1 dp divider at onSurface alpha 0.15. The legacy `ProjectCardActions` name remains, but no Card surface is rendered. |
| Project operations | Create opens the new counter after persistence. Edit/delete selection is resolved by saved ID. Projects archive/restore is immediate and stays on Projects; Counter archive requires confirmation and returns to Projects. Delete requires confirmation in both routes. |
| Counter toolbar | Project name uppercased with the first configuration locale, bold titleLarge, heading semantics, one line/ellipsis and 8 dp end padding. Back, Settings, then overflow edit/set/reset/archive/delete. Overflow is enabled/expanded only for an active project. |
| Counter loading/missing | Null project shows a centered spinner only while `isLoading`; after loading, null renders no workspace while state-derived return navigation runs. Archived content, if briefly present, disables count controls/edit/menu before return. |
| Counter workspace | Maximum 600 dp outer width; scroll modifier precedes minimum viewport height, then 20 dp side and 24 dp top/bottom padding. Centered column fills the available height where content fits and scrolls when taller. |
| Counter hierarchy | Unit label at 25 sp; editable number in a minimum 128 dp region; optional target status at 19 sp plus 4 dp progress bar; optional repeat text (titleMedium) and completed-repeat count (bodySmall when positive); controls after 32 dp top space. No repeat badge. |
| Target/repeat layout | Target status has 8 dp top gap; bar follows 8 dp gap, capped at 360 dp, primary progress over surfaceContainerHighest. Repeat has 16 dp top gap and completed-repeat text 4 dp. Target reached is textual; counting remains possible above target. |
| Count sizing | Bases: 115 sp for 0–3 digits, 102 for 4, 90 for 5, 76 otherwise; multiply by `clamp(width / 340, 0.78, 1)` and, above font scale 1.3, `1.3 / fontScale`. Grouping separators do not count (`Char.isDigit`). Uses displayMedium with Bold, tabular-number feature `tnum`, one line, centered onSurface text; clickable text padding is 8/4 dp. |
| Counter controls | Maximum 360 dp column. Minus and plus share a SpaceBetween row with equal 144 dp reference touch sizes, artwork 123/125 dp. Undo is centered below, reference size 92 dp, 16 dp gap. Minus optical Y offset is 1 dp. Scale is `clamp(availableWidth / 288, 48 / 144, 1)`; very narrow widths below 96 dp cannot accommodate two 48 dp targets without exceeding width. |
| Image interaction | Click box is at least 48 dp; artwork capped at requested touch size; ContentScale.Fit and no tint. 90 ms tween changes scale to 0.982 and translates down by 1.5 dp when pressed. No ripple; disabled alpha 0.46 with disabled click semantics. |
| Settings | Maximum 600 dp LazyColumn with 32 dp bottom padding. Uppercase labelSmall/secondary heading rows use 16 dp horizontal and 8 dp vertical padding. Radio/toggle/action rows use 16/12 dp padding and at least 48 dp height; section dividers use outlineVariant. No section Cards. Toolbar title uses headlineMedium. |
| Settings contents | Appearance: system/light/dark radios. Counter: haptic/keep-awake switches with summaries. Data: export/import rows with summaries. About: app name, installed package version, Finnvek, privacy and business-model summaries, in a 16/12 dp padded column with 8 dp gaps. No busy-state UI disables these actions during I/O. |
| Shared menus | Material dropdown item with 22 dp decorative image tinted onSurface; menu expansion is remembered in composition and closes before dispatching its action. |
| Root snackbar | Shared `SnackbarHost`; horizontal/bottom safeDrawing insets and 72 dp bottom padding on all destinations. `RowToolApp` appends resolved strings to a remembered FIFO list. A single presenter calls `showSnackbar` only inside activity `repeatOnLifecycle(RESUMED)`. Dropping below resumed cancels and removes the active snackbar from the host but leaves the queue head; returning to resumed presents it again with a fresh default visible duration. Messages arriving while paused/stopped wait, identical messages are not deduplicated, completed entries do not replay, and destination removal/navigation does not cancel the root-owned queue. The queue and host are not saveable, so activity recreation/process death may lose an already consumed-but-not-completed root message. There is no action label or explicit duration override. Fixed clearance is not a measured non-overlap guarantee. |

Scaffolds provide content insets. No alternate two-pane/tablet navigation or orientation-specific screen exists. Projects and Counter use the shared 20 dp phone padding; Settings pads individual rows by 16 dp. `RowToolDimens` contains spacing tokens 4/8/12/16/20/24/32 dp, a 600 dp content cap, the shared 48 dp `MinimumTouchSize`, and Counter-specific sizing tokens. `ArchivedProjectsHeader`, settings rows, `CounterImageButton`, counter scaling, and tests all reuse the same minimum-touch authority. Counter font compensation limits number growth beyond font scale 1.3; it does not cap other text.

### Editors and accessibility semantics

`ProjectEditorDialog` is a custom `Dialog(usePlatformDefaultWidth = false)`. Its Surface modifier order is `fillMaxWidth(0.92f) → wrapContentWidth() → widthIn(max = 560.dp) → heightIn(max = windowHeight * 0.88) → imePadding()`. The wrap modifier relaxes the exact fill-width constraint before the cap. Shape, container/content/title colors and tonal elevation come from `AlertDialogDefaults`, not a hard-coded 16 dp dialog shape. The entire column, including actions, scrolls with 24 dp padding and 16 dp gaps.

New defaults are empty name, ROWS, start 0, target/repeat disabled. Choice FlowRows wrap FilterChips with 8 dp gaps. Name and enabled numeric fields are filled TextFields with large (16 dp) shapes, surfaceVariant background, transparent ordinary indicators, secondary focused label/cursor. Error container and readable error foreground roles are explicit. Optional toggle rows own Role.Switch and give text remaining width with weight; child Switch callbacks are null. Disabled optional text remains saved in the editor but emits null. Numeric parsing uses `toLongOrNull`/`toIntOrNull`, no input truncation, grouping parser or automatic whitespace cleanup. Name is trimmed and checked by code-point count. Save requires every enabled input to be valid; invalid enabled target/repeat blocks independently. Actions wrap in a FlowRow aligned end, 8 dp horizontal/12 dp vertical gaps and 8 dp top padding.

`CountEditorDialog` uses AlertDialog and an OutlinedTextField, selects the whole current count via saveable TextFieldValue, and requests focus. Number keyboard/Done are hints, not an installed keyboard submit callback. Save is disabled outside 0..999999 or on parse failure. Focused border/label/cursor use secondary; errors explicitly use errorContainer/onErrorContainer. All ordinary dialog Save/Cancel/reset/archive actions use secondary text. Project delete and replacement-import confirmation use errorContainer/onErrorContainer; reset/archive remain ordinary actions. Dismissal happens before project/count mutations; import failure can retain its preview.

- Count exposes Role.Button, heading, formatted state description and labeled OnClick set-count semantics (not a separate custom-actions list). Disabled archived count has no custom enabled callback.
- Counter image boxes merge descendants, name their action and expose disabled state; inner images have null descriptions. Plus disables at 999999, minus at zero, undo without history, all three when archived.
- Counter title, editor title and Settings section labels have explicit heading semantics. Projects labels are styled as section labels but do not explicitly call heading().
- Project row retains deprecated `isContainer` semantics under a local suppression to preserve its former Card accessibility container. Active row and overflow are distinct actions; archived row does not claim a count-opening action.
- Settings radio/switch rows own selectable/toggleable semantics; child controls have null callbacks. The code does not add a separate selectableGroup modifier.
- Target completion, archive state, repeat totals, validation errors and selected controls communicate state beyond color. Localized/pluralized descriptions are source evidence, not a completed TalkBack audit.

CounterRoute sets `View.keepScreenOn` only for enabled preference plus active project and clears it in DisposableEffect disposal. It is scoped to composition, with no explicit lifecycle-resumed test. MainActivity enables edge-to-edge and updates status/navigation icon lightness from theme selection. XML launch themes use system day/night resources before DataStore is read, so splash and an explicitly selected theme may differ temporarily. XML platform font is sans; Compose typography is separate. RTL is declared, but the back vector has no explicit autoMirrored attribute. Runtime focus, TalkBack traversal, all font scales/locales, system bars and touch filtering were not exercised by this documentation task.

### Material color roles

Explicit mappings from `Color.kt` and `Theme.kt`; values are opaque RGB hex. Roles not overridden there retain Material 3 defaults.

| Role | Light | Dark |
|---|---|---|
| primary / onPrimary | `#C45100` / `#FFFFFF` | `#C45100` / `#FFFFFF` |
| primaryContainer / onPrimaryContainer | `#D4722A` / `#161610` | `#D4722A` / `#161610` |
| secondary / onSecondary | `#394B18` / `#FFFFFF` | `#93AE4F` / `#161610` |
| secondaryContainer / onSecondaryContainer | `#D0DDB5` / `#2E2A1E` | `#3A4020` / `#E8E4D0` |
| tertiary / onTertiary | `#9A7B18` / `#161610` | `#C9A435` / `#161610` |
| tertiaryContainer / onTertiaryContainer | `#E8DFB5` / `#2E2A1E` | `#3A3520` / `#E8E4D0` |
| error / onError | `#8B3030` / `#FFFFFF` | `#C44D4D` / `#FFFFFF` |
| errorContainer / onErrorContainer | `#EAD0D0` / `#2E2A1E` | `#3A2020` / `#E8E4D0` |
| background / onBackground | `#E8E4D0` / `#2E2A1E` | `#1E1E12` / `#E8E4D0` |
| surface / onSurface | `#D2CDB5` / `#2E2A1E` | `#2E2E20` / `#E8E4D0` |
| surfaceVariant / onSurfaceVariant | `#BBB59A` / `#4C4634` | `#3A3A2A` / `#B8B4A0` |
| surfaceContainerLowest | `#E8E4D0` | `#1E1E12` |
| surfaceContainer | `#C8C3A8` | `#3A3A2A` |
| surfaceContainerLow | `#D2CDB5` | `#2E2E20` |
| surfaceContainerHigh | `#C8C3A8` | `#3A3A2A` |
| surfaceContainerHighest | `#A49D80` | `#454535` |
| outline / outlineVariant | `#4A473C` / `#C5C0A8` | `#A8A491` / `#3A3A2A` |
| inverseSurface / inverseOnSurface | `#2E2E20` / `#E8E4D0` | `#D2CDB5` / `#2E2A1E` |
| inversePrimary | `#D4722A` | `#C45100` |
| scrim | `#000000` | `#000000` |

### Typography, shapes, and previews

All 15 typography styles use the bundled variable Outfit font at `app/src/main/res/font/outfit.ttf`. `Type.kt` creates OptionalLocal font entries with explicit variation weights 400/500/600/700/800, each paired with a device `sans-serif` fallback. No downloadable font provider is used. ExtraBold 800 is available in the family but no configured typography style requests it. Custom line heights are not specified.

| Style | Weight | Size sp | Letter spacing sp |
|---|---|---|---|
| displayLarge | Bold | 57 | -0.25 |
| displayMedium | Bold | 45 (counter overrides responsively) | 0 |
| displaySmall | SemiBold | 36 | 0 |
| headlineLarge | Bold | 32 | 0 |
| headlineMedium | SemiBold | 28 | 0 |
| headlineSmall | SemiBold | 24 | 0 |
| titleLarge | SemiBold | 22 | 0 |
| titleMedium | SemiBold | 16 | 0.15 |
| titleSmall | Medium | 14 | 0.1 |
| bodyLarge | Normal | 16 | 0.5 |
| bodyMedium | Normal | 14 | 0.25 |
| bodySmall | Normal | 12 | 0.4 |
| labelLarge | SemiBold | 14 | 0.1 |
| labelMedium | SemiBold | 12 | 0.5 |
| labelSmall | SemiBold | 11 | 1.5 |

`Color.kt` now contains only the palette values consumed by `Theme.kt`; previously unused alternate-background, secondary-muted, disabled, and success declarations are absent. Material roles not explicitly passed (including surfaceTint, surfaceBright/dim and fixed accent roles) retain library defaults; no dynamic-color branch exists.

Custom shape radii are small 8 dp, medium 12 dp, and large 16 dp; other shape slots retain defaults. Spacing tokens are 4, 8, 12, 16, 20, 24, and 32 dp, with 600 dp maximum content width.

`ui/RowToolPreviews.kt` provides eight `@Preview` configurations: empty Projects, active/archived Projects, basic Counter, target Counter, repeat Counter, reached-target dark Counter, light Settings, and dark Settings. Preview projects are sample-only values constructed by `previewProject`; they do not seed the database. Previews call content composables with inert actions and do not exercise repositories, lifecycle effects, file pickers, or navigation.

### Localization and asset inventory

There are **100 string keys and 8 plural groups in each of 11 locale resource files** at inspection time. Base `values/strings.xml` is English. The other locales are Finnish `fi`, Swedish `sv`, German `de`, French `fr`, Spanish `es`, Portuguese `pt`, Italian `it`, Norwegian Bokmål `nb`, Danish `da`, and Dutch `nl`. `locales_config.xml` lists exactly these locale codes. XML parsing and resource-key parity were verified in this task; neither is a linguistic review. Plural groups: `counter_target_rows`, `counter_target_rounds`, `row_count`, `round_count`, `repeat_count`, `project_count`, `active_project_count`, `archived_project_count`. Every file supplies one/other; es/fr/it/pt also supply many.

Six limit messages are formatted at the call site instead of embedding independent bounds: `backup_import_too_large` receives `BackupCodec.MAX_BACKUP_MIB`; `project_name_too_long`, `project_target_error`, and `project_repeat_error` receive `ProjectValidation` limits (with target maximum from `CounterConstants.MAX_COUNT`); `counter_set_error` and `counter_max_reached` receive `CounterConstants.MIN_COUNT`/`MAX_COUNT` as applicable. All 11 catalogs retain the same placeholders. Android resource formatting localizes the grouped large-count placeholders (`%,d`) for target/set/maximum messages; the MiB/name/repeat values use ordinary integer placeholders. Base `project_name_too_long` alone has a narrow `tools:ignore="PluralsCandidate"` because its numeric argument is the fixed validation bound 60, not a varying quantity needing plural selection. This is not a general plurals policy. `LimitMessageResourcesTest` substitutes deliberately different bounds in every locale and checks argument use, localized grouping, and absence of unresolved/old literal limits.

Counter count and target use `NumberFormat.getIntegerInstance()` remembered against configuration; project list counts/summaries and repeat text use Android string/plural formatting. Count input uses plain `Long.toString()` and Kotlin number parsing rather than parsing locale-specific grouping separators. Import preview dates use device-locale/time-zone DateFormat; exported filenames are `rowtool-backup-YYYY-MM-DD.json` using local ISO date. Counter title and Projects/Settings section capitalization use the first configuration locale, even when displayed resources fall back to another language (the test sources include Turkish casing with Finnish fallback). This is not an additional supported Turkish translation. These formatting paths should be considered separately when reviewing locale behavior.

The three root and packaged WebP pairs were SHA-256-compared and match. Vector action drawables include archive, back, delete, edit, expand, more, restore, and settings (24 dp); the old add vector is absent from the working tree; adaptive launcher definitions exist for regular/round icons, including API 33 monochrome support. Source manifest removes `androidx.profileinstaller.ProfileInstallReceiver` during merging. Launcher foreground reuses the plus WebP through an 18% inset drawable. Base adaptive icons suppress MonochromeLauncherIcon because API 33 variants provide the explicit monochrome plus silhouette. Day/night launcher and splash backgrounds are #E8E4D0/#1E1E12, and XML system_bar is #E8E4D0/#161610. Artwork is also used for Projects creation. Binary pair equality was checked in this task; this does not establish artwork licensing.

Asset paths: root `counter_plus_button.webp`, `counter_minus_button.webp` and `counter_undo_button.webp` each have a same-named packaged copy under `app/src/main/res/drawable-nodpi/`. The font is `app/src/main/res/font/outfit.ttf`; launcher definitions are `app/src/main/res/mipmap-anydpi/` and `app/src/main/res/mipmap-anydpi-v33/`. The root artwork originals are not Android source-set resources.

## Manifest and privacy boundary

`app/src/main/AndroidManifest.xml` contains no `<uses-permission>` entries. In particular, the app does not request Internet, camera, microphone, notification, or storage access. The only app-declared exported production component is launcher `MainActivity`; final merged component inventory requires an artifact inspection.

The application sets `android:allowBackup="false"` and `android:usesCleartextTraffic="false"`. `backup_rules.xml` and `data_extraction_rules.xml` exclude app files, databases, shared preferences, and external files from system backup and device transfer. Manual JSON export is the supported portability path.

Production source has no network client, Firebase, analytics, advertising, billing, crash-reporting, WebView, FileProvider or notification implementation. Test/build/scanner configurations include networking libraries and must not be confused with runtime features. The stability plugin's runtime dependency is present in the lockfile; no app telemetry call path is implemented.

This is a production-source boundary. Build/scanner tools do access external services and are not part of the offline product behavior. The source manifest alone does not prove every component or permission in a merged release artifact. There are no app-defined services, content providers, or additional activities in the source manifest; the explicit profile-installer removal is a merge directive, not a runtime receiver declaration.

The `app/src/debug/AndroidManifest.xml` overrides the Compose test host `androidx.activity.ComponentActivity` theme to `Theme.RowTool` with `tools:replace="android:theme"`. This is a debug overlay, not an extra production application screen. No merged manifest or APK/AAB was inspected in this task.

`MainActivity` also rejects obscured-window touches: the decor view enables `filterTouchesWhenObscured`, and `dispatchTouchEvent` drops events marked `FLAG_WINDOW_IS_PARTIALLY_OBSCURED`. Changes to activity-level touch handling must preserve this tapjacking boundary.

## Build and verification commands

Use JDK 21 for the full local build and test commands and Android SDK Platform 37. Application Java source/target compatibility and the Kotlin JVM target remain 17. The primary Android CI workflow also runs Gradle on Temurin JDK 21, while the CodeQL build runs on Temurin JDK 17. On Windows PowerShell:

```powershell
.\gradlew.bat :app:assembleDebug
.\gradlew.bat --no-configuration-cache :app:testDebugUnitTest
.\gradlew.bat :app:lintDebug
.\gradlew.bat :app:kspDebugKotlin
.\gradlew.bat :app:assembleDebugAndroidTest
```

Additional configured checks (run from repository root with the same JDK/SDK prerequisites):

```powershell
.\gradlew.bat :app:lintRelease
.\gradlew.bat :app:compileDebugAndroidTestKotlin
.\gradlew.bat :app:ktlintCheck
.\gradlew.bat :app:detekt
.\gradlew.bat :app:stabilityCheck
.\gradlew.bat --no-configuration-cache :app:createDebugUnitTestCoverageReport
.\gradlew.bat :app:dependencyCheckAnalyze
.\tools\lc.ps1
.\tools\tests\android-check-routing.tests.ps1
.\tools\tests\sonar-token.tests.ps1
```

`ktlintFormat` is a source-mutating formatter, not a documentation verification command. JDK 21 is the repository-documented full build/test baseline; there is no Java toolchain block proving all Gradle tasks work on each other JDK. SDK path must be available through normal AGP configuration (for example ANDROID_HOME or local.properties), with platform 37 installed. Test coverage tasks explicitly opt out of configuration cache; use the shown flag to avoid relying on the global enabled default.

Run device/emulator tests only when a target is available:

```powershell
.\gradlew.bat :app:connectedDebugAndroidTest
```

Release tasks are configured with code/resource shrinking:

```powershell
.\gradlew.bat :app:assembleRelease
.\gradlew.bat :app:bundleRelease
```

No release `signingConfig` or key reference is declared in the inspected build files; configure signing externally before treating an AAB as Play-ready. In a Git checkout, also run `git diff --check` before handoff.

Test sources are split between host-side JUnit tests under `app/src/test/` and AndroidJUnit4/Compose device tests under `app/src/androidTest/`. The host suite includes pure domain/presentation tests plus Robolectric-backed Room, repository, DataStore, resource, and ViewModel tests. The device suite covers isolated screen semantics, lifecycle delivery, and full-activity navigation/persistence flows. `assembleDebugAndroidTest` compiles the device tests; only `connectedDebugAndroidTest` executes them. CI in `.github/workflows/android.yml` runs the debug build, host suite, debug lint, and Android-test compilation with JDK 21. These command descriptions do not assert a result for a run. Final local verification is recorded below. Instrumentation execution, helper regressions, and hosted CI are separate checks.

## Test inventory and evidence limits

The inventory below counts current live source annotations independently of execution results. Host source contains **16 Kotlin files**, of which **14 files/classes contain 111 `@Test` methods**; the other two are helpers. **Seven Robolectric classes contain 49 methods** and **seven other JVM classes contain 62 methods**. Android-test source contains **16 Kotlin files**, of which **13 files/classes contain 54 `@Test` methods**; the other three are helpers. Every Android test class uses a Compose test rule. `DialogReadabilityTest` runs its four methods for two light/dark parameters, so the runner expands the 54 source methods to **58 cases** before filtering. Looped matrices inside a method are not counted as additional methods. Paths in this table are relative to the indicated source-set package root `java/com/finnvek/rowtool/`.

| Source set / file | Tests | Source cases |
|---|---:|---|
| test: `domain/counter/CounterCalculatorsTest.kt` | 7 | Absent progress, repeat boundary matrix, below/exact/above target, clamping. |
| test: `domain/model/ProjectValidationTest.kt` | 14 | Blank/trimmed/Unicode-length names; start, count, target, repeat limits; aggregate errors; persisted-unit fallback. |
| test: `data/repository/CounterRepositoryTest.kt` | 19 | Mutations, boundary no-ops, undoable manual/reset, 100-history cap, cascade deletion, archived/missing rejection, rapid and concurrent increments, project cap, metadata changes, and no-op edit preservation. |
| test: `data/repository/BackupCodecTest.kt` | 18 | Roundtrip, extra/missing fields, exact version/app/unit checks, invalid numeric values, duplicate/project-cap precedence, malformed JSON/UTF-8, displayed 5 MiB boundary, early bounded-stream rejection, and trimmed names. |
| test: `data/repository/ValidatedBackupTest.kt` | 9 | Factory acceptance boundaries, normalization, blank/duplicate IDs, project-count limit, invalid domain fields, and defensive snapshot of a mutable caller collection. |
| test: `data/repository/BackupAndPreferencesRepositoryTest.kt` | 16 | Defaults including unknown-theme fallback, valid/missing/archived startup selection, compare-and-set clear, preference failures, validation boundary, atomic replacement/rollback/history clearing, empty/archived-only imports, imported ordering, and export exclusion of history. |
| test: `test/InMemoryPreferencesDataStoreTest.kt` | 1 | Concurrent updates to the test DataStore implementation are serialized. |
| test: `ui/LimitMessageResourcesTest.kt` | 1 | All 11 locale catalogs consume supplied name/target/repeat/count/backup bounds, group large counts by locale, and leave no stale literals/placeholders. |
| test: `ui/screens/counter/CounterPresentationTest.kt` | 6 | Control scaling at ordinary/extreme widths and minimum targets, unit resource mapping, count sizing, SDK-dependent haptic constant, and unavailable-project message handling without navigation effect. |
| test: `ui/screens/counter/CounterViewModelTest.kt` | 4 | Deletion publishes missing state without a navigation effect; archived state/message remains observable; haptics do not replay without a collector; strong/light classification and silent manual/reset behavior. |
| test: `ui/screens/projects/ProjectEditorInputValidationTest.kt` | 7 | Domain/editor name and optional-bound parity, normalization, ignored disabled values, combined invalid input, and independently invalid enabled target/repeat. |
| test: `ui/screens/projects/ProjectsViewModelTest.kt` | 1 | Dedicated project-limit message. |
| test: `ui/screens/settings/SettingsViewModelTest.kt` | 1 | Latest selected import wins when file reads finish out of order. |
| test: `ui/theme/ThemeTest.kt` | 7 | Selected palette/surface mappings, primary-container consistency, tertiary/project-menu/error contrast, dialog accent contrast non-regression. Not a full rendered palette certification. |
| androidTest: `ui/RowToolFlowTest.kt` | 3 | Create/count/correct/undo/activity recreation, editable count and undo, reset/delete confirmation. |
| androidTest: `ui/CounterHapticLifecycleTest.kt` | 1 | A mutation completing after navigation does not replay haptics; a fresh resumed mutation delivers once; disabling preference suppresses delivery. |
| androidTest: `ui/CounterRouteNavigationTest.kt` | 3 | Missing/archive return retries after route composition recreation when navigation did not complete; a valid project stays on Counter. |
| androidTest: `ui/DirectCounterNavigationTest.kt` | 3 | Deleted direct-start project returns after stop/recreation; empty import clears Settings/direct-counter flow through graph anchor; ordinary direct-counter Back leaves no stale Counter. |
| androidTest: `ui/screens/projects/ProjectsScreenContentTest.kt` | 9 | Empty/loading/active opening, 72 dp image creation action, active/archived menu identity after reorder, archive expansion/restore/delete, large text and metadata, locale-aware casing. |
| androidTest: `ui/screens/projects/ProjectEditorDialogTest.kt` | 8 | Toggle ownership/IME hints, invalid pasted target, defaults/save/edit/cancel, saveable restoration including disabled numeric text, narrow large French/Italian forms. |
| androidTest: `ui/screens/counter/CounterScreenContentTest.kt` | 8 | Control hierarchy/roles/targets/callbacks, maximum/zero/archive disablement, count edit action, target/repeat values and visibility, locale title, 320/400 dp and 1×/2× text in both themes with 0/999999 counts. |
| androidTest: `ui/screens/counter/CounterImageButtonTest.kt` | 2 | Enabled description/callback; disabled semantics and ignored clicks. |
| androidTest: `ui/screens/counter/CountEditorDialogTest.kt` | 1 | Out-of-range pasted count disables Save. |
| androidTest: `ui/screens/settings/SettingsScreenContentTest.kt` | 5 | Exactly-once preference/action callbacks, row semantics and minimum height, About text, locale fallback casing, narrow French/Italian text growth/fit. |
| androidTest: `ui/DialogReadabilityTest.kt` | 4 | Parameterized light/dark count-editor selection/errors, ordinary versus destructive dialog actions, delete cancellation/confirmation, replacement-import cancellation/confirmation via temporary file. |
| androidTest: `ui/SnackbarLifecycleTest.kt` | 6 | Active/default duration restarts after stop, background messages wait for resume, FIFO/duplicate behavior, pause handling, no replay after completion, and root ownership across source-destination removal. |
| androidTest: `ui/TextLayoutAssertionsTest.kt` | 1 | The text-fit helper accepts fitting/wrapped text and rejects deliberately clipped or ellipsized text. |

The seven Robolectric classes use SDK 36. Repository/ViewModel fixtures use in-memory Room and test dispatchers where applicable. `BackupAndPreferencesRepositoryTest` also creates a real temporary Preferences DataStore file plus failure/stale-read fakes; ViewModel fixtures use `InMemoryPreferencesDataStore`. That test DataStore has a Mutex and MutableStateFlow; it is not the production on-disk delegate or its corruption handler. Counter repository accepts injected clock/ID generator; backup/settings accept IO dispatchers for controlled execution. `LimitMessageResourcesTest` is Robolectric-backed and iterates all 11 resource locales inside one test method.

Device content tests use `createComposeRule`; full activity tests use `createAndroidComposeRule<MainActivity>()`; `DialogReadabilityTest` and `SnackbarLifecycleTest` use `createAndroidComposeRule<ComponentActivity>()`; lifecycle/navigation probes may use the v2 Compose rule with explicit NavHost/ViewModel setup. `PrepareApplicationStateRule` clears Room and applies scenario setup before the wrapped activity rule. Activity/composition recreation tests are not actual OS process-kill tests. The haptic lifecycle test observes calls through a test `View`, not physical vibration. No screenshot-golden suite, all-locale visual matrix, production DataStore corruption test, or real system document-provider end-to-end import/export test is established by this inventory.

Test helpers `test/InMemoryPreferencesDataStore.kt` and `test/ProjectEntityFixtures.kt` have no test annotations. Android helpers `ui/PrepareApplicationStateRule.kt`, `ui/LayoutDiagnostics.kt` and `ui/TextLayoutAssertions.kt` likewise do not add test counts. `TextLayoutAssertions` rebuilds `MultiParagraph` using measured width before checking overflow/ellipsis; its comment attributes the workaround to Compose 1.12.1, but this task did not independently reproduce upstream behavior. `LayoutDiagnostics` now contains only `ComposeTestRule.captureDiagnostic`; the removed `TextLayoutResult.diagnostics()` extension is not part of current source. Diagnostics write screenshots under app external-files directories `diagnostics`, `dialog-readability`, and `counter-presentation`. These captures have no checked-in expected-image comparisons and are not screenshot goldens. Import-dialog tests use a temporary file URI/intent monitor, not a real user-selected document-provider flow.

### Latest repository-local verification evidence

Final local verification on 2026-09-14 used JDK 21 and the current source tree:

- `testDebugUnitTest` was explicitly rerun with configuration cache disabled: **111 tests, 0 failures, 0 errors, 0 skipped** across 14 XML suites.
- `assembleDebug`, `lintDebug`, `lintRelease`, `ktlintMainSourceSetCheck`, `ktlintTestSourceSetCheck`, `detekt`, and `assembleDebugAndroidTest` completed successfully. Gradle reused up-to-date outputs where applicable. Both lint XML reports and Detekt XML/SARIF contain zero findings.
- `tools/lc.ps1` and `tools/bc.ps1` returned CLEAN; their input checks confirmed stable inputs. The lint wrapper also checked Android-test Kotlin. Android-test compilation reports legacy Compose-rule deprecation warnings.
- `tools/dc.ps1` returned CLEAN for Gradle dependency verification, OSV (1,600 packages; zero findings without exceptions), and OWASP Dependency-Check (zero unsuppressed vulnerabilities under the current suppression configuration).
- `adb devices -l` listed no connected target. The current **58-case Android suite remains pending** on a device/emulator; successful Android-test assembly is compilation evidence only. Older connected-test outputs predate the lifecycle/navigation additions.

Two standalone PowerShell regression scripts exist: `tools/tests/android-check-routing.tests.ps1` checks explicit engine-root resolution and rejection of an invalid override; `tools/tests/sonar-token.tests.ps1` checks rejection of a whitespace-only token property. They use temporary fixtures and are separate from Gradle/JUnit and the Android CI command.

## Code-review invariants and quality tooling

- `ProjectValidation` is the repository-level authority for names and numeric bounds. Editor validation provides immediate UI feedback, but repository and import paths must continue to validate independently.
- Project persistence changes normally cross `CounterProject`, `ProjectEntity`, entity mappers, DAOs, the exported Room schema, backup DTO/codec logic, UI mapping, and focused tests. A schema change requires an explicit Room version/migration decision; destructive fallback is not configured.
- Count changes and undo must remain serialized through `CounterRepository` and atomic Room transactions. Archived or missing projects return structured results instead of being mutated, no-op boundary taps add no history, undo consumes rather than appends history, and history remains capped at 100 entries per project.
- Replacement import constructs `ValidatedBackup` only through its private-constructor factory, validates the entire payload before mutation, replaces projects and history in one Room transaction, and only then updates the last-active preference. A failed preference write does not roll back an already committed database replacement.
- Startup changes must preserve the exception boundary: recoverable preference `IOException` may fall back safely, while Room/database and programming failures must not release the splash into a false-ready empty UI.
- Navigation review must preserve `Screen`'s route/argument identity, URI encoding, the direct-counter fallback, import clearing through inclusive `graph.id`, and Counter's state-derived unavailable-project return. UI review must preserve the Route/Content seam, lifecycle-aware state collection, current callbacks around long-lived collectors, and accessibility that does not rely on color alone.
- Recreation/process-state review must keep `rememberSaveable` payloads Bundle-compatible; project dialog selections are IDs resolved from current state rather than saved `CounterProject` objects.
- Feedback/lifecycle review must keep buffered messages/navigation distinct from Counter's lossy resumed-only haptics and state-derived return. Root snackbar review must preserve FIFO order, `RESUMED` presentation, active-message duration restart, destination independence, horizontal/bottom safe area, and 72 dp Projects-FAB clearance, while recognizing its non-saveable recreation boundary.
- Window review must keep `keepScreenOn` limited to an enabled preference plus a present, non-archived project with disposal cleanup.
- Privacy/security review must re-check the manifest, dependencies, backup rules, SAF-only file access, and obscured-touch filtering whenever permissions, SDKs, storage, networking, analytics, billing, backup, or activity touch handling changes.
- `ktlintCheck` is blocking. Detekt uses the repository config and Compose rules but has `ignoreFailures = true`, so its exit code alone is not proof of a clean report. Android lint aborts on errors, checks release builds when requested, and includes the Google Android security lint ruleset. `app/lint.xml` is currently an empty `<lint>` document with no project-specific issue suppression.
- Sonar is configured by `sonar-project.properties`; the `sonar` task depends on the debug build and JaCoCo XML generation. Coverage excludes `MainActivity`, `RowToolApplication`, and all `ui/**`, so the coverage percentage is not evidence of UI coverage.
- The short check entry points in `tools/` are thin project wrappers around the shared Android-check installation with project ID `rowtool`; `tools/sonar.ps1` is the local Sonar wrapper. `tools/Resolve-RowToolAndroidCheck.ps1` resolves an explicit `ANDROID_CHECK_ROOT` first, then the established `C:\Dev\Android-check` or sibling checkout. A plan-only wrapper run proves routing/configuration, not that the underlying scan executed or was clean; use the produced report artifact for result claims.

## Build tooling and local check details

### Gradle setup and dependency boundary

- `settings.gradle.kts` includes only `:app`; plugin repositories are Google, Maven Central, and Gradle Plugin Portal. Dependency repositories are Google/Maven Central with `FAIL_ON_PROJECT_REPOS`.
- AGP supplies the built-in Kotlin compilation path; there is no separate `org.jetbrains.kotlin.android` plugin alias applied. Compose and serialization plugins are applied explicitly. Java/JVM target 17 is distinct from the JDK running Gradle.
- `gradle.properties` configures `-Xmx3g`, UTF-8, configuration cache enabled, build cache disabled, AndroidX enabled, nontransitive R classes, and official Kotlin style. CI disables configuration cache on the command line. There is no assertion here that the CodeQL JDK 17 configuration passed against the current toolchain.
- Wrapper distribution is `gradle-9.7.1-bin.zip` with a configured SHA-256 checksum, URL validation, 10-second network timeout, zero retries, and 500 ms retry backoff setting.
- Dependency locking applies to all configurations. Root `settings-gradle.lockfile` and `app/gradle.lockfile` coexist with `gradle/verification-metadata.xml` and `gradle/verification-keyring.keys`. Verification enables metadata/signature checks with explicit trusted/ignored key configuration; do not equate this with every artifact having a verified publisher signature. The settings lock contains only an empty catalog configuration. The app lock records Compose UI 1.12.1, Material 3 1.4.0, Robolectric 4.17 and application Kotlin stdlib 2.4.20, while some tooling configurations retain older stdlib lines including 2.4.10. These are inspected lock entries, not a fresh dependency resolution.
- Root buildscript resolution overrides jose4j to 0.9.6, Bouncy Castle `*-jdk18on` to 1.84, and jdom2 to 2.0.6.1. All-project configuration rules override Logback to 1.5.34, requested Netty 4.1.x to 4.1.137.Final, commons-lang3 to 3.20.0, HttpClient to 4.5.14, and Bouncy Castle `*-jdk18on` to 1.84. These are build resolution rules, not proof those libraries ship in the app.
- Compose is enabled and BuildConfig generation disabled. Settings reads installed package version through PackageManager. Packaged resources exclude `/META-INF/{AL2.0,LGPL2.1}`. App-specific ProGuard rules are currently empty apart from a comment; release uses the optimized default rules.
- `ui-tooling-preview` is an `implementation` dependency; interactive tooling and test manifest are `debugImplementation`. JUnit/Robolectric/coroutines-test are host-test dependencies; AndroidX test/Compose test/Room testing belong to androidTest. Room compiler is KSP. Security lint and Compose rules are check dependencies, not ordinary runtime implementation declarations.

Additional declared tool/test versions:

| Component | Version |
|---|---|
| JUnit | 4.13.2 |
| Robolectric | 4.17 |
| AndroidX Test Core / Runner | 1.7.0 / 1.7.0 |
| AndroidX Test JUnit extension / Espresso | 1.3.0 / 3.7.0 |
| ktlint Gradle plugin / engine | 14.2.0 / 1.8.0 |
| Detekt | 2.0.0-alpha.6 (declared prerelease) |
| Compose rules | 0.6.6 |
| Google Android security lints | 1.0.4 |
| OWASP Dependency-Check | 13.0.0 |
| Compose Stability Analyzer | 0.13.0 |
| Sonar Gradle plugin | 7.5.0.8588 |
| Explicit JaCoCo Ant instrumenter | 0.8.14 |

### Coverage and static-check behavior

Debug enables unit-test coverage. Custom `instrumentDataForCoverage` depends on `compileDebugKotlin`, reads `intermediates/built_in_kotlinc/debug/compileDebugKotlin/classes`, and offline-instruments only `com/finnvek/rowtool/data/**/*.class` into `build/jacoco/data-classes` using JaCoCo Ant. `testDebugUnitTest` depends on this task and prepends the instrumented directory to its execution classpath. Every Gradle `Test` task adds `--add-opens=java.base/jdk.internal.access=ALL-UNNAMED`. The instrumenter registers compiled-class inputs and the instrumented-directory output and deletes/recreates that output when executing. It includes all data-package classes, with no further generated-class exclusion inside that fileset. The Ant configuration is `jacocoOfflineAnt`; AGP coverage and the explicit Ant artifacts are locked to 0.8.14. This is selective extra offline instrumentation for data classes, not a statement that only those packages appear in all coverage reports.

Both task paths explicitly opt out of configuration-cache compatibility for their execution-time operations. This AGP output-path dependency matters when changing toolchains.

Sonar reads root properties, overrides the app JaCoCo XML path to `app/build/reports/coverage/test/debug/report.xml`, and excludes `MainActivity.kt`, `RowToolApplication.kt`, and `ui/**` from coverage. The root `sonar` task depends on debug assembly and `createDebugUnitTestCoverageReport`. Root properties exclude generated/build paths, use native Git blame, and exclude WebP from secret-file scanning. Analysis targets SonarCloud project `Insaner1980_row-tool`, organization `insaner1980`; a successful local upload is not by itself evidence of the remote quality-gate outcome.

ktlint emits plain and Checkstyle reports and excludes generated/build paths. Detekt emits Checkstyle and SARIF, not HTML/Markdown; uses default config plus `config/detekt/detekt.yml`; exempts Composable functions from naming, cyclomatic complexity, long-method, and magic-number rules; sets maximum line length 140. `.editorconfig` also exempts Composable function names from ktlint naming rules. Detekt's nonblocking exit behavior still requires report inspection.

Dependency-Check scans debug/release runtime classpaths, skips test groups, disables OSS Index, writes HTML/JSON/SARIF to root `reports`, and rejects unused suppression rules. Defaults are auto-update enabled and failure CVSS threshold 7. Environment configuration names are `DEPENDENCY_CHECK_DATA_DIRECTORY`, `DEPENDENCY_CHECK_AUTO_UPDATE`, `DEPENDENCY_CHECK_FAIL_BUILD_ON_CVSS`, `NVD_API_KEY`, `NVD_API_DELAY_MS`, and `NVD_API_MAX_RETRY_COUNT`. Default data directory is `.gradle/dependency-check-data`. Secret values must remain outside documentation and version control.

### Other scanner and reporting boundaries

| System | Local authority and limits |
|---|---|
| Android lint / Google security lint | Blocking abortOnError and checkReleaseBuilds; debug/full tasks in config. Standard configured lint outputs under app/build/reports, including lint-results-debug.xml/html and release equivalents; not inspected as fresh results. |
| ktlint / Compose rules | Blocking plugin/engine and Compose ruleset; reports under app/build/reports/ktlint. Generated/build files excluded. |
| Detekt / Compose rules | Nonblocking ignoreFailures; Checkstyle/SARIF under app/build/reports/detekt. Exit zero is insufficient to call findings clean. |
| Compose stability | Applied plugin, configured stabilityCheck, committed app/stability snapshots. Snapshot descriptions of stable/runtime parameters are not device performance measurements; report generation belongs to the plugin. |
| Sonar | Root properties/build configuration and separate tools/sonar.ps1. Coverage report and upload log paths described above; remote quality gate not checked here. |
| Dependency-Check | Runtime classpath scope, CVSS/unused-suppression failure policy, root reports output described above. |
| OSV / MobSF / secret scan / PMD / local CodeQL | Thin wrapper routes and project configuration establish invocation names. Engine versions, exact scan coverage, blocking policy and per-tool output filenames cannot be established from these wrappers alone. OSV and MobSF local exception sources are inventoried below. |
| Semgrep | 13 local rules in config/semgrep/rowtool-security.yml; path is passed through project configuration. No local CLI version or full invocation is pinned in RowTool. |
| DeepSec | Separate .deepsec/package.json, pnpm-lock.yaml and deepsec.config.ts workspace, project rowtool, root parent directory. Declares deepsec 2.3.9 and pnpm 9.15.4; overrides fast-uri 3.1.7, qs 6.16.0 and undici 8.5.0 to 8.10.0. It is tooling, not an app module. |

DeepSec scripts provide scan, process, revalidate (minimum HIGH), and Markdown-directory export to `.deepsec/findings/`; process/revalidate configure Codex with gpt-5.6-luna. `deepsec:report` chains scan → process → export with shell success gating; `deepsec:report:custom` aliases it. Configuration prioritizes backup validation/rollback, local integrity, manifest/XML, file/URI handling and sensitive logging. Its ignore file excludes node_modules, local env files, generated data/runs/reports/revalidation and findings. No finding status, successful external execution or agent upload/privacy behavior can be inferred from these declarations.

### Project wrapper map

The wrappers resolve the shared engine and pass explicit repository root and project ID `rowtool`. Arguments are forwarded via `@args`; wrappers pass through `$LASTEXITCODE`; `sc` and `tc` additionally force `-Full`. These thin wrappers do not implement PlanOnly themselves, so its shared-engine behavior cannot be proven from RowTool source. Do not treat a printed plan as scan execution. This table records local routing, not the external engine's complete implementation or current installation status.

| Script under `tools/` | Shared command |
|---|---|
| `ac.ps1` | `android-check` |
| `bc.ps1` | `build-check` |
| `tc.ps1` | `test-check` with `-Full` |
| `lc.ps1` | `lint-check` |
| `cr.ps1` | `compose-rules` |
| `cs.ps1` | `compose-stability` |
| `dc.ps1` | `dependency-check` |
| `db.ps1` | `dependabot-check` |
| `ds.ps1` | `deep-sec` |
| `ga.ps1` | `google-android-security` |
| `ms.ps1` | `mobsf-scan` |
| `os.ps1` | `osv-scan` |
| `pc.ps1` | `pmd-check` |
| `ql.ps1` | `codeql-check` |
| `sc.ps1` | `security-check` with `-Full` |
| `ss.ps1` | `secret-scan` |

`config/android-check.json` declares main/debug/test/androidTest for required app module, debug/release variants, no included builds, `dependabotEnabled = false`, debug build/host-test/device-test tasks, debug/release full lint, stability check, runtime dependency configurations, and the custom Semgrep path. Committed stability snapshots are `app/stability/app-debug.stability` and `app/stability/app-release.stability`; a snapshot is not a current rerun result.

`Resolve-RowToolAndroidCheck.ps1` uses explicit `ANDROID_CHECK_ROOT` first and throws `ANDROID_CHECK_ROOT_INVALID` if it lacks the requested file; it does not silently fall back from a bad explicit override. Without override it tries `C:\Dev\Android-check`, then a sibling `Android-check`, and throws `ANDROID_CHECK_ENGINE_NOT_FOUND` if neither works. Ordinary wrappers request `tools/InvokeProjectCheck.ps1`.

`sonar.ps1` instead imports local `SonarToken.psm1` and the shared `tools/CheckRuntime.psm1`, then runs managed `gradlew.bat sonar --console=plain --no-configuration-cache`. Timeout defaults to 3,600 seconds and is configurable from 1 to 86,400. Log destination is `reports/sonar.txt`; missing token, timeout, or nonzero Gradle exit produces wrapper exit 2. It accepts a nonblank process/user `SONAR_TOKEN` or a nonblank `systemProp.sonar.token` assignment in user/project Gradle properties. Its `-PlanOnly` branch describes the upload without running it. This documentation task did not read user credential values or invoke an upload.

### Local security rules and exception ledger

`config/semgrep/rowtool-security.yml` checks enabled Android backup/cleartext, nonlauncher exported components, broad FileProvider paths, literal signing credentials/secrets, weak crypto patterns, unsafe WebView settings, and potentially sensitive Android logging. Rule existence does not imply the corresponding feature exists in RowTool or that a scan ran.

The following are **configured exceptions and their recorded expiry dates**, not fresh endorsements of their external advisory rationale:

| File | Entry / scope | Expiry |
|---|---|---|
| `config/check-exceptions.json` | `rowtool-mobsf-viewmodel-key`, mobsfscan `android_kotlin_hardcoded`; counter ViewModel key in RowToolNavHost; recorded reason: identifier, not credential | 2026-11-10 |
| `config/check-exceptions.json` | `rowtool-mobsf-target-sdk`, mobsfscan `android_task_hijacking2`; manifest finding with build selector `targetSdk = 37`; recorded reason: source scanner does not resolve Gradle target | 2026-11-10 |
| `config/dependency-check/suppressions.xml` | Two entries: pkg:maven/androidx.sqlite/sqlite-android@2.6.2 and sqlite-framework-android@2.6.2 → cpe:/a:sqlite:sqlite:2.6.2; recorded rationale distinguishes AndroidX version from SQLite engine version | 2026-11-09Z |
| `config/dependency-check/suppressions.xml` | pkg:maven/com.github.skydoves/compose-stability-runtime-android@0.13.0 → cpe:/a:github:github:0.13.0; recorded rationale: unrelated GitHub server product | 2026-11-14Z |

`app/lint.xml` contains no issue entries. README's AGP 9.3.2 line remains older than the catalog's 9.4.0; this reference follows the catalog. Neither file was changed in this documentation-only task.

No dated exception is expired as of 2026-09-14. `gradle/osv-scanner.toml` has no active vulnerability exceptions, and the former Kotlin stdlib 2.4.10 Dependency-Check suppression is absent. The catalog and application runtime lock use Kotlin 2.4.20; older Kotlin entries remain in tooling configurations.

Other source-level suppressions/opt-ins, without expiry:

- Production manifest: MissingClass only on the ProfileInstallReceiver removal directive.
- Base regular/round adaptive icon XML: MonochromeLauncherIcon, with monochrome supplied by API 33 resources.
- CounterRoute: InlinedApi on SDK-guarded haptic constant selection.
- ProjectsScreen: DEPRECATION on legacy isContainer semantics.
- Counter/Projects/Settings screens: ExperimentalMaterial3Api for app bars; editor: ExperimentalLayoutApi for FlowRow; Type: ExperimentalTextApi for font setup.
- BackupModels: CPD-OFF/ON around intentionally mirrored serialization fields. CounterViewModelTest and SettingsViewModelTest mark shared fixture structure; CounterScreenContentTest marks explicit no-op callbacks. Whether a particular external duplication scanner honors those comments is not established here.
- Detekt and ktlint Composable-specific rule exclusions and verification trust configuration are described above. No TODO/FIXME markers were found in current production Kotlin; this absence is not evidence that every review concern has been resolved.

### CI and repository-generated files

Android CI in `.github/workflows/android.yml` runs on every push/pull request without branch/path filters, Ubuntu latest, contents-read permission and Temurin 21. Exact invocation: `./gradlew --no-daemon --no-configuration-cache :app:assembleDebug :app:testDebugUnitTest :app:lintDebug :app:assembleDebugAndroidTest`. CodeQL runs on pushes/PRs targeting `main` and Mondays at 04:23 UTC, with Java/Kotlin manual debug build, Temurin 17, contents-read and security-events-write permissions. CodeQL's exact build is `./gradlew --no-daemon --no-configuration-cache :app:assembleDebug`; initialization selects java-kotlin/manual. Both disable checkout credential persistence and pin checkout, setup-java, setup-gradle and (CodeQL only) init/analyze actions to commit SHAs. Neither workflow runs connected device tests or publishes a signed release.

`.gitignore` excludes `.gradle`, IDE metadata, build directories, `local.properties`, captures/native build state, `reports`, and `.scannerwork`. A local ignored report is not committed evidence. Release signing configuration is absent; do not infer that generic ignore patterns protect every possible signing-key filename.

## Source-of-truth locations

| Concern | Source |
|---|---|
| Versions and dependencies | `gradle/libs.versions.toml` |
| Android identity, SDK, release, lint, Room export | `app/build.gradle.kts` |
| Permissions and Android components | `app/src/main/AndroidManifest.xml` |
| App construction and persistence wiring | `RowToolApplication.kt` |
| Navigation | `ui/navigation/Screen.kt`, `ui/navigation/RowToolNavHost.kt` |
| Room entities/schema | `data/local/`, `app/schemas/com.finnvek.rowtool.data.local.RowToolDatabase/1.json` |
| Counter transactions and undo | `data/repository/CounterRepository.kt` |
| Project semantic bounds and normalization | `domain/model/CounterModels.kt`, `domain/model/ProjectValidation.kt`, `ui/screens/projects/ProjectEditorInputValidation.kt` |
| Backup schema, validation, and replacement | `data/repository/BackupModels.kt`, `BackupCodec.kt`, `BackupRepository.kt` |
| Persistent settings and startup selection | `data/preferences/`, `ui/RowToolAppViewModel.kt` |
| Projects UI | `ui/screens/projects/` |
| Counter UI | `ui/screens/counter/` |
| Settings and SAF flows | `ui/screens/settings/` |
| Theme and font fallback | `ui/theme/`, `app/src/main/res/font/outfit.ttf`, `app/src/main/res/values/themes.xml`, `app/src/main/res/values-night/themes.xml` |
| UI state/effect seams and platform ownership | `ui/RowToolApp.kt`, `ui/screens/*/*Route.kt`, `ui/screens/*/*ViewModel.kt`, `ui/screens/*/*Screen.kt` |
| Shared UI dimensions and components | `ui/theme/RowToolDimens.kt`, `ui/RowToolDialogActionColors.kt`, `ui/RowToolSectionHeadingText.kt`, `ui/RowToolConfirmationDialog.kt`, `ui/RowToolDropdownMenuItem.kt`, `ui/screens/counter/CounterImageButton.kt` |
| Localized text and locale declaration | `app/src/main/res/values*/`, `app/src/main/res/xml/locales_config.xml` |
| Tests | `app/src/test/`, `app/src/androidTest/` |
| CI | `.github/workflows/android.yml`, `.github/workflows/codeql.yml` |
| Static analysis, security, and Sonar | `app/build.gradle.kts`, `build.gradle.kts`, `config/`, `sonar-project.properties`, `tools/` |
| Play handoff | `docs/PLAY_STORE_LISTING.md`, `docs/DATA_SAFETY.md`, `docs/PRIVACY_POLICY.md`, `docs/privacy-policy.html`, `docs/RELEASE_CHECKLIST.md` |
| Quality-engine routing / scanner exceptions | `tools/Resolve-RowToolAndroidCheck.ps1`, `config/android-check.json`, `config/check-exceptions.json`, `config/dependency-check/suppressions.xml`, `gradle/osv-scanner.toml` |
| Offline coverage / report upload | `app/build.gradle.kts`, `build.gradle.kts`, `sonar-project.properties`, `tools/sonar.ps1` |
| Debug test-host theme / diagnostic helpers | `app/src/debug/AndroidManifest.xml`, `app/src/androidTest/java/com/finnvek/rowtool/ui/TextLayoutAssertions.kt`, `app/src/androidTest/java/com/finnvek/rowtool/ui/LayoutDiagnostics.kt` |
| Separate DeepSec workspace | `.deepsec/package.json`, `.deepsec/pnpm-lock.yaml`, `.deepsec/deepsec.config.ts` |

All Kotlin paths in this table are below `app/src/main/java/com/finnvek/rowtool/` unless an explicit root is shown.

## Change-impact and review map

Use each row as a focused starting point and verify the current implementation before treating a concern as a defect. Tests named above are evidence sources to inspect and, when needed, run; their presence alone is not proof of all listed boundaries.

| Proposed area | Source path to trace | Questions grounded in the current behavior |
|---|---|---|
| Count operation or undo | `CounterMutation` → `CounterRepository` transaction → project/history DAO flows → `CounterViewModel` → Counter content | Does every accepted change persist once? Do no-ops avoid timestamp/history changes? Are archived/missing results, cap, concurrent updates, and undo history consumption preserved? |
| Project editing | `ProjectEditorDialog` → `ProjectEditorInputValidation` → `ProjectValidation` → `ProjectEditorValues` → repository validation | Do UI fields reuse semantic authorities without losing parsing/enablement-specific validity? Are Unicode names, disabled optional values, pasted invalid input, unchanged edits, archived metadata, and start-value semantics preserved? |
| New persisted field | Domain/entity/mappers → Room schema/DAO → backup DTO/codec/export/import → presentation/tests | What default/migration is required? Is backup compatibility explicit? Are import and creation independently validated? |
| Import | SAF route → request sequence → codec → `ValidatedBackup.create` → preview → confirm atomic → trusted replacement transaction → preference selection → graph-anchor navigation | Can old reads replace a newer preview? Is validation construction unbypassable? Does failure preserve old DB rows? Is the whole replacement committed before preference handling? Are empty/archived-only payloads, equal timestamp ordering, and dynamic-start back-stack clearing handled? |
| Startup and recreation | Application/DataStore → app ViewModel → splash → `Screen`/NavHost → route saved IDs/state | Are fatal DB failures distinguished from recoverable preferences? Does direct-counter Back clear stale entries? Does missing/archive state retrigger mandatory return after recreation? Are saved values Bundle-compatible? Which state survives activity recreation versus process death? |
| Archive/delete | Projects/Counter actions → repository → conditional preference clear → observed Counter state plus one-shot feedback | Does clearing avoid overwriting a newer selection? Is mandatory return retried until navigation removes the route while optional feedback stays one-shot? Does delete cascade only the selected project's history? |
| UI hierarchy or sizing | `*Screen.kt`, `CounterImageButton`, `ProjectEditorDialog`, theme files | Do count and controls fit width/font scale? Are touch targets independent of artwork? Do dialogs fit with IME? Are project IDs retained during list reorder? |
| Accessibility/localization | Semantic modifiers, `values*/strings.xml`, number/date parsing, theme roles | Are actions uniquely labeled? Is state communicated beyond color? Are plurals/placeholders correct? Are locale display formatting and numeric input parsing intentionally different? |
| Snackbar/haptics | `RowToolApp.rememberSnackbarPresenter`, `CounterViewModel.haptics`, `CounterRoute` lifecycle collectors | Are durable-enough messages queued FIFO until resumed? Does stop restart visible duration? Are stale haptics dropped and fresh resumed haptics classified correctly? Is the activity-recreation limitation acceptable? |
| Theme/window behavior | XML themes → `MainActivity` → `RowToolTheme` and Counter disposable effect | Do system-bar icons follow the selected mode? Does keep-screen-on clear on route exit or archive? Does splash differ from selected app theme before preferences load? |
| Security/privacy | Manifest merge directives, backup exclusions, SAF streams, dependency scopes, activity touch filtering | Do source and final artifact still match? Are exported JSON and provider access described truthfully? Are logging/permissions/SDK changes evaluated on actual call paths? |
| Toolchain/check changes | Catalog, root/app Gradle, locks, verification files, coverage output paths, workflows, wrappers | Do declared versions resolve compatibly? Does offline instrumentation still target real class paths? Are nonblocking reports and exceptions included in result interpretation? |

Documentation precedence: production/configuration source first, executable test assertions for tested contracts second, this reference as a navigational summary, then README/release/history material for context. Product/store intent is not evidence of Play publication, actual paid price, a deployed privacy URL, or real-device acceptance.

## External release steps

The following are intentionally not completed by source code:

1. Check application-name and trademark availability and configure the Finnvek Play Console application.
2. Create or select a secure upload key, store it outside the repository, register it with Play App Signing, and provide release-signing configuration without committing key material or passwords.
3. Configure the Play app as a paid download around EUR 1.99 and review regional equivalents. No Play Billing integration is needed.
4. Produce and inspect the signed release AAB and merged manifest, verify the final dependency graph and permissions, and test the release on a real device.
5. Review every translation and store asset, capture current screenshots, and host `docs/privacy-policy.html` at a stable public URL.
6. Complete the Play listing, Data safety form, privacy-policy URL, content rating, target audience, category, and release notes.
7. Upload the inspected AAB and perform the selected test/production rollout. Increment `versionCode` for every later upload.

The operational checklist is `docs/RELEASE_CHECKLIST.md`. Its unchecked items are handoff guidance, not proof of which external actions have or have not occurred.

## Documentation conflicts and inspection limits

| Other source | Conflict or qualification found during this inspection |
|---|---|
| README.md technology | Lists AGP 9.3.2 / Kotlin 2.4.10; catalog declares 9.4.0 / 2.4.20. |
| README.md typography | Says Outfit is absent and SansSerif is the only implementation; live resource and Type.kt now wire bundled Outfit plus fallback. |
| README.md backup wording | Calls undo history transient even though Room persists it; it is excluded from manual export. Says import reads at most 5 MiB, while the decoder probes one extra byte to establish oversize. |
| Privacy/Data safety and Play support files | Describe app-owned offline behavior and intended declarations, not an inspected merged release, published listing or configured price. SAF provider may itself use cloud storage. No implementation contradiction was found in the local-only app call paths. |

Only PROJECT.md was edited by this task. The document incorporates and retains useful pre-existing documentation while replacing stale implementation descriptions. Source inventories, XML/resource structure, declared colors/dimensions, test annotations, asset hashes, paths and internal links were checked; these are static documentation checks. Full final-document and diff review, plus git diff --check, are the completion checks for this task.

The local verification above does not establish hosted CI, connected-device behavior, rendered UI acceptance, a signed release artifact, final merged-release manifest, translation quality, latest available releases, Play Console state, or hosted privacy-policy availability.
