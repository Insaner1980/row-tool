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
| Java / JVM | 17 |
| Business model | Paid Play download; no in-app billing, subscription, trial, ads, or feature gates |

## Architecture

RowTool is a single-activity Compose application with three Navigation Compose destinations. Screen-specific ViewModels expose immutable Flow-backed state and call a small repository layer. `AppContainer` in `RowToolApplication.kt` constructs Room, DataStore, repositories, and explicit ViewModel factories without a dependency-injection framework.

Room is the persistent source of truth for projects and undo history. Preferences DataStore is the persistent source of truth for app settings and the last-active project. Counter mutations are serialized by a repository `Mutex` and performed in Room transactions; accepted count changes are persisted immediately and retain the newest 100 undo records per project.

## Technology versions

| Component | Version |
|---|---|
| Gradle wrapper | 9.6.1 |
| Android Gradle Plugin | 9.3.1 |
| Kotlin / Compose plugin | 2.4.10 |
| KSP | 2.3.11 |
| Compose BOM | 2026.06.01 |
| Activity Compose | 1.13.0 |
| AndroidX Core | 1.19.0 |
| Lifecycle | 2.11.0 |
| Navigation Compose | 2.9.8 |
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

The navigation routes are `projects`, `counter/{projectId}`, and `settings`. Startup validates the stored last-active ID against Room; if it is missing or archived, the most recently updated active project is selected, or the Projects screen opens when none exists.

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

## Assets, theme, accessibility, and localization

- Root originals: `counter_plus_button.webp`, `counter_minus_button.webp`, `counter_undo_button.webp`.
- Packaged copies: `app/src/main/res/drawable-nodpi/` with identical binary content.
- Runtime usage: `CounterScreen.kt` and `CounterImageButton.kt`; `ContentScale.Fit`, no tint, and separate semantic/clickable targets of at least 48 dp.
- Launcher: adaptive regular/round resources and a monochrome icon are under `app/src/main/res/mipmap-anydpi*` and `app/src/main/res/drawable/`.
- Theme: fixed warm light/dark Material 3 schemes; dynamic color is not enabled.
- Typography: no Outfit font asset is present. `ui/theme/Type.kt` deliberately centralizes the production `FontFamily.SansSerif` fallback.
- Layout: counter and list content is width-bounded for phone/tablet use; the count and controls scale to available width and font scale.
- Localization: `values/` plus `values-fi`, `values-sv`, `values-de`, `values-fr`, `values-es`, `values-pt`, `values-it`, `values-nb`, `values-da`, and `values-nl`; `locales_config.xml` declares the same locales.

## Manifest and privacy boundary

`app/src/main/AndroidManifest.xml` contains no `<uses-permission>` entries. In particular, the app does not request Internet, camera, microphone, notification, or storage access. The only exported component is the launcher `MainActivity`.

The application sets `android:allowBackup="false"` and `android:usesCleartextTraffic="false"`. `backup_rules.xml` and `data_extraction_rules.xml` exclude app files, databases, shared preferences, and external files from system backup and device transfer. Manual JSON export is the supported portability path.

The dependency catalog contains no networking client, Firebase, analytics, advertising, billing, or crash-reporting SDK.

## Build and verification commands

Use JDK 17 and Android SDK Platform 37. On Windows PowerShell:

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

Test sources are split between pure JVM tests under `app/src/test/` and Room/repository/Compose instrumentation tests under `app/src/androidTest/`. CI in `.github/workflows/android.yml` runs the debug build, JVM suite, debug lint, and Android-test compilation with JDK 17. These command descriptions do not assert a result for a run that is not shown in the current task.

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
| Localized text and locale declaration | `app/src/main/res/values*/`, `app/src/main/res/xml/locales_config.xml` |
| Tests | `app/src/test/`, `app/src/androidTest/` |
| CI | `.github/workflows/android.yml` |
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
