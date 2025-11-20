# Copilot Instructions

## UI Development & Design System
- **Reuse Components**: Before creating new UI elements, ALWAYS check `com.appharbor.otter.ui.components` for existing Glassmorphism components.
  - Use `GlassCard` for containers.
  - Use `GlassButton`, `GlassIconButton`, `GlassFAB`, and `GlassExtendedFAB` for actions.
  - Use `GlassInput` for text fields.
  - Use `GlassNavigationBar` for bottom navigation.
  - Use `GlassSnackbar` for notifications/toasts.
  - Use `GlassBackground` for screen backgrounds.
- **Theming**: Strictly adhere to the Glassmorphism theme defined in `com.appharbor.otter.ui.theme`.
  - Use `Glass*` color variables from `Color.kt` (e.g., `GlassBackgroundStart`, `GlassAccentPrimary`).
  - Ensure high contrast for text on glass surfaces (white text is primary).
  - Apply `blur` and transparency effects consistent with the "Liquid Glass" aesthetic.

## Resources Management
- **Strings**: All user-facing text MUST be defined in `app/src/main/res/values/strings.xml`.
  - Do not hardcode strings in Composable functions.
  - Use `stringResource(R.string.your_string_id)` in Compose.

## Architecture
- **Python Integration**: When working on download functionality, remember that `yt-dlp` is integrated via Chaquopy.
  - Use `YtDlpBridge` to interact with Python code.
  - Python scripts are located in `app/src/main/python/`.
