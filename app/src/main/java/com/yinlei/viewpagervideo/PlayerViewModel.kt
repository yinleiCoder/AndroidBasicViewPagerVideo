package com.yinlei.viewpagervideo

import android.media.MediaPlayer
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

enum class PlayerStatus {
    Playing, Paused, Completed, NotReady
}

class PlayerViewModel: ViewModel() {

//    val mediaPlayer = MediaPlayer()
    private var controllerShowTime = 0L
    val mediaPlayer = MyMediaPlayer()
    private val _playerStatus = MutableLiveData(PlayerStatus.NotReady)
    val playerStatus: LiveData<PlayerStatus> = _playerStatus
    private val _controllerFrameVisibility = MutableLiveData(View.INVISIBLE)
    val controllerFrameVisibility: LiveData<Int> = _controllerFrameVisibility
    private val _bufferPercent = MutableLiveData(0)
    val bufferPercent: LiveData<Int> = _bufferPercent
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
            _playerStatus.value = PlayerStatus.NotReady
            setOnPreparedListener {
                _progressBarVisibility.value = View.INVISIBLE
//                isLooping = true
                it.start()
                _playerStatus.value = PlayerStatus.Playing
            }
//            setOnErrorListener { mp, what, extra ->
//
//            }
            setOnVideoSizeChangedListener { mp, width, height ->
                _videoResolution.value = Pair(width, height)
            }
            setOnBufferingUpdateListener { _, percent ->
                _bufferPercent.value = percent
            }
            setOnSeekCompleteListener {
                mediaPlayer.start()
                _progressBarVisibility.value = View.INVISIBLE
            }
            setOnCompletionListener {
                _playerStatus.value = PlayerStatus.Completed
            }
            prepareAsync()
        }
    }

    fun toggleControllerVisibility() {
        if (_controllerFrameVisibility.value == View.INVISIBLE) {
            _controllerFrameVisibility.value = View.VISIBLE
            controllerShowTime = System.currentTimeMillis()
            viewModelScope.launch {
                delay(3000)
                if (System.currentTimeMillis() - controllerShowTime > 3000) {
                    _controllerFrameVisibility.value = View.INVISIBLE
                }
            }
        } else {
            _controllerFrameVisibility.value = View.INVISIBLE
        }
    }

    fun togglePlayerStatus() {
        when(_playerStatus.value){
            PlayerStatus.Playing -> {
                mediaPlayer.pause()
                _playerStatus.value = PlayerStatus.Paused
            }
            PlayerStatus.Paused -> {
                mediaPlayer.start()
                _playerStatus.value = PlayerStatus.Playing
            }
            PlayerStatus.Completed -> {
                mediaPlayer.start()
                _playerStatus.value = PlayerStatus.Completed
            }
            else -> return
        }
    }

    fun playerSeekToProgress(progress: Int) {
        _progressBarVisibility.value = View.VISIBLE
        mediaPlayer.seekTo(progress)
    }
    
    fun emmitVideoResolution() {
        _videoResolution.value = _videoResolution.value
    }

    override fun onCleared() {
        super.onCleared()
        mediaPlayer.release()
    }

}