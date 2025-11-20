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
            # Convert dictionary to a simple dict for Java/Kotlin
            status_data = {
                'status': d.get('status'),
                'downloaded_bytes': str(d.get('downloaded_bytes', 0)),
                'total_bytes': str(d.get('total_bytes') or d.get('total_bytes_estimate', 0)),
                'speed': str(d.get('speed', 0)),
                'eta': str(d.get('eta', 0)),
                'filename': d.get('filename', '')
            }
            # Call the onProgress method of the Java interface
            try:
                progress_callback.onProgress(status_data)
            except AttributeError:
                # Fallback if it's a callable
                progress_callback(status_data)

    ydl_opts = {
        'format': format_id or 'best',
        'outtmpl': output_path,
        'progress_hooks': [progress_hook],
        'quiet': True,
        'no_warnings': True,
    }
    
    try:
        with yt_dlp.YoutubeDL(ydl_opts) as ydl:
            ydl.download([url])
        return json.dumps({'status': 'success'})
    except Exception as e:
        return json.dumps({'status': 'error', 'message': str(e)})
