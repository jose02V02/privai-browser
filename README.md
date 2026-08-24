# PrivAI Browser

PrivAI Browser is a private Android browser prototype based on Mozilla GeckoView.

## Version 0.1

- Android app in Kotlin
- GeckoView web rendering
- URL/search bar
- Private browsing mode
- Tracking protection enabled at session level
- Cleartext HTTP blocked by Android network security config
- Cloud backup and device-transfer backup disabled
- AI panel with page-text extraction and a mock AI response
- GitHub Actions workflow to build a debug APK

## Build APK From Android

1. Create a GitHub repository.
2. Upload this project.
3. Open the `Actions` tab.
4. Run `Build Android APK`.
5. Download the `privai-browser-debug-apk` artifact.

## Roadmap

1. Replace the mock AI response with a real local model connector.
2. Add tabs.
3. Add stronger content blocking lists.
4. Add reader mode with Mozilla Readability.
5. Add a privacy dashboard.
6. Add optional local-only page memory.
