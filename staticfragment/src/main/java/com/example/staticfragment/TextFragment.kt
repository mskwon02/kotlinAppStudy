
package com.example.staticfragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.example.staticfragment.databinding.FragmentTextBinding

class TextFragment : Fragment() {
    private var binding:FragmentTextBinding?=null

    //자신이 부착되어있는 액티비티에 있는 뷰모델 가져온다
    val model:MyViewModel by activityViewModels()

    val textList= arrayListOf<String>("ImageData1",
    "ImageData2","ImageData3")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentTextBinding.inflate(layoutInflater,container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //세로모드에서는 intent로 선택버튼 정보 받고
        //가로모드에서는 viewmodel을 통해서 선택버튼 정보 받는다

        //만약 세로모드면 requireActivity()를 통해 얻는 엑티비티는 secondActivity이고
        //만약 가로모드면 requireActivity()를 통해 얻는 엑티비티는 MainActivity다
        val i = requireActivity().intent
        //가로모드이면 해당 키값에 맞는 innent Extra정보 없음 -> default값 -1들어감
        var imgNum=i.getIntExtra("imgNum",-1)

        //가로모드가 아니면 = 세로모드이면
        if(imgNum!=-1){
            binding?.textView?.text=textList[imgNum]

        }else{//가로모드이면
            model.selectedNum.observe(viewLifecycleOwner, Observer {
                //it는 liveData로 변화가 관측되고 있는 변수
                binding?.textView?.text=textList[it]
            })

        }
    }



    override fun onDestroyView() {
        super.onDestroyView()
        binding=null
    }

}