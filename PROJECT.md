# RowTool implementation reference

This document describes the implementation currently present in the repository. It is not a feature roadmap.

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
| Business model | Paid Play download; no in-app billing, subscription, trial, ads, or feature gates |

## Architecture

RowTool is a single-activity Compose application with three Navigation Compose destinations. Screen-specific ViewModels expose immutable Flow-backed state and call a small repository layer. `AppContainer` in `RowToolApplication.kt` constructs Room, DataStore, repositories, and explicit ViewModel factories without a dependency-injection framework.

Room is the persistent source of truth for projects and undo history. Preferences DataStore is the persistent source of truth for app settings and the last-active project. Counter mutations are serialized by a repository `Mutex` and performed in Room transactions; accepted count changes are persisted immediately and retain the newest 100 undo records per project.

Startup remains behind the splash screen until `RowToolAppViewModel` resolves the last-active project. An `IOException` during that resolution falls back to the Projects screen, but Room/database and other non-recoverable failures are not converted into a successful resolved state.

The UI follows a Route/Content split. `ProjectsRoute`, `CounterRoute`, and `SettingsRoute` collect lifecycle-aware `StateFlow` values, consume one-shot effects, own dialogs and Android platform launchers/side effects, and translate ViewModel operations into action bundles. `ProjectsScreenContent`, `CounterScreenContent`, and `SettingsScreenContent` render state and invoke those actions without resolving repositories or navigation themselves. ViewModels expose screen state through `StateFlow` and transient navigation/message/haptic events through buffered `Channel` flows.

`ProjectsRoute` stores edit/delete dialog selection as saveable project-ID strings and resolves the current `CounterProject` from the latest Room-backed UI state. Domain model instances are not placed directly in saved-instance state.

## Technology versions

| Component | Declared version |
|---|---|
| Gradle wrapper | 9.7.1 |
| Android Gradle Plugin | 9.4.0 |
| Kotlin / Compose plugin | 2.4.10 |
| KSP | 2.3.11 |
| Compose BOM | 2026.08.00 |
| Activity Compose | 1.13.0 |
| AndroidX Core | 1.19.0 |
| Lifecycle | 2.11.0 |
| Navigation Compose | 2.10.0 |
| Room | 2.8.4 |
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
| `id` | non-null text primary key | UUID project identity |
| `name` | non-null text | Trimmed project name, maximum 60 Unicode code points |
| `counterUnit` | non-null text | `ROWS` or `ROUNDS`; unknown persisted values map safely to `ROWS` |
| `count` | non-null integer | Current count, `0..999999` |
| `startValue` | non-null integer | Reset value, `0` or `1` |
| `targetCount` | nullable integer | Optional target, `1..999999` |
| `repeatLength` | nullable integer | Optional repeat length, `2..999` |
| `isArchived` | non-null integer/boolean | Active/archive state |
| `createdAt` | non-null integer | Epoch milliseconds |
| `updatedAt` | non-null integer | Epoch milliseconds |

The table has an index on `isArchived, updatedAt` for ordered active/archive project access.

### `counter_history`

| Column | Storage | Meaning |
|---|---|---|
| `id` | auto-generated integer primary key | History ordering identity |
| `projectId` | non-null text foreign key | Owning project |
| `previousCount` | non-null integer | Count before the mutation |
| `newCount` | non-null integer | Count after the mutation |
| `changeReason` | non-null text | `INCREMENT`, `DECREMENT`, `MANUAL_SET`, or `RESET` |
| `createdAt` | non-null integer | Epoch milliseconds |

`projectId` references `projects.id` with `ON DELETE CASCADE`. The `projectId, id` index supports newest-history lookup. Undo consumes the newest history row and does not add another row.

The exported schema is committed at `app/schemas/com.finnvek.rowtool.data.local.RowToolDatabase/1.json`.

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
- Provides edit, set-count, reset, archive, and delete actions; archived projects are not mutable.
- Performs optional light haptics for accepted taps and stronger feedback at a repeat boundary or target.

### Settings

- Theme: system, light, or dark.
- Haptic feedback toggle.
- Keep-screen-awake toggle, applied while an active counter is open.
- Manual JSON export and validated replacement import through the system document picker.
- App version, Finnvek identity, local-data privacy summary, and business-model summary.

