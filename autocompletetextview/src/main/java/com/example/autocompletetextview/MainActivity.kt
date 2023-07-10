package com.example.autocompletetextview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.ArrayAdapter
import android.widget.MultiAutoCompleteTextView
import android.widget.Toast
import com.example.autocompletetextview.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    private lateinit var countries:MutableList<String>

    //자동완성 EditTextView에는 어댑터로 자동완성으로 뜰 데이터들 연결해줘야 한다.
    //그걸 위한 어댑터(String타입의 Array를 연결해주는 어댑터인 ArrayAdapter) 생성
    private lateinit var adapter:ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initLayout()

    }

    private fun initLayout() {
        //res/values/Strings.xml에 정의해 놓은 string-array 배열(name:countries_array) 가져오기
        countries=resources.getStringArray(R.array.countries_array).toMutableList()

        //ArrayAdapter객체를 만들 때 필요한 인자는 1)context, 2)각 아이템의 레이아웃(여기서는 android에 이미 정의되어있는 layout중 하나 씀) 3)실제 데이터
        adapter= ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line,countries)

        //뷰 객체에 어뎁터 달기
        binding.autoCompleteTextView.setAdapter(adapter)


        binding.autoCompleteTextView.setOnItemClickListener { parent, view, position, id ->
            val item=parent.getItemAtPosition(position).toString()
            Toast.makeText(this, item, Toast.LENGTH_SHORT).show()
        }

        //multi에는 선택 요소들의 구분자로 comma를 만들어주는 토크나이저를 달아준다
        binding.multiAutoCompleteTextView.setTokenizer(MultiAutoCompleteTextView.CommaTokenizer())
        binding.multiAutoCompleteTextView.setAdapter(adapter)

        //추가할 나라 입력받는 editTextView -> 뭔가를 입력해야만 추가 버튼 활성화 되도록
        binding.editTextTextAdd.addTextChangedListener(object:TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                //TODO("Not yet implemented")
            }

            override fun afterTextChanged(s: Editable?) {
                val str=s.toString()
                binding.button.isEnabled=str.isNotEmpty()
                binding.button.setOnClickListener {
                    adapter.add(str)
                    adapter.notifyDataSetChanged()
                    it.isEnabled=false
                    binding.editTextTextAdd.text.clear()

                }
            }

        })

    }
}