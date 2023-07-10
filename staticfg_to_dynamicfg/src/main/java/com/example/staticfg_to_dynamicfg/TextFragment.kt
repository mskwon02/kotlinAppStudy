
package com.example.staticfg_to_dynamicfg

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.example.staticfg_to_dynamicfg.databinding.FragmentTextBinding


class TextFragment : Fragment() {
    private var binding: FragmentTextBinding?=null

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


        model.selectedNum.observe(viewLifecycleOwner, Observer {
            //it는 liveData로 변화가 관측되고 있는 변수
            binding?.textView?.text=textList[it]
        })
    }



    override fun onDestroyView() {
        super.onDestroyView()
        binding=null
    }

}