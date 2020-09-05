package com.yinlei.viewpagervideo

import android.media.PlaybackParams
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.MediaController
import androidx.lifecycle.lifecycleScope
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

//https://s3.pstatp.com/aweme/resource/web/static/image/index/tvc-v3_0b9db49.mp4
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val videoPath = "android.resource://$packageName/${R.raw.meimei2}"
        videoView.setVideoPath(videoPath)
//        videoView.setVideoURI(Uri.parse(videoPath))
//        videoView.setMediaController(MediaController(this))
        videoView.setOnPreparedListener {
            Log.d("yinlei", "mediaplay id: ${it}")
            progressBar2.visibility = View.INVISIBLE
            progressBar.max = it.duration
            it.isLooping = true
            it.playbackParams = PlaybackParams().apply {
//                speed = 2f // 倍速播放
//                pitch = 0.5f // 改变音高
            }
//            it.seekTo(5000)
            it.start()
        }
        // 自定义控制器
        lifecycleScope.launch {
            while (true) {
                if (videoView.isPlaying) {
                    progressBar.progress = videoView.currentPosition
                }
                delay(500)
            }
        }

//        videoView.start()
    }
}