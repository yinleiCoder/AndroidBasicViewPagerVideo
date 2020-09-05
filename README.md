# Android滑动播放基础Demo:vhs:


### 简要:

无论是音视频播放的核心对象是MediaPlayer。

除此之外，还有一个比较多用的是ExoPlayer.【Google维护】，支持Http的动态自适应流DASH、SmoothStreaming和通道加密等。

然后是视图来呈现画面——SurfaceView或者Texture View。

还有个控制器(音量、进度等控制)。

现在还有个Media2，不过现在官方文档不太完善。

### 官方提供的音视频播放的2种不同的方式：

- VideoView[dev分支演示]
- 自己实现SurfaceView和MediaPlayer

