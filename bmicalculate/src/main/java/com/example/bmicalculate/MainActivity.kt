package com.example.bmicalculate

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.bmicalculate.databinding.ActivityMainBinding
import kotlin.math.pow

class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }
    fun init(){
        val button=binding.button
        val weight=binding.weight
        val height=binding.height
        val imageView=binding.imageView

        button.setOnClickListener {
            val bmi=weight.text.toString().toDouble()/(height.text.toString().toDouble()/100).pow(2.0)
            var resultString:String
            when {
                bmi>=35 -> {
                    resultString="고도비만"
                    imageView.setImageResource(R.drawable.baseline_sentiment_very_dissatisfied_24)
                }
                bmi>=23 -> {
                    resultString="과체중"
                    imageView.setImageResource(R.drawable.baseline_sentiment_dissatisfied_24)

                }
                bmi>=18.5 -> {
                    resultString="정상"
                    imageView.setImageResource(R.drawable.baseline_sentiment_satisfied_alt_24)

                }
                else -> {
                    resultString="저체중"
                    imageView.setImageResource(R.drawable.baseline_sentiment_satisfied_24)

                }
            }
            Toast.makeText(this, resultString,Toast.LENGTH_SHORT).show()

        }


    }
}
