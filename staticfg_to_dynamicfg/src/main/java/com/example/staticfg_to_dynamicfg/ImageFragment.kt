package com.example.staticfg_to_dynamicfg

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.staticfg_to_dynamicfg.databinding.FragmentImageBinding


class ImageFragment : Fragment() {

    var binding: FragmentImageBinding?=null

    //자신이 붙어있는 액티비티에 있는 뷰 모델을 가져온다는 뜻
    val model:MyViewModel by activityViewModels()

    val imgList= arrayListOf<Int>(R.drawable.img32, R.drawable.img33,R.drawable.img34)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //매개변수 뒤에 2개는 원래 return에 있었던 마지막 매개변수 2개 그대로 긁어오면 됨
        binding= FragmentImageBinding.inflate(layoutInflater,container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding!!.apply {
            ivWindoIimage.setOnClickListener {

                //이미지 클릭하면 동적으로 다른 프레그먼트(itemFragment) 갈아껴지도록
                //supportFragmentManager는 엑티비티 객체만 사용가능. 지금은 프레그먼트다
                // -> requireActivity()로 액티비티 객체 얻은 후 supportFragmentManager사용
                val transaction=requireActivity().supportFragmentManager.beginTransaction()
                val imgFragment=TextFragment()
                //현재 프레그먼트 백스택에 넣어서 프레그먼트 교체된 후 뒤로가기 버튼 누르면 이 프레그먼트 나오도록
                transaction.addToBackStack(null)
                transaction.replace(R.id.frameLayout,imgFragment)
                transaction.commit()

            }
            rgSelectImg.setOnCheckedChangeListener { group, checkedId ->
                when(checkedId){
                    R.id.rb1 -> {
                        model.setLiveData(0)

                    }
                    R.id.rb2 -> {
                        model.setLiveData(1)

                    }
                    R.id.rb3 -> {
                        model.setLiveData(2)

                    }
                }
                //선택 버튼에 따른 이미지 변화
                ivWindoIimage.setImageResource(imgList[model.selectedNum.value!!])
            }
        }
    }

    //가비지컬렉션 이뤄질 수 있게 프레그먼트가 종료될 때 binding의 값을 null로 바꿔야함
    override fun onDestroyView() {
        super.onDestroyView()
        binding=null
    }
}