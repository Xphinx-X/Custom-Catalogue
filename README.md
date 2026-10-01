# Custom Catalogue (base)

Minimal Android base with Material 3 Expressive UI.

## Tech stack

- Kotlin `2.2.10`, AGP `9.2.1`, Gradle `9.4.1` (wrapper committed)
- `compileSdk 36` (minor 1), `minSdk 30`, `targetSdk 36`, Java 11
- Jetpack Compose + Material 3 Expressive (`1.5.0-alpha17`), Compose BOM `2025.02.00`
- `activity-compose`, `lifecycle-runtime/viewmodel`, `core-ktx`, `core-splashscreen`, `navigation-compose`
- Edge-to-edge + SplashScreen + dynamic color + expressive surfaces (see `ui/theme/Theme.kt`)
- Kotlin DSL + version catalog (`gradle/libs.versions.toml`)

Package: `com.customcatalogue.app` (debug: `com.customcatalogue.app.debug`)

## What's in the base

- `MainActivity` (splash + edge-to-edge)
- `AppNav` with 3 destinations: Home / Buttons / Settings
- Home: counter card, toggle, snackbar, navigation buttons
- Buttons gallery: filled / tonal / elevated / outlined / text buttons, chips, slider
- Settings: placeholder for DataStore/Room later

## Build locally

```bash
./gradlew assembleDebug
# APK: app/build/outputs/apk/debug/app-debug.apk
```

## Build in CI (GitHub Actions)

Workflow: `.github/workflows/android-apk.yml`

- Triggers on push to `main`/`master`, PRs, or manual `workflow_dispatch`
- Builds `./gradlew assembleDebug`
- Uploads artifact `CustomCatalogue-debug-apk` (`CustomCatalogue-debug.apk`, 14 days)

## Install on mobile via adb (CI-only flow)

1. Run the workflow (push or Actions → Build Debug APK → Run workflow).
2. Download artifact `CustomCatalogue-debug-apk` and unzip → `CustomCatalogue-debug.apk`.
3. On phone: enable Developer options → USB debugging (or Wireless debugging).
4. Connect:
   ```bash
   adb devices        # should list your device
   ```
   Wireless (Android 11+):
   ```bash
   adb pair <phone-ip>:<pair-port>   # code from Wireless debugging → Pair
   adb connect <phone-ip>:<connect-port>
   ```
5. Install:
   ```bash
   adb install -r CustomCatalogue-debug.apk
   ```
6. Re-install after new CI build: same `adb install -r` command.

## Notes

- `reference-repos/` is local-only and git-ignored. Do not commit it.
