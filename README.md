# Otter 🦦

A modern, beautiful Android client for downloading videos and audio using yt-dlp. Inspired by [Seal](https://github.com/JunkFood02/Seal) and [ytdlnis](https://github.com/deniscerri/ytdlnis), Otter brings a premium glassmorphism UI with liquid glass aesthetics.

## ✨ Features

- 🎨 **Premium Glassmorphism UI** - Apple-inspired liquid glass design with smooth animations
- 🎥 **Video Downloads** - Download videos from 1000+ websites powered by yt-dlp
- 🎵 **Audio Extraction** - Extract audio in various formats (MP3, M4A, OPUS, etc.)
- 📱 **Modern Android** - Built with Jetpack Compose and Material Design 3 foundations
- 🎯 **Format Selection** - Choose video quality, audio quality, and container formats
- 📋 **Playlist Support** - Download entire playlists or individual videos
- 🌙 **Dark Mode** - Beautiful dark theme with glassmorphic elements
- 📦 **No Ads** - Free and open source, no tracking

## 🏗️ Architecture

Otter is built using modern Android development practices:

- **Kotlin** - 100% Kotlin codebase
- **Jetpack Compose** - Modern declarative UI
- **Material Design 3** - Enhanced with custom glassmorphism components
- **MVVM Architecture** - Clean separation of concerns
- **yt-dlp Integration** - Python bridge for video downloading capabilities

For detailed architecture documentation, see [docs/ARCHITECTURE.md](docs/ARCHITECTURE.md).

## 🐍 Python Integration

While Otter is an Android app written in Kotlin, it uses yt-dlp (Python) for the downloading functionality. The integration is achieved through:

- **Chaquopy** - Python runtime for Android
- **yt-dlp** - The powerful video downloader library
- **FFmpeg** - For media processing and conversion

See [docs/YTDLP_INTEGRATION.md](docs/YTDLP_INTEGRATION.md) for implementation details.

## 🚀 Getting Started

### Prerequisites

- Android Studio Hedgehog or later
- JDK 17 or later
- Android SDK 24+ (target 34)

### Building

```bash
# Clone the repository
git clone https://github.com/Alecocluc/Otter.git
cd Otter

# Build the app
./gradlew assembleDebug

# Install on device
./gradlew installDebug
```

## 🎨 Design Philosophy

Otter embraces a **premium glassmorphism** design language that prioritizes:

- **Depth & Layering** - Frosted glass effects with blur
- **Smoothness** - Rounded corners and fluid animations
- **Clarity** - High contrast text on glass surfaces
- **Minimalism** - Clean, uncluttered interfaces
- **Apple-inspired** - Liquid glass aesthetics over standard Material Design

## 📚 Documentation

- [Architecture Overview](docs/ARCHITECTURE.md)
- [yt-dlp Integration Guide](docs/YTDLP_INTEGRATION.md)
- [Development Roadmap](docs/ROADMAP.md)

## 🗺️ Roadmap

Check out our [detailed roadmap](docs/ROADMAP.md) to see what's planned for future releases.

## 📄 License

This project is licensed under the GNU General Public License v3.0 - see the [LICENSE](LICENSE) file for details.

## 🙏 Acknowledgments

- [yt-dlp](https://github.com/yt-dlp/yt-dlp) - The amazing video downloader
- [Seal](https://github.com/JunkFood02/Seal) - Design inspiration
- [ytdlnis](https://github.com/deniscerri/ytdlnis) - Feature inspiration
- [FFmpeg](https://ffmpeg.org/) - Media processing

## ⚠️ Disclaimer

This app is intended for downloading content that you have the right to download. Please respect copyright laws and content creators' rights.

---

Made with ❤️ by the Otter team
