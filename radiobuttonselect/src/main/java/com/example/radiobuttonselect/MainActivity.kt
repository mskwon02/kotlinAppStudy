package com.example.radiobuttonselect

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.radiobuttonselect.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }
    
    fun init(){
        binding.rgSelectImg.setOnCheckedChangeListener { group, checkedId ->
            when(checkedId){
                R.id.rb1 -> binding.ivWindoIimage.setImageResource(R.drawable.img32)
                R.id.rb2 -> binding.ivWindoIimage.setImageResource(R.drawable.img33)
                R.id.rb3 -> binding.ivWindoIimage.setImageResource(R.drawable.img34)
            }
        }
        binding.tbToggle.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked)
                Toast.makeText(this, "ToggleOn",Toast.LENGTH_SHORT).show()
            else
                Toast.makeText(this, "ToggleOff",Toast.LENGTH_SHORT).show()

        }
    }
}