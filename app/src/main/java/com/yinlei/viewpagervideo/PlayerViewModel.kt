package com.yinlei.viewpagervideo

import android.media.MediaPlayer
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PlayerViewModel: ViewModel() {

//    val mediaPlayer = MediaPlayer()
    val mediaPlayer = MyMediaPlayer()
    private val _progressBarVisibility = MutableLiveData(View.VISIBLE)
    val progressBarVisibility: LiveData<Int> = _progressBarVisibility

    private val _videoResolution = MutableLiveData(Pair(0, 0))
    val videoResolution: LiveData<Pair<Int, Int>> = _videoResolution

    init {
        loadVideo()
    }

    fun loadVideo() {
        mediaPlayer.apply {
            _progressBarVisibility.value = View.VISIBLE
            reset()
            setDataSource("https://vd4.bdstatic.com/mda-ka6i0f71pw5b4pz3/mda-ka6i0f71pw5b4pz3.mp4?playlist=%5B%22hd%22%2C%22sc%22%5D&auth_key=1599312195-0-0-07f5497aebd81dc18225c5b6940c6d75&bcevod_channel=searchbox_feed&pd=1&pt=3")
            setOnPreparedListener {
                _progressBarVisibility.value = View.INVISIBLE
                isLooping = true
                it.start()
            }
//            setOnErrorListener { mp, what, extra ->
//
//            }
            setOnVideoSizeChangedListener { mp, width, height ->
                _videoResolution.value = Pair(width, height)
            }
            prepareAsync()
        }
    }

    override fun onCleared() {
        super.onCleared()
        mediaPlayer.release()
    }

}