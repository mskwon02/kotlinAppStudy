package com.example.recyclerview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.recyclerview.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    private lateinit var items:List<MyData>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        items= listOf<MyData>(
            MyData("item1",10),
            MyData("item2",8),
            MyData("item3",6),
            MyData("item4",4),
            MyData("item5",18),
            MyData("item6",12)
            )
        setContentView(binding.root)
        initRecyclerView()
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        //만들어 놓은 메뉴 xml을 시스템메뉴(앱 바에 달려있는 메뉴 버튼)에 연결
        menuInflater.inflate(R.menu.menu1, menu)
        return true
    }

    //시스템메뉴를 선택하면 호출되는 함수
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        Toast.makeText(this,"${item}",Toast.LENGTH_SHORT).show()

        when(item.itemId){
            R.id.menuitem1 -> binding.recyclerView.layoutManager=LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
            R.id.menuitem2 -> binding.recyclerView.layoutManager=GridLayoutManager(this, 3)
            R.id.menuitem3 -> binding.recyclerView.layoutManager=StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initRecyclerView() {
        //레이아웃 메니저(항목들 배치 방법 지정)
        binding.recyclerView.layoutManager=LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        //어뎁터 달기
        val adapter=MyDataAdapter(items)
        binding.recyclerView.adapter=adapter


    }
}