The navigation routes are `projects`, `counter/{projectId}`, and `settings`; project IDs are URI-encoded when routes are built. Startup validates the stored last-active ID against Room; if it is missing or archived, the most recently updated active project is selected, or the Projects screen opens when none exists. Archiving or deleting the selected project clears the matching preference immediately when possible, while startup remains the repair path for a stale or failed preference write.

## Persistent settings

Preferences DataStore name: `rowtool_preferences`.

| Key | Default | Purpose |
|---|---|---|
| `theme_mode` | `SYSTEM` | System, light, or dark theme |
| `haptic_feedback_enabled` | `true` | Counter haptic feedback |
| `keep_screen_awake` | `true` | Keep the display awake on the Counter screen |
| `last_active_project_id` | absent | Project reopened at startup |

These settings are not part of the JSON project backup.

## Backup format and replacement semantics

Backup format version: `schemaVersion: 1`; required application identity: `application: "RowTool"`.

The UTF-8 JSON root contains `schemaVersion`, `application`, `exportedAt`, and `projects`. Each project contains the persisted project fields listed in the Room schema. Undo history and DataStore settings are intentionally excluded.

Import accepts unknown JSON keys for forward-compatible optional additions, but rejects malformed JSON, files over 5 MiB, unsupported schema versions, another application identity, more than 1,000 projects, duplicate/blank IDs, unknown counter units, and invalid project values. The UI presents active/archive counts and requires confirmation before replacement. Confirmed replacement clears projects and undo history and inserts the validated projects in one Room transaction, then resolves the last-active imported project.

The Storage Access Framework supplies file access, so export/import requires no broad storage permission.

## UI implementation, assets, accessibility, and localization

- Root originals: `counter_plus_button.webp`, `counter_minus_button.webp`, `counter_undo_button.webp`.
- Packaged copies: `app/src/main/res/drawable-nodpi/` with identical binary content.
- Runtime usage: `CounterScreen.kt` and `CounterImageButton.kt`; `ContentScale.Fit`, no tint, and separate semantic/clickable targets of at least 48 dp.
- UI ownership: Route files own lifecycle collection, effects, dialogs, and platform integration; `*ScreenContent` composables own layout and receive explicit state/action objects. `ProjectEditorDialog` is shared by Projects and Counter edit flows, and `RowToolDropdownMenuItem` is shared by project and counter menus.
- Design system: semantic light/dark colors are defined in `ui/theme/Color.kt` and mapped in `Theme.kt`; typography, shapes, and shared spacing/content-width values live in `Type.kt`, `Shapes.kt`, and `RowToolDimens.kt`. Screen content is capped at 600 dp with 20 dp phone-side padding; the project editor is capped at 560 dp and 88% of the available window height.
- Counter interaction: the localized, locale-formatted count scales by digit count, available width, and font scale and is itself an accessible set-count button. Image controls expose button roles, localized descriptions, disabled semantics, a minimum 48 dp target, and brief press-scale feedback.
- Global messages: `RowToolApp` owns the shared `SnackbarHost`. It applies horizontal/bottom `safeDrawing` insets and 72 dp of bottom clearance so messages remain inside the safe area and above the Projects extended FAB.
- Window side effects: `CounterRoute` enables `View.keepScreenOn` only when the preference is enabled and the current project exists and is not archived. Its `DisposableEffect` clears the flag when that condition becomes false or the route leaves composition.
- System UI: `MainActivity` enables edge-to-edge layout and keeps status/navigation-bar icon appearance aligned with the selected light or dark theme.
- Launcher: adaptive regular/round resources and a monochrome icon are under `app/src/main/res/mipmap-anydpi*` and `app/src/main/res/drawable/`.
- Theme: fixed warm light/dark Material 3 schemes; dynamic color is not enabled.
- Typography: no Outfit font asset is present. `ui/theme/Type.kt` deliberately centralizes the production `FontFamily.SansSerif` fallback.
- Layout: counter and list content is width-bounded for phone/tablet use; the count and controls scale to available width and font scale.
- Localization: `values/` plus `values-fi`, `values-sv`, `values-de`, `values-fr`, `values-es`, `values-pt`, `values-it`, `values-nb`, `values-da`, and `values-nl`; `locales_config.xml` declares the same locales.

