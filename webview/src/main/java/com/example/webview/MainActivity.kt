package com.example.webview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebViewClient
import com.example.webview.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }
    private fun init(){
        binding.apply {
            webView.webViewClient= WebViewClient()
            webView.settings.javaScriptEnabled=true
            webView.settings.defaultTextEncodingName="utf-8"
            //웹뷰에 불러올 웹페이지 url정보 준다
            webView.loadUrl("https://www.google.com")
        }
    }

}