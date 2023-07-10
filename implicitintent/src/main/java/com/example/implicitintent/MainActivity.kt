package com.example.implicitintent

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import com.example.implicitintent.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        checkPermissions()
        initLayout()
    }

    private fun initLayout() {
        binding.apply {
            btnCall.setOnClickListener {
                callAction()
            }
            btnMsg.setOnClickListener {
                val message=Uri.parse("sms:010-1234-1234")
                val messageIntent= Intent(Intent.ACTION_SENDTO,message)
                messageIntent.putExtra("sms_body","빨리 다음거 하자")
                startActivity(messageIntent)
            }
            btnNaver.setOnClickListener {
                val web=Uri.parse("http://www.naver.com")
                val webIntent= Intent(Intent.ACTION_VIEW,web)
                startActivity(webIntent)
            }
            btnMap.setOnClickListener {
                val location=Uri.parse("geo:37.543684,127.077130?z=16")
                val mapIntent= Intent(Intent.ACTION_VIEW,location)
                startActivity(mapIntent)
            }
            btnCamera.setOnClickListener {
                cameraAction()
            }
        }
    }


    val permissionsArray=arrayOf(android.Manifest.permission.CALL_PHONE, android.Manifest.permission.CAMERA)

    //단일 퍼미션 요청하는 객체 -> launch()시키면 권한 요청 페이지 띄운다
    val permissionLauncher=registerForActivityResult(ActivityResultContracts.RequestPermission()){
        if(it){ //사용자가 요청 승인 하면
            callAction()
        }else{  //사용자가 요청 거절하면
            Toast.makeText(this, "권한요청이 거부되었습니다", Toast.LENGTH_SHORT).show()
        }
    }

    //복수 퍼미션 요청하는 객체 -> launch()시키면 권한 요청 페이지 띄운다
    val multiPermissionLauncher=registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()){
        //it = 퍼미션과 퍼미션 허용 결과가 쌍으로 들어있는 맵 객체
        //it의 모든 요소(맵에서 한 쌍씩) 돌아다니며 {}내용 수행. 만약 모두 true면 resultPermission이 true가 된다
        val resultPermission=it.all{ map ->
            //람다 식에서는 마지막 표현식이 return값이 된다
            map.value
        }
        if(!resultPermission){
            //앱 종료
            finish()
        }
    }

    fun checkPermissions(){
        when{
            //모든 권한(전화, 카메라) 승인 난 경우
            (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.CALL_PHONE)==PackageManager.PERMISSION_GRANTED)&&
                    (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA)==PackageManager.PERMISSION_GRANTED)-> {
                Toast.makeText(this,"모든권한 승인 됨",Toast.LENGTH_SHORT).show()
            }
            //사용자가 2권한 중 하나라도 명시적으로 거절한 경우
            ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.CALL_PHONE) ||
                    ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.CAMERA) -> {
                        permissionCheckAlertDlg()

            }
            //앱 실행이 처음인 경우
            else ->{
                //퍼미션 허락받는 객체 실행 -> 권한 요청 페이지 뜬다
                multiPermissionLauncher.launch(permissionsArray)
            }

        }
    }

    //다이얼로그 띄우는 함수
    private fun permissionCheckAlertDlg() {
        val builder=AlertDialog.Builder(this)
        builder.setMessage("반드시 모든 권한이 승인되어야 합니다")
            .setTitle("권한 체크")
            .setPositiveButton("OK"){
                _,_ -> multiPermissionLauncher.launch(permissionsArray)
            }
            .setNegativeButton("Cancel"){
                //dismiss()는 다이얼로그 창 닫는 함수
                dlg,_ -> dlg.dismiss()
            }

    }


    //모든 권한이 허락되지 않으면 앱이 아예 종료되도록 설계했기 때문에 여기서 추가로 권한 체크할 필요 없다
    private fun callAction(){
        val number=Uri.parse("tel:010-1234-1234")
        val callIntent= Intent(Intent.ACTION_CALL,number)
        startActivity(callIntent)
    }
    
    private fun cameraAction() {
        val intent=Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivity(intent)
    }


}