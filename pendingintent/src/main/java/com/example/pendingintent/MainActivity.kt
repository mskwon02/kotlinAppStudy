package com.example.pendingintent

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.app.TimePickerDialog.OnTimeSetListener
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TimePicker
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.example.pendingintent.databinding.ActivityMainBinding
import java.util.*

//알림 기능 실행 안되면 앱 설정에서 알림 허용이 되어있는지 확인해보기
class MainActivity : AppCompatActivity(),OnTimeSetListener {
    private lateinit var binding:ActivityMainBinding
    var msg:String=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initLayout()
    }


    private fun initLayout() {
        binding.calendarView.setOnDateChangeListener { view, year, month, dayOfMonth ->
            //시스템에서 월은 0부터 시작하기 때문에 1 더해줘야 함
            msg="${year}년 ${month+1}월 ${dayOfMonth}일 "

            //캘린더 객체를 통해 현재 시간 정보 가져올 수 있다
            val cal=Calendar.getInstance()
            val hour=cal.get(Calendar.HOUR_OF_DAY)
            val minute=cal.get(Calendar.MINUTE)

            //액티비티가 OnTimeSetListener를 상속받고 있기 때문에 리스너가 들어가야 하는 매개변수 자리에 this로 이 액티비티를 넣을 수 있다
            val timepicker=TimePickerDialog(this,this, hour, minute, true)
            timepicker.show()
        }

    }

    //엑티비티 자체가 OnTimeSetListener를 상속 -> 타임피커 다이얼로그에서 시간 선택하고 OK 누르면 아래 함수 자동으로 호출됨
    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        //타임피커 다이얼로그에서 선택한 시간이 null이 아니면
        if(view!=null){
            //notification생성
            msg+="${hourOfDay}시 ${minute}분 "

            val timerTask=object:TimerTask(){
                @RequiresApi(Build.VERSION_CODES.O)
                override fun run() {
                    makeNotification()
                }
            }

            //스케쥴링 담당하는 타이머 객체(내가 생각하는 그 타이머 아님)
            val timer= Timer()
            //2초 후에 timerTask객체가 구현한 run()함수를 실행한다
            timer.schedule(timerTask,2000)
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()

        }

    }

    //NotificationChannel은 오레오 버전 이상부터 가능.
    //따라서 sdk버전 체크해서 따로 코드짜야 하는데 지금은 귀찮앙!
    //RequiresApi 어노테이션이 붙으면 메소드나 클래스가 특정 API 레벨 이상에서만 사용될 수 있다는 것을 나타냄
    @RequiresApi(Build.VERSION_CODES.O)
    private fun makeNotification() {

        //1st - notificationCHannel생성=> 알림 효과 설정
        val id="MyChannel"
        val name="TimeCheckChannel"
        val notificationChannel=NotificationChannel(id, name, NotificationManager.IMPORTANCE_DEFAULT)

        notificationChannel.enableVibration(true)
        notificationChannel.enableLights(true)
        notificationChannel.lightColor=Color.BLUE
        notificationChannel.lockscreenVisibility= Notification.VISIBILITY_PRIVATE

        //2nd - builder생성 => 알림 화면 설정
        val builder=NotificationCompat.Builder(this, id)
            .setSmallIcon(R.drawable.baseline_access_alarm_24)
            .setContentTitle("일정알람")
            .setContentText(msg)
            .setAutoCancel(true)

        val intent= Intent(this, MainActivity::class.java)
        intent.putExtra("time",msg)
        intent.flags=Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP

        val pendingIntent=PendingIntent.getActivity(this,1,intent,
        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
        builder.setContentIntent(pendingIntent)

        //위에서 builder에 설정한 내용으로 notification객체 생성
        val notification=builder.build()

        val manager=getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.createNotificationChannel(notificationChannel)
        manager.notify(10, notification)
    }
}