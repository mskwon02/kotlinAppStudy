package com.example.dynamicfragment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.dynamicfragment.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding

    //동적 프레그먼트로 사용할 프래그먼트 객체 생성
    val imgFragment=ImageFragment()
    val itemFragment=ItemFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initLayout()
    }

    private fun initLayout() {
        //트랜잭션 객체 생성 -> 트랜잭션 시작
        val fragmentTransaction=supportFragmentManager.beginTransaction()
        //R.id.frameLayout이라는 레이아웃 공간에 imgFragment 끼운다
        fragmentTransaction.replace(R.id.frameLayout,imgFragment)
        //트랜잭션 종료
        fragmentTransaction.commit()

        binding.button1.setOnClickListener {
            //img프레그먼트가 안보이는 상태라면
            if(!imgFragment.isVisible){
                //트랜잭션 객체 생성 -> 트랜잭션 시작
                val fragmentTransaction=supportFragmentManager.beginTransaction()

                //백스택에 넣는 작업 -> 그냥 해봤다
                fragmentTransaction.addToBackStack(null)
                //R.id.frameLayout이라는 레이아웃 공간에 imgFragment 끼운다
                fragmentTransaction.replace(R.id.frameLayout,imgFragment)
                //트랜잭션 종료
                fragmentTransaction.commit()
            }

        }
        binding.button2.setOnClickListener {
            //item프레그먼트가 안보이는 상태라면
            if(!itemFragment.isVisible){
                //트랜잭션 객체 생성 -> 트랜잭션 시작
                val fragmentTransaction=supportFragmentManager.beginTransaction()

                //백스택에 넣는 작업 -> 그냥 해봤다
                fragmentTransaction.addToBackStack(null)
                //R.id.frameLayout이라는 레이아웃 공간에 imgFragment 끼운다
                fragmentTransaction.replace(R.id.frameLayout,itemFragment)
                //트랜잭션 종료
                fragmentTransaction.commit()
            }
        }
    }
}