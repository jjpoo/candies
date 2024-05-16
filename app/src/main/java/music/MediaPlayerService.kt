package music

import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.Binder
import android.os.Build
import android.os.IBinder
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.Log
import com.android.candywords.R

class MediaPlayerService : Service() {

    private val binder = MediaPlayerBinder()
    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var vibrator: Vibrator
    private lateinit var clickMediaPlayer: MediaPlayer
    private lateinit var winMediaPlayer: MediaPlayer

    inner class MediaPlayerBinder : Binder() {
        fun getService(): MediaPlayerService = this@MediaPlayerService
    }

    override fun onBind(intent: Intent?): IBinder? {
        return binder
    }

    override fun onCreate() {
        super.onCreate()
        mediaPlayer = MediaPlayer.create(this, R.raw.bg)
        clickMediaPlayer = MediaPlayer.create(this, R.raw.tap)
        winMediaPlayer = MediaPlayer.create(this, R.raw.win)
        Log.e("Service started", "STARTED")
        mediaPlayer.isLooping = true
        vibrator = this@MediaPlayerService.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
    }

    fun startVibration() {
        if (Build.VERSION.SDK_INT >= 26) {
            vibrator.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
        } else {
            vibrator.vibrate(200)
        }
    }

    fun stopVibratioin() {
        vibrator.cancel()
    }

    //start the mediaPlayer from the beginning if it wasn't playback before
    fun startMusic() {
        if (!mediaPlayer.isPlaying) {
            mediaPlayer.start()
        }
    }

    fun stopMusic() {
        if (mediaPlayer.isPlaying) {
            mediaPlayer.pause()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        //release resources of using mediaPlayer
        mediaPlayer.release()
    }

    fun setClickSound() {
        clickMediaPlayer.start()
    }

    fun stopClickSound() {
        clickMediaPlayer.stop()
    }

    fun startWinSound() {
        winMediaPlayer.start()
    }

    fun stopWinSound() {
        winMediaPlayer.stop()
    }
}