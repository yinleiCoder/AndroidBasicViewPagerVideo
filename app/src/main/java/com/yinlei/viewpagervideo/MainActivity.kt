package com.yinlei.viewpagervideo

import android.content.res.Configuration
import android.media.PlaybackParams
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.SurfaceHolder
import android.view.View
import android.widget.FrameLayout
import android.widget.MediaController
import android.widget.VideoView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {


    private lateinit var playerViewModel: PlayerViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        playerViewModel = ViewModelProvider(this).get(PlayerViewModel::class.java).apply {
            progressBarVisibility.observe(this@MainActivity, Observer {
                progressBar3.visibility = it
            })
            videoResolution.observe(this@MainActivity, Observer {
                playerFrame.post {// 放到消息队列中
                    resizePlayer(it.first, it.second)
                }
            })
        }
        lifecycle.addObserver(playerViewModel.mediaPlayer)
        surfaceView.holder.addCallback(object : SurfaceHolder.Callback {
            override fun surfaceCreated(holder: SurfaceHolder?) {
            }

            override fun surfaceChanged(
                holder: SurfaceHolder?,
                format: Int,
                width: Int,
                height: Int
            ) {
                playerViewModel.mediaPlayer.setDisplay(holder)
                playerViewModel.mediaPlayer.setScreenOnWhilePlaying(true) // 永远点亮屏幕
            }

            override fun surfaceDestroyed(holder: SurfaceHolder?) {
            }
        })


//        val videoPath = "android.resource://$packageName/${R.raw.meimei2}"
//        videoView.setVideoPath(videoPath)
////        videoView.setVideoURI(Uri.parse(videoPath))
////        videoView.setMediaController(MediaController(this))
//        videoView.setOnPreparedListener {
//            Log.d("yinlei", "mediaplay id: ${it}")
//            progressBar2.visibility = View.INVISIBLE
//            progressBar.max = it.duration
//            it.isLooping = true
//            it.playbackParams = PlaybackParams().apply {
////                speed = 2f // 倍速播放
////                pitch = 0.5f // 改变音高
//            }
////            it.seekTo(5000)
//            it.start()
//        }
//        // 自定义控制器
//        lifecycleScope.launch {
//            while (true) {
//                if (videoView.isPlaying) {
//                    progressBar.progress = videoView.currentPosition
//                }
//                delay(500)
//            }
//        }

//        videoView.start()
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            hideSystemUI()
        }
    }

    private fun resizePlayer(width: Int, height: Int) {
        if (width == 0 || height == 0) return
        surfaceView.layoutParams = FrameLayout.LayoutParams(
            playerFrame.height * width / height,
            FrameLayout.LayoutParams.MATCH_PARENT,
            Gravity.CENTER
        )
    }

    private fun hideSystemUI() {
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_IMMERSIVE
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN)
    }
}