## Manifest and privacy boundary

`app/src/main/AndroidManifest.xml` contains no `<uses-permission>` entries. In particular, the app does not request Internet, camera, microphone, notification, or storage access. The only exported component is the launcher `MainActivity`.

The application sets `android:allowBackup="false"` and `android:usesCleartextTraffic="false"`. `backup_rules.xml` and `data_extraction_rules.xml` exclude app files, databases, shared preferences, and external files from system backup and device transfer. Manual JSON export is the supported portability path.

The dependency catalog contains no networking client, Firebase, analytics, advertising, billing, or crash-reporting SDK.

`MainActivity` also rejects obscured-window touches: the decor view enables `filterTouchesWhenObscured`, and `dispatchTouchEvent` drops events marked `FLAG_WINDOW_IS_PARTIALLY_OBSCURED`. Changes to activity-level touch handling must preserve this tapjacking boundary.

## Build and verification commands

Use JDK 21 for the full local build and test commands and Android SDK Platform 37. Application Java source/target compatibility and the Kotlin JVM target remain 17. The primary Android CI workflow also runs Gradle on Temurin JDK 21, while the CodeQL build runs on Temurin JDK 17. On Windows PowerShell:

```powershell
.\gradlew.bat :app:assembleDebug
.\gradlew.bat :app:testDebugUnitTest
.\gradlew.bat :app:lintDebug
.\gradlew.bat :app:kspDebugKotlin
.\gradlew.bat :app:assembleDebugAndroidTest
```

Run device/emulator tests only when a target is available:

```powershell
.\gradlew.bat :app:connectedDebugAndroidTest
```

Release tasks are configured with code/resource shrinking:

```powershell
.\gradlew.bat :app:assembleRelease
.\gradlew.bat :app:bundleRelease
```

The repository does not contain an upload key or `signingConfig`; configure signing externally before treating an AAB as Play-ready. In a Git checkout, also run `git diff --check` before handoff.

Test sources are split between host-side JUnit tests under `app/src/test/` and AndroidJUnit4/Compose device tests under `app/src/androidTest/`. The host suite includes pure domain/presentation tests plus Robolectric-backed Room, repository, DataStore, and backup tests. The device suite covers isolated screen semantics and full-activity navigation and persistence flows. `assembleDebugAndroidTest` compiles the device tests; only `connectedDebugAndroidTest` executes them. CI in `.github/workflows/android.yml` runs the debug build, host suite, debug lint, and Android-test compilation with JDK 21. These command descriptions do not assert a result for a run that is not shown in the current task.

## Code-review invariants and quality tooling

- `ProjectValidation` is the repository-level authority for names and numeric bounds. Editor validation provides immediate UI feedback, but repository and import paths must continue to validate independently.
- Project persistence changes normally cross `CounterProject`, `ProjectEntity`, entity mappers, DAOs, the exported Room schema, backup DTO/codec logic, UI mapping, and focused tests. A schema change requires an explicit Room version/migration decision; destructive fallback is not configured.
- Count changes and undo must remain serialized through `CounterRepository` and atomic Room transactions. Archived or missing projects return structured results instead of being mutated, no-op boundary taps add no history, undo consumes rather than appends history, and history remains capped at 100 entries per project.
- Replacement import validates the entire payload before mutation, replaces projects and history in one Room transaction, and only then updates the last-active preference. A failed preference write does not roll back an already committed database replacement.
- Startup changes must preserve the exception boundary: recoverable preference `IOException` may fall back safely, while Room/database and programming failures must not release the splash into a false-ready empty UI.
- Navigation review must preserve the direct-counter fallback and back-stack clearing in `RowToolNavHost`; UI review must preserve the Route/Content seam, lifecycle-aware collection, `rememberUpdatedState` around long-lived effect collectors, and accessibility that does not rely on color alone.
- Recreation/process-state review must keep `rememberSaveable` payloads Bundle-compatible; project dialog selections are IDs resolved from current state rather than saved `CounterProject` objects.
- Window/inset review must preserve the shared snackbar's horizontal/bottom safe area and 72 dp Projects-FAB clearance, and must keep `keepScreenOn` limited to an enabled preference plus a present, non-archived project with disposal cleanup.
- Privacy/security review must re-check the manifest, dependencies, backup rules, SAF-only file access, and obscured-touch filtering whenever permissions, SDKs, storage, networking, analytics, billing, backup, or activity touch handling changes.
- `ktlintCheck` is blocking. Detekt uses the repository config and Compose rules but has `ignoreFailures = true`, so its exit code alone is not proof of a clean report. Android lint aborts on errors, checks release builds when requested, and includes the Google Android security lint ruleset.
- Sonar is configured by `sonar-project.properties`; the `sonar` task depends on the debug build and JaCoCo XML generation. Coverage excludes `MainActivity`, `RowToolApplication`, and all `ui/**`, so the coverage percentage is not evidence of UI coverage.
- `tools/*.ps1` are thin project wrappers around the shared Android-check installation with project ID `rowtool`; `tools/sonar.ps1` is the local Sonar wrapper. `tools/Resolve-RowToolAndroidCheck.ps1` resolves an explicit `ANDROID_CHECK_ROOT` first, then the established `C:\Dev\Android-check` or sibling checkout. A plan-only wrapper run proves routing/configuration, not that the underlying scan executed or was clean; use the produced report artifact for result claims.

