package com.example.webpageparsing

import android.app.Activity
import android.content.Intent
import android.content.Intent.ACTION_VIEW
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.webpageparsing.databinding.ActivityMainBinding
import com.example.webpageparsing.databinding.RowBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var adapter: MyAdapter
    val url ="https://news.daum.net"

    //네트워크 작업 -> 백그라운드에서 작업해야함
    //코루틴이나 volley라이브러리(https라이브러리) 둘 중 하나 사용하면 된다
    val scope= CoroutineScope(Dispatchers.IO)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initLayout()
    }

    private fun initLayout() {
        //화면 아래로 당기면 새로고침 하는 기능
        // -> implementation 'androidx.swiperefreshlayout:swiperefreshlayout:1.1.0' 추가
        //xml에 swipeLayout 생성하고 내부에 리사이클러 뷰 넣는다
        binding.swipe.setOnRefreshListener {
            //로딩중 표현 위젯이 화면에 보여짐
            binding.swipe.isRefreshing=true
            //다시 뉴스 불러온다
            getnews()
        }
        binding.recyclerView.layoutManager=
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)

        //리사이클러뷰에서 각 데이터 아래에 구분 선 넣는 작업 -> drawable에서 stroke로 선넣고 background로 설정할 필요 없음
        binding.recyclerView.addItemDecoration(DividerItemDecoration(this,LinearLayoutManager.VERTICAL))

        adapter= MyAdapter(ArrayList())
        adapter.itemClickListener =
            MyAdapter.OnItemClickListener{ position:Int ->
                val intent=Intent(ACTION_VIEW,Uri.parse(adapter.items[position].url))
                startActivity(intent)
            }
        binding.recyclerView.adapter=adapter

        //url로 부터 뉴스 정보 가져온다
        getnews()
    }

    private fun getnews() {
        scope.launch {
            adapter.items.clear()
            //url의 html문서를 가져와 doc에 저장
            val doc= Jsoup.connect(url).get()
            //Log.i("check",doc.toString())

            //원하는 태그들만 고른다 -> .select()함수에 태그 조건 넣는다
            val headlines=doc.select("ul.list_newsissue>li>div.item_issue>div.cont_thumb>strong.tit_g>a")
            for(news in headlines){
                //해당 태그가 감싸고 있는 정보 가져오기-> .text()
                adapter.items.add(MyData(news.text(),news.absUrl("href")))
            }

            //어뎁터에 들어간 데이터가 변경되었음으로 어뎁터에게 해당 사실 알려줘야 함
            //UI바꾸는 작업은 현재 코루틴(dispatchers.IO)에서 불가능하고 메인에서 작업해야함
            //withContext사용
            //dataSet바뀌었다는 사실 알려주면 바뀐 내용으로 UI업데이트
            withContext(Dispatchers.Main){
                adapter.notifyDataSetChanged()
                //로딩중 표현 위젯 화면에서 제거
                binding.swipe.isRefreshing=false
            }
        }

    }
}