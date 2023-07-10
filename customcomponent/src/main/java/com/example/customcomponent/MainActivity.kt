package com.example.customcomponent

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.customcomponent.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    private lateinit var volumeControlListener: VolumeControlView
    var mediaPlayer:MediaPlayer?=null
    var vol=0.5f


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }
    private fun init(){
        binding.apply {
            ivDial.setVolumeListener{ angle->
                vol=if(angle>0){
                    angle/360
                }else{
                    (360+angle)/360
                }
                mediaPlayer?.setVolume(vol,vol)
            }
            btnStart.setOnClickListener {
                if(mediaPlayer==null){
                    mediaPlayer=MediaPlayer.create(this@MainActivity, R.raw.song)
                    mediaPlayer?.setVolume(vol,vol)
                }
                mediaPlayer?.start()

            }
            btnEnd.setOnClickListener {
                mediaPlayer?.stop()

                //mediaPlayer를 메모리에서 아예 제거
                mediaPlayer?.release()

                //시작 버튼을 눌렀을때 mediaPlayer 객체 새로 생성 되도록 하기 위해서 null값 넣어놓는다
                mediaPlayer=null
            }
            btnPause.setOnClickListener {
                mediaPlayer?.pause()
            }
        }

    }
}
