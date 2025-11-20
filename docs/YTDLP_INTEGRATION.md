# yt-dlp Integration Guide

## Overview

Otter integrates yt-dlp, a powerful Python-based video downloader, into an Android application. This document outlines the integration strategy, implementation details, and best practices.

## Why yt-dlp?

- **Wide Platform Support**: 1000+ websites including YouTube, Vimeo, Dailymotion, etc.
- **Active Development**: Regular updates and bug fixes
- **Rich Features**: Format selection, playlist support, subtitles, metadata, etc.
- **Battle-tested**: Fork of youtube-dl with improvements
- **Flexible**: Extensive configuration options

## Integration Approach

### Option 1: Chaquopy (Recommended)

**Chaquopy** is the Python SDK for Android that allows running Python code in Android apps.

#### Pros:
- ✅ Direct Python integration
- ✅ Access to entire Python ecosystem
- ✅ Can use yt-dlp directly without modifications
- ✅ FFmpeg integration available

#### Cons:
- ❌ Increases APK size (~50MB)
- ❌ Commercial licensing required for closed-source apps (free for open-source)
- ❌ Performance overhead

#### Implementation:

```kotlin
// build.gradle.kts
plugins {
    id("com.chaquo.python")
}

chaquopy {
    defaultConfig {
        version = "3.11"
        pip {
            install("yt-dlp")
        }
    }
}
```

### Option 2: Native Executable

Package yt-dlp as a standalone executable and call it via ProcessBuilder.

#### Pros:
- ✅ No Python runtime needed
- ✅ Smaller overhead
- ✅ No licensing concerns

#### Cons:
- ❌ Complex to maintain
- ❌ Less flexible
- ❌ Cross-platform compilation challenges

### Option 3: Backend Service

Run yt-dlp on a server and communicate via REST API.

#### Pros:
- ✅ No client-side dependencies
- ✅ Centralized updates
- ✅ Better performance

#### Cons:
- ❌ Requires server infrastructure
- ❌ Network dependency
- ❌ Privacy concerns
- ❌ Hosting costs

**Recommended**: Option 1 (Chaquopy) for open-source project.

## Implementation Plan

### Phase 1: Setup Python Bridge

```kotlin
// PythonManager.kt
object PythonManager {
    private lateinit var python: Python
    
    fun initialize(context: Context) {
        if (!Python.isStarted()) {
            Python.start(AndroidPlatform(context))
        }
        python = Python.getInstance()
    }
    
    fun getModule(name: String): PyObject {
        return python.getModule(name)
    }
}
```

### Phase 2: Create yt-dlp Wrapper

```python
# ytdlp_wrapper.py
import yt_dlp
import json

def get_video_info(url):
    """Extract video information without downloading."""
    ydl_opts = {
        'quiet': True,
        'no_warnings': True,
        'extract_flat': False,
    }
    
    with yt_dlp.YoutubeDL(ydl_opts) as ydl:
        info = ydl.extract_info(url, download=False)
        return json.dumps({
            'title': info.get('title'),
            'thumbnail': info.get('thumbnail'),
            'duration': info.get('duration'),
            'formats': [{
                'format_id': f.get('format_id'),
                'ext': f.get('ext'),
                'resolution': f.get('resolution'),
                'filesize': f.get('filesize'),
            } for f in info.get('formats', [])]
        })

def download_video(url, output_path, format_id=None, progress_callback=None):
    """Download video with specified format."""
    ydl_opts = {
        'format': format_id or 'best',
        'outtmpl': output_path,
        'progress_hooks': [progress_callback] if progress_callback else [],
    }
    
    with yt_dlp.YoutubeDL(ydl_opts) as ydl:
        ydl.download([url])
```

### Phase 3: Kotlin Bridge

