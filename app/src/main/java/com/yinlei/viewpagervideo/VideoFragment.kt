package com.yinlei.viewpagervideo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.adapter.FragmentStateAdapter
import kotlinx.android.synthetic.main.fragment_video.*


private val videoUrls = listOf<String>(
    "https://giligili-yinlei.oss-cn-shanghai.aliyuncs.com/yinliyuan/liyuan_video1.mp4",
    "https://giligili-yinlei.oss-cn-shanghai.aliyuncs.com/yinliyuan/liyuan_video2.mp4",
    "https://giligili-yinlei.oss-cn-shanghai.aliyuncs.com/yinliyuan/liyuan_video3.mp4",
    "https://giligili-yinlei.oss-cn-shanghai.aliyuncs.com/yinliyuan/liyuan_video4.mp4",
    "https://giligili-yinlei.oss-cn-shanghai.aliyuncs.com/yinliyuan/liyuan_video5.mp4",
    "https://giligili-yinlei.oss-cn-shanghai.aliyuncs.com/yinliyuan/liyuan_video6.mp4",
    "https://giligili-yinlei.oss-cn-shanghai.aliyuncs.com/yinliyuan/liyuan_video7.mp4",
    "https://giligili-yinlei.oss-cn-shanghai.aliyuncs.com/yinliyuan/liyuan_video8.mp4",
    "https://giligili-yinlei.oss-cn-shanghai.aliyuncs.com/yinliyuan/liyuan_video9.mp4",
    "https://giligili-yinlei.oss-cn-shanghai.aliyuncs.com/yinliyuan/liyuan_video10.mp4"
)
class VideoFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_video, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        videoViewPager.apply {
            adapter = object : FragmentStateAdapter(this@VideoFragment) {
                override fun getItemCount() = videoUrls.size
                override fun createFragment(position: Int) = PlayerFragment(videoUrls[position])
            }
            offscreenPageLimit = 3
        }
    }

}