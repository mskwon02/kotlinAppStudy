package com.example.staticfg_to_dynamicfg

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.example.staticfg_to_dynamicfg.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    val myViewModel:MyViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        myViewModel.selectedNum.value=0
        setContentView(binding.root)
        initLayout()
    }

    private fun initLayout() {
        myViewModel.setLiveData(0)
        val transaction=supportFragmentManager.beginTransaction()
        val imgFragment=ImageFragment()
        transaction.replace(R.id.frameLayout,imgFragment)
        transaction.commit()

    }
}