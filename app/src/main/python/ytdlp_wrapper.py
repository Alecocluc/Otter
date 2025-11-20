import yt_dlp
import json

def get_video_info(url):
    """Extract video information without downloading."""
    ydl_opts = {
        'quiet': True,
        'no_warnings': True,
        'extract_flat': False,
    }
    
    try:
        with yt_dlp.YoutubeDL(ydl_opts) as ydl:
            info = ydl.extract_info(url, download=False)
            
            # Process formats to be JSON serializable
            formats = []
            for f in info.get('formats', []):
                formats.append({
                    'format_id': f.get('format_id'),
                    'ext': f.get('ext'),
                    'resolution': f.get('resolution'),
                    'filesize': f.get('filesize'),
                    'vcodec': f.get('vcodec'),
                    'acodec': f.get('acodec'),
                    'url': f.get('url')
                })
                
            return json.dumps({
                'title': info.get('title'),
                'thumbnail': info.get('thumbnail'),
                'duration': info.get('duration'),
                'uploader': info.get('uploader'),
                'view_count': info.get('view_count'),
                'formats': formats
            })
    except Exception as e:
        return json.dumps({'error': str(e)})

def download_video(url, output_path, format_id=None, progress_callback=None):
    """Download video with specified format."""
    
    def progress_hook(d):
        if progress_callback:
            # Import java.util.HashMap to create a proper Java Map
            try:
                from java.util import HashMap
                
                # Create a Java HashMap that can be passed to Kotlin
                status_data = HashMap()
                status_data.put('status', str(d.get('status', '')))
                status_data.put('downloaded_bytes', str(d.get('downloaded_bytes', 0)))
                # Ensure we have a valid total bytes value
                total = d.get('total_bytes')
                if total is None:
                    total = d.get('total_bytes_estimate')
                if total is None:
                    total = 0
                
                status_data.put('total_bytes', str(total))
                
                # Try to get percentage directly from yt-dlp
                # This is often more accurate for HLS streams or when total size is estimated
                percent = d.get('_percent_str', '').replace('%','')
                status_data.put('percent', str(percent))
                
                status_data.put('speed', str(d.get('speed', 0)))
                status_data.put('eta', str(d.get('eta', 0)))
                status_data.put('filename', str(d.get('filename', '')))
                
                # Call the onProgress method of the Java interface
                progress_callback.onProgress(status_data)
            except Exception as e:
                # Silently fail on callback error to not interrupt download
                pass

    # Select format that doesn't require merging (pre-merged mp4)
    # This avoids FFmpeg dependency
    format_selector = format_id if format_id and format_id != 'best' else 'best[ext=mp4]/bestvideo[ext=mp4]+bestaudio[ext=m4a]/best'
    
    ydl_opts = {
        'format': format_selector,
        'outtmpl': output_path,
        'progress_hooks': [progress_hook],
        'quiet': False,  # Enable output for debugging
        'no_warnings': False,
        'ignoreerrors': False,
        'nocheckcertificate': True,  # Avoid SSL issues
        # Don't use FFmpeg for now - just download single file formats
        'prefer_ffmpeg': False,
    }
    
    try:
        print(f"Starting download: {url} to {output_path}")
        print(f"Format: {format_selector}")
        with yt_dlp.YoutubeDL(ydl_opts) as ydl:
            error_code = ydl.download([url])
            if error_code != 0:
                return json.dumps({'status': 'error', 'message': f'yt-dlp returned error code {error_code}'})
        print(f"Download completed successfully: {output_path}")
        return json.dumps({'status': 'success', 'path': output_path})
    except Exception as e:
        error_msg = str(e)
        print(f"Download error: {error_msg}")
        import traceback
        traceback.print_exc()
        return json.dumps({'status': 'error', 'message': error_msg})
