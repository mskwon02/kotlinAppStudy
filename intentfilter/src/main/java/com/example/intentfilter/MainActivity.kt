package com.example.intentfilter

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.intentfilter.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    private lateinit var adapter:AppAdapater
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initRecyclerView()
    }

    @Suppress("DEPRECATION")
    private fun initRecyclerView() {
        adapter=AppAdapater(ArrayList<MyData>())

        val intent= Intent(Intent.ACTION_MAIN)
        intent.addCategory(Intent.CATEGORY_LAUNCHER)

        val appList=
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            packageManager.queryIntentActivities(intent, PackageManager.ResolveInfoFlags.of(0))
        } else {
            packageManager.queryIntentActivities(intent, 0)

            }

        if(appList.size>0){
            for(appinfo in appList){

                var appLabel=appinfo.loadLabel(packageManager).toString()
                var appClass=appinfo.activityInfo.name
                var appPackname=appinfo.activityInfo.packageName
                var appIcon=appinfo.loadIcon(packageManager)

                adapter.items.add(MyData(appLabel,appClass,appPackname,appIcon))
            }
        }

        adapter.itemClickListener=object:AppAdapater.OnItemClickListener{
            override fun OnItemClick(data: MyData) {
                val intent=packageManager.getLaunchIntentForPackage(data.appPackName)
                startActivity(intent)

            }
        }
        binding.recyclerView.adapter=adapter
    }
}