package com.example.customcomponent

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageView
import kotlin.math.PI
import kotlin.math.atan2


//나만의 뷰 만들기
//여기서는 ImageView를 상속해서 만들었다
//Activity도 그냥 Activity가 아닌 AppCompatActivity()를 상속받았는데 해당 클래스를
//상속받은 엑티비티 위에 뜨는 뷰들도 모두 그냥 View가 아닌 AppCompat~View()를 상속받아야 함
class VolumeControlView(context: Context, attrs: AttributeSet?) :
    AppCompatImageView(context, attrs) {
    var mx=0.0f
    var my=0.0f
    var tx=0.0f
    var ty=0.0f
    var angle=180.0f

    var listener:VolumeListener?=null

    fun getAngle(x1:Float, y1:Float):Float{
        mx=x1-(width/2.0f)
        my=(height/2.0f)-y1
        return (atan2(mx,my)*180.0f/ PI).toFloat()
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if(event!=null){
            tx=event.getX(0)
            ty=event.getY(0)
            angle = getAngle(tx,ty)

            //"onDraw함수를 다시 실행해줘"라는 메시지를 발생시키는 함수
            invalidate()
            if(listener!=null){
                listener?.onChanged(angle)
            }
            return true
        }
        return false
    }

    override fun onDraw(canvas: Canvas?) {
        canvas?.rotate(angle,width/2.0f,height/2.0f)
        super.onDraw(canvas)
    }

    fun interface VolumeListener{
        fun onChanged(angle:Float):Unit
    }

    fun setVolumeListener(listener:VolumeListener){
        this.listener=listener
    }
}