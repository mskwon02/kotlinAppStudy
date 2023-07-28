package com.example.coroutine

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.android.volley.AsyncRequestQueue
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.coroutine.databinding.ActivityMainBinding
import kotlinx.coroutines.*
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : AppCompatActivity() {
    lateinit var binding:ActivityMainBinding
    //네트워크 작업을 백그라운드에서 하기 위한 코루틴 객체 생성

    //UI(프로그레스 바) 업데이트 시키기 위한 코루틴
    val scope= CoroutineScope(Dispatchers.Main)

    //volley라이브러리 사용하기 위해 필요한 큐객체
    lateinit var requestQueue: RequestQueue
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //initLayout()
        initLayout_With_Volley()
    }

    //volley라이브러리 사용해 네트워크로 부터 데이터 받아오기
    //복잡하게 네트워크 작업 할 필요도 없고(아래 적은 loadNetwork함수 같은거 적을 필요 없음)
    //받아온 데이터를 readline()으로 읽어 string으로 바꿀 필요도 없고
    //코루틴 사용할 필요도 없음 -> request객체 생성과정에 await기능까지 적용되어 있음
    private fun initLayout_With_Volley() {
        requestQueue= Volley.newRequestQueue(this)
        binding.apply {
            button.setOnClickListener {
                progressBar.visibility=View.VISIBLE

                //네트워크로 부터 결과값을 string형으로 받아오고 싶기 떄문에 StringRequest()객체 생성
                val stringRequest= StringRequest(
                    Request.Method.GET,
                    editText.text.toString(),
                    //요청 성공시 작동하는 콜백함수
                    {
                        //textView.text=it
                        //받아온 데이터를 그냥 string으로 넣으니까 한글이 깨져서 나옴
                        //인코딩 모드를 바꿔서 넣어준다
                        //바이트 array로 바꾼후 UTF_8모드로 바꿔준다
                        textView.text=String(it.toByteArray(Charsets.ISO_8859_1),Charsets.UTF_8)
                        progressBar.visibility=View.GONE
                    },
                    //요청 실패시 작동하는 콜백함수
                    {
                        textView.text=it.message
                    }

                )
                requestQueue.add(stringRequest)
            }
        }
    }

    private fun initLayout() {
        binding.apply {
            //버튼 누르면 할 일
            //progressbar 보이게 하기
            //네트워크에서 데이터 다운받기
            //다운받은 데이터 textview에 넣기
            //이후 progressbar다시 안보이게
            button.setOnClickListener {

                //아래 코루틴 객체는 메인쓰레드에서 돌아가는 코루틴 -> UI건들일 수 있다
                scope.launch {
                    progressBar.visibility= View.VISIBLE
                    var data=""

                    //네트워크로 부터 데이터 받아야 함
                    //-> 해당 작업은 메인쓰레드에서 불가능
                    //-> 현재 scope객체는 Dispatcher.Main으로 메인쓰레드에서 돌아가는 코루틴
                    //백그라운드 쓰레드에서 돌아가는 코루틴 객체 새로 만들어야 한다
                    //또한 네트워크에서 데이터를 다 받은 후 다음작업으로 넘어가야 하기 떄문에 async로 코루틴 실행하고
                    //await()메서드를 사용해 해당 코루틴 완료 전까지 다음 코드로 넘어가지 못하게 한다
                    //await()메서드는 코루틴을 async로 실행해야만 사용할 수 있다
                    CoroutineScope(Dispatchers.IO).async {
                        data=loadNetwork(URL(editText.text.toString()))
                    }.await()
                    textView.text=data
                    progressBar.visibility=View.INVISIBLE

                }

            }
        }
    }

    //네트워크 작업
    fun loadNetwork(url: URL):String{
        var result=""
        val connect = url.openConnection() as HttpURLConnection
        connect.connectTimeout=4000
        connect.readTimeout=1000
        connect.requestMethod="GET"
        connect.connect()

        //connect에 대한 response코드가 반환됨
        val responseCode=connect.responseCode
        //HTTP_OK = 200
        if(responseCode==HttpURLConnection.HTTP_OK){
            result=streamToString(connect.inputStream)
        }
        return result
    }

    private fun streamToString(inputStream: InputStream): String {
        val bufferReader = BufferedReader(InputStreamReader(inputStream))
        var line:String
        var result =""
        try{
            do{
                line=bufferReader.readLine()
                if(line!=null){
                    result+=line
                }
            }while(line!=null)
            Log.i("close","Close")
            inputStream.close()
        }catch (ex:Exception){
            Log.e("error","읽기실패")
        }
        return result
    }
}