## Source-of-truth locations

| Concern | Source |
|---|---|
| Versions and dependencies | `gradle/libs.versions.toml` |
| Android identity, SDK, release, lint, Room export | `app/build.gradle.kts` |
| Permissions and Android components | `app/src/main/AndroidManifest.xml` |
| App construction and persistence wiring | `RowToolApplication.kt` |
| Navigation | `ui/navigation/Screen.kt`, `ui/navigation/RowToolNavHost.kt` |
| Room entities/schema | `data/local/`, `app/schemas/.../1.json` |
| Counter transactions and undo | `data/repository/CounterRepository.kt` |
| Backup schema, validation, and replacement | `data/repository/BackupModels.kt`, `BackupCodec.kt`, `BackupRepository.kt` |
| Persistent settings and startup selection | `data/preferences/`, `ui/RowToolAppViewModel.kt` |
| Projects UI | `ui/screens/projects/` |
| Counter UI | `ui/screens/counter/` |
| Settings and SAF flows | `ui/screens/settings/` |
| Theme and font fallback | `ui/theme/` |
| UI state/effect seams and platform ownership | `ui/screens/*/*Route.kt`, `ui/screens/*/*ViewModel.kt`, `ui/screens/*/*Screen.kt` |
| Shared UI dimensions and components | `ui/theme/RowToolDimens.kt`, `ui/RowToolDropdownMenuItem.kt`, `ui/screens/counter/CounterImageButton.kt` |
| Localized text and locale declaration | `app/src/main/res/values*/`, `app/src/main/res/xml/locales_config.xml` |
| Tests | `app/src/test/`, `app/src/androidTest/` |
| CI | `.github/workflows/android.yml`, `.github/workflows/codeql.yml` |
| Static analysis, security, and Sonar | `app/build.gradle.kts`, `build.gradle.kts`, `config/`, `sonar-project.properties`, `tools/` |
| Play handoff | `docs/PLAY_STORE_LISTING.md`, `DATA_SAFETY.md`, `PRIVACY_POLICY.md`, `RELEASE_CHECKLIST.md` |

All Kotlin paths in this table are below `app/src/main/java/com/finnvek/rowtool/` unless an explicit root is shown.

## External release steps

The following are intentionally not completed by source code:

1. Check application-name and trademark availability and configure the Finnvek Play Console application.
2. Create or select a secure upload key, store it outside the repository, register it with Play App Signing, and provide release-signing configuration without committing key material or passwords.
3. Configure the Play app as a paid download around EUR 1.99 and review regional equivalents. No Play Billing integration is needed.
4. Produce and inspect the signed release AAB and merged manifest, verify the final dependency graph and permissions, and test the release on a real device.
5. Review every translation and store asset, capture current screenshots, and host `docs/privacy-policy.html` at a stable public URL.
6. Complete the Play listing, Data safety form, privacy-policy URL, content rating, target audience, category, and release notes.
7. Upload the inspected AAB and perform the selected test/production rollout. Increment `versionCode` for every later upload.

The operational checklist is `docs/RELEASE_CHECKLIST.md`.
