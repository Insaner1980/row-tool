# RowTool

RowTool is a calm, tactile, completely offline row and round counter for knitting and crochet, published by Finnvek.

## Features

- Multiple active and archived projects, each counting either rows or rounds.
- A large editable count with image-backed increment, decrement, and persistent multi-step undo controls.
- Immediate local persistence, a configurable start value, confirmed reset, and a count range of `0..999,999`.
- Optional targets and repeating-section tracking.
- Project create, rename, edit, archive, restore, and confirmed delete flows.
- Automatic reopening of the last valid active project.
- System, light, and dark themes; optional haptic feedback; and optional keep-screen-awake behavior.
- Manual JSON export and validated replacement import through Android's system document picker.
- English plus Finnish, Swedish, German, French, Spanish, Portuguese, Italian, Norwegian Bokmal, Danish, and Dutch resources.

## Privacy and offline operation

RowTool has no account, advertising, analytics, telemetry, cloud sync, or remote API. The source manifest declares no permissions, including no `INTERNET` or storage permission. Projects, undo history, and settings remain on the device.

Android system backup and device-transfer backup are disabled. Moving data between devices is an explicit user action through the export and import controls. See [DATA_SAFETY.md](docs/DATA_SAFETY.md) and [PRIVACY_POLICY.md](docs/PRIVACY_POLICY.md).

## Technology

- Kotlin, Jetpack Compose, Material 3, and Navigation Compose
- Room for projects and undo history
- Preferences DataStore for theme, haptics, keep-awake, and last-active-project state
- Coroutines, Flow, lifecycle-aware state collection, and Kotlin serialization
- Android SplashScreen API and manual application-container/ViewModel construction
- `minSdk 29`, `compileSdk 37`, and `targetSdk 37`
- Java/JVM 17, Gradle 9.6.1, AGP 9.3.1, and Kotlin 2.4.10

Dependency versions are centralized in [`gradle/libs.versions.toml`](gradle/libs.versions.toml).

## Build

Prerequisites:

- JDK 17
- Android SDK Platform 37
- An Android SDK path configured through the normal Android Gradle mechanisms, such as `local.properties` or `ANDROID_HOME`

From PowerShell:

```powershell
.\gradlew.bat :app:assembleDebug
```

Useful release tasks are:

```powershell
.\gradlew.bat :app:assembleRelease
.\gradlew.bat :app:bundleRelease
```

The repository intentionally contains no signing key or release signing configuration. A Play-ready AAB requires external upload-key configuration; see [Release boundary](#release-boundary).

## Tests and checks

Run the local JVM tests, lint, KSP generation, and Android-test compilation with:

```powershell
.\gradlew.bat :app:testDebugUnitTest
.\gradlew.bat :app:lintDebug
.\gradlew.bat :app:kspDebugKotlin
.\gradlew.bat :app:assembleDebugAndroidTest
```

With an emulator or device connected:

```powershell
.\gradlew.bat :app:connectedDebugAndroidTest
```

CI runs the debug build, JVM tests, debug lint, and Android-test compilation from [`.github/workflows/android.yml`](.github/workflows/android.yml).

## Product assets and typography

The supplied button artwork remains at the repository root and is copied byte-for-byte into `app/src/main/res/drawable-nodpi/`:

- `counter_plus_button.webp` — primary increment control
- `counter_minus_button.webp` — decrement control
- `counter_undo_button.webp` — undo control

Compose renders these resources with `ContentScale.Fit` inside separate accessible touch targets. The images are not tinted or density-scaled.

No Outfit font file is present in the repository. [`Type.kt`](app/src/main/java/com/finnvek/rowtool/ui/theme/Type.kt) therefore uses the specified production fallback, `FontFamily.SansSerif`, from one centralized typography definition. A legally supplied Outfit asset can later replace that family without changing the screens.

## Backup and restore

Settings provides Android Storage Access Framework actions for export and import:

1. Export writes a UTF-8 JSON file containing backup schema version `1` and all active and archived projects. Settings and transient undo history are not exported.
2. Import reads at most 5 MiB, validates the application identity, schema version, project count, IDs, counter units, and project values, then shows a summary and destructive-replacement confirmation.
3. Confirmation atomically replaces the project database and clears old undo history. The newest active imported project becomes the last-active selection.

## Release boundary

The Android implementation and store-support documents are in this repository, but these steps remain external:

- Check the RowTool name and trademark, then create/configure the Finnvek Play Console app.
- Create or configure an upload key outside version control and connect it to release signing without committing secrets.
- Configure the app as a paid download around EUR 1.99 with regional equivalents in Play Console. The app contains no Play Billing code.
- Build and inspect the signed release AAB and merged manifest, then test the release on a physical device.
- Review translations, capture screenshots, host the privacy policy, and complete the Play listing, Data safety, content rating, target audience, and category declarations.
- Upload the inspected AAB and perform the chosen Play release rollout.

See [RELEASE_CHECKLIST.md](docs/RELEASE_CHECKLIST.md) for the concise handoff checklist and [PROJECT.md](PROJECT.md) for the implementation reference.