```kotlin
// YtDlpBridge.kt
class YtDlpBridge {
    private val ytdlpModule: PyObject
    
    init {
        ytdlpModule = PythonManager.getModule("ytdlp_wrapper")
    }
    
    suspend fun getVideoInfo(url: String): VideoInfo = withContext(Dispatchers.IO) {
        val result = ytdlpModule.callAttr("get_video_info", url)
        val jsonString = result.toString()
        Json.decodeFromString<VideoInfo>(jsonString)
    }
    
    suspend fun downloadVideo(
        url: String,
        outputPath: String,
        formatId: String? = null,
        onProgress: (Float) -> Unit
    ) = withContext(Dispatchers.IO) {
        val progressCallback = object : PyObject {
            fun __call__(d: PyObject) {
                val status = d.get("status").toString()
                if (status == "downloading") {
                    val downloaded = d.get("downloaded_bytes")?.toLong() ?: 0
                    val total = d.get("total_bytes")?.toLong() ?: 1
                    val progress = downloaded.toFloat() / total.toFloat()
                    onProgress(progress)
                }
            }
        }
        
        ytdlpModule.callAttr("download_video", url, outputPath, formatId, progressCallback)
    }
}
```

## Configuration

### Essential yt-dlp Options

```python
ydl_opts = {
    # Output
    'outtmpl': '%(title)s.%(ext)s',
    'format': 'best',
    
    # Network
    'socket_timeout': 30,
    'retries': 10,
    
    # Filesystem
    'restrictfilenames': True,
    'windowsfilenames': True,
    
    # Progress
    'progress_hooks': [progress_callback],
    
    # Metadata
    'writethumbnail': True,
    'embedthumbnail': True,
    'addmetadata': True,
    
    # Subtitles
    'writesubtitles': True,
    'writeautomaticsub': True,
    'subtitleslangs': ['en'],
    
    # Postprocessing
    'postprocessors': [{
        'key': 'FFmpegExtractAudio',
        'preferredcodec': 'mp3',
        'preferredquality': '192',
    }],
}
```

## Error Handling

```kotlin
sealed class YtDlpResult<out T> {
    data class Success<T>(val data: T) : YtDlpResult<T>()
    data class Error(val exception: YtDlpException) : YtDlpResult<Nothing>()
}

sealed class YtDlpException(message: String) : Exception(message) {
    class NetworkError(message: String) : YtDlpException(message)
    class UnsupportedUrl(message: String) : YtDlpException(message)
    class DownloadError(message: String) : YtDlpException(message)
    class ParseError(message: String) : YtDlpException(message)
}
```

## Performance Optimization

### 1. Lazy Initialization
Initialize Python runtime only when needed.

### 2. Connection Pooling
Reuse HTTP connections for multiple downloads.

### 3. Caching
Cache video info to avoid repeated API calls.

### 4. Background Processing
Use WorkManager for long-running downloads.

### 5. Resource Management
Properly cleanup Python objects and file handles.

## Security Considerations

### URL Validation
```kotlin
fun isValidUrl(url: String): Boolean {
    return url.matches(Regex("^https?://.*"))
}
```

### Sandboxing
- Run Python code in isolated context
- Limit file system access
- Validate all outputs

### Update Strategy
- Regular yt-dlp updates
- Security patch monitoring
- Automated testing

## Testing

### Unit Tests
```kotlin
@Test
fun `test video info extraction`() = runBlocking {
    val bridge = YtDlpBridge()
    val info = bridge.getVideoInfo("https://www.youtube.com/watch?v=dQw4w9WgXcQ")
    
    assertNotNull(info.title)
    assertTrue(info.formats.isNotEmpty())
}
```

### Integration Tests
- Test with real URLs
- Verify download completion
- Check metadata extraction
- Validate format selection

## Troubleshooting

### Common Issues

**APK size too large**
- Use Android App Bundle
- Enable code shrinking
- Remove unused Python modules

**Slow initialization**
- Move Python.start() to splash screen
- Use lazy initialization
- Cache Python module references

**Download failures**
- Check network connectivity
- Verify URL validity
- Update yt-dlp version
- Check device storage

**FFmpeg not found**
- Include FFmpeg binary
- Configure PATH correctly
- Use Chaquopy's FFmpeg package

## Future Enhancements

- [ ] Parallel downloads
- [ ] Resume support
- [ ] Playlist batch processing
- [ ] Custom extractors
- [ ] Advanced filtering
- [ ] Cookie support for authenticated content
- [ ] Proxy configuration
- [ ] Rate limiting
- [ ] Download scheduling
