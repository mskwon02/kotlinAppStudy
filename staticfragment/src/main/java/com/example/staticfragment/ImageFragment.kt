package com.example.staticfragment

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.staticfragment.databinding.FragmentImageBinding


class ImageFragment : Fragment() {

    var binding:FragmentImageBinding?=null

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
                //만약 세로모드이면 이미지 클릭시 다른 엑티비티창 띄워야 한다
                //세로방향모드인 지 체크
                if(resources.configuration.orientation ==
                        Configuration.ORIENTATION_PORTRAIT){

                    //여기서 activity란 프레그먼트가 현재 부착되어 있는 엑티비티 -> getActivity()에 의해 자동으로 반환된 객체
                    val intent= Intent(activity,SecondActivity::class.java)
                    intent.putExtra("imgNum",model.selectedNum.value)
                    startActivity(intent)
                }

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